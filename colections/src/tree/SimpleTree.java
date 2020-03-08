package tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SimpleTree<E> implements Tree<E>{
    private Leaf<E> root;
    private List<E> list;
    private int size = 0;

    public SimpleTree(){
        list = new LinkedList<>();
        root = new Leaf<>(null);
    }

    @Override
    public boolean add(E e) {
        if(size == 0){
            return initRootLeaf(e);
        }
        Leaf<E> newNode = new Leaf<>(e);
        Leaf<E> lastNode = findLastLeaf(root, newNode);

        if(lastNode == null){
            return false;
        }

        size++;

        newNode.parent = lastNode;

        if(lastNode.compareTo(newNode) < 0){
            lastNode.right = newNode;
            return true;
        }
        else{
            lastNode.left = newNode;
            return true;
        }

    }

    private Leaf<E> findLastLeaf(final Leaf<E> oldLeaf,final Leaf<E> newLeaf) {
        Leaf<E> lastLeaf = oldLeaf;
        int compare = oldLeaf.compareTo(newLeaf);

        if(compare < 0 && oldLeaf.right != null){
            lastLeaf = findLastLeaf(oldLeaf.right, newLeaf);
            return lastLeaf;
        }

        if (compare > 0 && oldLeaf.left != null){
            lastLeaf = findLastLeaf(oldLeaf.left, newLeaf);
            return lastLeaf;
        }
        return lastLeaf;
    }

    private boolean initRootLeaf(E e) {
        root.element = e;
        size++;
        return true;

    }

    @Override
    public List<E> get(){
        for (E e : this) {
            list.add(e);
        }
        return list;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Leaf find(E e) {
        Leaf<E> eLeaf = new Leaf<>(e);
        return binarySearch(root,eLeaf);
    }

    private Leaf binarySearch(Leaf<E> leaf, Leaf<E> eLeaf) {
        int compare = leaf.compareTo(eLeaf);
        if(compare < 0 && leaf.right != null){
            return binarySearch(leaf.right, eLeaf);
        }
        if(compare > 0 && leaf.left != null){
            return binarySearch(leaf.left, eLeaf);
        }
        if(compare == 0){
            return leaf;
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int count = 0;
            Iterator<Leaf<E>> iterator = new TreeIterator<>(root);
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                return iterator.next().element;
            }

            @Override
            public void remove() {

            }
        };
    }

    private class TreeIterator<E> implements Iterator<Leaf<E>>{
        private Leaf<E> next;

        private TreeIterator(Leaf<E> root) {
            this.next = root;
            goToLeftMost();
        }

        private void goToLeftMost() {
            while(next.left != null){
                next = next.left;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null && next.element != null;
        }

        @Override
        public Leaf<E> next() {
            Leaf<E> r = next;
            if(next.right != null)
                return goRight(r);
            return goUp(r);
        }

        private Leaf<E> goRight(Leaf<E> r) {
            next = next.right;
            while(next.left != null){
                next = next.left;
            }
            return r;
        }

        private Leaf<E> goUp(Leaf<E> r) {
            while(true){
                if(next.parent == null){
                    next = null;
                    return r;
                }
                if(next.parent.left == next){
                    next = next.parent;
                    return r;
                }
                next = next.parent;
            }

        }



        @Override
        public void remove() {

        }
    }

    class Leaf<E> implements Comparable<E> {
        private Leaf<E> parent;
        private Leaf<E> right;
        private Leaf<E> left;
        private E element;

        public Leaf(E element) {
            this.element = element;
        }

        @Override
        public int hashCode() {
            int hash = 31;
            hash = hash * 17 + element.hashCode();
            return hash;
        }

        @Override
        public int compareTo(Object obj) {
            Leaf<E> node = (Leaf<E>) obj;

            return this.hashCode() - node.hashCode();
        }
    }

}
