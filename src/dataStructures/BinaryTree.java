package dataStructures;

public abstract class BinaryTree<E> extends Tree<E> {
	
	//Returns the node of n's left child (or null if no child exists).
	protected abstract Node<E> left(Node<E> n);
	
	//Returns the node of n's right child (or null if no child exists).
	protected abstract Node<E> right(Node<E> n);
	
	//Returns the node of n's parent (or null if no parent exists).
	protected abstract Node<E> parent(Node<E> n);
	
	//Returns the node of n's sibling (or null if no sibling exists).
	protected Node<E> sibling(Node<E> n){
		Node<E> parent=parent(n);
		if (parent==null) //the root
			return null;
		if (n==left(parent))
			return right(parent);
		return left(parent);
	}
	
	//Returns the number of children of node n
	protected int numChildren(Node<E> n) {
		int count=0;
		if (left(n)!=null)
			count++;
		if (right(n)!=null)
			count++;
		return count;
	}
	
	//Returns an iterator containing the children of node n (if any).
	protected Iterator<Node<E>> children(Node<E> n){
		if (isExternal(n))
			return null;
		List<Node<E>> aux = new SinglyLinkedList<Node<E>>(2);
		if (left(n)!=null)
			aux.addLast(left(n));
		if (right(n)!=null)
			aux.addLast(right(n));
		return aux.iterator();
	}
	
}
