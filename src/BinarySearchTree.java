import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An implementation of an AVL tree.
 *
 * @author Jim Glenn
 * @version 0.2 2016-09-13 added inorder iterator
 * @version 0.1 2014-10-08
 */

public class BinarySearchTree<E extends Comparable<? super E>> implements IntSet301
{
    private Node<E> root;
    private int size;

    /**
     * Counts the number of tree in this tree.
     *
     * @return the number if items in this tree
     */
    //Initializing empty tree
    public BinarySearchTree(){
    	root=null;
    }
    private int height(Node<E> n){
    	return n==null?-1:n.height;
    }
    //Rotation for left-left case
    private void rotateIfRightChild(Node<E> a1){
    	Node<E> a2=a1.right;
    	Node<E>a2leftChild=a2.left;
    	a1.right=a2leftChild;
    	if (a2leftChild!=null){
    		a2leftChild.parent=a1;
    	}
    	a2.left=a1;
    	if (root!=a1&&a1.isLeftChild()){
    		a1.parent.left=a2;
    	}
    	else if(root!=a1&&a1.isRightChild()){
    		a1.parent.right=a2;
    	}
    	if (a1==root){
    		root=a2;
    		root.parent=null;
    	}
    	else a2.parent=a1.parent;
    	a1.parent=a2;
    	a1.height=Math.max(height(a1.left), height(a1.right))+1;
    	a2.height=Math.max(a1.height, height(a2.right))+1;
    }
    //Rotation for right-right case
    private void rotateIfLeftChild(Node<E> a2){
    	Node<E> a1=a2.left;
    	Node<E>a1rightChild=a1.right;
    	a2.left=a1rightChild;
    	if (a1rightChild!=null){
    		a1rightChild.parent=a2;
    	}
    	a1.right=a2;
    	if (root!=a2&&a2.isLeftChild()){
    		a2.parent.left=a1;
    	}
    	else if(root!=a2&&a2.isRightChild()) a2.parent.right=a1;
    	if (root==a2){
    		root=a1;
    		root.parent=null;
    	}
    	else {
    		a1.parent=a2.parent;
    	}
    	a2.parent=a1;
    	a2.height=Math.max(height(a2.left), height(a2.right))+1;
    	a1.height=Math.max(a2.height, height(a1.left))+1;
    	
    }
    //Rotation for right-left case
    private void rotateGrandchildLeft(Node<E> a3){
    	rotateIfLeftChild(a3.right);
    	rotateIfRightChild(a3);
    	
    }
    //Rotation for left-right case
    private void rotateGrandchildRight(Node<E> a4){
    	rotateIfRightChild(a4.left);
    	rotateIfLeftChild(a4);
    }

