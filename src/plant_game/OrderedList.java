/*
MAYBE LATER
 */
package plant_game;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Used to sort high scores within the plant game.
 * 
 * Links in new objects comparing them so that they remain in order of large to small
 *
 * @author breco
 * @param <E>
 */
public class OrderedList<E extends Comparable<E>> extends AbstractList {

    private int numElements;
    private Node<E> firstNode;

    public OrderedList() {
        super();
        numElements = 0;
        firstNode = null;

    }

    // constructor for creating a new set which
    // initially holds the elements in the collection c
    public OrderedList(Collection<? extends E> c) {
        this();
        c.forEach(element -> {
            add(element);
        });
    }

    public boolean add(E o) {
        //To return
        boolean possible = false;
        Node<E> newNode = new Node<E>(o);

        //If the first node is blank we add the elemnt
        if (this.firstNode == null) {
            newNode.next = firstNode;
            firstNode = newNode;
            numElements++;
            possible = true;
        } //If first node contains an object this searches through the nodes comparing itself to other elements to find its possition.
        else {
            Node<E> currentNode = this.firstNode;

            //If the next node is not null we search to see where to add object
            if (currentNode.next != null) {

                //Special case the node is added to start of chain
                if (o.compareTo(currentNode.element) < 0) {

                    newNode.next = currentNode;
                    firstNode = newNode;
                    numElements++;
                    possible = true;
                } else {
                    //if the next object is not null and it is greater then the next element we cycel to the next node.
                    while (currentNode.next != null && o.compareTo(currentNode.next.element) >= 0) {
                        currentNode = currentNode.next;
                    }

                    //We add the current object in the appropriate possition 
                    //Link the new nodes next element to the the curren elements next element
                    newNode.next = currentNode.next;
                    //set the current elements next node as the new node
                    currentNode.next = newNode;

                    numElements++;
                    possible = true;
                }

            } //If the next node is null we add in.
            else {
                //Special case the current value is less than the first node add to start of chain
                if (o.compareTo(currentNode.element) < 0) {
                    newNode.next = currentNode;
                    firstNode = newNode;
                    numElements++;
                    possible = true;
                } else {
                    //Add the node to the end of the chain
                    newNode.next = currentNode.next;
                    currentNode.next = newNode;
                    numElements++;
                    possible = true;
                }
            }
        }
        //return wether adding was possible or not.
        return possible;
    }

    @Override
    public Object get(int index) {

        if (index > this.size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        //Creates a node to hold current nodes chain
        Node<E> origanalNode = this.firstNode;
        //Creates a node to return results from
        Node<E> returnNode = this.firstNode;
        for (int i = 0; i < index; i++) {
            //Cycles through elements till it reachs index node
            this.firstNode = firstNode.next;
        }
        //Sets the return node equal to current node
        returnNode = this.firstNode;
        //Sets current node back to orignal chain
        this.firstNode = origanalNode;

        //Returns the element in index position
        return returnNode.element;
    }

    @Override
    public int size() {
        return this.numElements;
    }

    @Override
    /**
     * Dereference the list and sets its elements to zero.
     */
    public void clear() {
        firstNode = null;
        numElements = 0;
    }

    @Override
    /**
     * Returns the iterator for OrderedList
     */
    public Iterator<E> iterator() {
        return new LinkedSortIterator<E>(firstNode);
    }

    //Currently implimented for all elements.
    protected class Node<E> {

        //Node has an element and a link
        public E element;
        public Node<E> next;

        public Node(E a) {
            this.element = a;
            next = null;
        }
    }

    //Private itterator
    private class LinkedSortIterator<E> implements Iterator<E> {

        private Node<E> nextNode;

        //Sets up a intial node
        public LinkedSortIterator(Node<E> firstNode) {
            nextNode = firstNode;
        }

        @Override
        /**
         * Checks if nextNode is null. If it's null return false.
         */
        public boolean hasNext() {
            return (nextNode != null);
        }

        @Override
        /**
         * Links the next node in and loses reference to previous.
         */
        public E next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = nextNode.element;
            nextNode = nextNode.next;
            return element;
        }

    }
}
