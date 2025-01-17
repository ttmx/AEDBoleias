package dataStructures;

import java.io.Serializable;
/**
 * This class implements a Binary Search Tree.
 * 
 * @param <K> Comparable key type
 * @param <V> Element type to store
 * @author Tiago Teles
 */
public class BST<K extends Comparable<K>,V> implements SortedMap<K,V> {

	/**
	 * This class implements the nodes of a Binary Search Tree. 
	 * It keeps track of it's parent, left and right nodes,
	 * along it the content of the node.
	 * @param <E> Type of elements that the node stores
	 */
	 static class BSTNode<E> implements Serializable {
		
		protected BSTNode<E> parent;
		protected BSTNode<E> left;
		protected BSTNode<E> right;
		protected E element;

		
		BSTNode(E elem,BSTNode<E> parent,BSTNode<E> left,BSTNode<E> right){
			this.parent=parent;
			this.left=left;
			this.right=right;
			element=elem;
		}
		BSTNode(E elem){
			this(elem,null,null,null);
		}
		
		E getElement() {
			return element;
		}
		BSTNode<E> getLeft() {
			return left;
		}
		
		BSTNode<E> getRight() {
			return right;
		}

		BSTNode<E> getParent() {
			return parent;
		}
		boolean isInternal() {
			return (left != null || right !=null);
		}
		
	}

	//The root of the tree
	protected BSTNode<Entry<K,V>> root;
		
	//Number of elements
	protected int currentSize;
	
	@Override
	public Iterator<Entry<K,V>> iterator() {
		return new BSTOrderIterator<K,V>(root);
	}
	
	@Override
	public Iterator<K> keys() throws NoElementException {
		return new BSTKeyIterator<K,V>(root);
	}
	@Override
	public Iterator<V> values() throws NoElementException {
		return new BSTValueIterator<K,V>(root);
	}
	
	protected BSTNode<Entry<K,V>> findNode(BSTNode<Entry<K,V>> n, K key) {
		BSTNode<Entry<K,V>> res=null;
		if (n!=null) {
			int num = key.compareTo(n.getElement().getKey());
			if (num==0)
				res=n;
			else if (num<0)
				res=findNode(n.getLeft(),key);
			else
				res=findNode(n.getRight(),key);
		}
		return res;
	}
	
	@Override
	public V find(K key) {
		BSTNode<Entry<K,V>> res=findNode(root,key);
		if (res==null)
			return null;
		return res.getElement().getValue();
	}
	@Override
	public V insert(K key, V value) {
		if(root==null){
			root = new BSTNode<Entry<K,V>>(new EntryClass<K,V>(key,value));
			currentSize++;
			return null;
		}
		BSTNode<Entry<K,V>> next = root;
		BSTNode<Entry<K,V>> current = null;
		while(true){
			current = next;
			if(key.compareTo(next.element.getKey())<0){
				next = next.left;
				if(next==null){
					current.left = new BSTNode<>(new EntryClass<K,V>(key,value),current,null,null);
					currentSize++;
					return null;
				}
			}else if(key.compareTo(next.element.getKey())>0){
				next = next.right;
				if(next==null){
					current.right = new BSTNode<>(new EntryClass<K,V>(key,value),current,null,null);
					currentSize++;
					return null;
				}
			}else{
				V toReturn = next.getElement().getValue();
				next.element = new EntryClass<K,V>(key,value);
				return toReturn;
			}
		}
	}
	/**	
	 * Remove from the tree the element with K key. 
	 * If the key is not found it's left unchanged.
	 * @return null if it didn't find the node, the node removed if it found the node with the K key
	 */
	@Override public V remove(K key) {
		BSTNode<Entry<K,V>> toRemove = findNode(root,key);
		if(toRemove==null)
			return null;
		V toReturn = toRemove.getElement().getValue();
		BSTNode<Entry<K,V>> parent = toRemove.getParent();
		boolean isLeft = (parent != null) && toRemove.getElement().getKey().compareTo(parent.getElement().getKey()) < 0;

		if(!toRemove.isInternal()) {
			//Its a leaf
			if (toRemove == root){
				root = null;
			}else if(isLeft){
				parent.left = null;
			}else{
				parent.right = null;
			}
		} else if (toRemove.right == null) {
			//Only has left
			if (toRemove == root) {
				root = toRemove.left;
			}else if(isLeft) {
				parent.left = toRemove.left;
			}else {
				parent.right = toRemove.left;
			}
			toRemove.left.parent = parent;
		} else if (toRemove.left == null){
			//Only has right
			if (toRemove == root) {
				root = toRemove.right;
			}else if(isLeft) {
				parent.left = toRemove.right;
			}else{
				parent.right = toRemove.right;
			}
			toRemove.right.parent = parent;
		}else if(toRemove.getRight()!=null && toRemove.getLeft()!=null){
			BSTNode<Entry<K,V>> maxOfMin = maxNode(toRemove.parent.getLeft());
			if(toRemove == root)
				root = maxOfMin;
			else if(isLeft)
				parent.left = maxOfMin;
			else
				parent.right = maxOfMin;
			maxOfMin.parent = parent;
			maxOfMin.left = toRemove.left;
			if(maxOfMin.getLeft() !=null)
				maxOfMin.getLeft().parent = maxOfMin;
			if(maxOfMin.getRight() != null)
				maxOfMin.getRight().parent = maxOfMin;
		}
		currentSize--;
		return toReturn;
	}

	@Override 
	public Entry<K, V> minEntry() throws NoElementException {
		if(this.isEmpty())
			throw new NoElementException();
		return this.minNode(root).getElement();
	}
	/**
	 * Returns the maxNode to the left of node
	 * @to-check is this really what you want ?
	 * @param node the node for which we want to determine the max
	 * @return the maxnode to the left of node.
	 */
	protected BSTNode<Entry<K,V>> minNode( BSTNode<Entry<K,V>> node ){
		if ( node.getLeft() == null )
			return node;
		return this.maxNode(node.getLeft());
	}
	@Override
	public Entry<K, V> maxEntry() throws NoElementException {
		if ( this.isEmpty() )
			throw new NoElementException();
		return this.maxNode(root).getElement();
	}
	// Precondition: node != null.
	protected BSTNode<Entry<K,V>> maxNode( BSTNode<Entry<K,V>> node ){
		if ( node.getRight() == null )
			return node;
		return this.maxNode(node.getRight());
	}

	@Override
	public boolean isEmpty() {
		return currentSize==0;
	}

	@Override
	public int size() {
		return currentSize;
	}
	

}
