package dataStructures;

/**
 * This class implements a self balancing Binary Search Tree(BST), an AVL Tree
 * @author Tiago Teles
 */
public class AVL <K extends Comparable<K>,V> extends AdvancedBST<K,V> implements SortedMap<K,V>{

	/**
	 * This class implements and AVL node of the AVL Tree
	 * @param <E> The type of the element on each node
	 */
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
		/**  
			Obtains the height of the AVLNode given as parameters
			@param no The node for which we're searching it's height.
			@return the height this AVL, 0 when null.
		*/
		protected int getHeight(AVLNode<E> no) {
			if (no==null)
				return 0;
			return no.getHeight();
		}
		/**  
			Obtains the height of this AVLNode
			@return the height this AVL
		*/
		public int getHeight() {
			return height;
		}
		/**
		 * Check if this node is balanced
		 * @return true if the left and right nodes differ at most 1 on their respective heights, false otherwise
		 */
		public boolean isBalance() {
			int dif= getHeight((AVLNode<E>)left)-getHeight((AVLNode<E>)right);
			return dif==0 ||dif==-1 ||dif ==1;
		}
		/**
		 * Resets the height of the node based on the height it's branches. 
		 * It set the node height to the max height of both nodes added by 1.
		 * @return the new calculate height
		 */
		public int setHeight() {
			height= 1 + Math.max(getHeight((AVLNode<E>)left),getHeight((AVLNode<E>)right));
			return height;
		}


	}
	/**
	 * Create a new AVL tree with n as it's root node
	 * @param n the node that will become the root node
	 */
	protected AVL(AVLNode<Entry<K,V>> n) {
		root=n;
	}
	public AVL() {
		this(null);
	}
	/**
	 * @return a child of p with greater height
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
	 * @param zPos the starting node from which the rebalancing will occur upwards
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

			toRemove.left.parent = maxOfMin;
			if(toRemove.left.equals(maxOfMin)){
				maxOfMin.parent = parent;
			}else{
				maxOfMin.parent.right = maxOfMin.left;
				maxOfMin.left = toRemove.left;
			}
			maxOfMin.parent = parent;

			maxOfMin.right = toRemove.right;
			toRemove.right.parent = maxOfMin;

		}
		currentSize--;
		AVLNode<Entry<K,V>> node=(AVLNode<Entry<K,V>>) toRemove.parent; // father of node where the key was
		// removeNode is the BST remove(key)
		if(node != null) //(if find(key)==null)
			rebalance(node); // rebalance up from the node
		return toReturn;
	}

}
