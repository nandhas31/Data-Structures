
/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Nandha Sundaravadivel
 * @version 1.0
 * @userid nsundara3
 * @GTID 903599469
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
import java.util.NoSuchElementException;

public class CircularSinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index "+index+" does not exist in the list. Try index between 0 and "+size);
        }
        if (data == null) {
            throw new IllegalArgumentException("The data you inserted was null");
        } else if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            CircularSinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            current.setNext(new CircularSinglyLinkedListNode<T>(data, current.getNext()));
            size++;
        }

    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data was null but can not be null");
        }
        if (isEmpty()) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
        } else {
            head.setNext(new CircularSinglyLinkedListNode<>(data, head.getNext()));
            T temp = head.getData();
            head.setData(head.getNext().getData());
            head.getNext().setData(temp);

        }
        size++;
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addToFront(data);
        head = head.getNext();
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index "+index+" does not exist in the list. Try index between 0 and "+size);
        } else if (index == 0) {
            return removeFromFront();
        } else {
            CircularSinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            T temp = current.getNext().getData();
            current.setNext(current.getNext().getNext());
            size--;
            return temp;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is currently empty");
        }
        if (size == 1) {
            T temp = head.getData();
            clear();
            return temp;
        }
        T temp = head.getData();
        head.setData(head.getNext().getData());
        head.setNext(head.getNext().getNext());
        size--;
        return temp;
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        CircularSinglyLinkedListNode<T> current = head;
        if (isEmpty()) {
            throw new NoSuchElementException("The list is currently empty.");
        }
        if (size == 1) {
            T temp = head.getData();
            clear();
            return temp;
        }
        while (current.getNext().getNext() != head) {
            current = current.getNext();
        }
        T temp = current.getNext().getData();
        current.setNext(head);
        size--;
        return temp;
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index "+index+" does not exist in the list. Try index between 0 and "+size);
        }
        CircularSinglyLinkedListNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();

    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that was
     * stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data was null and can not be null");
        }
        CircularSinglyLinkedListNode<T> current = head;
        int cursor = -1;
        for (int i = 0; i < size; i++) {
            if ((current.getData()).equals(data)) {
                cursor = i;
            }
            current = current.getNext();
        }
        if (cursor == -1) {
            throw new NoSuchElementException("Data was not found in the list");
        }
        return removeAtIndex(cursor);

    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the nodes) in
     *         the list in the same order
     */
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        int cursor = 0;
        CircularSinglyLinkedListNode<T> current = head;
        while (cursor < size) {
            arr[cursor] = current.getData();
            current = current.getNext();
            cursor++;
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since you
     * have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since you
     * have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
