package lists;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

import interfaces.List;

/**
 * This is the ArrayList class. It is the implementation of a List using an array to store the data.
 * @author Gretchen Y. Bonilla
 *
 * @param <E> - Placeholder for whichever the type of object we want to store. 
 * Example: If we wanted to make a List of String we would call: List<String>.
 */
@SuppressWarnings("unchecked")
public class ArrayList<E> implements List<E>{
	
	// Private fields needed by this list
	// Will hold the data
	private E[] elements;
	/* Will track of the current size of the list (how many elements are present).
	   NOTE: size != elements.length, size is how many occupied spaces, 
	   length is how many elements fit in the array */
	private int size;
	
	/**
	 * Default constructor. 
	 * It creates an ArrayList with an initial capacity of 15.
	 */
	public ArrayList() {
		this.elements = (E[]) new Object[15];
		this.size = 0;
	}

	/**
	 * Constructs an ArrayList with an initial capacity given by the parameter initialCapacity.
	 * @param initialCapacity - the initial capacity (length of array) we want the ArrayList to start with.
	 */
	public ArrayList(int initialCapacity) {
		this.elements = (E[]) new Object[initialCapacity];
		this.size = 0;
	}
	/**
	 * Returns the current size if the List
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Returns whether the List is currently empty, no values in the array. 
	 * @return True if empty, false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Adds a new element to the end of the array. 
	 * We expand the array if it's full and place the new value at position size.
	 * 
	 * @param obj - Object that we wish to add the the list
	 */
	@Override
	public void add(E obj) {
		// Check if array is full, expands if it is
		if(this.size == this.elements.length)
			reallocate();
		// Add value at end and increase size
		this.elements[size++] = obj;
//		this.size++;
		
	}
	/**
	 * Adds value obj at position index. 
	 * When the value gets added the elements need to be shifted one position to the right (position+1).
	 * 
	 * @param index - position where we want to add value
	 * @param obj - Object that we wish to add the the list
	 */
	@Override
	public void add(int index, E obj) {
		// Check bounds
		if(index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		// If array full, expands if it is
		if(this.size == this.elements.length)
			reallocate();
		// Shift values to the right
		for(int i = size; i > index; i--)
			this.elements[i] = this.elements[i-1];
		// Place new value
		this.elements[index] = obj;
		// Increase size
		this.size++;
	}
	/**
	 * Expands the size of elements. 
	 * It create an new array twice the length of the current elements array and places all the existing 
	 * elements in it. This new array then replaces the old elements array.
	 */
	private void reallocate() {
		E[] temp = (E[]) new Object[this.size*2];
		
		for(int i=0; i<this.elements.length; i++) {
			temp[i] = this.elements[i];
			elements[i] = null;
		}
		this.elements = temp;
		
	}

	/**
	 * Returns the element at position index.
	 * we access the array directly at position index.
	 * 
	 * @param index - the position of the element we want to access
	 */
	@Override
	public E get(int index) {
		// Check bounds
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		// Return value
		return this.elements[index];
	}

	/**
	 * Replaces the value at position index with obj.
	 * 
	 * @param index - position of the element we want to replace
	 * @param obj - value we will replace the current on with
	 * 
	 * @return (E) the old value, the value that was replaced
	 */
	@Override
	public E set(int index, E obj) {
		// Check bounds
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		// Get value being replaced
		E oldValue = this.elements[index];
		// Update value
		this.elements[index] = obj;
		// Return old value
		return oldValue;
	}

	@Override
	/**
	 * Remove value at position index.
	 * It shifts the values from index + 1 to size on position to the left (position - 1)
	 * and empties the value at the last filled position. 
	 * 
	 * @param index - position of element we want to remove
	 * 
	 * @return always true since the index is valid
	 */
	public boolean remove(int index) {
		// Check bounds
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		// Shift values to the left
		for(int i = index; i < this.size-1; i++)
			this.elements[i] = this.elements[i+1];
		// Null the last position
		this.elements[this.size-1]= null;
		// Decrease size
		this.size--;
		return true;
	}

	@Override
	/**
	 * Remove element obj from the array if it exists.
	 * 
	 * @param obj - the element we want to remove
	 * 
	 * @return (boolean) True if object is removed, false if it's not present in the list
	 */
	public boolean remove(E obj) {
		// Find the FIRST instance of the obj in the array to remove it
		for(int i=0; i<this.size ; i++) {
			if(this.elements[i].equals(obj)) {
				return this.remove(i);
			}
		}
		// Did not find obj to remove
		return false;
	}

	/**
	 * Checks if the element obj is present in the array.
	 * 
	 * @return True if present, false otherwise
	 */
	@Override
	public boolean contains(E obj) {
		// Search the array for obj
		for(int i = 0; i < this.size; i++) {
			// if found we are done
			if(this.elements[i].equals(obj))
				return true;
		}
		// Was not found
		return false;
	}

	/**
	 * Empties the array and sets size back to 0
	 */
	@Override
	public void clear() {
		// Empty array
		for(int i = 0; i < this.size; i++)
			this.elements[i] = null;
		this.size = 0;
		
	}

	/**
	 * Remove all instances of obj from the array.
	 * 
	 * @return Returns the total number of instances removed
	 */
	@Override
	public int removeAll(E obj) {
		// Counter
		int count = 0;
		
//		while(remove(obj)) 
//			count++;
		boolean found = remove(obj);
		while(found) {
			count++;
			found = remove(obj);
			
		}
		return count;
	}

	/**
	 * Gets the first position in the array where obj appears
	 * 
	 * @param obj - element to search for
	 * @return - index where obj was found -1 if not found.
	 */
	@Override
	public int firstIndex(E obj) {
		// Search for obj
		for(int i = 0; i < this.size; i++) {
			// If found we are done
			if(this.elements[i].equals(obj))
				return i;
		}
		// Wasn't found
		return -1;
	}

	/**
	 * Gets the last position in the array where obj appears
	 * 
	 * @param obj - element to search for
	 * @return - index where obj was found -1 if not found.
	 */
	@Override
	public int lastIndex(E obj) {
		// Search for obj
		// To be more efficient we start from position size-1 and move towards 0
		for(int i = this.size-1; i >=0; i--) {
			// If found we are done
			if(this.elements[i].equals(obj))
				return i;
		}
		// Wasn't found
		return -1;
	}
	/**
	 * Returns the first element in the List.
	 * 
	 * @return - Element at position 0.
	 */
	@Override
	public E first() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		return this.elements[0];
	}

