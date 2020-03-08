package set;

import java.util.Iterator;

public class HashMySet<E> implements MySet<E>, Iterable<E>{
    private Entry[] buckets;
    private int size = 0;


    public HashMySet(int capacity) {
        buckets = new Entry[capacity];
    }

    @Override
    public boolean add(E e) {
        int index = hashFunction(e.hashCode());
        Entry current = buckets[index];

        while (current != null) {
            if (current.key.equals(e)) {
                return false;
            }
            current = current.next;
        }

        Entry entry = new Entry();

        entry.key = e;
        entry.next = buckets[index];
        buckets[index] = entry;
        size++;
        return true;
    }

    private int hashFunction(int hashCode) {
        int index = hashCode;

        if (index < 0) {
            index = -index;
        }
        return index % buckets.length;
    }

    @Override
    public boolean remove(E e) {
        int index = hashFunction(e.hashCode());
        Entry current = buckets[index];
        Entry previous = null;

        while (current != null) {
            if (current.key.equals(e)) {
                removeCurrent(index, current, previous);
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    private void removeCurrent(int index, Entry current, Entry previous) {
        if (previous == null) {
            buckets[index] = current.next;
        } else {
            previous.next = current.next;
        }
    }

    @Override
    public boolean contains(E e) {
        int index = hashFunction(e.hashCode());
        Entry current = buckets[index];

        while (current != null) {
            if (current.key.equals(e)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    static class Entry<E> {
        E key;
        Entry<E> next;
    }

    public class SimpleHashSetIterator implements Iterator {
        private int currentBucket = -1;
        private int previousBucket = -1;
        private Entry currentEntry = null;
        private Entry previousEntry = null;

        @Override
        public boolean hasNext() {
            if (currentEntry != null && currentEntry.next != null) {
                return true;
            }

            for (int index = currentBucket+1; index < buckets.length; index++) {
                if (buckets[index] != null) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Object next() {
            return null;
        }
    }

}
