package assignment11;

import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Represents a priority queue of generically-typed items. The queue is
 * implemented as a min heap. The min heap is implemented implicitly as an
 * array. This implementation is specifically designed for assignment 11, and as
 * such has a few key differences from a traditional priority queue
 * 
 * @author Joshua Cragun
 */
@SuppressWarnings("unchecked")
public class PriorityQueue<AnyType> {

	private int currentSize;

	private AnyType[] array;

	private Comparator<? super AnyType> cmp;

	private HashMap<AnyType, Integer> map;

	/**
	 * Constructs an empty priority queue. Orders elements according to their
	 * natural ordering (i.e., AnyType is expected to be Comparable) AnyType is not
	 * forced to be Comparable.
	 */

	public PriorityQueue() {
		currentSize = 0;
		cmp = null;
		map = new HashMap<>();
		array = (AnyType[]) new Object[10]; // safe to ignore warning
	}

	/**
	 * Construct an empty priority queue with a specified comparator. Orders
	 * elements according to the input Comparator (i.e., AnyType need not be
	 * Comparable).
	 */
	public PriorityQueue(Comparator<? super AnyType> c) {
		currentSize = 0;
		cmp = c;
		map = new HashMap<>();
		array = (AnyType[]) new Object[10]; // safe to ignore warning
	}

	/**
	 * @return the number of items in this priority queue.
	 */
	public int size() {
		return currentSize;
	}

	public HashMap<AnyType, Integer> map() {
		return map;
	}

	/**
	 * Makes this priority queue empty.
	 */
	public void clear() {
		currentSize = 0;
		array = (AnyType[]) new Object[10];
	}

	/**
	 * @return the minimum item in this priority queue.
	 * @throws NoSuchElementException
	 *             if this priority queue is empty.
	 * 
	 *             (Runs in constant time.)
	 */
	public AnyType findMin() throws NoSuchElementException {
		if (currentSize == 0) {
			throw new NoSuchElementException();
		}
		return array[1];
	}

	/**
	 * Removes and returns the minimum item in this priority queue.
	 * 
	 * @return the minimum item in this priority queue.
	 * @throws NoSuchElementException
	 *             if this priority queue is empty.
	 * 
	 *             (Runs in logarithmic time.)
	 */
	public AnyType deleteMin() throws NoSuchElementException {
		// if the heap is empty, throw a NoSuchElementException
		if (currentSize == 0) {
			throw new NoSuchElementException();
		}
		// store the minimum item so that it may be returned at the end
		AnyType min = array[1];
		map.remove(min);
		// replace the item at minIndex with the last item in the tree
		array[1] = array[currentSize];
		map.put(array[1], 1);
		array[currentSize] = null;
		// update size
		currentSize--;
		// percolate the item at minIndex down the tree until heap order is restored
		percolateDown();
		// return the minimum item that was stored
		return min;
	}

	/**
	 * Adds an item to this priority queue.
	 * 
	 * (Runs in logarithmic time.) Can sometimes terminate early.
	 * 
	 * @param item
	 *            - the item to be inserted
	 */
	public void add(AnyType item) {
		if (item == null) {
			return;
		} else if (map.containsKey(item)) {
			// Consider the case where something has already been added to the PQ. Check to
			// see if the priority has changed.
			int index = map.get(item);
			if (compare(item, array[index / 2]) < 0) {
				swap(index);
			} else if ((array[index * 2] != null && compare(item, array[index * 2]) > 0)
					|| (array[(index * 2) + 1] != null && compare(item, array[(index * 2) + 1]) > 0)) {
				// If any of the children are less than the item, then the item must be sifted
				// down
				// Swap with the smallest child
				if (compare(getLeftChild(index), getRightChild(index)) > 0) {
					swap((index * 2) + 1);
					// Add it again until changes are no longer necessary
					add(item);
				} else if (compare(getLeftChild(index), getRightChild(index)) <= 0) {
					swap(index * 2);
					// Add it again until changes are no longer necessary
					add(item);
				}
			}
			return;
		} else {
			currentSize++;
			if (currentSize >= array.length) {
				resize();
			}
			array[currentSize] = item;
			map.put(item, currentSize);
			percolateUp();
		}
	}

	/**
	 * Generates a string for visualizing the binary heap.
	 * 
	 * @return DOT format string to enter at http://www.webgraphviz.com
	 */
	public String generateDot() {
		String result = "digraph Heap {\n\tnode [shape=record]\n";

		for (int i = 1; i <= currentSize; i++) {
			result += "\tnode" + i + " [label = \"<f0> |<f1> " + array[i] + "|<f2> \"]\n";
			if (((i * 2)) <= currentSize)
				result += "\tnode" + i + ":f0 -> node" + (i * 2) + ":f1\n";
			if (((i * 2) + 1) <= currentSize)
				result += "\tnode" + i + ":f2 -> node" + ((i * 2) + 1) + ":f1\n";
		}
		return result + "}\n";
	}

