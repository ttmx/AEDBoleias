package dataStructures;


public class AdvancedBST <K extends Comparable<K>, V> extends BST<K,V>{

		// metodos comuns a arvores binarias de pesquisa avancadas
		// Operacoes basicas para trocar a forma da arvore tratando
		// de reduzir a sua altura
		
	/**
	 * Performs a single left rotation rooted at Y node.
	 * Node X was a  right  child  of Y before the  rotation,  
	 * then Y becomes the left child of X after the rotation.
	 * @param Y - root of the rotation
	 * @pre: Y has a right child
     */
    protected void rotateLeft( BSTNode<Entry<K,V>> Y){
        //  a single rotation modifies a constant number of parent-child relationships, 
    	// it can be implemented in O(1)time

		BSTNode<Entry<K,V>> Z = Y.parent;
		BSTNode<Entry<K,V>> X = Y.right;
		BSTNode<Entry<K,V>> xLeft = X.left;

		X.parent = Z;
		if(Z!=null && Z.right!= null && Z.right.equals(Y))
			Z.right = X;
		else if (Z!=null && Z.left!=null && Z.left.equals(Y))
			Z.left = X;
		Y.parent = X;

		X.left = Y;
		Y.right = xLeft;
		if(xLeft!=null)
			xLeft.parent = Y;
    }
    
    /**
     * Performs a single right rotation rooted at z node.
     * Node X was a  left  child  of z before the  rotation,
     * then z becomes the right child of X after the rotation.
     * @param z - root of the rotation
     * @pre: z has a left child
	 *     z          y
	 *    / \        / \
	 *   y   c      a   z
	 *  / \            / \
	 * a   b          b   c
	 *
     */
    protected void rotateRight( BSTNode<Entry<K,V>> z){
        //  a single rotation modifies a constant number of parent-child relationships, 
    	// it can be and was implemented in O(1)time

		BSTNode<Entry<K,V>> zParent = z.parent;
		BSTNode<Entry<K,V>> y = z.left;
		BSTNode<Entry<K,V>> b = y.right;

		y.parent = zParent;
		if(zParent!=null && zParent.left!= null && zParent.left.equals(z))
			zParent.left = y;
		else if (zParent!=null && zParent.right!=null && zParent.right.equals(z))
			zParent.right = y;
		z.parent = y;

		y.right = z;
		z.left = b;
		if(b!=null)
			b.parent = z;

    }
    
	    
	   /** 
	   * Performs a tri-node restructuring (a single or double rotation rooted at X node).
	   * Assumes the nodes are in one of following configurations:
	   *
	   * @param x - root of the rotation
	   * <pre>
	   *          z=c       z=c        z=a         z=a
	   *         /  \      /  \       /  \        /  \
	   *       y=b  t4   y=a  t4    t1  y=c     t1  y=b
	   *      /  \      /  \           /  \         /  \
	   *    x=a  t3    t1 x=b        x=b  t4       t2 x=c
	   *   /  \          /  \       /  \             /  \
	   *  t1  t2        t2  t3     t2  t3           t3  t4
	   * </pre>
	   * @return the new root of the restructured subtree
	   */
	    protected BSTNode<Entry<K,V>> restructure (BSTNode<Entry<K,V>> x) {
			// the modification of a tree T caused by a trinode restructuring operation
			// can be implemented through case analysis either as a single rotation or as a
			// double rotation.
			// The double rotation arises when position x has the middle of the three
			// relevant keys
			// and is first rotated above its parent Y, and then above what was originally
			// its grandparent Z.
			// In any of the cases, the trinode restructuring is completed with O(1)running
			// time
			BSTNode<Entry<K, V>> y = x.getParent();
			BSTNode<Entry<K, V>> z = y.getParent();
			if (y.getLeft() == x && z.getLeft() == y) {
				rotateRight(z);
				return y;
			} else if (y.getLeft() == x && z.getRight() == y) {
				rotateRight(y);
				rotateLeft(z);
				return x;
			} else if (y.getRight() == x && z.getLeft() == y) {
				rotateLeft(y);
				rotateRight(z);
				return x;
			} else if (y.getRight() == x && z.getRight() == y) {
				rotateLeft(z);
				return y;
			}
			return null;
		}
	}

