import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	private Node<T> head, tail;
	private int size;
	private int modCount;
	
	public IUDoubleLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/**  
     * Adds the specified element to the front of this list. 
     *
     * @param element the element to be added to the front of this list    
     */
	@Override
	public void addToFront(T element) {
		Node<T> newNode = new Node<T>(element);
		
		if (isEmpty()) {
			head = newNode;
			tail = newNode;
		}
		else {
			newNode.setNext(head);
			head.setPrev(newNode);
			head = newNode;
		}
		size++;
		modCount++;
	}

	/**  
     * Adds the specified element to the rear of this list. 
     *
     * @param element the element to be added to the rear of this list    
     */
	@Override
	public void addToRear(T element) {
		Node<T> newNode = new Node<T>(element);
		if (isEmpty()) {
			tail = newNode;
			head = newNode;
		}
		else {
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
		modCount++;
		
	}

	/**  
     * Adds the specified element to the rear of this list. 
     *
     * @param element  the element to be added to the rear of the list    
     */
	@Override
	public void add(T element) {
		addToRear(element);
	}

	 /**  
     * Adds the specified element after the specified target. 
     *
     * @param element the element to be added after the target
     * @param target  the target is the item that the element will be added after
     * @throws NoSuchElementException if target element is not in this list
     */
	@Override
	public void addAfter(T element, T target) {
		Node<T> targetNode = head;
		boolean found = false;
		while(targetNode != null && !found) {
			if (targetNode.getElement().equals(target)) {
				found = true;
			}
			else {
				targetNode = targetNode.getNext();
			}
		}
		//throw NoSuchElementException if not found
		if (!found) {
			throw new NoSuchElementException();
		}
		//create new Node with element
		Node<T> newNode = new Node<T>(element);
		//set new Node's next to target Node's next
		newNode.setNext(targetNode.getNext());
		//set target Node's next to new Node
		targetNode.setNext(newNode);
		newNode.setPrev(targetNode);
		//update tail if necessary
		if (newNode.getNext() == null) {
			tail = newNode;
		}
		else {
			newNode.getNext().setPrev(newNode);
		}
		size++;
		modCount++;
	}

	/**  
     * Inserts the specified element at the specified index. 
     * 
     * @param index   the index into the array to which the element is to be inserted.
     * @param element the element to be inserted into the array
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
     */
	@Override
	public void add(int index, T element) {
		Node<T> newNode = new Node<T>(element);
		Node<T> currentNode =  head;
		
		//If the list is empty the newNode will be head and tail 
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		//If the index is empty, can only add to make the list one element which 
		//will be head and tail 
		if (isEmpty()) {
			head = newNode;
			tail = newNode;
		}
		
		//If the index is being added to the end of the list, the newNode is the tail
		else if (index == size) {
			newNode.setPrev(tail);
			tail = newNode;
		}
		
		else {
			for (int i = 0; i <= index; i++) {
				if (i == index) {
					//set the previous values
					newNode.setNext(currentNode.getNext());
					currentNode.setNext(newNode);
					newNode.setPrev(currentNode);
				}
				currentNode = currentNode.getNext();
			}
			//update tail if necessary
			if (newNode.getNext() == null) {
				tail = newNode;
			}
		}
		size++;
		modCount++;
	}

	/**  
     * Removes and returns the first element from this list. 
     * 
     * @return the first element from this list
     * @throws NoSuchElementException if list contains no elements
     */
	@Override
	public T removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
	
		if (size > 1) {
			head = head.getNext();
		}
		//List is going to empty if it just had one element
		else {
			head = null;
			tail = null;
		}
		size--;
		modCount++; 
		return retVal;
	}

	/**  
     * Removes and returns the last element from this list. 
     *
     * @return the last element from this list
     * @throws NoSuchElementException if list contains no elements
     */
	@Override
	public T removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		tail = tail.getPrev();
		if (size > 1) {
			tail.setNext(null);
		}
		else {
			head = null;
		}
		size--;
		modCount++;
		return retVal; 
	}

	/**  
     * Removes and returns the first element from the list matching the specified element.
     *
     * @param element the element to be removed from the list
     * @return removed element
     * @throws NoSuchElementException if element is not in this list
     */
	@Override
	public T remove(T element) {
		//super special case: list is empty, throw NoSuchElementException
		if (isEmpty()) {
			throw new NoSuchElementException();
			}
		T retVal;
		//special case part 1: element is in the head node 
		if (head.getElement().equals(element)) {
			//store element for return
			retVal = head.getElement();
			//update head
			head = head.getNext();
			//special case part 2: and it was the only node
			if (head == null) {
				tail = null;
			}
			}
		else {
		//general case: find predecessor to target node
			Node<T> preNode = head;
			boolean found = false;
			while (preNode.getNext() != null && !found) {
				if (preNode.getNext().getElement().equals(element)) {
					found = true;
				}
				else {
					preNode = preNode.getNext();
				}
				}
			//throw NoSuchElementException if target is not found
			if (!found) {
				throw new NoSuchElementException();
			}
			//store element for return
			retVal = preNode.getNext().getElement();
			//update predecessor node's next
			preNode.setNext(preNode.getNext().getNext());
			//update tail if necessary
			if (preNode.getNext() == null) {
				tail = preNode;
			}
			}
			size--;
			modCount++;
			return retVal;
	}

	/**  
     * Removes  and returns the element at the specified index. 
     *
     * @param index the index of the element to be retrieved
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
	@Override
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		T retVal = null;
		Node<T> prevNode = null;
		Node<T> currentNode = head;
		for (int i = 0; i < index; i++) {
			prevNode = currentNode;
			currentNode = currentNode.getNext();
		}
		
		retVal = currentNode.getElement();
		
		//removes first element if index is 0
		if (index == 0) {
			head = currentNode.getNext();
			currentNode.setPrev(null);
		}
		
		
		
		else {
			prevNode.setNext(currentNode.getNext());
			if (currentNode.getNext() != null) {
				currentNode.getNext().setPrev(prevNode);
			}
		}
		
		//update tail if necessary
		if (currentNode.getNext() == null) {
			tail = currentNode;
		}
		size--;
		modCount++;
		return retVal;
	}

	/**  
     * Replace the element at the specified index with the given element. 
     *
     * @param index   the index of the element to replace
     * @param element the replacement element to be set into the list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
	@Override
	public void set(int index, T element) {
		//If index is not in range, throw exception 
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
			}
		
		Node<T> current = head;
		for (int i = 0; i <= index; i++) {
			if (i == index) {
				current.setElement(element);
			}
				current = current.getNext();
			}
		modCount++;
		}
	
	/**  
     * Returns a reference to the element at the specified index. 
     *
     * @param index  the index to which the reference is to be retrieved from
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
	@Override
	public T get(int index) {
		Node<T> current = head;
		T retVal = null;
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i <= index; i++) {
			if (i == index) {
				retVal = current.getElement();
			}
			current = current.getNext();
		}
		return retVal;
	}

	/**  
     * Returns the index of the first element from the list matching the specified element. 
     *
     * @param element  the element for the index is to be retrieved
     * @return the integer index for this element or -1 if element is not in the list    
     */
	@Override
	public int indexOf(T element) {
		int index = -1;
		Node<T> current = head;
		int currentIndex = 0;
		while (current != null && index < 0) {
			if (current.getElement().equals(element)) {
				index = currentIndex;
			}
			else {
				current = current.getNext();
				currentIndex++;
			}
		}
		return index;
	}

	/**  
     * Returns a reference to the first element in this list. 
     *
     * @return a reference to the first element in this list
     * @throws NoSuchElementException if list contains no elements
     */
	@Override
	public T first() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
			return head.getElement();
	}

	/**  
     * Returns a reference to the last element in this list. 
     *
     * @return a reference to the last element in this list
     * @throws NoSuchElementException if list contains no elements
     */
	@Override
	public T last() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	 /**  
     * Returns true if this list contains the specified target element. 
     *
     * @param target the target that is being sought in the list
     * @return true if the list contains this element, else false
     */
	@Override
	public boolean contains(T target) {
		if (indexOf(target) == -1) {
			return false;
		}
		return true;
	}

	/**  
     * Returns true if this list contains no elements. 
     *
     * @return true if this list contains no elements
     */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	/**  
     * Returns the number of elements in this list. 
     *
     * @return the integer representation of number of elements in this list
     */
	@Override
	public int size() {
		return size;
	}

	/**  
     * Returns a string representation of this list. 
     *
     * @return a string representation of this list
     */
	@Override
	public String toString() {
		Node<T> current = head;
    	String output = "";
    	//return empty list if it is empty 
    	if (isEmpty()) {
    		return "[]";
    	}
    	else {
    		output+= "[";
    		for (int i = 0; i < size; i++) {
    			output += current.getElement();
    			//can't call next if there are no more elements in list
    			if (i < size - 1) {
    				output += " , ";
    				current = current.getNext();
    			}
    			}
    		output+="]";
    		}
    	return output;
	}
	
	/**  
     * Returns an Iterator for the elements in this list. 
     *
     * @return an Iterator over the elements in this list
     */
	@Override
	public Iterator<T> iterator() {
		return new DLListIterator();
	}

	/**  
     * Returns a ListIterator for the elements in this list. 
     *
     * @return a ListIterator over the elements in this list
     *
     * @throws UnsupportedOperationException if not implemented
     */
	@Override
	public ListIterator<T> listIterator() {
		return new DLListIterator();
	}

	/**  
     * Returns a ListIterator for the elements in this list, with
     * the iterator positioned before the specified index. 
     *
     * @return a ListIterator over the elements in this list
     *
     * @throws UnsupportedOperationException if not implemented
     */
	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		return new DLListIterator(startingIndex);
	} 
	
	/** ListIterator for IUDoubleLinkedList */
	private class DLListIterator implements ListIterator<T> {
		private Node<T> nextNode;
		private	Node<T> lastAccessed;
		private int iterModCount;
		private int nextIndex;
		
		/** Default constructor for listIterator*/
		public DLListIterator() {
			iterModCount = modCount;
			nextNode = head;
			lastAccessed = null;
			nextIndex = 0;
		}
		
		/** Constructor for listIterator 
		 * @param index  the starting index for the list iterator
		 * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
		 * */
		public DLListIterator(int index) {
			lastAccessed = null;
			//throw exception if the index is out of bounds 
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			nextNode = head;
			
			//set currentNode equal to the node at the index passed in 
			for (int i = 0; i < index; i++) {
				nextNode = nextNode.getNext();
			}
			nextIndex = index;
			iterModCount = modCount;
		}
		
		/**
		 * Adds value before current iterator position 
		 */
		@Override
		public void add(T elem) {
			failFast();
			
			Node<T> newNode = new Node<T>(elem);
			if (hasPrevious()) {
				if (hasNext()) {
					newNode.setPrev(nextNode.getPrev());
				}
				else {
					newNode.setPrev(tail);
				}
				newNode.getPrev().setNext(newNode);
				
				
			}
			else {
				head = newNode;
			}
			if (hasNext()) {
				newNode.setNext(nextNode);
				nextNode.setPrev(newNode);
			}
			else {
				tail = newNode;
			}

			iterModCount++;
			modCount++;
			size++;
			
			//Sets to null so can't run remove or set after add
			lastAccessed = null;
		}

		/**
		 * Checks if listIterator has a next value 
		 * @return true or false if there is a next value
		 */
		@Override
		public boolean hasNext() {
			failFast();
			return nextNode != null;
		}

		/**
		 * Checks if listIterator has a previous value 
		 * @return true or false if there is a previous value
		 */
		@Override
		public boolean hasPrevious() {
			failFast();
			if (hasNext()) {
				return nextNode.getPrev() != null;
			}
			if (isEmpty()) {
				return false;
			}
			return true;
		}

		/**
		 * Moves listIterator to the next position between elements
		 * @return retVal  The value the listIterator passes over
		 * @throws NoSuchElementException if there is no next element
		 */
		@Override
		public T next() {
			failFast();
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			//sets to nextNode so can run remove or set after call to next
			lastAccessed = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			return lastAccessed.getElement();
		}

		/**
		 * Returns the index of the next element
		 * @returns index  The index of the next element
		 */
		@Override
		public int nextIndex() {
			failFast();
			return nextIndex;
		}

		/**
		 * Moves listIterator to the previous position between elements
		 * @return retVal  The value the listIterator passes over
		 * @throws NoSuchElementException if there is no previous element
		 */
		@Override
		public T previous() {
			failFast();
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			nextNode = nextNode.getPrev();
			//sets to nextNode so can run remove or set after previous 
			lastAccessed = nextNode;
			nextIndex--;
			return lastAccessed.getElement();
		}

		/**
		 * Returns the index of the previous element
		 * @return index  The index of the previous element
		 */
		@Override
		public int previousIndex() {
			failFast();
			return nextIndex - 1;
		}
		
		/**
		 * Removes the element most recently returned by either next() or previous()
		 * @throws IllegalStateException if there is no last returned element
		 */
		@Override
		public void remove() {
			failFast();
			if (lastAccessed == null){
				throw new IllegalStateException();
			}
			if (lastAccessed.getPrev() == null) {
				head = nextNode;
				}
			
			else {
				lastAccessed.getPrev().setNext(lastAccessed.getNext());
			}
			//if just did a call to previous
			if (lastAccessed.equals(nextNode)) {
				nextNode = nextNode.getNext();
			}
				
			
			size--;
			iterModCount++;
			modCount++;
			
			//set node to null at end so cannot call remove two times in a row or call set after 
			lastAccessed = null;
		}

		/**
		 * Replaces most recently returned element with given element
		 * @param element  element to replace last returned value
		 * @throws IllegalStateException if not preceded by call to next() or previous()
		 */
		@Override
		public void set(T elem) {
			failFast();
			if (lastAccessed == null) {
				throw new IllegalStateException();
			}
			lastAccessed.setElement(elem);
			iterModCount++;
			modCount++;
			lastAccessed = null;
		}
		
		/**
		 * Prevents iterator from continuing use if the array was 
		 * modified without the use of the iterator 
		 * @throws ConcurrentModificationException if array was modified 
		 * without use of iterator 
		 */
		private void failFast() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
		}
		
	}

}