	/**
	 * Returns the last element in the List.
	 * 
	 * @return - Element at position size - 1.
	 */
	@Override
	public E last() {
		if(this.isEmpty())
			throw new NoSuchElementException();
		return this.elements[this.size-1];
	}
	/**
	 * Returns iterator object for the ArrayList
	 * 
	 * @return - Iterator for this List
	 */
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new ArrayListIterator<E>();
	}
	
	/**
	 * Returns a String version of the ArrayList. 
	 * The format will be: { A, B, C, null, null }, where each element within the brackets { }
	 * represent the current data in the List. The null values represent the empty spaces in
	 * the internal array. This is to better reflect what is currently happening in the internal array.
	 * 
	 * @return - String with the content of the List.
	 */
	@Override
	public String toString() {
		// We will enclose the elements in brackets { }
		String str = "{ ";
		// We print all the elements in the array including the empty spaces
		for(E e: this.elements)
			str+= e + ", ";
		// The final comma is unnecessary so we remove it and close the brackets
		int lastComma = str.lastIndexOf(",");
		if(lastComma != -1)
			str = str.substring(0, lastComma);
		str = str + " }";
		return str;
	}
	
	/**
	 * Method that prints the content of the List after it was modified by applying a function F.
	 * @param F (Function<E, E> - function that modifies the content of the List. 
	 *  The idea is that it receives each element individually and returns the modified result.
	 */
	public void printModifiedList(Function<E, E> F) {
//		for(E e: this)
//			System.out.println(F.apply(e));
		for(int i = 0; i < this.size; i++)
			System.out.println(F.apply(this.elements[i]));
	}
	
	/**
	 * Iterator for the ArrayList implementation.
	 * What this does is that it makes it possible for use to iterate through the ArrayList
	 * using for-each or using the Iterator methods.
	 * 
	 * @author Gretchen Bonilla
	 *
	 * @param <E>
	 */
	private class ArrayListIterator<E> implements Iterator<E> {
		
		// Will keep track of what position in the List we are currently in
		private int currentPosition;
		
		public ArrayListIterator() {
			// Start of array
			currentPosition = 0;
		}
		/**
		 * Checks if we can still move in the list. This is done with an index.
		 */
		@Override
		public boolean hasNext() {
			// There is a next value if the current position is smaller than the current size
			return currentPosition < size;
		}

		/**
		 * Returns the current element and updates position for the next value
		 */
		@Override
		public E next() {
			// Returns the value at the current position and increases the index
			return (E) elements[currentPosition++];
		}
		
	}
}
