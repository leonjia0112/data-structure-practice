package datastructure.implementation.practive;

import java.util.Arrays;

public class MyHashMap<K, V> {
	
	public static class Node<K, V> {
		final K key;
		V value;
		Node<K, V> next;
		Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	// static final variable are global constants
	public static final int DEFAULT_CAPACITY = 16;
	public static final float DEFAULT_LOAD_FACTOR = 0.75f;
	
	private Node<K, V>[] table;
	private int size; // number of key-value pair are actually stored in this HashMap
	private float loadFactor; // determine when to rehash
	
	public MyHashMap() {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	public MyHashMap(int cap, float loadfactor) {
		if (size < 0) {
			throw new IllegalArgumentException("size must larger or equal than 0");
		}
		this.table = (Node<K, V>[]) (new Node[size]);
		this.size = 0;
		this.loadFactor = this.DEFAULT_LOAD_FACTOR;
	}
	
	public V put(K key, V value) {
		int index = getIndex(key);
		Node<K, V> head = table[index];
		Node<K, V> node = head;
		while(node != null) {
			if (equalsKey(node.key, key)) {
				V result = node.value;
				node.value = value;
				return result;
			}
			node = node.next;
		}
		
		Node<K, V> newNode = new Node(key, value);
		newNode.next = head;
		table[index] = newNode;
		size++;
		if (needRehashing()) {
			rehashing();
		}
		return null;
	}
	
	public V remove(K key) {
		int index = getIndex(key);
		Node<K, V> node = table[index];
		while(node.next != null) {
			if (equalsKey(node.next.key, key)) {
				return node.next.value;
			}
			node = node.next;
		}
		return null;
	}
	
	private void rehashing() {
		// double table size
		Node<K, V>[] oldTable = this.table;
        this.table = (Node<K, V>[]) (new Node[size * 2]);
        this.size *= 2;
		for (Node n : oldTable) {
			int index = getIndex((K) n.getKey());
			table[index] = new Node(n.getKey(), n.getValue());
		}
	}


	public int size() {
		return table.length;
	}
	
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	public void clear() {
		Arrays.fill(table, null);
		size = 0;
	}
	
	public V get(K key) {
		int index = getIndex(key);
		
		// null key return the first entry in the bucket
		if (key == null) {
			return table[0].value;
		}
		
		Node<K, V> node = table[index];
		while(node != null) {
			if (equalsKey(node.key, key)) {
				return node.value;
			}
			node = node.next;
		}
		return null;
	}
	
	private boolean needRehashing() {
		float ratio = (size + 0.0f) / table.length;
		return ratio >= loadFactor;
	}
	
	private int hash(K key) {
		if (key == null) {
			return 0;
		}
		return key.hashCode() & 0x7FFFFFFF;
	}
	
	private int getIndex(K key) {
		return hash(key) % table.length;
	}
	
	public boolean containsValue(V value) {
		if (isEmpty()) {
			return false;
		}
		
		for (Node<K, V> node : table) {
			while (node != null) {
				if (equalsValue(node.value, value)) {
			        return true;		
				}
				node = node.next;
		   }
		}
		return false;
	}
	
	public boolean containsKey(K key) {
		// get the index of the key
		int index = getIndex(key);
		Node<K, V> node = table[index];
		while(node != null) {
			if (equalsKey(node.key, key)) {
				return true;
			}
			node = node.next;
		}
		return false;
	}
	
	private boolean equalsValue(V v1, V v2) {
		// v1 and v2 are both possible to be null
//		if (v1 == null && v2 == null) 
//			return true;
//		if (v1 == null || v2 == null)
//			return false;
//		return v1.equals(v2);
		return v1 == v2 || v1 != null && v1.equals(v2);
	}
	
	private boolean equalsKey(K k1, K k2) {
		// k1, k2 all possibly to the null
//		if (k1 == null && k2 == null)
//			return true;
//		if (k2 == null || k2 == null)
//			return false;
//		return k1.equals(k2);
		return k1 == k2 || k1 != null && k1.equals(k2);
	}
}
