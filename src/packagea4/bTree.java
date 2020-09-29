package packagea4;

/**
 * Implements a B-Tree class
 * @author gregorywalfish 
 * Code used is a derivation of the bTree class written by ferrie and my previous assignment 2
 * 
 */

public class bTree {
	
	// Instance variables
	
	bNode root=null;
	double lastsize=0;
	double x=0;
	double y=600;
	double DELTASIZE=0.1;
	double SCALE = 6;
	
/**
 * addNode method - adds a new node by descending to the leaf node
 *                  using a while loop in place of recursion.  Ugly,
 *                  yet easy to understand.
 * @param root 
 */
	

	
	public void addNode(aBall data) {
		
		bNode current;

// Empty tree
		
		if (root == null) {
			root = makeNode(data);
		}
		
// If not empty, descend to the leaf node according to
// the input data.  
		
		else {
			current = root;
			while (true) {
				if (data.bSize < current.data.bSize) {
					
// New data < data at node, branch left
					
					if (current.left == null) {				// leaf node
						current.left = makeNode(data);		// attach new node here
						break;
					}
					else {									// otherwise
						current = current.left;				// keep traversing
					}
				}
				else {
// New data >= data at node, branch right
					
					if (current.right == null) {			// leaf node	
						current.right = makeNode(data);		// attach
						break;
					}
					else {									// otherwise 
						current = current.right;			// keep traversing
					}
				}
			}
		}
		
	}
	

	
/**
 * makeNode
 * 
 * Creates a single instance of a bNode
 * 
 * @param	data   Data to be added
 * @return  bNode node Node created
 */
	
	bNode makeNode(aBall data) {
		bNode node = new bNode();							// create new object
		node.data = data;									// initialize data field
		node.left = null;									// set both successors
		node.right = null;									// to null
		return node;										// return handle to new object
	}
	
	
/**
 * inorder method - inorder traversal via call to recursive method
 */
	
	public void inorder() {									// hides recursion from user
		traverse_inorder(root);
	}
	
/**
 * traverse_inorder method - recursively traverses tree in order (LEFT-Root-RIGHT) and prints each node.
 * Moves balls in order 
 */
//	public void stopAll(bNode root) {
//		//something like this
//		if (root.left != null) stopAll(root.left);
//		root.interrupt();
//		if (root.right != null) stopAll(root.right);
//	}
	
	private void traverse_inorder(bNode root) {
		if (root.left != null) traverse_inorder(root.left);
		
		// Move balls 
		if (root.data.bSize -lastsize > DELTASIZE ) { 	//creates new stack
			x=root.data.bSize*2*SCALE +x;				//x position
			y=600-root.data.bSize*2*SCALE;				//y position
		}
		else {											//stacks ball on top of previous 
			x=x;										//x is equal to previous x position
			y=y-root.data.bSize*2*SCALE;				//y position
		}
		root.data.moveTo(x,y);
		lastsize=root.data.bSize;

		if (root.right != null) traverse_inorder(root.right);
	}
	
	@SuppressWarnings("deprecation")
	public void inorder_stop(bNode root) { //searches through the tree in order and stops the thread of each one so they stop moving
		if (root.left != null) inorder_stop(root.left);
		if (root.data != null) root.data.stop(); //stops the thread of a gBall
		if (root.right != null) inorder_stop(root.right);
	}
	public void stop() { //stops the balls 
		inorder_stop(root);
	}

	int runningBalls = 0;
	
	private void traverse(bNode root) {				//this traverses the tree and returns boolean false to indicate isRunning
		if (root.left != null) {
			traverse(root.left);
		}
		
		boolean nodeIsRunning = root.data.isRunning;
		if(nodeIsRunning) {
			runningBalls++;
		}

		if (root.right != null) {
			traverse(root.right);
		}
		
	}
	
/**
 * preorder method - preorder traversal via call to recursive method
 * 
 */
	
	public void preorder() {
		traverse_preorder(root);
	}
	
/**
 * traverse_preorder method - recursively traverses tree in preorder (Root-LEFT-RIGHT) and prints each node.
 */

	public void traverse_preorder(bNode root) {				//do not use this - was from ferries 
		System.out.println(root.data);
		if (root.left != null) traverse_preorder(root.left);
		if (root.right != null) traverse_preorder(root.right);
	}
	
/**
 * postorder method - postorder traversal via call to recursive method
 */
	
	public void postorder() {
		traverse_postorder(root);
	}
	
/**
 * traverse_postorder method - recursively traverses tree in postorder (LEFT-RIGHT-Root) and prints each node.
 */
	
	public void traverse_postorder(bNode root) {
		if (root.left != null) traverse_postorder(root.left);
		if (root.right != null) traverse_postorder(root.right);
		System.out.println(root.data.bSize);
	}
	
	//check in simulation if still running for any ball
	public boolean isRunning() {
		runningBalls =0;

		traverse(root);
		if(runningBalls > 0) {
			return true;
		}
		
		return false;
	}



/**
 * A simple bNode class for use by bTree.  The "payload" can be
 * modified accordingly to support any object type.
 @author gregorywalfish 
 * Code used is a derivation of the bTree class written by ferrie and my previous assignment 2
 *
 */

class bNode {
	aBall data;
	bNode left;
	bNode right;
	
}


	
	
}
