package dataStructures;

public class SinglyLinkedList<E> implements List<E> {
	// Node at the head of the list.
	protected SListNode<E> head;

	// Node at the tail of the list.
	protected SListNode<E> tail;

	// Number of elements in the list.
	protected int currentSize;

	public SinglyLinkedList( ){
		head = null;
		tail = null;
		currentSize = 0;
	}

	@Override
	public boolean isEmpty() {
		return currentSize ==0;
	}

	@Override
	public int size() {
		return currentSize;
	}


	@Override
	public SinglyLLIterator<E> iterator() throws NoElementException {
		if (currentSize==0) throw new NoElementException("List is empty.");
		return new SinglyLLIterator<E>();
	}


	@Override
	public int find(E element) {
		int pos=0;
		SListNode<E> auxNo;
		boolean found=false;
		//TODO
		return -1;
	}

	@Override
	public E getFirst() throws NoElementException {
		if (currentSize==0) throw new NoElementException("No such element.");
		return getNode(0).getElement();
	}

	@Override
	public E getLast() throws NoElementException {
		if (currentSize==0) throw new NoElementException("No such element.");
		return getNode(currentSize-1).getElement();
	}

	@Override
	public E get(int position) throws InvalidPositionException {
		if (position<0 || position>=currentSize)
			throw new InvalidPositionException("Invalid position.");
		return getNode(position).getElement();
	}

	@Override
	public void addFirst(E element) {
		SListNode<E> dln = new SListNode<E>(element,head);
		if(currentSize==0){
			tail = dln;
		}
		head = dln;
		currentSize++;
	}

	@Override
	public void addLast(E element) {
		SListNode<E> dln = new SListNode<E>(element,tail);
		if(currentSize==0){
			head = dln;
			tail = dln;
		}else{
			tail.setNext(dln);
		}
		tail = dln;
		currentSize++;
	}

	@Override
	public void add(int position, E element) throws InvalidPositionException {
		if (position<0 || position >currentSize)
			throw new InvalidPositionException("Invalid Position.");
		if (position==0)
			addFirst(element);
		else if (position==currentSize)
			addLast(element);
		else {
			addMiddle(position,element);
		}
		currentSize++;
	}


	private void addMiddle(int position, E element) {
		SListNode<E> prev = getNode(position-1);
		SListNode<E> next = getNode(position);
		SListNode<E> curr = new SListNode<E>(element,next);
		prev.setNext(curr);
		currentSize++;
	}

	private E removeMiddle(int position) {
		SListNode<E> aux=getNode(position);
		//TODO
		return null;
	}

	private SListNode<E> getNode(int position){
		SListNode<E> aux=head;
		for(int i=1;i<=position;i++)
			aux=aux.getNext();
		return aux;
	}
	@Override
	public E removeFirst() throws NoElementException {
		if (currentSize == 0) throw new NoSuchElementException();
		SListNode<E> toReturn = head;
		head = head.next;
		currentSize--;
		return toReturn.element;
	}

	@Override
	public E removeLast() throws NoElementException {
		return null;
	}


	@Override
	public E remove(int position) throws InvalidPositionException {
		if(position<0 || position>=currentSize)
			throw new InvalidPositionException("Invalid position.");
		if (position==0)
			return removeFirst();
		if (position==currentSize-1)
			return removeLast();
		return removeMiddle(position);
	}


}
