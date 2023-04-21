package lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

import interfaces.List;

/**
 * Implementation of a list using simple nodes. All the data is stored using nodes.
 * @author Gretchen Bonilla
 *
 * @param <E>
 */
public class SinglyLinkedList<E> implements List<E>{
	
	/**
	 * Class that holds a single piece of data and a single reference to the node that goes after it.
	 * @author Gretchen Bonilla
	 *
	 * @param <E>
	 */
	private class Node<E> {
		// References the node that goes after it
		private Node<E> next;
		// The data
		private E element;
		
		/**
		 * Create empty node.
		 */
		public Node() {
			this.next = null;
			this.element =null;
		}
		/**
		 * Creates node with data elm and whose next node is next
		 * @param next - (Node) The node the goes after this one
		 * @param elm - (E) the data that this node holds
		 */
		public Node(Node<E> next, E elm) {
			this.next = next;
			this.element = elm;
		}
		/**
		 * Creates a node that holds the element elm, but doesn't have a next node yet.
		 * @param elm - (E) the data that the node will hold
		 */
		public Node(E elm) {
			this.element = elm;
			this.next=null;
		}
		public Node<E> getNext() {
			return next;
		}
		public void setNext(Node<E> next) {
			this.next = next;
		}
		public E getElement() {
			return element;
		}
		public void setElement(E element) {
			this.element = element;
		}
		/**
		 * EMpties the node to help with clean up
		 */
		public void clear() {
			this.next = null;
			this.element=null;
		}	
		
	}
	
	// Represent the first node in the list
	private Node<E> head;
	// Tracks the current size of the list
	private int size;
	

	/**
	 * Creates an empty list. Head starts as null since there is no data yet. Size is 0.
	 */
	public SinglyLinkedList() {
		this.head = null;
		this.size = 0;
	}
	

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	/**
	 * Adds a new element to the list. In this case we appends a node with the value obj
	 * to the end of the chain
	 */
	@Override
	public void add(E obj) {
		// Node to be added. Hold element obj.
		Node<E> newNode = new Node<E>(obj);
		
		// If list is empty then the new node is now head.
		if(head == null)
			head = newNode;
		else {
			// Lets search for the last node in the list
			Node<E> temp = head;
			while(temp.getNext() != null) {
				temp = temp.getNext();
			}
			// Set the next of the last node to the new node.
			// We now have a new last node
			temp.setNext(newNode);
		}
		this.size++;
		
	}
	/**
	 * Finds the node whose position is index
	 * @param index - (int) position of the node we want to find
	 * @return - (Node<E>) The node at the given position
	 * 
	 * @throws IndexOutOfBoundsException if index is not between 0 and size.
	 */
	private Node<E> getNode(int index) {
		// Check the index is valid
		if(index < 0 || index >=size)
			throw new IndexOutOfBoundsException();
		// We start at head
		Node<E> temp = head;
		// Move through the list until we find the node at position index
		for(int i = 0; i < index; i++)
			temp = temp.getNext();
		// Return the node
		return temp;
	}

	/**
	 * Adds a node with element obj at position index.
	 * 
	 * @param index - (int) position to add the node
	 * @param obj - (E) the value that the node will hold
	 */
	@Override
	public void add(int index, E obj) {
		// Check index is valid
		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		// Create the node to be added
		Node<E> newNode = new Node<E>(obj);
		// If index is 0 that means this element will be the new head node
		if(index == 0) {
			// The next of this node is the current head
			// We place had as the second node now
			newNode.setNext(head);
			// Make head point to the new node since it is now the first node
			head = newNode;
		}
		// If index is any position other than 0
		else {
			/*
			 * When we add a new node we don't want to break the chain, for this we have to be careful of how we 
			 * update the references.
			 * 
			 * For example: If we have the list A->B->C->D->null and we call add(2, Z).
			 * Then we want the list to become A->B->Z->C->D->null.
			 * Notice that the references that got updated were that of B (the node at position index-1) and Z.
			 * 
			 * So which should we update first? B or Z?
			 * Well consider that in the new list the next of Z will be C, which is the next of B in the initial list.
			 * If we change B's next reference to Z first we would lose any way of accessing C. 
			 * Therefore, we first update the next of Z and then that of B.
			 *  
			 */
			// Find the node that is in the position before index
			Node<E> previousNode = getNode(index-1);
			
			// Update the reference of the new node to the next reference of the previous node
			newNode.setNext(previousNode.getNext());
			/*^^^^^^^^
			 * If the above line of code is hard to visualize maybe this would help. 
			 * What this line does is the same as doing the following:
			 * 
			 * Node<E> nextNode = previousNode.getNext(); // This node will be the node that goes after the new node
			 * newNode.setNext(nextNode);
			 * 
			 * If we follow the example given in the previous comment then we have that:
			 * newNode = Z
			 * nextNode = C
			 * So after this line we have that Z->C->D->null
			 */
			
			
			// The new next of the previous node is now new node
			previousNode.setNext(newNode);

		}
		this.size++;
	}

