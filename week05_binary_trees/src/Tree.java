import java.io.PrintStream;


public class Tree<T extends Comparable<T>> {
	
	public T root = null ;
	private Tree<T> left = null;
	private Tree<T> right = null;
	
	public Tree() {
		root = null ;
		left = null ;
		right = null ;
	}
	
	public void add(T elem) {
		if(root == null) //empty tree, insert at root
			root = elem ;
		
		else if(elem.compareTo(root) <= 0) { // insert at left
			if(left==null)
				left = new Tree<T>() ;
			left.add(elem);
		}
		else // elem.compareTo(root) > 0 // insert at right
		{
			if(right==null)	
				right = new Tree<T>() ;
			right.add(elem) ;
		}		
	}
	
	public boolean remove(T elem, Tree parent) { //pass parent along in case we need to remove current node that becomes empty
		if(root==null) //didnt find any, failed
			return false ; 

		if(elem.compareTo(root)==0) { 
			//remove root and replace with maximum from left 
			// or minimum from right if left is empty
			T newroot = null ;
			if(left!=null) {
				newroot = left.maximum() ;
				
				left.remove(newroot,this);  //remove old value that now is root from left branch
			}
			else if(right!=null) {
				newroot = right.minimum() ;
				
				right.remove(newroot,this);  //remove old value that now is root from left branch
			}

			root = newroot ;
			
			if(isEmpty())
				parent.deleteEmpty();
				
			checkConsistency("before return true");
			return true ; //removed			
		}
		
		else if(elem.compareTo(root) <= 0 && left!=null) //if its lowest and doesnt exist branch, elem doesnt exist for removal
			return left.remove(elem,this) ;
		else if(/*elem.compareTo(root) > 0 &&*/ right!=null) //remove from right if exists
			return right.remove(elem,this) ;
		
		return false ; //left and right were nulls or just not found/doesnt exist in tree
	}

	public boolean isEmpty() {
		return root==null && left==null && right==null ;
	}
	
	public void deleteEmpty() {
		if(left!=null && left.isEmpty())
			left = null ;
		if(right!=null && right.isEmpty())
			right = null ;
	}
	
	public T maximum() {
		if(right==null) //then root is maximum, see picture with maximum/minimum tree explanation, maximum is always at right
			return root ;
		
		return right.maximum() ;
	}
	
	public T minimum() {
		if(left==null) //then root is maximum, see picture with maximum/minimum tree explanation, maximum is always at right
			return root ;
		
		return left.minimum() ;
	}
	
	public boolean contains(T elem) {
		if(root==null) {
			return false ;
		}
		
		if(root.compareTo(elem)==0) {
			System.out.println("found");
			return true ;
		}

		if(left!=null && left.contains(elem))
			return true ;
		if(right!=null && right.contains(elem))
			return true ;
		
		return false ; //this is not the tree you are looking for
	}

	public void printInOrder(PrintStream out) {
		if(left!=null) left.printInOrder(out) ;		
		out.println(root);		
		if(right!=null) right.printInOrder(out) ;
	}

	public void printPreOrder(PrintStream out) {
		out.println(root);		
		if(left!=null) left.printPreOrder(out) ;		
		if(right!=null) right.printPreOrder(out) ;
	}

	public void printPosOrder(PrintStream out) {
		if(left!=null) left.printPosOrder(out) ;		
		if(right!=null) right.printPosOrder(out) ;
		out.println(root);		
	}
	
	public T lca(T node1, T node2) {
		//assuming the nodes are in the tree
		//or else we would complicate the code with even more checks...		
		boolean leftHas = false ;
		boolean rightHas = false ;
		
		//polulating the booleans
		if(left!=null && (left.contains(node1) || left.contains(node2)) )
			leftHas = true ;
		if(right!=null && (right.contains(node1) || right.contains(node2)) )
			rightHas = true ;
		
		//checking the varios scenarios possible (see the pictures)
		if(leftHas && rightHas)
			return root ;
		if(leftHas && (left.root.compareTo(node1)==0 || left.root.compareTo(node2)==0) )
			return root ;
		if(rightHas && (right.root.compareTo(node1)==0 || right.root.compareTo(node2)==0) )
			return root ;
		
		//if so far no condition was hit, we have to go deeper...
		//at least one of the booleans has to be true due to the 
		//pre condition we assumed : node1 and node2 exist in this tree
		if(leftHas)
			return left.lca(node1, node2);
		else //if(rightHas) 
			//ELSE or the compiler would demand a default return at 
			//the end even if its impossible in due to our precondition
			return right.lca(node1, node2);
	}
	
	public boolean checkConsistency(String s) {
		if(root==null && (left!=null || right!=null)){
			System.err.println("SCENARIO: "+s+"\troot:"+root+" left:"+left+" right:"+right);
			System.err.flush();
			return false ; //root null and branches not null?!
		}
						
		return (left==null || left.checkConsistency(s)) &&
				(right==null || right.checkConsistency(s)) ;
	}
}
