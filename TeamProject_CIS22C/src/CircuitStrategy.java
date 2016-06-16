/**
   Implemented Strategy desgin pattern, algorithm is determined at runtime
   @author Hung Tong
*/
public interface CircuitStrategy<E> {
	void implement(E firstNode, E secondNode);
}
