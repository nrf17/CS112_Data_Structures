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
		if(rear == null){ throw new NoSuchElementException("The Tree is Empty."); }
		else{
			if(rear != rear.next){
				PartialTree partTree = rear.next.tree;
				rear.next = rear.next.next;
				size--;
				return partTree;
			}else{
				PartialTree partTree = rear.tree;
				rear = null;
				size--;
				return partTree;
			}
		}
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
		if(rear==null){ throw new NoSuchElementException("No Matching Tree, the Tree is Empty."); }
		if(rear==rear.next && vertex.equals(rear.tree.getRoot())){
			PartialTree partTree = rear.tree;
			rear = null;
			return partTree;
		}
		Node prev = rear;
		Node ptr = rear.next;
		do{
			if(vertex.equals(ptr.tree.getRoot())){
				PartialTree partTree = ptr.tree;
				prev.next = ptr.next;
				if(ptr==rear){ rear = prev; }
				return partTree;
			}
			prev = ptr;
			ptr = ptr.next;
		} while(prev!=rear);
		if(prev==rear && ptr==rear){ throw new NoSuchElementException("There is no Matching Tree."); }
		PartialTree partTree = rear.tree;
		return partTree;
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


