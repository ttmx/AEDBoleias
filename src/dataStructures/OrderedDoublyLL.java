package dataStructures;

public class OrderedDoublyLL<E extends Comparable<E>>{
	
	// Node at the head of the list.
	protected DListNode<E> head;

	// Node at the tail of the list.
	protected DListNode<E> tail;

	// Number of elements in the list.
	protected int currentSize;
	

	public OrderedDoublyLL() {
		head=null;
		tail=null;
		currentSize=0;
	}
	
	public boolean isEmpty() {
		return currentSize==0;
	}

	
	public int size() {
		return currentSize;
	}
	
	public Iterator<E> iterator() throws NoElementException {
		if (currentSize==0) throw new NoElementException("OrderedDoublyLL is empty.");
		return new DoublyLLIterator<E>(head,tail);
	}

	
	//
	protected DListNode<E> findNode (E elem){
		DListNode<E> node=head;
		while (node!=null && node.getElement().compareTo(elem)< 0) {
			node=node.getNext();
		}
		return node;
	}


	public E insert(E elem) {
		//TO DO
		return null;
	}

	public E find(E elem) {
		//TO DO
		return null;
	}

	public E remove(E elem) {
		//TO DO
		return null;
	}


	public E min() throws NoElementException {
		if (isEmpty()) throw new NoElementException("OrderedDoublyLL is empty.");
		return head.getElement();
	}

	public E max() throws NoElementException {
		if (isEmpty()) throw new NoElementException("OrderedDoublyLL is empty.");
		return tail.getElement();
	}

}
