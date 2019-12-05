package dataStructures;

public class BSTValueIterator<K,V> implements Iterator<V> {
    BSTOrderIterator<K,V> ite;
    public BSTValueIterator(BST.BSTNode<Entry<K,V>> root) {
        ite = new BSTOrderIterator<K,V>(root);
    }

    @Override
    public boolean hasNext() {
        return ite.hasNext();
    }

    @Override
    public V next() throws NoSuchElementException {
        return ite.next().getValue();
    }

    @Override
    public void rewind() {
        ite.rewind();
    }
}
