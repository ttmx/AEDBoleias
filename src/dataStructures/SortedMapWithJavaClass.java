package dataStructures;

public class SortedMapWithJavaClass<K extends Comparable<K>, V> implements SortedMap<K, V> {

	protected java.util.SortedMap<K,V> elementos;


	public SortedMapWithJavaClass() {
		elementos = new java.util.TreeMap<K,V>();
	}
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return elementos.isEmpty();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return elementos.size();
	}

	@Override
	public Iterator<K> keys() throws NoElementException {
		return new IteratorWithJavaClass<>(elementos.keySet().iterator());
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		// TODO Auto-generated method stub
		return new IteratorWithJavaClass<>(elementos.values().iterator());
	}

	@Override
	public Iterator<Entry<K, V>> iterator() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V find(K key) {
		// TODO Auto-generated method stub
		return elementos.get(key);
	}

	@Override
	public V insert(K key, V value) {
		return elementos.put(key,value);
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return elementos.remove(key);
	}

	@Override
	public Entry<K, V> minEntry() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry<K, V> maxEntry() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

}
