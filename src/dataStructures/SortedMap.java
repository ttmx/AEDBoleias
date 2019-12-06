package dataStructures;

public interface SortedMap<K, V> extends Map<K,V>{
	
	/**
	 * @return the entry with the smallest key in the SortedMap.
	 * @throws NoElementException if isEmpty()
	 */
	Entry<K,V> minEntry( ) throws NoElementException;
	 
	/** 
	 * @return the entry with the largest key in the SortedMap.
	 * @throws NoElementException if isEmpty() 
	 * */
	Entry<K,V> maxEntry( ) throws NoElementException;
	
}
