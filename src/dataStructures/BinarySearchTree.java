package dataStructures;

public class BinarySearchTree<K extends Comparable<K>,V> extends BinaryTree<Entry<K,V>> implements SortedMap<K,V>{
	
	protected BinarySearchTree(Node<Entry<K,V>> n) {
		root=n;
	}
	public BinarySearchTree() {
		this(null);
	}
	
	@Override
	protected Node<Entry<K, V>> left(Node<Entry<K, V>> n) {
		return ((BTNode<Entry<K, V>>)n).getLeft();
	}
	@Override
	protected Node<Entry<K, V>> right(Node<Entry<K, V>> n) {
		return ((BTNode<Entry<K, V>>)n).getRight();
	}
	@Override
	protected Node<Entry<K, V>> parent(Node<Entry<K, V>> n) {
		return ((BTNode<Entry<K, V>>)n).getParent();
	}
	
	@Override
	public Iterator<Entry<K,V>> iterator() {
		return new BSTOrderIterator<K,V>(root);
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
	
	private Node<Entry<K,V>> findNode(Node<Entry<K,V>> n, K key) {
		Node<Entry<K,V>> res=null;
		if (n!=null) {
			int num= n.getElement().getKey().compareTo(key);	
			if (num==0)
				res=n;
			else if (num<0)
				res=findNode(left(n),key);
			else
				res=findNode(right(n),key);
		}
		return res;
	}
	
	@Override
	public V find(K key) {
		Node<Entry<K,V>> res=findNode(root,key);
		if (res==null)
			return null;
		return res.getElement().getValue();
	}
	@Override
	public V insert(K key, V value) {
		// TODO 
		return null;
	}
	@Override
	public V remove(K key) {
		// TODO 
		return null;
	}
	@Override
	public Entry<K, V> minEntry() throws NoElementException {
		// TODO 
		return null;
	}
	@Override
	public Entry<K, V> maxEntry() throws NoElementException {
		if ( this.isEmpty() )
			throw new NoElementException();
		return this.maxNode(root).getElement();
	}
	// Precondition: node != null.
	protected Node<Entry<K,V>> maxNode( Node<Entry<K,V>> node ){
		if ( ((BTNode<Entry<K, V>>) node).getRight() == null )
			return node;
		return this.maxNode(( (BTNode<Entry<K, V>>) node).getRight());
	}
	

}
