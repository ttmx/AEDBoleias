package dataStructures;

public class SinglyLLIterator<E> implements Iterator<E> {


    // Node with the first element in the iteration.
    private SListNode<E> firstNode;
    // Node with the next element in the iteration.
    private SListNode<E> nextToReturn;



    public SinglyLLIterator(SListNode<E> head) {
        firstNode=head;
        rewind();
    }



    @Override
    public boolean hasNext() {
        return nextToReturn!=null;
    }

    @Override
    public E next() throws NoSuchElementException {
        if ( !this.hasNext() )
            throw new NoSuchElementException("No more elements.");
        E element = nextToReturn.getElement();
        nextToReturn = nextToReturn.getNext();
        return element;
    }

    @Override
    public void rewind() {
        nextToReturn = firstNode;
    }


}
