import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

//--- Graph class ------------------------------------------------------
public class Graph<E>
{
   // the graph data is all here --------------------------
   protected HashMap<E, Vertex<E> > vertexSet;
   
   private LinkedStack<Pair<E, E>> removedEdges;

   // public graph methods --------------------------------
   public Graph ()
   {
      vertexSet = new HashMap<E, Vertex<E> >();
      removedEdges = new LinkedStack<>();
   }

   public void addEdge(E source, E dest, double cost)
   {
      Vertex<E> src, dst;

      // put both source and dest into vertex list(s) if not already there
      src = addToVertexSet(source);
      dst = addToVertexSet(dest);

      // add dest to source's adjacency list
      src.addToAdjList(dst, cost);
      dst.addToAdjList(src, cost); // ADD THIS IF UNDIRECTED GRAPH
   }

   public void addEdge(E source, E dest, int cost)
   {
      addEdge(source, dest, (double)cost);
   }
   
   public void addEdge(E source, E dest) {
	   addEdge(source, dest, 0);
   }

   // adds vertex with x in it, and always returns ref to it
   public Vertex<E> addToVertexSet(E x)
   {
      Vertex<E> retVal = null;
      Vertex<E> foundVertex;

      // find if Vertex already in the list:
      foundVertex = vertexSet.get(x);

      if ( foundVertex != null ) // found it, so return it
      {
         return foundVertex;
      }

      // the vertex not there, so create one
      retVal = new Vertex<E>(x);
      vertexSet.put(x, retVal);

      return retVal;   // should never happen
   }

   // remove edge, not vertices, all vertices will remain in the graph even if we remove everything
   public boolean remove(E start, E end)	 
   {
	   Vertex<E> startVertex = vertexSet.get(start);
	   boolean removedOK = false;

	   if( startVertex != null )
	   {
		   Pair<Vertex<E>, Double> endPair = startVertex.adjList.remove(end);
		   removedOK = endPair != null;
	   }
	   
	    // Undirected Graph so we remove the other way as well
		Vertex<E> endVertex = vertexSet.get(end);
		if( endVertex != null )
		{
			Pair<Vertex<E>, Double> startPair = endVertex.adjList.remove(start);
			removedOK = startPair != null;
		}
		
		if (removedOK) 
			removedEdges.push(new Pair<E, E>(start, end));
		
	    return removedOK;
   }

   public void showAdjTable()
   {
      Iterator<Entry<E, Vertex<E>>> iter;

      System.out.println( "------------------------ ");
      iter = vertexSet.entrySet().iterator();
      while( iter.hasNext() )
      {
         (iter.next().getValue()).showAdjList();
      }
      System.out.println();
   }


   public void clear()
   {
      vertexSet.clear();
   }

   // reset all vertices to unvisited
   public void unvisitVertices()
   {
	      Iterator<Entry<E, Vertex<E>>> iter;

	      iter = vertexSet.entrySet().iterator();
	      while( iter.hasNext() )
	      {
	         iter.next().getValue().unvisit();
	      }
   }

   /** Breadth-first traversal from the parameter startElement*/
   public void breadthFirstTraversal(E startElement, Visitor<E> visitor)
   {
	   unvisitVertices();

	   Vertex<E> startVertex = vertexSet.get(startElement);
	   breadthFirstTraversalHelper( startVertex, visitor );
   }

   /** Depth-first traversal from the parameter startElement */
   public void depthFirstTraversal(E startElement, Visitor<E> visitor)
   {
	   unvisitVertices();

	   Vertex<E> startVertex = vertexSet.get(startElement);
	   depthFirstTraversalHelper( startVertex, visitor );
   }

   protected void breadthFirstTraversalHelper(Vertex<E> startVertex,
		   Visitor<E> visitor)
   {
	   LinkedQueue<Vertex<E>> vertexQueue = new LinkedQueue<>();
	   E startData = startVertex.getData();

	   startVertex.visit();
	   visitor.visit(startData);
	   vertexQueue.enqueue(startVertex);
	   while( !vertexQueue.isEmpty() )
	   {
		   Vertex<E> nextVertex = vertexQueue.dequeue();
		   Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> iter =
				   nextVertex.iterator(); // iterate adjacency list

		   while( iter.hasNext() )
		   {
			   Entry<E, Pair<Vertex<E>, Double>> nextEntry = iter.next();
			   Vertex<E> neighborVertex = nextEntry.getValue().first;
			   if( !neighborVertex.isVisited() )
			   {
				   vertexQueue.enqueue(neighborVertex);
				   neighborVertex.visit();
				   visitor.visit(neighborVertex.getData());
			   }
		   }
	   }
   } // end breadthFirstTraversalHelper

   public void depthFirstTraversalHelper(Vertex<E> startVertex, Visitor<E> visitor)
   {
	    // YOU COMPLETE THIS (USE THE ALGORITHM GIVEN FOR LESSON 11 EXERCISE)
	   startVertex.visit();
	   Vertex<E> nextVertex = startVertex;
	   
	   Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> iter = nextVertex.iterator(); // iterate adjacency list

	   while( iter.hasNext() )
	   {
		   Entry<E, Pair<Vertex<E>, Double>> nextEntry = iter.next();
		   Vertex<E> neighborVertex = nextEntry.getValue().first;
		   if( !neighborVertex.isVisited() ) {
			   depthFirstTraversalHelper(neighborVertex, visitor);
			   neighborVertex.visit();
		   }
	   }
   }


   // WRITE THE INSTANCE METHOD HERE TO
   //         WRITE THE GRAPH's vertices and its
   //         adjacency list TO A TEXT FILE (SUGGEST TO PASS AN
   //        ALREADY OPEN PrintWriter TO THIS) !
   public void saveAsTextFile(PrintWriter writer) throws IOException {  
	   BufferedWriter bufferedWriter = new BufferedWriter(writer);
	   Iterator<Entry<E, Vertex<E>>> iter = vertexSet.entrySet().iterator();
	   
       while( iter.hasNext() ) 
    	   saveAdjacencyList(iter.next().getValue(), bufferedWriter);
       
	   bufferedWriter.close();
	
   }
   
   private void saveAdjacencyList(Vertex<E> startVertex, BufferedWriter bufferedWriter) {
	   Iterator<Entry<E, Pair<Vertex<E>, Double>>> iter ;
	   Entry<E, Pair<Vertex<E>, Double>> entry;
	   Pair<Vertex<E>, Double> pair;
	   
	   try {
		   bufferedWriter.newLine();
		   bufferedWriter.write("Adj List for " + startVertex.getData() + ": ");
		   iter = startVertex.adjList.entrySet().iterator();
		   while( iter.hasNext() ) {
		         entry = iter.next();
		         pair = entry.getValue();
		         bufferedWriter.write(
		        	 pair.first.data + "("
		             + String.format("%3.1f", pair.second)
		             + ") " 
		         );
		   }
		   bufferedWriter.write("\n");;
	   }
	   catch (IOException ex) {
		   System.out.println("Failed to save graph as text file");
	   }
   }

   public void undoRemoval() {
	   undoRemoval(1);
   }

   public void undoRemoval(int times) {
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

}