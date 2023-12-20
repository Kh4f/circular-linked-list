package io.github.kh4f;

import util.LinkedList;
import java.util.Arrays;
import java.util.Iterator;

public class CircularLinkedList<T> implements Iterable<T>, LinkedList<T> {

    public static class CircularLinkedListException extends Exception {
        public CircularLinkedListException(String message) {
            super(message);
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this(value, null);
        }
        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }
        public void setValue(T value) {
            this.value = value;
        }
        public Node<T> getNext() {
            return next;
        }
    }

    public CircularLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void checkEmpty() throws CircularLinkedListException {
        if (isEmpty()) throw new CircularLinkedListException("Empty list");
    }

    public Node<T> getHead() throws  CircularLinkedListException {
        checkEmpty();
        return head;
    }

    public Node<T> getTail() throws CircularLinkedListException {
        checkEmpty();
        return tail;
    }

    public T getFirst() throws CircularLinkedListException {
        checkEmpty();
        return head.value;
    }

    public T getLast() throws CircularLinkedListException {
        checkEmpty();
        return tail.value;
    }

    public void addFirst(T value) {
        head = new Node<>(value, head);
        if (isEmpty()) {
            tail = head;
        }
        tail.next = head;
        size++;
    }

    public void addLast(T value) {
        if (isEmpty()) {
            addFirst(value);
            return;
        }
        tail.next = new Node<T>(value, head);
        tail = tail.next;
        size++;
    }

    public void removeFirst() throws CircularLinkedListException {
        checkEmpty();
        head = head.next;
        tail.next = head;
        size--;
    }

    public void removeLast() throws CircularLinkedListException {
        checkEmpty();
        tail = getNode(size() - 1);
        tail.next = head;
        size--;
    }

    public void removeAll() throws CircularLinkedListException {
        while (!isEmpty()) {
            removeLast();
        }
    }

    public void insert(int index, T value) throws CircularLinkedListException {
        if (index == 0) {
            addFirst(value);
            return;
        } else if (index == size) {
            addLast(value);
            return;
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
        Node<T> newNode = new Node<>(value);
        Node<T> prevNode = getNode(index - 1);

        newNode.next = prevNode.next;
        prevNode.next = newNode;
        size++;
    }

    public void remove(int index) throws CircularLinkedListException {
        checkEmpty();

        if (index == 0) {
            removeFirst();
            return;
        } else if (index == (size - 1)) {
            removeLast();
            return;
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }

        Node<T> prevNode = getNode(index - 1);
        Node<T> currNode = prevNode.next;
        prevNode.next = currNode.next;
        size--;
    }

    private Node<T> getNode(int index) throws CircularLinkedListException {
        checkEmpty();

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index must be grater or equal 0");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index must be below %d", size));
        }
        int counter = 0;
        Node<T> current = head;
        while (counter++ < index) {
            current = current.next;
        }
        return current;
    }

    public T get(int index) throws CircularLinkedListException {
        return getNode(index).getValue();
    }

    public String toString() {
        return Arrays.toString(toStringArray());
    }

    public String[] toStringArray() {
        String[] newAr = new String[size()];

        Node<T> currNode = head;
        int counter = 0;
        while (counter < size) {
            newAr[counter] = String.valueOf(currNode.getValue());
            currNode = currNode.getNext();
            counter++;
        }

        return newAr;
    }

    public Iterator<T> iterator() {
        class CircularLinkedListIterator implements Iterator<T> {
            Node<T> curr = head;
            int count = 0;

            @Override
            public boolean hasNext() {
                return count!=size;
            }

            @Override
            public T next() {
                T value = curr.value;
                curr = curr.next;
                count++;
                return value;
            }
        }

        return new CircularLinkedListIterator();
    }
}