	/**
	 * Internal method for comparing lhs and rhs using Comparator if provided by the
	 * user at construction time, or Comparable, if no Comparator was provided.
	 * 
	 * @param lhs
	 *            - the left-hand-side item being compared
	 * @param rhs
	 *            - the right-hand-side item being compared
	 * @return a negative integer if lhs < rhs, 0 if lhs == rhs, a positive integer
	 *         if lhs > rhs
	 */
	private int compare(AnyType lhs, AnyType rhs) {
		if (cmp == null) {
			return ((Comparable<? super AnyType>) lhs).compareTo(rhs); // safe to ignore warning
		}
		// We won't test your code on non-Comparable types if we didn't supply a
		// Comparator

		return cmp.compare(lhs, rhs);
	}

	// LEAVE IN for grading purposes
	public Object[] toArray() {
		Object[] ret = new Object[currentSize];
		for (int i = 0; i < currentSize; i++) {
			ret[i] = array[i + 1];
		}
		return ret;
	}

	/**
	 * Swaps the places of a parent and child
	 * 
	 * @param index
	 *            -- Refers to the index of the child
	 */
	private void swap(int index) {
		int parentLoc = index / 2;
		AnyType temp = array[parentLoc];
		map.put(array[index], parentLoc);
		array[parentLoc] = array[index];
		map.put(temp, index);
		array[index] = temp;
	}

	/**
	 * Percolates up the binary heap
	 */
	protected void percolateUp() {
		int curPos = currentSize;
		while (getParent(curPos) != null && compare(array[curPos], getParent(curPos)) < 0) {
			swap(curPos);
			curPos /= 2;
		}
	}

	/**
	 * Percolates down the binary heap
	 */
	protected void percolateDown() {
		// Start at root node
		int curPos = 1;
		while (curPos <= currentSize) {
			if (getLeftChild(curPos) == null && getRightChild(curPos) == null) {
				return;
			}
			// Check if the left child is null and the right node is less then curPos
			else if (getLeftChild(curPos) == null && compare(array[curPos], getRightChild(curPos)) > 0) {
				swap((curPos * 2) + 1);
				curPos = (curPos * 2) + 1;
				continue;
			}
			// Check if the right child is null and the left node is less then curPos
			else if (getRightChild(curPos) == null && compare(array[curPos], getLeftChild(curPos)) > 0) {
				swap(curPos * 2);
				curPos *= 2;
				continue;
			}
			// So now if both children aren't null
			else if (getLeftChild(curPos) != null && getRightChild(curPos) != null
					&& (compare(array[curPos], getLeftChild(curPos)) > 0
							|| compare(array[curPos], getRightChild(curPos)) > 0)) {
				if (compare(getLeftChild(curPos), getRightChild(curPos)) > 0) {
					swap((curPos * 2) + 1);
					curPos = (curPos * 2) + 1;
					continue;
				} else if (compare(getLeftChild(curPos), getRightChild(curPos)) <= 0) {
					swap(curPos * 2);
					curPos *= 2;
					continue;
				}
			}
			// If none of these conditions are met, then we're done.
			break;
		}
		return;
	}

	/**
	 * Resizes the priority queue
	 */
	protected void resize() {
		int size = (currentSize + 1) * 2;
		AnyType[] intermediate = (AnyType[]) new Object[size];
		for (int i = 1; i < currentSize; i++) {
			intermediate[i] = array[i];
		}
		array = intermediate;
	}

	/**
	 * Fetches the parent to a given node
	 * 
	 * @param index
	 *            -- the index of the child
	 * @return The data in the parant node
	 */
	protected AnyType getParent(int index) {
		return array[index / 2];
	}

	/**
	 * Obtains the left child of a given node
	 * 
	 * @param index
	 * @return
	 */
	protected AnyType getLeftChild(int index) {
		if (2 * index <= currentSize) {
			return array[2 * index];
		}
		return null;
	}

	/**
	 * Obtains the right child of a given node
	 * 
	 * @param index
	 * @return
	 */
	protected AnyType getRightChild(int index) {
		if ((2 * index) + 1 <= currentSize) {
			return array[(2 * index) + 1];
		}
		return null;
	}

	protected boolean isEmpty() {
		return this.currentSize == 0;
	}
}