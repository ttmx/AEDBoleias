package dataStructures;

public class BSTKeyIterator<K,V> implements Iterator<K> {
    BSTOrderIterator<K,V> ite;
    public BSTKeyIterator(BST.BSTNode<Entry<K,V>> root) {
        ite = new BSTOrderIterator<K,V>(root);
    }

    @Override
    public boolean hasNext() {
        return ite.hasNext();
    }

    @Override
    public K next() throws NoSuchElementException {
        return ite.next().getKey();
    }

    @Override
    public void rewind() {
        ite.rewind();
    }
}
