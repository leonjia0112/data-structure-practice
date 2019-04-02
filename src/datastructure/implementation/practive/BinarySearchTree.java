package datastructure.implementation.practive;

public class BinarySearchTree {

	// TreeNode
	private class TreeNode {
		public TreeNode left;
		public TreeNode right;
		public TreeNode parent;
		public Integer value;

		public TreeNode() {
			this.value = null;
			this.left  = null;
			this.right = null;
			this.parent = null;
		}

		public TreeNode(Integer value) {
			this();
			this.value = value;
		}

		@SuppressWarnings("unused")
		public TreeNode(Integer value, TreeNode parent) {
			this(value);
			this.parent = parent;
		}

		@SuppressWarnings("unused")
		public Integer getValue() {
			return this.value;
		}
	}

	private TreeNode root;

	public BinarySearchTree() {
		this.root = new TreeNode();
	}

	public BinarySearchTree(Integer value) {
		this.root = new TreeNode(value);
	}

	public TreeNode search(TreeNode target) {
		if (target == null) {
			return null;
		}

		return __searchHelper(root, target);
	}

	private TreeNode __searchHelper(TreeNode root, TreeNode target) {
		if (root == null || root.value == target.value) { // base case
			return root;
		} else if (root.value < target.value) {
			return __searchHelper(root.right, target);
		} else {
			return __searchHelper(root.left, target);
		}
	}

	public void insert(Integer target) {
		if (target == null || root == null) {
			root = new TreeNode(target);
		}
		__insertHelper(root, target);
	}

	private void __insertHelper(TreeNode root, Integer target) {
		if (target == root.value) {
			return;
		} else if (target < root.value) {
			if (root.left == null) {
				root.left = new TreeNode(target);
			} else {
				__insertHelper(root.left, target);
			}
		} else {
			if (root.right == null) {
				root.left = new TreeNode(target);
			} else {
				__insertHelper(root.left, target);
			}
		}
	}

	public TreeNode delete(Integer target) {
		if (root == null)
			return null;
		return deleteHelper(root, target);
	}
	
	private TreeNode deleteHelper(TreeNode root, Integer target) {
		if (root == null) 
			return null;
		
		// find the target node
		// if equals then pass
		if (root.value > target) {
			root.left = deleteHelper(root.left, target);
			return root;
		} else if (root.value < target) {
			root.right = deleteHelper(root.right, target);
			return root;
		}
		
		// garantee root != null && root.value == target
		
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
}
