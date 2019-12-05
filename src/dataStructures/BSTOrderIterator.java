package dataStructures;

import dataStructures.BST.BSTNode;

public class BSTOrderIterator<K,V> implements Iterator<Entry<K,V>> {

	Stack<BSTNode<Entry<K,V>>> entries;
	BSTNode<Entry<K,V>> root;

	public BSTOrderIterator(BSTNode<Entry<K,V>> root) {
		entries = new StackInList<BSTNode<Entry<K,V>>>();
		this.root = root;
		while (root != null) {
			entries.push(root);
			root = root.left;
		}
	}
	@Override
	public boolean hasNext() {
		return !entries.isEmpty();
	}

	@Override
	public Entry<K,V> next() {
		BSTNode<Entry<K,V>> n = entries.pop();
		Entry<K,V> result = n.getElement();
		if (n.right != null) {
			n = n.right;
			while (n != null) {
				entries.push(n);
				n = n.left;
			}
		}
		return result;
	}

	@Override
	public void rewind() {
	    entries = new StackInList<BSTNode<Entry<K,V>>>();
		while (root != null) {
			entries.push(root);
			root = root.left;
		}
	}

}
