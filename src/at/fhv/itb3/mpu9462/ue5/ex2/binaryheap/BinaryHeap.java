/**
 * ITB3_1
 * Homework 5
 * Exercise 2
 * @author Tomio Maximilian
 */

package at.fhv.itb3.mpu9462.ue5.ex2.binaryheap;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * This class can save specified values in order of their priority (decided by a comparator).
 * Structure is that of a Leftist Height Biased Tree.
 * @param <T> The type of value that is handled by an Object of this class. Must extend Comparable<T>.
 */
public class BinaryHeap<T extends Comparable<T>> {

    private Comparator<T> comp;
    private Node root;

    /**
     * Constructor; Creates a new BinaryHeap Object.
     * @param c Comparator that is used to determine Priority of added values of this Heap. Must implement Comparator<T> interface
     */
    public BinaryHeap(Comparator<T> c){
        comp = c;
    }

    /**
     * Binary Node class that saves a specified value.
     * Knows its left and right child, as well as its predecessor (parent).
     */
    private class Node{
        private T value;
        private Node left;
        private Node right;
        private Node parent;


        /**
         * Constructor; Creates a new Node Object with a specified value T.
         * @param val Value of the new node.
         */
        public Node(T val){
            value = val;
        }

        /**
         * Swaps left child-node and right child-node.
         */
        public void swapChildren() {
            Node temp = left;
            left = right;
            right = temp;
        }
    }

    /**
     * Queues a new value onto the Heap. Value will merged into the tree as to not violate the law that nodes are ordered
     * in descending priority.
     * @param value That is added to the Heap.
     */
    public void enqueue(T value){
        if(root == null){
            root = new Node(value);
        }else{
            Node newNode = new Node(value);
            root = merge(root, newNode);
        }
    }

    /**
     * Returns the value of highest Priority. (Will always return root, since root is always of highest priority) The resulting
     * two trees will be merged.
     * @return root value
     * @throws NoSuchElementException if root == null
     */
    public T dequeue() throws NoSuchElementException{
        if(root == null){
            throw new NoSuchElementException();
        }else {
            T temp = root.value;
            if(root.left == null && root.right == null){
                root = null;
            }else{
                root = merge(root.right, root.left);
            }
            return temp;
        }
    }

    /**
     * Recursive function that merges two root Nodes together to a tree. The bigger node of the two will become the new root.
     * Bigger -> The node which is deemed of higher priority by the comparator of this tree. Comparator is added in the
     * constructor of this class, and can be custom-made.
     * @param treeNode The root node that is already on the tree.
     * @param newNode The root node that gets added and merged with the other.
     */
    private Node merge(Node treeNode, Node newNode) {
        if(treeNode == null)
            return newNode;
        if(newNode == null)
            return treeNode;

        if(comp.compare(treeNode.value, newNode.value) < 1) {
            Node temp = treeNode;
            treeNode = newNode;
            newNode = temp;
        }
        treeNode.right = merge(treeNode.right, newNode); //Merge to the right side

        if(treeNode.left == null) { //If leftnode is empty, move to the left (Because: Leftist Structure)
            treeNode.left = treeNode.right;
            treeNode.right = null;
        } else {//Else move bigger branch to the left (Because: Leftist Structure)
            if(pathToLeaf(treeNode.left) < pathToLeaf(treeNode.right)) {
                Node temp = treeNode.left;
                treeNode.left = treeNode.right;
                treeNode.right = temp;
            }
        }
        return treeNode;
    }

    /**
     * Recursive function that finds the shortest path to a leaf(nil-node).
     * @param n Node that starts the count at first, when called in recursion, child-node of the previous.
     * @return The number of nodes that have to be passed to get to a leaf(nil-node).
     */
    private int pathToLeaf(Node n){
        if(n == null){
            return 0;
        }
        if(n.left == null && n.right == null){
            return 1;
        }else {
            return Math.min(pathToLeaf(n.left), pathToLeaf(n.right)) + 1;
        }
    }

    /**
     * @return true if root is null, therefore tree is empty
     */
    public boolean isEmpty(){
        return (root == null);
    }
}
