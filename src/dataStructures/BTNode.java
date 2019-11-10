package dataStructures;

class BTNode<E> implements Node<E>{
	
	protected BTNode<E> parent;
	protected BTNode<E> left;
	protected BTNode<E> right;
	protected E element;

	
	public BTNode(E elem,BTNode<E> parent,BTNode<E> left,BTNode<E> right){
		this.parent=parent;
		this.left=left;
		this.right=right;
		element=elem;
	}
	public BTNode(E elem){
		this(elem,null,null,null);
	}
	@Override
	public E getElement() {
		return element;
	}
	//...
	public Node<E> getLeft() {
		return left;
	}
	
	public Node<E> getRight() {
		return right;
	}

	public Node<E> getParent() {
		return parent;
	}
}
