package dataStructures;

/**
 * @author Tiago Teles
 */

public class IteratorWithJavaClass<E> implements Iterator<E> {

    private static final long serialVersionUID = 1L;

    private java.util.Iterator<E> it, itInit;

    public IteratorWithJavaClass(java.util.Iterator<E> it){
        this.it = it;
        itInit = it;
    }
    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public E next() {
        return it.next();
    }

    public void rewind() {
            it = itInit;
    }
}
