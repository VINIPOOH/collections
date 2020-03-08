package linkedlist;

import java.util.Iterator;

public class LinkedContainer<E> implements Linked<E>, Iterable<E>, DescendingIterator<E> {
   private Node<E> fstNode;
   private Node<E> lstNode;
   private int size = 0;

    public LinkedContainer() {
        lstNode = new Node<E> (null, fstNode,null);
        fstNode = new Node<E> (null, null, lstNode);

    }

    @Override
    public void addLast(E e) {
        Node<E> prev = lstNode;
        prev.setElem(e);
        lstNode = new Node<>(null, prev, null );
        prev.setNext(lstNode);
        size++;

    }


    @Override
    public void addFirst(E e) {
        Node<E> next = fstNode;
        next.setElem(e);
        fstNode = new Node<>(null, null, next );
        next.setPrev(fstNode); ;
        size ++;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E getElementByIndex(int counter) {
        Node<E> target = fstNode.getNext();
        for(int i = 0; i < counter; i++){
            target = getNextElement(target);
        }
        return target.getElem();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int counter = 0;
            @Override
            public boolean hasNext() {
                 return  counter < size;
            }

            @Override
            public E next() {
                return getElementByIndex(counter ++);
            }

            @Override
            public void remove() {

            }
        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            int counter;

            @Override
            public boolean hasNext() {
                return counter >= 0;
            }

            @Override
            public E next() {
                return getElementByIndex(counter --);
            }

            @Override
            public void remove() {

            }
        };
    }

    private Node<E> getNextElement(Node<E> current){
        return current.getNext();
    }

    private class Node<E>{
        private E elem;
        private Node<E> next;
        private Node<E> prev;

        public Node(E elem, Node<E> prev, Node<E> next) {
            this.elem = elem;
            this.prev = prev;
            this.next = next;

        }

        public E getElem() {
            return elem;
        }

        public void setElem(E elem) {
            this.elem = elem;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> prev) {
            this.prev = prev;
        }
    }


}