    /**
     * Adds the given value to this tree if it is not already present.
     *
     * @param r a value
     */
    public boolean contains(int n){
    	if (root==null) return false;
    	Node<E> temp=root;
    	while (temp!=null){
    		if (temp.data[0]<=n&&temp.data[1]>=n)return true;
    		else {
    			if (Integer.compare(n, temp.data[0])<0){
    				temp=temp.left;
    			}
    			else temp=temp.right;
    		}
    	}
    	return false;
    	
    }
    public int size() {
		return size;
	}
    public Node<E>find(int n){
    	Node<E> curr=root;
    	Node<E> parCurr=null;
    	while (curr!=null&&(n>curr.data[1]||n<curr.data[0])){
    		
    		parCurr=curr;
    		
    		if (Integer.compare(n, curr.data[0])<0){
    			curr=curr.left;
    			if (curr!=null&&curr.data[0]==n)break;
    		}
    		else if (Integer.compare(n, curr.data[1])>0){
    			curr=curr.right;
    			if (curr!=null&&curr.data[0]==n)break;
    		}
    	}
    	return curr;
    }
    public void add(int n){
    	Node<E> curr=root;
    	Node<E> parCurr=null;
    	while (curr!=null&&(n>curr.data[1]||n<curr.data[0])){
    		
    		parCurr=curr;
    		
    		if (Integer.compare(n, curr.data[0])<0){
    			curr=curr.left;
    			if (parCurr.data[0]==n+1)break;
    		}
    		else if (Integer.compare(n, curr.data[1])>0) {
    			curr=curr.right;
    			if (parCurr.data[1]==n-1)break;
    		}
    	}
    	if (curr==null){
    		Node<E> newNode=new Node<E>(n,n,parCurr);
    		size++;
    		if (parCurr==null){
    			root=newNode;
    		}
    		else {
    			if (Integer.compare(n, parCurr.data[0])<0){
    				if (n==parCurr.data[0]-1){
    					parCurr.data[0]=n;
    				}
    				else parCurr.left=newNode;
    			}
    			else{
    				if (n==parCurr.data[1]+1){
    					parCurr.data[1]=n;
    				}
    				else parCurr.right=newNode;
    			}
    		}
    		readjustment(parCurr);
    	}
    	else if(parCurr!=null&&(n>curr.data[1]||n<curr.data[0])) {
    		if (n>parCurr.data[1]){
    			size++;
    			Node<E> temp=find(n+1);
    			if (temp==null){
    				parCurr.data[1]=n;
    			}
    			else {
    				size++;
    				int temp_end=temp.data[1];
    				delete(temp);
    				parCurr.data[1]=temp_end;
    			}
    		}
    		else {
    			size++;
    			Node<E> temp=find(n-1);
    			if (temp==null)parCurr.data[0]=n;
    			else {
    				size++;
    				int temp_begin=temp.data[0];
    				delete(temp);
    				parCurr.data[0]=temp_begin;
    			}
    		}
    		readjustment(parCurr);
    	}
    	
    	
    }
    
    
    //Keeps track of height of nodes and does necessary rotations
    public void readjustment (Node<E> parent){
    	while(parent!=null){
    		if (height(parent.left)-height(parent.right)==2){
    			//Left-left case
    			if (parent.left.left!=null&&height(parent.left.left)>height(parent.left.right)){
    				Node<E>temp=parent.parent;
    				rotateIfLeftChild(parent);
    				parent=temp;
    			}
    			//Left-right case
    			else {
    				Node<E>temp=parent.parent;
    				rotateGrandchildRight(parent);
    				parent=temp;
    			}
    		}
    		else if (height(parent.right)-height(parent.left)==2){
    			//right-right case
    			if (parent.right.right!=null&&height(parent.right.right)>height(parent.right.left)){
    				Node<E>temp=parent.parent;
    				rotateIfRightChild(parent);
    				parent=temp;
    			}
    			//right-left case
    			else{
    				Node<E>temp=parent.parent;
    				rotateGrandchildLeft(parent);
    				parent=temp;
    			}
    		}
    		//No rotations required; update height of nodes
    		else {
    			Node<E> temp=parent.parent;
    			parent.height=Math.max(height(parent.left),height(parent.right))+1;
    			parent=temp;
    		}
    	}
    }
   //adds intervals
   public void addInterval(int start, int end, int num){
	   Node<E> curr=root;
   	Node<E> parCurr=null;
   	while (curr!=null&&(start>curr.data[1]||start<curr.data[0])){
   		
   		parCurr=curr;
   		
   		if (Integer.compare(start, curr.data[0])<0){
   			curr=curr.left;
   			if (parCurr.data[0]==start+1)break;
   		}
   		else if (Integer.compare(start, curr.data[1])>0) {
   			curr=curr.right;
   			if (parCurr.data[1]==start-1)break;
   		}
   	}
   	if (curr==null){
		Node<E> newNode=new Node<E>(start,end,parCurr);
		size+=num;
		if (parCurr==null){
			root=newNode;
		}
		else if (Integer.compare(start, parCurr.data[0])<0){
   			parCurr.left=newNode;
   		}
		else if(Integer.compare(start, parCurr.data[0])>0){
			parCurr.right=newNode;
		}
   		
   	}
   	readjustment(parCurr);
   }
   /**
    * Removes r from this tree.  There is no effect if r is not in this tree.
    *
    * @param r the value to remove
    */
    public void remove(int r)
    {
    	Node<E> curr=root;
    	Node<E> parCurr=null;
    	while (curr!=null&&(r>curr.data[1]||r<curr.data[0])){
    		
    		parCurr=curr;
    		
    		if (Integer.compare(r, curr.data[0])<0){
    			curr=curr.left;
    		}
    		else if (Integer.compare(r, curr.data[1])>0) {
    			curr=curr.right;
    		}
    	}
	
	if (curr == null)
	    {
	    }
	else
	    {
		if (curr.data[0]==curr.data[1])delete(curr);
		else if (curr.data[0]==r) {
			size--;
			curr.data[0]++;
		}
		else if (curr.data[1]==r){
			size--;
			curr.data[1]--;
		}
		else {
			int start=curr.data[0];
			int end=curr.data[1];
			size-=end-start;
			delete(curr);
			addInterval(start,r-1,r-start);
			addInterval(r+1,end,end-r);
		}
	    }
    }

