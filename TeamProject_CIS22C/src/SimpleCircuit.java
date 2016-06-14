public class SimpleCircuit<T> extends LList2<T> {
		
	public void traverseCircuit(CircuitStrategy<T> strategy) {
	    Node2 currentNode;
	    currentNode = firstDummyNode.getNextNode(); 
	    while( currentNode != lastDummyNode ) {
		    Node2 nextNode = currentNode.getNextNode();
		    if (nextNode != lastDummyNode) 
		  	    strategy.implement(currentNode.getData(), nextNode.getData());
		    currentNode = nextNode;
	    }
	}
	
	public void displayForward() {   
	   Node2 currentNode;
	   currentNode = firstDummyNode.getNextNode(); 
	   
	   int lineBreakSignal = 0;
	   while( currentNode != lastDummyNode ) {
		   if (lineBreakSignal % 5 == 0) 
			   System.out.print("\n\t");
		   
		   lineBreakSignal++;
		  
		   System.out.print(currentNode.getData());
		   Node2 nextNode = currentNode.getNextNode();
		   
		   if (nextNode != lastDummyNode) 
			   System.out.print(" -> ");
		  
		   currentNode = nextNode;
	   }
	} // end display
	   
	public void displayBackward() {
		Node2 currentNode;
		currentNode = lastDummyNode.getPrevNode(); 
		
		 int lineBreakSignal = 0;
		while( currentNode != firstDummyNode ) {
			if (lineBreakSignal % 5 == 0) 
			   System.out.print("\n\t");
			
			lineBreakSignal++;
			
			System.out.print(currentNode.getData());
			Node2 previousNode = currentNode.getPrevNode();
			
			if (previousNode != firstDummyNode) 
				System.out.print(" -> ");

			currentNode = previousNode;
		}
	}
	   
}
