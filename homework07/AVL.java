import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Nandha Sundaravadivel
 * @userid nsundara3
 * @GTID 903599469
 *
 *       Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 *       Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there is no
     * need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the Collection.
     * The data should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data is
     *                                            null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are adding to the tree can not be null");
        }
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the tree
     * as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back up the
     * tree after adding the element, making sure to rebalance if necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are adding can not be null");
        }
        root = add(data, root);

    }

    /**
     * A helper method to help recursively add an element to the tree;
     * 
     * @param data The data that needs to be added to the tree
     * @param root The root of the current subtree being searched for a place to put
     *             the new data
     * @return The current root of the subtree
     */
    private AVLNode<T> add(T data, AVLNode<T> root) {
        if (root == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(add(data, root.getRight()));
        } else if (data.compareTo(root.getData()) < 0) {
            root.setLeft(add(data, root.getLeft()));
        }
        root.setHeight(1 + Integer.max(height(root.getLeft()), height(root.getRight())));
        root.setBalanceFactor(height(root.getLeft()) - height(root.getRight()));
        if (root.getBalanceFactor() > 1) {
            if (root.getLeft().getBalanceFactor() == -1) {
                root.setLeft(rotateLeft(root.getLeft()));
            }
            root = rotateRight(root);
        } else if (root.getBalanceFactor() < -1) {
            if (root.getRight().getBalanceFactor() == 1) {
                root.setRight(rotateRight(root.getRight()));
            }
            root = rotateLeft(root);
        }
        return root;
    }

    /**
     * Removes and returns the element from the tree matching the given parameter.
     *
     * There are 3 cases to consider: 1: The node containing the data is a leaf (no
     * children). In this case, simply remove it. 2: The node containing the data
     * has one child. In this case, simply replace it with its child. 3: The node
     * containing the data has 2 children. Use the successor to replace the data,
     * NOT predecessor. As a reminder, rotations can occur after removing the
     * successor node.
     *
     * Remember to recalculate heights and balance factors while going back up the
     * tree after removing the element, making sure to rebalance if necessary.
     *
     * Do not return the same data that was passed in. Return the data that was
     * stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are passing in can not be null");
        }
        AVLNode<T> returnNode = new AVLNode<>(null);
        root = remove(data, root, returnNode);
        return returnNode.getData();
    }

    /**
     * A helper method to remove a node from the tree
     * 
     * @param data       The data that is being removed from the tree
     * @param root       The current root of the subtree being searched
     * @param returnNode The node that contains the data that will be returned
     * @return The current node of the tree
     */
    private AVLNode<T> remove(T data, AVLNode<T> root, AVLNode<T> returnNode) {
        if (root == null) {
            throw new NoSuchElementException("The data you are looking for in the tree does not exist");
        } else if (root.getData().equals(data)) {
            size--;
            returnNode.setData(root.getData());
            if (root.getRight() != null && root.getLeft() != null) {
                root.setRight(successorHelper(root.getRight(), root));

            } else if (root.getRight() != null) {
                root = root.getRight();
            } else if (root.getLeft() != null) {
                root = root.getLeft();
            } else if (root.getLeft() == null && root.getRight() == null) {
                return null;
            }
        } else if (data.compareTo(root.getData()) > 0) {
            root.setRight(remove(data, root.getRight(), returnNode));
        } else if (data.compareTo(root.getData()) < 0) {
            root.setLeft(remove(data, root.getLeft(), returnNode));
        }
        root.setHeight(1 + Integer.max(height(root.getLeft()), height(root.getRight())));
        root.setBalanceFactor(height(root.getLeft()) - height(root.getRight()));
        if (root.getBalanceFactor() > 1) {
            if (root.getLeft().getBalanceFactor() == -1) {
                root.setLeft(rotateLeft(root.getLeft()));
            }
            root = rotateRight(root);
        } else if (root.getBalanceFactor() < -1) {
            if (root.getRight().getBalanceFactor() == 1) {
                root.setRight(rotateRight(root.getRight()));
            }
            root = rotateLeft(root);
        }
        return root;
    }

    /**
     * A tree to help set the data of the tree when removing a node with two
     * children
     * 
     * @param root    The node being removed initially but also set to the root of
     *                the subtree being searched when trying to find a successor
     * @param removed The node being removed
     * @return The right node of the node being removed
     */
    private AVLNode<T> successorHelper(AVLNode<T> root, AVLNode<T> removed) {
        if (root.getLeft() != null) {
            root.setLeft(successorHelper(root.getLeft(), removed));
        } else {
            removed.setData(root.getData());
            root = root.getRight();
        }
        if (root != null) {
            root.setHeight(1 + Integer.max(height(root.getLeft()), height(root.getRight())));
            root.setBalanceFactor(height(root.getLeft()) - height(root.getRight()));
            if (root.getBalanceFactor() > 1) {
                if (root.getLeft().getBalanceFactor() == -1) {
                    root.setLeft(rotateLeft(root.getLeft()));
                }
                root = rotateRight(root);
            } else if (root.getBalanceFactor() < -1) {
                if (root.getRight().getBalanceFactor() == 1) {
                    root.setRight(rotateRight(root.getRight()));
                }
                root = rotateLeft(root);
            }
        }

        return root;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that was
     * stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that you are searching for can not be null");
        }
        return get(data, root);
    }

    /**
     * Helper method for the get method to recursively return the node from the tree
     * 
     * @param data The data you are searching for
     * @param root The root of the current subtree you are searching in
     * @return The node in the tree that contains the data you are looking for
     */
    private T get(T data, AVLNode<T> root) {
        if (root == null) {
            throw new NoSuchElementException("The data you are searching for is not in the tree");
        }
        if (root.getData().equals(data)) {
            return root.getData();
        }
        if (data.compareTo(root.getData()) > 0) {
            return get(data, root.getRight());
        } else {
            return get(data, root.getLeft());
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained within
     * the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that you are searching for can not be null");
        }
        return contains(data, root);
    }

    /**
     * A helper method for the contains method that would recursively return if a
     * certain data element is in the tree
     * 
     * @param data The data that you are looking for
     * @param root The root of the subtree that you are searching
     * @return True if the data is in the tree and false otherwise
     */
    private boolean contains(T data, AVLNode<T> root) {
        if (root == null) {
            return false;
        }
        if (root.getData().equals(data)) {
            return true;
        }
        if (data.compareTo(root.getData()) > 0) {
            return contains(data, root.getRight());
        } else {
            return contains(data, root.getLeft());
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * A private helper method to get the height of any tree
     * 
     * @param root Any node in the tree
     * @return The height of the tree
     */
    public int height(AVLNode<T> root) {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node with the
     * same deepest depth, return the rightmost (i.e. largest) node with the deepest
     * depth.
     *
     * Must run in O(log n) for all cases.
     *
     * Example Tree: 2 / \ 0 3 \ 1 Max Deepest Node: 1 because it is the deepest
     * node
     *
     * Example Tree: 2 / \ 0 4 \ / 1 3 Max Deepest Node: 3 because it is the maximum
     * deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return maxDeepestNode(root);
    }

    /**
     * A helper method to recursively find the deepest node in the tree
     * 
     * @param root The root of the current subtree being searched
     * @return The data contained in the deepest node
     */
    private T maxDeepestNode(AVLNode<T> root) {
        if (height(root) == 0) {
            return root.getData();
        } else if (height(root.getLeft()) > height(root.getRight())) {
            return maxDeepestNode(root.getLeft());
        } else if (height(root.getLeft()) < height(root.getRight())) {
            return maxDeepestNode(root.getRight());
        } else {
            return maxDeepestNode(root.getRight());
        }
    }

    /**
     * In BSTs, you learned about the concept of the successor: the smallest data
     * that is larger than the current data. However, you only saw it in the context
     * of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data passed
     * in. There are 2 cases to consider: 1: The right subtree is non-empty. In this
     * case, the successor is the leftmost node of the right subtree. 2: The right
     * subtree is empty. In this case, the successor is the lowest ancestor of the
     * node containing data whose left child is also an ancestor of data.
     * 
     * The second case means the successor node will be one of the node(s) we
     * traversed left from to find data. Since the successor is the SMALLEST element
     * greater than data, the successor node is the lowest/last node we traversed
     * left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     *
     * Ex: Given the following AVL composed of Integers 76 / \ 34 90 \ / 40 81
     * successor(76) should return 81 successor(81) should return 90 successor(40)
     * should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the one given,
     *         return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are searching the successor for can not be null");
        }
        return successor(data, root);
    }

    /**
     * A helper method to find the root with the next highest data in it
     * 
     * @param data The data that you are trying to find the successor for
     * @param root The root of the current subtree being searched
     * @return The data value of the successor or null if there is none
     */
    private T successor(T data, AVLNode<T> root) {
        if (root == null) {
            throw new NoSuchElementException("The element that you are searching the successor for is not in the tree");
        }
        if (root.getData().equals(data)) {
            if (root.getRight() != null) {
                root = root.getRight();
                while (root.getLeft() != null) {
                    root = root.getLeft();
                }
                return root.getData();
            }
            return null;
        }
        if (data.compareTo(root.getData()) > 0) {
            return successor(data, root.getRight());
        } else {
            T temp = successor(data, root.getLeft());
            if (temp == null) {
                temp = root.getData();
            }
            return temp;
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since you
     * have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since you
     * have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Performs a left rotation on the tree to balance the tree
     * 
     * @param root of the subtree to be balanced with a rotation
     * @return New balanced root of the subtree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> root) {
        AVLNode<T> temp = root.getRight();
        root.setRight(temp.getLeft());
        temp.setLeft(root);
        root.setHeight(1 + Integer.max(height(root.getLeft()), height(root.getRight())));
        root.setBalanceFactor(height(root.getLeft()) - height(root.getRight()));
        temp.setHeight(1 + Integer.max(height(temp.getLeft()), height(temp.getRight())));
        temp.setBalanceFactor(height(temp.getLeft()) - height(temp.getRight()));
        return temp;
    }

    /**
     * Performs a right rotation on the tree to balance the tree
     * 
     * @param root of the subtree to be balanced with a rotation
     * @return New balanced root of the subtree
     */
    private AVLNode<T> rotateRight(AVLNode<T> root) {
        AVLNode<T> temp = root.getLeft();
        root.setLeft(temp.getRight());
        temp.setRight(root);
        root.setHeight(1 + Integer.max(height(root.getLeft()), height(root.getRight())));
        root.setBalanceFactor(height(root.getLeft()) - height(root.getRight()));
        temp.setHeight(1 + Integer.max(height(temp.getLeft()), height(temp.getRight())));
        temp.setBalanceFactor(height(temp.getLeft()) - height(temp.getRight()));
        return temp;
    }
}
