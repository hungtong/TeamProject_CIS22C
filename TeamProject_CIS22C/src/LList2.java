/**
   A class that implements the ADT list by using a chain of DOUBLY
   linked nodes that has a head dummy reference and tail dummy reference.

   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
   UPDATED by C. Lee-Klawender
*/
public class LList2<T> implements ListInterface<T>
{
	protected Node2 firstDummyNode;            // Reference to first node of chain
	protected Node2 lastDummyNode;
	protected int  numberOfEntries;

	public LList2()
	{
        firstDummyNode = new Node2(null);
        lastDummyNode = new Node2(null);
        firstDummyNode.setNextNode(lastDummyNode);
        lastDummyNode.setPrevNode(firstDummyNode);
		initializeDataFields();
	} // end default constructor

	public void clear()
	{
		initializeDataFields();
	} // end clear

	public void add(T newEntry) 	      // OutOfMemoryError possible
	{
		Node2 newNode = new Node2(newEntry);

        // add to the end
        Node2 lastNode = lastDummyNode.getPrevNode(); // could be firstDummyNode
        lastNode.setNextNode(newNode); // Make last Node2 reference new Node2
        newNode.setPrevNode(lastNode);
        newNode.setNextNode(lastDummyNode);
        lastDummyNode.setPrevNode(newNode);

		numberOfEntries++;
	}  // end add

   public boolean add(int newPosition, T newEntry)
	{
 		if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1))
		{
		    Node2 newNode = new Node2(newEntry);

            // with dummy nodes, use same code if newPosition is 1 or > 1
            Node2 nodeAfter = getNodeAt(newPosition); // node to be moved over
            Node2 nodeBefore = nodeAfter.getPrevNode();

			newNode.setNextNode(nodeAfter);
			nodeAfter.setPrevNode(newNode);
			newNode.setPrevNode(nodeBefore);
            nodeBefore.setNextNode(newNode);

            numberOfEntries++;
            return true;
		}
        else
            return false;
   } // end add

	public boolean remove(T anEntry)
	{
		boolean found = false;
		Node2 currentNode = firstDummyNode.getNextNode(); 
		while (!found && (currentNode != lastDummyNode)) 
		{
			if (anEntry.equals(currentNode.getData()))
			found = true;
			else
			{
				//prevNode = currentNode; 
				currentNode = currentNode.getNextNode();
			}
		} // end while
		if( !found )
		return false;
		// must be found here, get following node and update
		Node2 nextNode = currentNode.getNextNode(); 
		Node2 prevNode = currentNode.getPrevNode(); 
		nextNode.setPrevNode(prevNode); 
		prevNode.setNextNode(nextNode);
		--numberOfEntries;
		return true;

	}

	public T remove(int givenPosition)
	{
      T result = null;                           // Return value

      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
      {
            // same code if givenPosition is 1 or > 1
            Node2 nodeToRemove = getNodeAt(givenPosition);
            Node2 nodeBefore = nodeToRemove.getPrevNode();
            result = nodeToRemove.getData();     // Save entry to be removed
            Node2 nodeAfter = nodeToRemove.getNextNode();
            nodeBefore.setNextNode(nodeAfter);   // Remove entry
            nodeAfter.setPrevNode(nodeBefore);

            numberOfEntries--;                      // Update count
            return result;                          // Return removed entry
      }
      else
          return null;
	} // end remove


   public T getEntry(int givenPosition)
   {
		if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            return getNodeAt(givenPosition).getData();
     	}
        else
            return null;
   } // end getEntry


	public boolean contains(T anEntry)
	{
		boolean found = false;
		Node2 currentNode = firstDummyNode.getNextNode();

		while (!found && (currentNode != lastDummyNode))
		{
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		} // end while

		return found;
	} // end contains

   public int getLength()
   {
      return numberOfEntries;
   } // end getLength

   public boolean isEmpty()
   {
        return numberOfEntries == 0;

   } // end isEmpty

   public void display()
   {
	   Node2 currNode;
	   currNode = firstDummyNode.getNextNode(); 
	   while( currNode != lastDummyNode )
	   {
		   System.out.print(currNode.getData());
		   currNode = currNode.getNextNode();
	   }
    } // end display
     
   // Initializes the class's data fields to indicate an empty list.
   protected void initializeDataFields()
   {
		firstDummyNode.setNextNode(lastDummyNode);
		lastDummyNode.setPrevNode(firstDummyNode);
		numberOfEntries = 0;
   } // end initializeDataFields

   // Returns a reference to the Node2 at a given position.
   // Precondition: The chain is not empty;
   //               1 <= givenPosition <= numberOfEntries.
   // Returns a reference to the node at a given position.
   // Precondition: The chain is not empty;
   //               1 <= givenPosition <= numberOfEntries.
	protected Node2 getNodeAt(int givenPosition)
	{
		if((1 <= givenPosition) && (givenPosition <= numberOfEntries)) {
			if (givenPosition == 1)
				return firstDummyNode.getNextNode();
			
			if (givenPosition == numberOfEntries)
				return lastDummyNode.getPrevNode();
			
            Node2 currentNode = firstDummyNode.getNextNode();

            // Traverse the chain to locate the desired node
            // (skipped if givenPosition is 1)
            for (int counter = 1; counter < givenPosition; counter++)
                currentNode = currentNode.getNextNode();

            return currentNode;
        }
        else
            return null;
	} // end getNodeAt

	protected class Node2 {
      protected T    data; // Entry in list
      protected Node2 next; // Link to next Node2
      protected Node2 prev; // Link to previous Node2

      protected Node2(T dataPortion)
      {
         data = dataPortion;
         next = null;
         prev = null;
      } // end constructor

      protected Node2(T dataPortion, Node2 nextNode)
      {
         data = dataPortion;
         next = nextNode;
         prev = null;
      } // end constructor

      protected T getData()
      {
         return data;
      } // end getData

      protected void setData(T newData)
      {
         data = newData;
      } // end setData

      protected Node2 getNextNode()
      {
         return next;
      } // end getNextNode

      protected void setNextNode(Node2 nextNode)
      {
         next = nextNode;
      } // end setNextNode

      protected Node2 getPrevNode()
      {
         return prev;
      } // end getNextNode

      protected void setPrevNode(Node2 prevNode)
      {
         prev = prevNode;
      } // end setNextNode
	} // end Node2
} // end LList2



