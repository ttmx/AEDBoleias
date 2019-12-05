package dataStructures;

public abstract class Tree<E> {
	
	//The root
	protected Node<E> root;
	
	//Number of elements
	protected int currentSize;
	
	//Returns the position of the root of the tree (or null if empty).
	protected Node<E> root(){
		return root;
	}
	
	//Returns the node of the parent of node n (or null if n is the root).
	protected abstract Node<E> parent(Node<E> n);
	
	//Returns an iterator containing the children of node n (if any).
	protected abstract Iterator<Node<E>> children(Node<E> n);
	
	//Returns the number of children of node n
	protected abstract int numChildren(Node<E> n);
	
	//Returns true if node n has at least one child.
	protected boolean isInternal(Node<E> n) {
		return numChildren(n)>=1;
	}
	
	//Returns true if node n does not have any children.
	protected boolean isExternal(Node<E> n) {
		return numChildren(n)==0;
	}
	
	//Returns true if node n is the root of the tree.
	protected boolean isRoot(Node<E> n) {
		return n==root;
	}
	
	//Returns  the  number  of  elements that are contained in the tree.
	public int size() {
		return currentSize;
	}
	
	//Returns true if  the  tree  does  not  contain  any elements
	public boolean isEmpty() {
		return currentSize==0;
	}

	//Returns an iterator for all elements in the tree
	public abstract Iterator<E> iterator();

	//Returns the height of the subtree rooted at Node n.
	protected int height(Node<E> n) {
		int h=1;
		if (isExternal(n))
			return h;
		Iterator <Node<E>> it =children(n);
		while (it.hasNext())
			h=Math.max(h,1+ height(it.next()));
		return h;
	}
	
	//Returns the height of the tree rooted at Node n.
	public int height() {
		if(isEmpty())
			return 0;
		return height(root);
	}

}