	/**
	 * Returns the element at position index
	 * 
	 * @param index - (int) position of the element we want
	 * @return The element at position i
	 * @throws IndexOutOfBoundsException - index must be between 0 and size - 1
	 */
	@Override
	public E get(int index) {
		// Get the node at position index and return it's element
		return this.getNode(index).getElement();
	}

	/**
	 * Replace the element at position index with obj
	 * @param index - (int) position of the element we want to replace
	 * @param obj - (E) Value to replace element at position index with
	 * @return (E) Value at position index before being replaced
	 */
	@Override
	public E set(int index, E obj) {
		// Get the node with the element wa want to alter
		Node<E> nodeToChange = this.getNode(index);
		// Get current value
		E oldValue = nodeToChange.getElement();
		// Change the value
		nodeToChange.setElement(obj);
		// Return old value
		return oldValue;
	}

	/**
	 * Removes the node at position index.
	 * 
	 * @param index - (int) position of node we want to remove
	 * @return (boolean) always true since index must be valid
	 * @throws IndexOutOfBoundsException - index must be between 0 and size - 1
	 */
	@Override
	public boolean remove(int index) {
		// Check index is valid
		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		// If it's the first node
		if(index == 0) {
			// Get the node to be removed
			Node<E> holdNode = this.head;
			/* 
			 * Change head. Now the second node of the List becomes the first.
			 * Notice this still works if there is only one node. 
			 */
			this.head = this.head.getNext();
			// Help gc
			holdNode.clear();
		}
		else {
			/*
			 * When we remove a node we don't want to break the chain, for this we have to be careful of how we 
			 * update the references.
			 * 
			 * For example: If we have the list A->B->C->D->null and we call remove(2).
			 * Then we want the list to become A->B->D->null.
			 * Notice that the references that got updated was that of B (the node at position index-1).
			 * 
			 * After node C is out of the list, we then clear() it so that the Java Garbage Collector (GC) will have an easier time
			 * cleaning up memory.
			 * 
			 * So as always maintaining the chain is priority, which is why updating the B's reference must
			 * be done before clearing C.
			 *  
			 */
			
			// Get node before 
			Node<E> previousNode = getNode(index - 1);
			// Get the node we want to remove
			Node<E> nodeToRemove = previousNode.getNext();
			// Update references
			previousNode.setNext(nodeToRemove.getNext());
			// Help GC
			nodeToRemove.clear();
		}
		size--;
		return true;
	}

	/**
	 * Removes the first node in the list that contains obj
	 * 
	 * @param obj - (E) value we want to remove from the list
	 * @return (boolean) True if the node is removed, false if the node doesn't exist in the list
	 */
	@Override
	public boolean remove(E obj) {
		// Start at head
		Node<E> temp = head;
		// Iterate through list we want to keep track of the position
		for(int i = 0; i < this.size; i++) {
			if(temp.getElement().equals(obj))
				// If object found remove it
				return remove(i);
			// Go to next node
			temp = temp.getNext();
		}
		// Was not found
		return false;
	}

	/**
	 * Checks if the value obj is present in the list (if any node containes it)
	 * 
	 * @param obj - (E) Value we want to find
	 * @return (boolean) True if the value is present in list, false otherwise
	 */
	@Override
	public boolean contains(E obj) {
		// Get start of list
//		Node<E> temp = head;
//		// Iterate through list
//		while(temp != null) {
//			// If we find it we are done
//			if(temp.getElement().equals(obj))
//				return true;
//			temp = temp.getNext();
//		}
//		// Not found
//		return false;
		return firstIndex(obj) != -1;
	}

