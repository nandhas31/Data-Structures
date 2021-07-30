import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there is no
     * need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the Collection.
     * The data should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type of
     * loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data is
     *                                            null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The collection that you passed in was null");
        }
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("An element in the list that you passed in was null");
            } else if (root == null) {
                root = new BSTNode<>(item);
                size += 1;
            } else {
                add(item);
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is already in
     * the tree, then nothing should be done (the duplicate shouldn't get added, and
     * size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data that you are adding can not be null");
        }
        root = addHelper(root, data);
    }

    /**
     * Travels down the tree recursively down from the root picking the left or
     * right child as the new root based on if the data being added is greater to or
     * less than the current root
     * 
     * @param node Root of the subtree to traverse
     * @param data Data that needs to be added to the tree
     * 
     * @return New sub root of the node to add to
     */
    private BSTNode<T> addHelper(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(node.getRight(), data));
        }
        return node;
    }

    /**
     * Removes and returns the element from the tree matching the given parameter.
     *
     * There are 3 cases to consider: 1: The node containing the data is a leaf (no
     * children). In this case, simply remove it. 2: The node containing the data
     * has one child. In this case, simply replace it with its child. 3: The node
     * containing the data has 2 children. Use the predecessor to replace the data.
     * You MUST use recursion to find and remove the predecessor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that was
     * stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are searching for can not be null");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = remove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helps the array remove the node by recusring through the array
     *
     * @param subnode The node that is being currently searched for data equality
     * @param dummy The dummy node that will contain the data being returned
     * @param data    The data that is being searched for
     * @return T data that was found in the array
     * @throws NoSuchElementException If the data is not in the tree
     */
    private BSTNode<T> remove(BSTNode<T> subnode, T data, BSTNode<T> dummy) {
        if (subnode == null) {
            throw new NoSuchElementException("The element that you are searching for does not exist in the tree");
        } else if (data.compareTo(subnode.getData()) > 0) {
            subnode.setRight(remove(subnode.getRight(), data, dummy));
        } else if (data.compareTo(subnode.getData()) < 0) {
            subnode.setLeft(remove(subnode.getLeft(), data, dummy));
        } else {
            dummy.setData(subnode.getData());
            size--;
            if (subnode.getLeft() == null && subnode.getRight() == null) {
                return null;
            } else if (subnode.getRight() != null && subnode.getLeft() == null) {
                return subnode.getRight();
            } else if (subnode.getLeft() != null && subnode.getRight() == null) {
                return subnode.getLeft();
            } else {
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                subnode.setLeft(removePredecessor(subnode.getLeft(), dummy2));
                subnode.setData(dummy2.getData());
            }

        }
        return subnode;
    }

    /**
     * Helps rearrange the tree when the node being removed has two children
     * @param subnode The node that is being removed
     * @param dummy The node containing the data being removed
     * @return The node that belongs in the specified place
     */
    private BSTNode<T> removePredecessor(BSTNode<T> subnode, BSTNode<T> dummy) {
        if (subnode.getRight() == null) {
            dummy.setData(subnode.getData());
            return subnode.getLeft();
        } else {
            subnode.setRight(removePredecessor(subnode.getRight(), dummy));
        }
        return subnode;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
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
            throw new IllegalArgumentException("The data you are looking for is null");
        }
        return getHelper(data, root);
    }

    /**
     * Recursivley searches the tree for the data trying to be found
     * 
     * @param data    The data being searched for
     * @param subnode The portion of the entire tree to search
     * @return The data that matches the data being searched for
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private T getHelper(T data, BSTNode<T> subnode) {
        if (subnode == null) {
            throw new NoSuchElementException("The data was not found in the tree");
        } else if (data.compareTo(subnode.getData()) < 0) {
            return getHelper(data, subnode.getLeft());
        } else if (data.compareTo(subnode.getData()) > 0) {
            return getHelper(data, subnode.getRight());
        }
        return subnode.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained within
     * the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * 
     * This method must be implemented recursively.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are looking for is null");
        }
        return containsHelper(data, root);

    }

    /**
     * Recursivley searches the tree for the data trying to be found
     * 
     * @param data    The data being searched for
     * @param subnode The portion of the entire tree to search
     * @return False if the data is not found in the tree or true if it is
     */
    private boolean containsHelper(T data, BSTNode<T> subnode) {
        if (subnode == null) {
            return false;
        } else if (data.compareTo(subnode.getData()) < 0) {
            return containsHelper(data, subnode.getLeft());
        } else if (data.compareTo(subnode.getData()) > 0) {
            return containsHelper(data, subnode.getRight());
        }
        return true;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * This method must be implemented recursively.
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        if (root != null) {
            preorderHelper(list, root);
        }
        return list;
    }

    /**
     * Adds the data of the current node to the list and then recursively travels
     * through the binary tree to the left most element then adds the data to the
     * list then travels to its siblings then back up
     * 
     * @param list    List to add data to
     * @param subnode The subnode of the tree being searched
     */
    private void preorderHelper(List<T> list, BSTNode<T> subnode) {
        list.add(subnode.getData());
        if (subnode.getLeft() != null) {
            preorderHelper(list, subnode.getLeft());
        }
        if (subnode.getRight() != null) {
            preorderHelper(list, subnode.getRight());
        }
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        if (root != null) {
            inorderHelper(list, root);
        }
        return list;
    }

    /**
     * Recursively travels through the binary tree to the left most element then
     * adds the data to the list then travels to its siblings then back up
     * 
     * @param list    List to add data to
     * @param subnode The subnode of the tree being searched
     */
    private void inorderHelper(List<T> list, BSTNode<T> subnode) {
        if (subnode.getLeft() != null) {
            inorderHelper(list, subnode.getLeft());
        }
        list.add(subnode.getData());
        if (subnode.getRight() != null) {
            inorderHelper(list, subnode.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        if (root != null) {
            postorderHelper(list, root);
        }
        return list;
    }

    /**
     * Recursively travels through the binary tree to the left most element then
     * adds the data to the list then travels to its siblings then back up then adds
     * the data of the current node to the list
     * 
     * @param list    List to add data to
     * @param subnode The subnode of the tree being searched
     */
    private void postorderHelper(List<T> list, BSTNode<T> subnode) {
        if (subnode.getLeft() != null) {
            postorderHelper(list, subnode.getLeft());
        }
        if (subnode.getRight() != null) {
            postorderHelper(list, subnode.getRight());
        }
        list.add(subnode.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial node
     * you should add to the queue and what loop / loop conditions you should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            if (q.peek().getLeft() != null) {
                q.add(q.peek().getLeft());
            }
            if (q.peek().getRight() != null) {
                q.add(q.peek().getRight());
            }
            list.add(q.remove().getData());
        }
        return list;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A leaf node
     * has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     * 
     * This method must be implemented recursively.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root != null) {
            return heightHelper(root);
        }
        return -1;
    }

    /**
     * Recruses breadth first through the list and returns a one for every depth
     * travelled
     * 
     * @param subnode subnode of the tree being traversed
     * @return The height of the tree
     */
    private int heightHelper(BSTNode<T> subnode) {
        if (subnode.getRight() != null && subnode.getLeft() != null) {
            return 1 + Integer.max(heightHelper(subnode.getRight()), heightHelper(subnode.getLeft()));
        } else if (subnode.getRight() != null) {
            return 1 + heightHelper(subnode.getRight());
        } else if (subnode.getLeft() != null) {
            return 1 + heightHelper(subnode.getLeft());
        } else {
            return 0;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being a
     * binary search tree.
     *
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     *
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is correct,
     * that there are no duplicates, and that every parent has at most 2 children.
     * So, what you will have to check is that the order property of a BST is still
     * satisfied.
     *
     * Should run in O(n). However, you should stop the check as soon as you find
     * evidence that the tree is not a BST rather than checking the rest of the
     * tree.
     * 
     * This method must be implemented recursively.
     *
     * @param <T>      the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(BSTNode<T> treeRoot) {
        return isBSTHelper(null, null, treeRoot);
    }

    /**
     * Recurse throught the tree and sees if left child < node < right child for
     * every node in the tree
     * 
     * @param <T>     The type of data stored in the BSTNode
     * @param min the value that the data has to be greater than
     * @param max the value that the data has be less than
     * @param subnode The subnode being tested
     * @return True if the tree is a BST
     */
    public static <T extends Comparable<? super T>> boolean isBSTHelper(T min, T max, BSTNode<T> subnode) {
        if (subnode == null) {
            return true;
        } else if ((min != null && min.compareTo(subnode.getData()) > 0)
                || (max != null && max.compareTo(subnode.getData()) < 0)) {
            return false;
        } else {
            return isBSTHelper(min, subnode.getData(), subnode.getLeft())
                    && isBSTHelper(subnode.getData(), max, subnode.getRight());
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
    public BSTNode<T> getRoot() {
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
}
/**
 * if (subnode.getLeft() != null) { if
 * (subnode.getData().compareTo(subnode.getLeft().getData()) > 0) { return
 * isBSTHelper(subnode.getLeft()); } else { return false; } } if
 * (subnode.getRight() != null) { if
 * (subnode.getData().compareTo(subnode.getRight().getData()) < 0) { return
 * isBSTHelper(subnode.getRight()); } else { return false; } } return true;
 */