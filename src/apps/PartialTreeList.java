package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList() {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
    	Node ptr = new Node(tree);
    	if (rear == null) {
    		ptr.next = ptr;
    	} else {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }

    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() 
    throws NoSuchElementException {
    	//Using a Circular Linked List with rear node given
    	
    	//Checking if rear is null, if it is than throw exception
    	if(rear==null) 	{ throw new NoSuchElementException("ERROR: Tree is Null"); 	}
       	//If only one node in CLL, return node's partial tree

    	//Decrement size by 1
    	if(size==1 && rear != null) 	{
    		PartialTree z = rear.tree;
    		rear=null;
    		size--;
    		
    		return z;
     }
    	
    	//If more than one node, than remove rear.next as that is the front of the CLL
    	//Decrement size by 1
    	//Also rear.next should point to rear.next.next
   	Node tree = rear.next;
    	rear.next = rear.next.next;
    	size--;
    	
    	return tree.tree;
    	
    	}
      	
    	
  
    

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
    	
    	//Use Circular Linked List again
    	//1. If size == 0, throw exception
    	//2. If size == 1, check if vertex is present within current tree, otherwise throw an exception
    	//3. If size > 1, go through LL and check vertices
    	
    	if(size==0)	{ throw new NoSuchElementException("Error: Tree is empty"); 	}
    	if(size==1) 	{
    		if(containsVertex(rear, vertex)){
    			PartialTree t;
    			t=rear.tree;
    			rear = null;
    			return t;
    			
    		}else {
    			throw new NoSuchElementException("ERROR: Vertex cannot be found");
    		}
    	}
    	
    	// If size is greater than one
    	//Creating pointer
    		Node ptr=rear;
    	//Check if ptr.next tree's vertex contains the vertex passed
    		
    			while( ptr.next != rear ) 	{   
    				if(containsVertex(ptr.next, vertex)){  
    			PartialTree temp=ptr.next.tree;
    			ptr.next=ptr.next.next;
    			size--;
    			return temp;
    		}
    			ptr=ptr.next;
    		
    	}
    		if (containsVertex(ptr.next, vertex)){
    		PartialTree temp = ptr.next.tree;
    		ptr.next = ptr.next.next;
    		rear = ptr;
    		size--;
    		return temp;
    	}
    		//If all fails...
    		throw new NoSuchElementException("ERROR: Tree containing vertex cannot be found!!!");
   
   
     }
    
    private static boolean containsVertex(Node ptr, Vertex v) {
    	if (ptr.tree.getRoot().equals(v.getRoot())) {
    		return true;
    	}else {
    		return false;
    	}
    }
    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
    	return size;
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
    	return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
    	
    	private PartialTreeList.Node ptr;
    	private int rest;
    	
    	public PartialTreeListIterator(PartialTreeList target) {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}
    	
    	public PartialTree next() 
    	throws NoSuchElementException {
    		if (rest <= 0) {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}
    	
    	public boolean hasNext() {
    		return rest != 0;
    	}
    	
    	public void remove() 
    	throws UnsupportedOperationException {
    		throw new UnsupportedOperationException();
    	}
    	
    }
}


