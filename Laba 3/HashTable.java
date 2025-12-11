import java.util.*;


public class HashTable<K, V> {
    private static class Entry<K, V> {  // создаем класс одной пары ключ-значение
        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        K getKey() { return key; }
        V getValue() { return value; }
        void setValue(V value) { this.value = value; }
    }

    private LinkedList<Entry<K, V>>[] table;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public HashTable() {
        table = (LinkedList<Entry<K, V>>[]) new LinkedList[DEFAULT_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public HashTable(int capacity) {
        if (capacity <= 0) capacity = DEFAULT_CAPACITY;
        table = (LinkedList<Entry<K, V>>[]) new LinkedList[capacity];
        size = 0;
    }

    private int hash(K key) {
        if (key == null) return 0;
        return (key.hashCode() & 0x7FFFFFFF) % table.length;
    }

    public void put(K key, V value) {
        int index = hash(key);
        
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        
        for (Entry<K, V> entry : table[index]) { 
            if (Objects.equals(entry.getKey(), key)) {
                entry.setValue(value);
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        
        if (table[index] == null) return null;
        
        for (Entry<K, V> entry : table[index]) {
            if (Objects.equals(entry.getKey(), key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public V remove(K key) {
        int index = hash(key);
        if (table[index] == null) return null;
        
        Iterator<Entry<K, V>> it = table[index].iterator();
        while (it.hasNext()) {
            Entry<K, V> entry = it.next();
            if (Objects.equals(entry.getKey(), key)) {
                V val = entry.getValue();
                it.remove();
                size--;
                if (table[index].isEmpty()) table[index] = null;
                return val;
            }
        }
        return null;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }
}
