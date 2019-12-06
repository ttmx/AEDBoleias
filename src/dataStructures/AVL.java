package dataStructures;


public class AVL <K extends Comparable<K>,V> extends AdvancedBST<K,V> implements SortedMap<K,V>{

	static class AVLNode<E> extends BSTNode<E> {
		// Height of the node
		protected int height;

		public AVLNode(E elem) {
			super(elem);
			height=1;
		}

		public AVLNode( E element, AVLNode<E> parent,AVLNode<E> left, AVLNode<E> right ){ //char balance,
			super(element, parent,left, right);
			height= 1 + Math.max(getHeight((AVLNode<E>)left),getHeight((AVLNode<E>)right));
		}

		protected int getHeight(AVLNode<E> no) {
			if (no==null)
				return 0;
			return no.getHeight();
		}

		public int getHeight() {
			return height;
		}

		public boolean isBalance() {
			int dif= getHeight((AVLNode<E>)left)-getHeight((AVLNode<E>)right);
			return dif==0 ||dif==-1 ||dif ==1;
		}

		public int setHeight() {
			height= 1 + Math.max(getHeight((AVLNode<E>)left),getHeight((AVLNode<E>)right));
			return height;
		}
	}
	protected AVL(AVLNode<Entry<K,V>> n) {
		root=n;
	}
	public AVL() {
		this(null);
	}
	/**
	 * Return a child of p with greater height
	 */
	protected AVLNode<Entry<K,V>> tallerChild(AVLNode<Entry<K,V>> top)  {
		if(top.left!=null && top.right!=null)
			return (((AVLNode<Entry<K,V>>)top.left).getHeight()>((AVLNode<Entry<K,V>>)top.right).getHeight())?((AVLNode<Entry<K,V>>)top.left):((AVLNode<Entry<K,V>>)top.right);
		return (AVLNode<Entry<K, V>>) ((top.right==null)?top.left:top.right);
	}
	/**
	 * Rebalance method called by insert and remove.  Traverses the path from
	 * zPos to the root. For each node encountered, we recompute its height
	 * and perform a trinode restructuring if it's unbalanced.
	 * the rebalance is completed with O(log n)running time
	 */
	protected void rebalance(AVLNode<Entry<K,V>> zPos) {
		if(zPos.isInternal())
			zPos.setHeight();
		// Melhorar se possivel
		while ((zPos = (AVLNode<Entry<K, V>>) zPos.getParent()) !=null) {  // traverse up the tree towards the root
			//zPos = (AVLNode<Entry<K, V>>) zPos.getParent();
			zPos.setHeight();
			if (!zPos.isBalance()) {
				AVLNode<Entry<K,V>> prevzPos = zPos;
				// perform a trinode restructuring at zPos's tallest grandchild
				//If yPos (tallerChild(zPos)) denote the child of zPos with greater height.
				//Finally, let xPos be the child of yPos with greater height
				AVLNode<Entry<K,V>> xPos =  tallerChild((AVLNode<Entry<K, V>>) tallerChild(zPos));
				zPos = (AVLNode<Entry<K, V>>) restructure(xPos); // tri-node restructure (from parent class)
				if(zPos.getLeft()!=null)
					((AVLNode<Entry<K, V>>) zPos.getLeft()).setHeight();  // recompute heights~
				if(zPos.getRight()!=null)
					((AVLNode<Entry<K, V>>) zPos.getRight()).setHeight();
				zPos.setHeight();

				if(prevzPos.equals(root))
					root = zPos;
			}
		}
	}




	@SuppressWarnings("DuplicatedCode")
	@Override
	public V insert(K key, V value) {
		if(root==null){
			root = new AVLNode<Entry<K,V>>(new EntryClass<K,V>(key,value));
			currentSize++;
			return null;
		}
		AVLNode<Entry<K,V>> next = (AVLNode<Entry<K, V>>) root;
		AVLNode<Entry<K,V>> current = null;
		while(true){
			current = next;
			if(key.compareTo(next.element.getKey())<0){
				next = (AVLNode<Entry<K, V>>) next.left;
				if(next==null){
					current.left = new AVLNode<>(new EntryClass<K,V>(key,value),current,null,null);
					currentSize++;
					rebalance((AVLNode<Entry<K, V>>) current.left);
					return null;
				}
			}else if(key.compareTo(next.element.getKey())>0){
				next = (AVLNode<Entry<K, V>>) next.right;
				if(next==null){
					current.right = new AVLNode<>(new EntryClass<K,V>(key,value),current,null,null);
					currentSize++;
					rebalance((AVLNode<Entry<K, V>>) current.right);
					return null;
				}
			}else{
				V toReturn = next.getElement().getValue();
				next.element = new EntryClass<K,V>(key,value);
				rebalance(next);
				return toReturn;
			}
		}
	}

	@SuppressWarnings("DuplicatedCode")
	@Override
	public V remove(K key) {
		// TODO
		BSTNode<Entry<K,V>> toRemove = findNode(root,key);
		if(toRemove==null)
			return null;
		V toReturn = toRemove.getElement().getValue();
		BSTNode<Entry<K,V>> parent = toRemove.getParent();
		boolean isLeft = (parent != null) && toRemove.getElement().getKey().compareTo(parent.getElement().getKey()) < 0;

		if(!toRemove.isInternal()) {
			//Its a leaf
			if (toRemove == root){
				root = null;
			}else if(isLeft){
				parent.left = null;
			}else{
				parent.right = null;
			}
		} else if (toRemove.right == null) {
			//Only has left
			if (toRemove == root) {
				root = toRemove.left;
			}else if(isLeft) {
				parent.left = toRemove.left;
			}else {
				parent.right = toRemove.left;
			}
			toRemove.left.parent = parent;
		} else if (toRemove.left == null){
			//Only has right
			if (toRemove == root) {
				root = toRemove.right;
			}else if(isLeft) {
				parent.left = toRemove.right;
			}else{
				parent.right = toRemove.right;
			}
			toRemove.right.parent = parent;
		}else if(toRemove.getRight()!=null && toRemove.getLeft()!=null){
			BSTNode<Entry<K,V>> maxOfMin = maxNode(toRemove.getLeft());
			if(toRemove == root)
				root = maxOfMin;
			else if(isLeft)
				parent.left = maxOfMin;
			else
				parent.right = maxOfMin;

			if(toRemove.left.equals(maxOfMin)){
				maxOfMin.parent = parent;
			}else{
				maxOfMin.parent.right = maxOfMin.left;
				maxOfMin.left = toRemove.left;
			}
			maxOfMin.parent = parent;

			if(maxOfMin.getLeft() !=null) {
				maxOfMin.getLeft().parent = maxOfMin.parent;
			}
		}
		currentSize--;
		AVLNode<Entry<K,V>> node=(AVLNode<Entry<K,V>>) toRemove.parent; // father of node where the key was
		// removeNode is the BST remove(key)
		if(node != null) //(if find(key)==null)
			rebalance(node); // rebalance up from the node
		return toReturn;
	}

}
