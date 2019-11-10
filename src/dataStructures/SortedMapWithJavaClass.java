package dataStructures;

public class SortedMapWithJavaClass<K extends Comparable<K>, V> implements SortedMap<K, V> {

	protected java.util.SortedMap<K,V> elementos;
	protected int capPrevista;


	public SortedMapWithJavaClass(int capPrevista) {
		elementos = new java.util.TreeMap<K,V>();
		this.capPrevista = capPrevista;
	}
	
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<K> keys() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<V> values() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Entry<K, V>> iterator() throws NoElementException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V find(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V insert(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
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
