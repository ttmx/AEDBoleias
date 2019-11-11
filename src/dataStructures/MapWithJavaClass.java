package dataStructures;

public class MapWithJavaClass<K,V> implements Map<K,V> {
	
	protected java.util.Map<K,V> elementos;
	protected int capPrevista;

	public MapWithJavaClass(int prevusers) {
		elementos = new java.util.HashMap<K,V>(prevusers);
		capPrevista = prevusers;
	}

	@Override
	public boolean isEmpty() {
		return elementos.isEmpty();
	}

	@Override
	public int size() {
		return elementos.size();
	}

	@Override
	public Iterator<K> keys() throws NoElementException {
		// TODO Auto-generated method stub
		K[] arrKeys = (K[]) elementos.keySet().toArray();
		return new ArrayIterator<K>(arrKeys, arrKeys.length);
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		// TODO Auto-generated method stub
		V[] arrValues = (V[]) elementos.values().toArray();
		return new ArrayIterator<V>(arrValues, arrValues.length);
	}

	@Override
	public Iterator<Entry<K, V>> iterator() throws NoElementException {
		Entry<K,V>[] arr = (Entry<K, V>[]) elementos.entrySet().toArray();
		return new ArrayIterator<Entry<K,V>>(arr, arr.length);
	}

	@Override
	public V find(K key) {
		return elementos.get(key);
	}

	@Override
	public V insert(K key, V value) {
		return elementos.put(key, value);
	}

	@Override
	public V remove(K key) {
		return elementos.remove(key);
	}

}
