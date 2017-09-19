package util;

public class MyLinkedList<T> {
	
	private int maxSize, currentSize, currentCursor;
	private Node<T> firstNode, lastNode, currentNode;
	private int cycleTo = 0;
	public MyLinkedList(int maxSize) {
		if(maxSize < 1){
			throw new RuntimeException("The max size must be greater than 0.");
		}
		this.maxSize = maxSize;
		this.currentSize = 0;
		this.firstNode = null;
		this.lastNode = null;
		this.currentNode = null;
		currentCursor = 0;
	}

	/**
	 * Adds a node first in the list.
	 * @param value T value of node.
	 */
	public void addFirst(T value){
		Node<T> node = new Node<T>(value);
		if(currentSize == 0){
			firstNode = node;
			lastNode = node;
			node.left = firstNode;
			node.right = lastNode;
		} 
		else if(currentSize == maxSize){
			throw new RuntimeException("Currently on max list size: " + currentSize);
		} 
		else if(currentSize > maxSize){
			throw new RuntimeException("Exceeded max list size: " + currentSize);
		}
		else{
			firstNode.left =node;
			node.left = lastNode;
			node.right = firstNode;
			firstNode = node;
		}
		
		currentSize++;
		currentCursor = 0;
		currentNode = firstNode;
	}
	
	/**
	 * Adds a node last in the list.
	 * @param value T value of node.
	 */
	public void addLast(T value){
		Node<T> node = new Node<T>(value);
		if(currentSize == 0){
			firstNode = node;
			lastNode = node;
			node.left = firstNode;
			node.right = lastNode;
		} 
		else if(currentSize == maxSize){
			throw new RuntimeException("Currently on max list size: " + currentSize);
		} 
		else if(currentSize > maxSize){
			throw new RuntimeException("Exceeded max list size: " + currentSize);
		}
		else{
			lastNode.right = node;
			node.left = lastNode;
			node.right = firstNode;
			lastNode = node;
		}
		
		currentSize++;
		currentCursor = currentSize;
		currentNode = lastNode;
	}
	
	/**
	 * Adds all elements of an array in the list.
	 * @param list
	 * @return True if added, false if not
	 */
	public boolean addAll(T[] list){
		if(list.length > maxSize - currentSize){
			return false;
		}
		
		for(int i = 0; i < list.length; i++){
			this.addLast(list[i]);
		}
		
		return true;
	}
	
	/**
	 * Removes the first Node<T> in the list.
	 */
	public T removeFirst(){
		T prev;
		if(currentSize == 0){
			throw new RuntimeException("List is empty.");
		}
		else if (currentSize == 1){
			prev = firstNode.value;
			lastNode = null;
			firstNode = null;
		}
		else{
			prev = firstNode.value;
			Node<T> newFirstNode = firstNode.right; 
			newFirstNode.left = lastNode;
			lastNode.right = newFirstNode;
			firstNode = newFirstNode;
			currentSize--;
			currentCursor = 0;
			currentNode = firstNode;
		}
		return prev;
	}
	
	/**
	 * Removes the last Node<T> in the list.
	 */
	public T removeLast(){
		T prev;
		if(currentSize == 0){
			throw new RuntimeException("List is empty.");
		}
		else if (currentSize == 1){
			prev = lastNode.value;
			lastNode = null;
			firstNode = null;
		}
		else{
			prev = lastNode.value;
			Node<T> newLastNode = lastNode.left; 
			newLastNode.left = lastNode;
			lastNode.right = newLastNode;
			lastNode = newLastNode;
			currentSize--;
			currentCursor = currentSize - 1;
			currentNode = lastNode;
		}
		return prev;
	}
	
	/**
	 * Remove the node on index i
	 * @param i Must be > 0 and < current size
	 */
	public T remove(int i){	
		T prev;
		if(i == 0){
			return removeFirst();
		} else if(i == currentSize - 1){
			return removeLast();
		} else {
			cycleToIndex(i);
			prev = currentNode.value;
			currentNode.left.right = currentNode.right;
			currentNode.right.left = currentNode.left;
			currentNode = currentNode.right;
			currentSize--;
		}	
		return prev;
	}
	
	/**
	 * Returns a T value based on index i.
	 * Test shows that it is -10%-40% more efficient than Java's LinkedList.
	 * @param i
	 * @return
	 */
	public T get(int i){				
		if(i == 0){
			return firstNode.value;
		} else if (i == currentSize - 1){
			return lastNode.value;
		} else {
			cycleToIndex(i);
		}		
		return currentNode.value;
	}
	 
	/**
	 * Sets a new T value in the index.
	 * Test shows that it is -10%-40% more efficient than Java's LinkedList.
	 * @param value a T value
	 * @param i index must be > 0 and < current size
	 * @return the element previously at the specified position
	 */
	public T set(int i, T value){
		if(i == 0){
			T prev = firstNode.value;
			firstNode.value = value;
			return prev;
		} else if(i == currentSize - 1){
			T prev = lastNode.value;
			lastNode.value = value;
			return prev;
		} else {
			cycleToIndex(i);
			T prev = currentNode.value;
			currentNode.value = value;
			return prev;
		}
			
	}
		
	/**
	 * Checks if the list contains a T value. 
	 * @param value a T value
	 * @return True if exists, false if not
	 */
	public boolean contains(T value){
		//good ole brute force
		currentCursor = 0;
		currentNode = firstNode;
		for(int i = 0; i < currentSize; i++){
			if(currentNode.value.equals(value)){
				return true;
			} else {
				currentCursor++;
				currentNode = currentNode.right;
			}
		}
		return false;		
	}
	
	/**
	 * Returns the current size of the list.
	 * @return
	 */
	public int size(){
		return currentSize;
	}
	/**
	 * Returns list as string in "[node0][node1]...[nodeN]" format.
	 */
	public String toString(){
		String str = "";
		Node<T> curr = firstNode;
		for(int i = 0; i < currentSize; i++){
			str += "["+curr.toString()+ "]";
			curr = curr.right;
		}		
		return str;
	}
	
	/**
	 * Private method that cycles the current cursor 
	 * and current node to provided index. 
	 * Throws exception where applicable.
	 * @param index
	 */
	private void cycleToIndex(int index){		
		int distanceFromCursor = Math.abs(index - currentCursor);
		//calculate the necessary measures to decide which directions
		//would be best for the cursor to move when retrieving objects
		if(index >= 0 && index < currentSize){
			if(index - 0 < distanceFromCursor){
				currentCursor = 0;
				currentNode = firstNode;
			} else if ((currentSize-1) - index < distanceFromCursor){
				currentCursor = currentSize - 1;
				currentNode = lastNode;
			}
			
			while(index < currentCursor){
				currentNode = currentNode.left;
				currentCursor--;
			}	
			
			while(index > currentCursor){
					currentNode = currentNode.right;
					currentCursor++;
			}			
		} else {
			throw new RuntimeException("Index out of bounds: " + index);
		}					
	}	
	
	@SuppressWarnings("hiding")
	private class Node<T>{
		public Node<T> left, right;
		public T value;
		
		public Node(T value){
			this.value = value;
		}
		public String toString(){
			return value.toString();
		}
		
	}

}