	/**
	 * Removes all nodes from the list. By the end size is 0 and head is null.
	 */
	@Override
	public void clear() {
		// Get first node
		Node<E> temp = head;
		// Iterate through list
		while(temp != null) {
			// Hold the current node for elimination
			Node<E> holdNode = temp;
			// Move to next node
			temp =temp.getNext();
			// Clear node
			holdNode.clear();
			
		}
		
		// Does this work???
//		while(!isEmpty()) remove(0);
		// Reset values for empty list
		size = 0;
		head = null;		
	}

	/**
	 * Removes all nodes holding element obj
	 * 
	 * @param obj - element we want to remove
	 * @return (int) how many copies of obj were removed
	 */
	@Override
	public int removeAll(E obj) {
		// counter
		int count = 0;
		// Nodes we need for removing. Reference to the previous node and the current node
		Node<E> previousNode = null;
		Node<E> currentNode = head;
		while(currentNode != null) {
			if(currentNode.getElement().equals(obj)) {
				if(currentNode == head) {
					// currentNode now point to the new head
					currentNode = currentNode.getNext();
					// Remove head. !!!This is O(1), why????
					remove(0);
					
					// No need to update previous since there is no node before head
				}
				else {
					// Hold the current node
					Node<E> holdNode = currentNode;
					// Update references
					previousNode.setNext(currentNode.getNext());
					currentNode = currentNode.getNext();
					holdNode.clear();
					// Need to decrease size as we remove a node
					size--;
				}
				count++;
			}
			else {
				previousNode = currentNode;
				currentNode = currentNode.getNext();
			}
		}
		return count;
	}
	
	/**
	 * Returns the first position where element obj appears
	 * 
	 * @param obj - (E) Element to search for
	 * @return (int) Position where the element first appears
	 */
	@Override
	public int firstIndex(E obj) {
		// Current node
		Node<E> temp = head;
		for(int  i = 0; i < this.size; i++) {
			if(temp.getElement().equals(obj))
				return i;
			temp = temp.getNext();
		}
		return -1;
	}

	/**
	 * Returns the last position where element obj appears
	 * 
	 * @param obj - (E) Element to search for
	 * @return (int) Position where the element last appears
	 */
	@Override
	public int lastIndex(E obj) {
		// Current position (Default -1, not in list)
		int pos = -1;
		// Current node
		Node<E> temp = head;
		for(int  i = 0; i < this.size; i++) {
			if(temp.getElement().equals(obj))
				pos = i;
			temp = temp.getNext();
		}
		return pos;
	}
	
	@Override
	public E first() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		return this.head.getElement();
	}

	@Override
	public E last() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		return this.get(size-1);
	}
	/**
	 * Returns a String version of the SinglyLinkedList. 
	 * 
	 * The format will be: { A -> B -> C -> null }, where each element within the brackets { }
	 * represent the current data of the nodes in the List. Example: A represents the node with
	 * the value A and -> represents what A is pointing to, in this case, the node with element B.
	 * Since the next of the last element of the SinglyLinkedList if null we end the List with a null value.
	 * 
	 * @return - String with the content of the List.
	 */
	public String toString() {
		// We will enclose the elements in brackets { }
		String str = "{ ";
		for(E e: this)
			str+= e + "-> ";
		
		str = str + "null }";
		return str;
	}
	
	/**
	 * Returns an iterator for this list
	 */
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new LinkedListIterator<E>();
	}
	/**
	 * This class implements an Iterator for the LinkedList implemented here.
	 *  
	 * @author Gretchen Bonilla
	 *
	 * @param <E>
	 */
	private class LinkedListIterator<E> implements Iterator<E> {
		// For keeping track of the current node of the list
		private Node<E> currentNode;
		
		@SuppressWarnings("unchecked")
		public LinkedListIterator() {
			// We start at the head node
			currentNode = (Node<E>) head;
		}

		/**
		 * Checks if we can still move in the list
		 */
		@Override
		public boolean hasNext() {
			// Check if we reached the final node of the list
			return currentNode != null;
		}

		/**
		 * Gives the current value and moves us to the next
		 */
		@Override
		public E next() {
			// Get the current value
			E value = currentNode.getElement();
			// Move to next node
			currentNode = currentNode.getNext();
			return value;
		}
		
	}

}
