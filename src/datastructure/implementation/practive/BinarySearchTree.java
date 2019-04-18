package datastructure.implementation.practive;

public class BinarySearchTree <AnyType extends Comparable<? super AnyType>> {

	// TreeNode
	private static class TreeNode<AnyType extends Comparable<? super AnyType>> {
		public TreeNode left;
		public TreeNode right;
		public AnyType value;

		// Constructors
		public TreeNode() {
			this(null, null, null);
		}

		public TreeNode(AnyType e) {
			this(e, null, null);
		}
		
		public TreeNode(AnyType value, TreeNode<AnyType> left, TreeNode<AnyType> right) {
			this.left = left;
			this.right = right;
			this.value = value;
		}
	}

	// Instance variable
	private TreeNode root;

	// Constructors
	public BinarySearchTree() {
		this.root = null;
	}

	public BinarySearchTree(AnyType value) {
		this.root = new TreeNode(value);
	}

	
	public void makeEmpty() {
		root = null;
	}
	
	public boolean isEmpty() {
		return root == null;
	}

	public boolean contains(TreeNode<AnyType> target) {
		return contains(target);
	}

	/**
	 * Insert new element to the binary search tree
	 * @param target the item to insert
	 */
	public void insert(AnyType target) {
		if (target == null) {
			throw new IllegalArgumentException();
		}
		root = insert(root, target);
	}

	/**
	 * Internal method to insert into a subtree
	 * @param x the item to insert
	 * @param t the node that roots the subtree
	 * @return the new root of the subtree
	 */
	private TreeNode insert(TreeNode<AnyType> t, AnyType x) {
		if (t == null)
			return new TreeNode<>(x, null, null);
		
		int compareResult = root.value.compareTo(x);
		
		if (compareResult < 0) {
			t.left = insert(t.left, x);
		} else if (compareResult > 0) {
			t.right = insert(t.right, x);
		} else {
			throw new IllegalArgumentException();
		}
		return t;
	}

	public TreeNode delete(AnyType target) {
		if (root == null)
			return null;
		return deleteHelper(root, target);
	}
	
	private TreeNode deleteHelper(TreeNode root, AnyType target) {
		if (root == null) 
			return null;
		
		// find the target node
		// if equals then pass
		int compareResult = root.value.compareTo(target);
		if (compareResult < 0) { // smaller than the current node
			root.left = deleteHelper(root.left, target);
			return root;
		} else if (compareResult > 0) { // larger than the current node
			root.right = deleteHelper(root.right, target);
			return root;
		}
		
		// Guarantee root != null && root.value == target
		
		// case 1, 2, 3
		if (root.left == null) {
			// case 1 and 2
			return root.right;
		} else if (root.right == null) {
			return root.left;
		}
		
		// guarantee root.left != null && root.right != null
		
		// case 4.1
		if (root.right.left == null) {
			root.right.left = root.left;
			return root.right;
		}
		
		
		// case 4.2
		// 1. find and delete smallest node in root.right
		TreeNode smallest = successor(root.right);
		
		// 2. connect the smallest node with root.left and root.right
		smallest.left = root.left;
		smallest.right = root.right;
		
		// 3. return the smallest node
		return smallest;
	}

	private TreeNode successor(TreeNode curr) {
		TreeNode prev = curr;
		curr = curr.left;
		while(curr.left != null) {
			prev = curr;
			curr = curr.left;
		}
		
		// curr is the smallest one larger than root, and prev is its parent
		// Invariance: curr (prev.left) does not have left child
		prev.left = prev.left.right;
		return curr;
	}
	
	
	/**
	 * Internal method to find an item in a subtree
	 * @param x is item to search for 
	 * @param t the node that roots the subtree
	 * @return true if the item is found; false otherwise
	 */
	private boolean contains(AnyType x, TreeNode<AnyType> t) {
		if (t == null) {
			return false;
		}
		
		int compareResult = x.compareTo(t.value);
		if(compareResult < 0)
			return contains(x, t.left);
		else if (compareResult > 0)
			return contains(x, t.right);
		else
			return true; // result match
	}
	
	/**
	 * Internal method to find the smallest item in the tree, leftmost node
	 * @param t the node that roots the subtree
	 * @return node containing the smallest item
	 */
	private TreeNode<AnyType> findMin(TreeNode<AnyType> t) {
		if (t == null)
			return null;
		else if (t.left == null)
			return t;
		return findMin(t.left);  // all the way to the left
    }
	
	/**
	 * Internal method to find the largest item in a subtree, rightmost node
	 * @param t the node that roots the subtree
	 * @return node containing the largest item
	 */
	private TreeNode<AnyType> findMax(TreeNode<AnyType> t) {
		if (t == null)
			return null;
		else if (t.right == null)
			return t;
		return findMax(t.right); // all the way to the right
	}
}
