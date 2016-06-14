import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class PossibleEulerCircuit<E> extends Graph<E> {	
	
	private SimpleCircuit<Vertex<E>> eulerCircuit;
	
	public PossibleEulerCircuit() {
		removedEdges = new LinkedStack<>();
		eulerCircuit = new SimpleCircuit<>();
	}
	
	/*
		Check if the graph is connected or not
		@return true if ALL non-isolated vertices construct only one graph
	*/
	public boolean isConnected() {
		if (vertexSet.size() == 0) {
			System.out.println("\tThis Graph has no vertices");
			return false;
		}
		
		// the first step is actually marking unvisited all vertices. However, in our Graph class, when calling
		// Depth First Traversal, it automatically do that for us
		
		// Find a non-zero-degree vertex
		Iterator<Map.Entry<E, Vertex<E>>> iterator = vertexSet.entrySet().iterator();
		int adjacencyListSize = 0;
		while (iterator.hasNext()) {
			Vertex<E> currentVertex = iterator.next().getValue();
			adjacencyListSize = currentVertex.adjList.size();
			if (adjacencyListSize > 0)
				break;
		}
		
		// The objective is to check if all non-isolated vertices connected, if there was no non-isolated vertex, 
		// every vertex would be considered as connected
		if (adjacencyListSize == 0) {
			System.out.println("\tThis Graph is Euler Circuit because it has no non-isolated vertex");
			return true;
		}
		
		// Choose a random vertex Visit all vertices using Depth First Traversal
		Random randomGenerator = new Random();
		Object[] vertices = vertexSet.values().toArray();
		@SuppressWarnings("unchecked")
		Vertex<E> startVertex = ( Vertex<E> ) vertices[randomGenerator.nextInt(vertices.length)];
		depthFirstTraversal(startVertex.data);
		
		// If there was a non-isolated vertex unvisited, the graph is not connected at all
		// We only one graph with our set of vertices
		iterator = vertexSet.entrySet().iterator();	// reset iterator
		while (iterator.hasNext()) {
			Vertex<E> currentVertex = iterator.next().getValue();
			if (currentVertex.adjList.size() > 0 && !currentVertex.isVisited()) {
				System.out.println(
					"\tThe Graph cannot be Euler Circuit because it is not connected!\n\t" +
					"A bridge is necessary at " + currentVertex
				);
				return false;
			}
		}
		
		return true;
	}

	/*
		Check if all non-isolated vertices in the graph have even degree. Degree means the number of edges that enter a
		particular vertex
		@return true if ALL non-isolated vertices have even degree 
	*/ 
	public boolean hasAllEvenDegreeVertices() {
		Iterator<Map.Entry<E, Vertex<E>>> iterator = vertexSet.entrySet().iterator();
		while (iterator.hasNext()) {
			Vertex<E> currentVertex = iterator.next().getValue();
			if (currentVertex.adjList.size() % 2 != 0) {
				System.out.println(
						"\tThe Graph cannot be Euler Circuit because\n\t\"" +
						currentVertex + "\" has a degree of " + currentVertex.adjList.size() + " (odd number)"
					);
				return false;
			}
		}
		
		return true;
	}

	/*
		Check if the graph is a Euler Circuit
		@return true if the graph is connected and has all even degree vertices, meaning from any vertex, we can always form a Euler Circuit 
	*/
	public boolean isEulerCircuit() {
		return hasAllEvenDegreeVertices() && isConnected();
	}

	/*
		Implement Hierholzer's algorithm to find a Euler Circuit with starting vertex is given. 
		Notice that we can start from any vertex
		@param startVertex - given starting vertex
	*/
	public <T> void findEulerCircuit(Vertex<E> startVertex) {
		LinkedStack<Vertex<E>> simpleCycle = new LinkedStack<>();
		
		simpleCycle.push(startVertex);
		Vertex<E> currentVertex = startVertex;
		while (!simpleCycle.isEmpty()) {
			Vertex<E> nextVertex = getNextVertex(currentVertex);
			if (currentVertex == nextVertex) {
				eulerCircuit.add(currentVertex);
				simpleCycle.pop();
			}
			else {
				simpleCycle.push(nextVertex);
				currentVertex.adjList.remove(nextVertex.getData());
				nextVertex.adjList.remove(currentVertex.getData());
			}
			currentVertex = simpleCycle.peek();
		}
					
		displayEulerCircuit(startVertex);
	}
	
	private void displayEulerCircuit(Vertex<E> startVertex) {
		eulerCircuit.traverseCircuit(new CircuitStrategy<Vertex<E>>() {

			@Override
			public void implement(Vertex<E> firstNode, Vertex<E> secondNode) {
				addEdge(firstNode.getData(), secondNode.getData(), 0);				
			}
		});
		
		System.out.println("\n\t-------------------------------------------------------------------------------");
		System.out.println("\tONE EULER CIRCUIT STARTING AT \"" + startVertex.data + "\" IS");
		eulerCircuit.displayForward();
		System.out.println("\n\n\tOr");
		eulerCircuit.displayBackward();
	}
		
	/*
		Abitrarily choose a vertex in the adjacency list of a vertex
		Notice that we don't mark visited edge here, we will do it while forming simple cycle 
		We won't have to worry about avoiding visited vertices, visited edge was already removed
		@param currentVertex - given vertex 
		@return a random vertex in the adjacency list of given vertex
	*/
	@SuppressWarnings("unchecked")
	private Vertex<E> getNextVertex(Vertex<E> currentVertex) {
		Object[] adjacentVertices = currentVertex.adjList.values().toArray();
		if (adjacentVertices.length <= 0)
			return currentVertex;
		else {
			Random randomGenerator = new Random();
			int randomIndex = randomGenerator.nextInt(adjacentVertices.length);
			Pair<Vertex<E>, Double> pair = ( Pair<Vertex<E>, Double> ) adjacentVertices[randomIndex];
			return pair.first;
		}
	}
	
	

   /** Depth-first traversal from the parameter startElement */
   private void depthFirstTraversal(E startElement) {
   		unvisitVertices();

   		Vertex<E> startVertex = vertexSet.get(startElement);
   		depthFirstTraversalHelper(startVertex);
   }

   private void depthFirstTraversalHelper(Vertex<E> startVertex) {
	   startVertex.visit();
	   Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> iter = startVertex.iterator(); // iterate adjacency list
 	   
	   while( iter.hasNext() ) {
		   Entry<E, Pair<Vertex<E>, Double>> nextEntry = iter.next();
	   	   Vertex<E> neighborVertex = nextEntry.getValue().first;
	   	   if( !neighborVertex.isVisited() ) {
	   		   depthFirstTraversalHelper(neighborVertex);
	   	   }
	   }
   }
   
   @Override
   public void showAdjTable() {
	   Iterator<Entry<E, Vertex<E>>> vertexIterator;
	
	   vertexIterator = vertexSet.entrySet().iterator();
	   while( vertexIterator.hasNext() ) {
		   Vertex<E> currentVertex = vertexIterator.next().getValue();
		   
		   Iterator<Entry<E, Pair<Vertex<E>, Double>>> adjacencyListIterator ;
		   Entry<E, Pair<Vertex<E>, Double>> entry;
		   Pair<Vertex<E>, Double> pair;

		   System.out.print("\t+ Adjacency List for \"" + currentVertex.getData() + "\":\n\t\t");
		   adjacencyListIterator = currentVertex.adjList.entrySet().iterator();
		   while( adjacencyListIterator.hasNext() ) {
		       entry = adjacencyListIterator.next();
		       pair = entry.getValue();
		       System.out.print( pair.first.data);
		       if (adjacencyListIterator.hasNext())
		    	   System.out.print("  ->  ");
		   }
		   System.out.println();
	   }
	   System.out.println();
   }

}
