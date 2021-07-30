import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Nandha Sundaravadivel
 * @version 1.0
 * @userid nsundara3
 * @GTID 903599469
 *
 *       Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 *       Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit. As a
     * reminder, this is the algorithm that involves building the heap from the
     * bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the ArrayList to
     * the backingArray (leaving index 0 of the backingArray empty). The data in the
     * backingArray should be in the same order as it appears in the passed in
     * ArrayList before you start the BuildHeap algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the number of data in
     * the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should remain empty,
     * indices 1 to n should contain the data in proper order, and the rest of the
     * indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data is
     *                                            null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are adding can not be null");
        }
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("A piece of data in the array you are adding can not be null");
            }
            size++;
            backingArray[size] = element;
        }
        for (int i = size / 2; i > 0; i--) {
            upheap(i);
        }
    }

    /**
     * Adds the data to the heap.
     *
     * If sufficient space is not available in the backing array (the backing array
     * is full except for index 0), resize it to double the current length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are adding can not be null");
        }
        size++;
        if (size == backingArray.length) {
            resize();
        }
        backingArray[size] = data;
        addUpheap(size);

    }

    /**
     * A recursive helper method to make the heap valid after adding an element
     * 
     * @param index The index to check for valid positioning
     */
    public void addUpheap(int index) {
        if (index > 1) {
            if (backingArray[index].compareTo(backingArray[index / 2]) > 0) {
                T temp = backingArray[index];
                backingArray[index] = backingArray[index / 2];
                backingArray[index / 2] = temp;
                addUpheap(index / 2);
            }
        }
    }

    /**
     * Removes and returns the root of the heap.
     *
     * Do not shrink the backing array.
     *
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is currently empty");
        }
        T temp = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        removeDownHeap(1);
        size--;
        return temp;
    }

    /**
     * A recursive helper method to make the heap valid after removing an element
     * 
     * @param index The index to check for valid positioning
     */
    public void removeDownHeap(int index) {
        if (backingArray.length >= index * 2 + 1 && backingArray[index * 2 + 1] != null) {
            if (backingArray[index * 2 + 1].compareTo(backingArray[index * 2]) > 0) {
                if (backingArray[index * 2 + 1].compareTo(backingArray[index]) > 0) {
                    T temp = backingArray[index];
                    backingArray[index] = backingArray[index * 2 + 1];
                    backingArray[index * 2 + 1] = temp;
                    removeDownHeap(index * 2 + 1);
                }
            } else {
                if (backingArray[index * 2].compareTo(backingArray[index]) > 0) {
                    T temp = backingArray[index];
                    backingArray[index] = backingArray[index * 2];
                    backingArray[index * 2] = temp;
                    removeDownHeap(index * 2);
                }
            }
        } else if (backingArray.length >= index * 2 && backingArray[index * 2] != null) {
            if (backingArray[index * 2].compareTo(backingArray[index]) > 0) {
                T temp = backingArray[index];
                backingArray[index] = backingArray[index * 2];
                backingArray[index * 2] = temp;
                removeDownHeap(index * 2);
            }
        }
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is currently empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and resets
     * the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since you
     * have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since you
     * have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Resizes the element to twice its size
     */
    public void resize() {
        T[] arr = (T[]) new Comparable[backingArray.length * 2];
        for (int i = 1; i < backingArray.length; i++) {
            if (backingArray[i] != null) {
                arr[i] = backingArray[i];
            }
        }
        backingArray = arr;
    }

    /**
     * Moves the element up the tree if it greater than the parent
     * 
     * @param index The index to upheap
     */
    public void upheap(int index) {

        if (index > 0) {
            if (backingArray.length > index * 2 + 1 && backingArray[index * 2 + 1] != null) {
                if (backingArray[index * 2 + 1].compareTo(backingArray[index * 2]) > 0) {
                    if (backingArray[index * 2 + 1].compareTo(backingArray[index]) > 0) {
                        T temp = backingArray[index];
                        backingArray[index] = backingArray[index * 2 + 1];
                        backingArray[index * 2 + 1] = temp;
                        upheap(index * 2 + 1);
                    }
                } else {
                    if (backingArray[index * 2].compareTo(backingArray[index]) > 0) {
                        T temp = backingArray[index];
                        backingArray[index] = backingArray[index * 2];
                        backingArray[index * 2] = temp;
                        upheap(index * 2);
                    }
                }
            } else if (backingArray.length >= index * 2 && backingArray[index * 2] != null) {
                if (backingArray[index * 2].compareTo(backingArray[index]) > 0) {
                    T temp = backingArray[index];
                    backingArray[index] = backingArray[index * 2];
                    backingArray[index * 2] = temp;
                    upheap(index * 2);
                }
            }

        }
    }
}
