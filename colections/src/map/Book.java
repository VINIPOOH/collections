package map;

public interface Book<K,V> {
    boolean insert(K key, V value);
    boolean delete(K key);
    V get(K key);
    int size();


}
