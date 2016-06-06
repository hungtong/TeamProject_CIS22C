public class Edge<E> implements Comparable< Edge<E> >
{
	 Vertex<E> source, dest;
	 double cost;
	
	 public Edge( Vertex<E> src, Vertex<E> dst, Double cst)
	 {
	    source = src;
	    dest = dst;
	    cost = cst;
	 }
	 
	 public Edge( Vertex<E> src, Vertex<E> dst, Integer cst)
	 {
	    this (src, dst, cst.doubleValue());
	 }
	 
	 public Edge(Vertex<E> src, Vertex<E> dst) {
		 this (src, dst, 0);
	 }
	 
	 public Edge()
	 {
	    this(null, null, 1.);
	 }
	 
	 public String toString(){ return "Edge: "+source.getData() + " to " + dest.getData()
			 + ", distance: " + cost;
	 }
	 
	 public int compareTo( Edge<E> rhs ) 
	 {
	    return (cost < rhs.cost? -1 : cost > rhs.cost? 1 : 0);
	 }
}