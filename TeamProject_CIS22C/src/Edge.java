/**
 * Edge is the line to connect any two vertices. I WRITE IT BY MYSELF
 */
public class Edge<E> {
	public Vertex<E> source;	// from vertex
	public Vertex<E> dest;		// to another vertex
	public double cost;			// cost to travel between two vertices
}
