package interfaces;

/**
 * Interface that describes the behavior of a List.
 * 
 * It's a type of collection that can dynamically add and remove elements. 
 * Each value has an assigned position.
 * @author Gretchen Bonilla
 *
 * @param <E>
 */
public interface List<E> extends Iterable<E> {
	/**
	 * Returns the size of the List.
	 * @return (int) size of the list
	 */
	public int size();
	
	/**
	 * Returns whether the List is empty of not.
	 * True if empty, false otherwise.
	 * @return (boolean) whether the list is empty
	 */
	public boolean isEmpty();
	
	/**
	 * Adds an element to the end of the List
	 * @param obj - The element we wish to add to the List
	 */
	public void add(E obj);
	
	/**
	 * Adds an element to the position given by index.
	 * @param index - position we want to add the element to.
	 * @param obj - the element we wish to add
	 * @throws IndexOutOfBoundsException if the index is not valid
	 */
	public void add(int index, E obj);
	
	/**
	 * Returns the value at the given position
	 * @param index - position of the value we want
	 * @throws IndexOutOfBoundsException if the index is not valid
	 * @return (E) value at position index
	 */
	public E get(int index);
	
	/**
	 * Replaces the value at the position given by index.
	 * @param index - position we want to replace
	 * @param obj - the new value we want to place
	 * @throws IndexOutOfBoundsException if the index is not valid
	 * @return (E) the old value at position index (the replaced value)
	 */
	public E set(int index, E obj);
	
	/**
	 * Removes the element at position index
	 * @param index - The position of the element we want to remove
	 * @return (boolean) Always true since the position has to be valied (exist) to be removed.
	 * @throws IndexOutOfBoundsException if the index is not valid
	 */
	public boolean remove(int index);
	
	/**
	 * Removes the first instance of the element obj.
	 * @param obj - the value we want to remove
	 * @return (boolean) Whether the element was successfully found and removed. True if removed, false otherwise.
	 */
	public boolean remove(E obj);
	
	/**
	 * Checks whether the value obj is present in the List
	 * @param obj - the value we want to verify its existance 
	 * @return (boolean) whether obj is present or not. True if present, false otherwise
	 */
	public boolean contains(E obj);
	
	/**
	 * Empties the List. 
	 */
	public void clear();
	
	/**
	 * Removes every instance of element obj.
	 * @param obj - the value we want to remove
	 * @return (int) number of entries that were removed.
	 */
	public int removeAll(E obj);
	
	/**
	 * Returns the position where an instance of obj first appears.
	 * @param obj - obj we want to search for
	 * @return (int) position where obj first appears in the List
	 */
	public int firstIndex(E obj);
	
	/**
	 * Returns the position where an instance of obj last appears.
	 * @param obj - obj we want to search for
	 * @return (int) position where obj last appears in the List
	 */
	public int lastIndex(E obj);	
	
	/**
	 * Returns the first element in the List
	 * @return (E) First element in the List
	 */
	public E first();
	/**
	 * Returns the last element in the List.
	 * @return (E) last element in the List
	 */
	public E last();
	
}