    /**
     * Deletes the given node from this tree.
     *
     * @param curr a node in this tree
     */
    private void delete(Node<E> curr)
    {
	size--; 

	if (curr.left == null && curr.right == null)
	    {
		Node<E> parent = curr.parent;
		if (curr.isLeftChild())
		    {
			parent.left = null;
		    }
		else if (curr.isRightChild())
		    {
			parent.right = null;
		    }
		else
		    {
			// deleting the root
			root = null;
		    }
		readjustment(parent);
	    }
	else if (curr.left == null)
	    {
		// node to delete only has right child
		Node<E> parent = curr.parent;

		if (curr.isLeftChild())
		    {
			parent.left = curr.right;
			curr.right.parent = parent;
		    }
		else if (curr.isRightChild())
		    {
			parent.right = curr.right;
			curr.right.parent = parent;
		    }
		else
		    {
			root = curr.right;
			root.parent = null;
		    }
		readjustment(parent);
	    }
	else if (curr.right == null)
	    {
		// node to delete only has left child
		Node<E> parent = curr.parent;

		if (curr.isLeftChild())
		    {
			parent.left = curr.left;
			curr.left.parent = parent;
		    }
		else if (curr.isRightChild())
		    {
			parent.right = curr.left;
			curr.left.parent = parent;
		    }
		else
		    {
			root = curr.left;
			root.parent = null;
		    }
		readjustment(parent);
	    }
	else
	      {
		  // node to delete has two children

		  // find inorder successor (leftmopst in right subtree)
		  Node<E> replacement = curr.right;
		  while (replacement.left != null)
		      {
			  replacement = replacement.left;
		      }

		  Node<E> replacementChild = replacement.right;
		  Node<E> replacementParent = replacement.parent;
		  if (replacement!=curr.right&&replacementChild!=null){
			  replacement.parent.left=replacementChild;
			  Node<E> temp=replacement.parent;
			  replacementChild.parent=replacement.parent;
			  readjustment(temp);
			  replacement.right=curr.right;
			  curr.right.parent=replacement;
			  replacement.left=curr.left;
			  if (curr.left!=null)curr.left.parent=replacement;
			  
		  }
		  else {
			  replacement.left=curr.left;
			  if (curr.left!=null)curr.left.parent=replacement;
		  }
		  

		  // stitch up
		  if (curr.isLeftChild())
		      {
			  curr.parent.left = replacement;
			  replacement.parent=curr.parent;
		      }
		  else if (curr.isRightChild())
		      {
			  curr.parent.right = replacement;
			  replacement.parent=curr.parent;
		      }
		  else
		      {
			  root = replacement;
			  root.parent=null;
		      }
		  readjustment(replacement);
		
	      }
    }
    public int nextExcluded(int n){
    	// start at root
    	Node<E> curr = root;

    	// keep track of parent of current and last direction travelled
    	Node<E> parCurr = null;

    	// traverse tree to insertion location or node containing r
    	while (curr!=null&&(n>curr.data[1]||n<curr.data[0])){
    		parCurr=curr;
    		if (Integer.compare(n, curr.data[0])<0){
    			curr=curr.left;
    		}
    		else {
    			curr=curr.right;
    		}
    	}
    	if (curr==null){
    		return n;
    	}
    		Node<E> temp2=curr;
    		return temp2.data[1]+1;
    }
   

 

    /**
     * Returns the inorder successor of the given node.
     *
     * @param curr a node in this tree that is not the rightmost
     * @return the inorder successor of that node
     */
    public Node<E> min(Node<E> x){
    	Node<E> temp=x;
    	while (temp.left!=null){
    		temp=temp.left;
    	}
    	return temp;
    }
    public Node<E> successor(Node<E> curr)
    {
    	Node<E> temp=curr;
    	if (temp.right!=null){
    		return min(temp.right);
    	}
    	Node<E> y=temp.parent;
    	while(y!=null&&temp==y.right){
    		temp=y;
    		y=y.parent;
    	}
    	return y;
	    }

    /**
     * Returns a printable representation of this tree.
     *
     * @return a printable representation of this tree
     */
    public String toString()
    {
	StringBuilder s = new StringBuilder();
	buildOutput(root, s, 0);
	return s.toString();
    }

    /**
     * Appends a printable representation of the subtree rooted at the
     * given node to the given string builder.
     *
     * @param curr a node in this tree
     * @param s a string builder
     * @param depth the depth of curr
     */
    private void buildOutput(Node<E> curr, StringBuilder s, int depth)
    {
	if (curr != null)
	    {
		buildOutput(curr.left, s, depth + 1);
		if (curr.data[0]!=curr.data[1]){
			s.append(depth + " " + curr.data[0] +"-"+curr.data[1]+ "\n");
		}
		else s.append(depth + " " + curr.data[0] + "\n");
		buildOutput(curr.right, s, depth + 1);
	    }
    }

    /**
     * Checks the class invariants for this tree.  An assertion will
     * fail if the tree violates the order property or if size is not
     * maintained correctly.  (Run with the -ea option to enable
     * assertions.)
     */
    public static class Node<E>
    {
	private int height;
	private Node<E> parent;
	private Node<E> left;
	private Node<E> right;

	private int[] data=new int[2];

	private Node(int start, int end, Node<E> p)
	    {
		data[0] = start;
		data[1]=end;
		parent = p;
	    }

	private boolean isLeftChild()
	{
	    return (parent != null && parent.left == this);
	}

	private boolean isRightChild()
	{
	    return (parent != null && parent.right == this);
	}
    }

  
}