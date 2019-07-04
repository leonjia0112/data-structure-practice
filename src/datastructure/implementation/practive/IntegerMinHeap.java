package datastructure.implementation.practive;

import java.util.Arrays;
import java.util.NoSuchElementException;

// Integer MinHeap
public class IntegerMinHeap {
	
	// Constants and instance variable
	private static final int DEFAULT_CAPACITY = 16;
	private int[] array;
	private int size;
	
	
	// Constructors
	/**
	 * Create a new min heap with the default capacity
	 */
	public IntegerMinHeap() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Create a new min heap use the given array, and maintain the min heap order
	 * @param given array
	 */
	public IntegerMinHeap(int[] array) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException("input array can not be null or empty");
		}
		
		this.array = array;
		size = array.length;
		heapify();
	}

	/**
	 * Create a new empty min heap with the given capacity
	 * @param cap
	 */
	public IntegerMinHeap(int cap) {
		if (cap < 0)
			throw new IllegalArgumentException("capacity can not be smaller than 0");
		
		array = new int[cap];
		size = 0;
	}
	
	/**
	 * @return current size of the heap
	 */
	public int size() {
		return size;
	}
	
	/**
	 * @return true if heap is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * @return true if the heap full (hit the capacity), false otherwise
	 */
	public boolean isFull() {
		return array[size - 1] != 0;
	}
	
	/**
	 * Get the min element to the heap without removing the element
	 * 
	 * @return min element
	 */
	public int peek() {
		if (size == 0) {
			throw new NoSuchElementException("heap is empty");
		}
		return array[0];
	}
	
	/**
	 * Remove and return the min element of the heap, and maintain the heap order.
	 * 
	 * @return min elemnet
	 */
	public int poll() {
		if (size == 0)
			throw new NoSuchElementException("heap is empty");
		
		int result = array[0];
		array[0] = array[size - 1];
		size--;
		percolateDown(0);
		return result;
	}

	/**
	 * Add new element to the heap
	 * @param new element
	 */
	public void offer(int n) {
		if (size == array.length) {
			int[] newArray = new int[array.length * 2];
			newArray = Arrays.copyOf(array, array.length * 2);
			array = newArray;
		}
		
		array[size] = n;
		size++;
		percolateUp(size - 1);
	}
	
	/**
	 * Update the element value at the index location and maintain the heap order
	 * 
	 * @param index of element
	 * @param new value of the element
	 * @return old value of the element at index
	 */
	public int update(int index, int value) {
		if (index < 0 || index > size - 1) {
			throw new ArrayIndexOutOfBoundsException("invalid index range");
		}
		
		int old = array[index];
		array[index] = value;
		if (old > value) {
			percolateDown(index);
		} else {
			percolateUp(index);
		}
		return old;
	}
	
	// helper methods
	private void heapify() {
		for (int i = size / 2 - 1; i >= 0; i++) {
			percolateDown(i);
		}
	}

	private void swap(int[] array, int i, int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	
	private void percolateUp(int index) {
		while (index > 0) {
			int parentIndex = (index - 1) / 2;
			if (array[parentIndex] > array[index]) {
				swap(array, parentIndex, index);
			} else {
				break;
			}
			index = parentIndex;
		}
	}
	
	private void percolateDown(int index) {
		// check if index is legal?
		// last node: size - 1
		// last node's father: (size - 1 - 1) / 2
		while (index <= size / 2 - 1) {
			int leftChildIndex = index * 2 + 1;
			int rightChildIndex = index * 2 + 2;
			
			// smallest one among left and right child
			int swapCandidate = leftChildIndex;
			
			// update swap candidate if right child exist and smaller than
			// left child
			if (rightChildIndex <= size - 1 && array[leftChildIndex] >= array[rightChildIndex]) {
				swapCandidate = rightChildIndex;
			}
			
			if (array[index] > array[swapCandidate]) {
				swap(array, index, swapCandidate);
			} else {
				break;
			}
			
			// move index
			index = swapCandidate;
		}
	}
}