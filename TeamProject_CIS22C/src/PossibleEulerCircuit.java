import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class PossibleEulerCircuit<E> extends Graph<E> {	
	
	private Visitor<E> visitor;
	private Boolean isEulerCiruit;
	
	private LinkedStack<Pair<E, E>> removedEdges;
	
	public PossibleEulerCircuit(Visitor<E> visitor) {
		this.visitor = visitor;
		isEulerCiruit = null;
		removedEdges = new LinkedStack<>();
	}
	
	public void addEdge(E source, E dest) {
	    super.addEdge(source, dest, 0);
    }
	
	/*
		Check if the graph is connected or not
		@return true if ALL non-isolated vertices construct only one graph
	*/
	public boolean isConnected() {
		// the first step is actually marking unvisited all vertices. However, in our Graph class, when calling
		// Depth First Traversal, it automatically do that for us
		
		// Find a non-zero-degree vertex
		Iterator<Map.Entry<E, Vertex<E>>> iterator = vertexSet.entrySet().iterator();
		while (iterator.hasNext()) {
			Vertex<E> currentVertex = iterator.next().getValue();
			if (currentVertex.adjList.size() > 0)
				break;
		}
		
		// The objective is to check if all non-isolated vertices connected, if there was no non-isolated vertex, 
		// every vertex would be considered as connected
		if (iterator.hasNext() == false)
			return true;
		
		// Choose a random vertex Visit all vertices using Depth First Traversal
		Random randomGenerator = new Random();
		Object[] vertices = vertexSet.values().toArray();
		@SuppressWarnings("unchecked")
		Vertex<E> randomVertex = ( Vertex<E> ) vertices[randomGenerator.nextInt(vertices.length)];
		depthFirstTraversal(randomVertex.data, visitor);
		
		// If there was a non-isolated vertex unvisited, the graph is not connected at all
		// We only one graph with our set of vertices
		iterator = vertexSet.entrySet().iterator();	// reset iterator
		while (iterator.hasNext()) {
			Vertex<E> currentVertex = iterator.next().getValue();
			if (currentVertex.adjList.size() > 0 && !currentVertex.isVisited())
				return false;
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
			if (currentVertex.adjList.size() % 2 != 0)
				return false;
		}
		
		return true;
	}

	/*
		Check if the graph is a Euler Circuit
		@return true if the graph is connected and has all even degree vertices, meaning from any vertex, we can always form a Euler Circuit 
	*/
	public boolean isEulerCircuit() {
		if (isEulerCiruit == null) {
			isEulerCiruit = new Boolean(hasAllEvenDegreeVertices() && isConnected());
		}
		return isEulerCiruit;
	}

	/*
		Implement Hierholzer's algorithm to find a Euler Circuit with starting vertex is given. 
		Notice that we can start from any vertex
		@param startVertex - given starting vertex
	*/
	public void findEulerCircuit(Vertex<E> startVertex) {
		if (isEulerCiruit) {

			// This stack is used to form a simple cycle (not necessarily the final answer)
			LinkedStack<Vertex<E>> simpleCycle = new LinkedStack<>();	

			// This queue is used to store the Euler Circuit that we want (the final answer)
			// Notice that if we consider a cycle as a clock. Either going clockwise or counterclockwise,
			// we get a valid answer at the end
			LinkedQueue<Vertex<E>> eulerCircuit = new LinkedQueue<>();
				
			// A prototype of vertexSet. By removing an element in a vertex's adjacency list, we can mark
			// an edge unvisited. For instance, A has edges to B,C,E and E has edges to A,B. When I visited A - E
			// I have to remove E in A, and A in E, resulting in A has edges to B,C and E has edges to B
			@SuppressWarnings("unchecked")
			HashMap<E, Vertex<E>> vertices = (HashMap<E, Vertex<E>>) vertexSet.clone();

			simpleCycle.push(startVertex);

			while (!simpleCycle.isEmpty()) {
				Vertex<E> currentVertex = simpleCycle.peek();
				vertices.get(currentVertex.data);
			}
		}
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
		Random randomGenerator = new Random();
		return ( Vertex<E> ) adjacentVertices[randomGenerator.nextInt(adjacentVertices.length)];
	}
	
	protected void undoRemoval() {
    	undoRemoval(1);
    }

    protected void undoRemoval(int times) {
	   if (times > 0 && removedEdges.size() > 0) {
		   Pair<E,E> currentEdge;

		   if (times >= removedEdges.size())
			   times = removedEdges.size();

		   while (times > 0) {
			   currentEdge = removedEdges.pop();
			   addEdge(currentEdge.first, currentEdge.second);
			   --times;
		   }	   	   
	   }
   } 
    
   @Override
   public boolean remove(E start, E end) {
	    Vertex<E> startVertex = vertexSet.get(start);
	    boolean removedOK = false;

	    if( startVertex != null ) {
		    Pair<Vertex<E>, Double> endPair = startVertex.adjList.remove(end);
		    removedOK = endPair != null;
	    }
	   
	    // Undirected Graph so we remove the other way as well
		Vertex<E> endVertex = vertexSet.get(end);
		if( endVertex != null ) {
			Pair<Vertex<E>, Double> startPair = endVertex.adjList.remove(start);
			removedOK = startPair != null;
		}
		
		if (removedOK) 
			removedEdges.push(new Pair<E, E>(start, end));
		
	    return removedOK;
   }
}
