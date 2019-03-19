package tree;

import java.util.ArrayList;

//allows comparisons of all types, such ints, strings, ext
public class BST<T extends Comparable <T>> {
	
	BSTNode<T> root;
	int size;
	
	public BST (){
		root = null;
		size = 0;
	}
	
	
	void insert (T key){
		// 1. search for key
		BSTNode<T> ptr = root;
		BSTNode<T> p = null;
		int c = 0;
		while(ptr!=null){
			c = key.compareTo(ptr.key);
			if(c==0){//equal
				throw new IllegalArgumentException(key + "already in BST");
			}
			p = ptr;
			if(c<0){
				ptr = ptr.right;
			}
			else{
				ptr = ptr.right;
			}
		}
		
		//2. insert key, create new node and insert it
		BSTNode <T> node = new BSTNode(key, null, null);
		if(p==null){
			//empty tree
			root = node;
		} else if (c<0){
			p.left = node;
		} else {
			p.right = node;
		}
		size++;
	}
	
	
	
	
	
	
	public void delete(T key){
		//1. find node to delete
		BSTNode <T> x = root;
		BSTNode<T> p = null;
		int c = 0;
		while(x != null){
			c = key.compareTo(x.key);
			if(c==0){ // found it
				break;
			}
			p = x;
			x = (c<0) ? x.left : x.right; //ternary statement
		}
		
		
		// 2. key is not found
		/*if(x == null){
			throw new NoSuchElementException(key + "not found");
		}*/
		
		// 3. check if has 2 children
		BSTNode<T> y = null;
		if(x.left != null && x.right != null){
			//find inorder predecessor
			y = x.left;
			p = x;
			while(y.right != null){
				p = y;
				y = y.right;
			}
			// copy y into x
			x.key = y.key;
			
			
			
			//prepare to delete y
			x = y;
		}
		// 4.  p is null, meaning x is the root, and x doesnt have 2 children
		if(p==null){
			root = x.left != null ? x.left : x.right;
			size--;
			return;
		}
		
		// 5. case 1 and 2 at the same code
		BSTNode<T> tmp = x.right != null ? x.right : x.left;
		if(x == p.left){
			p.left = tmp;
		} else {
			p.right = tmp;
		}
		size--;
	}
	
	
	
	
	
	
	private static <T extends Comparable> void inOrder (BSTNode<T> root, ArrayList<T> list){
		if(root==null){
			return;
		}
		inOrder(root.left, list);
		list.add(root.key);
		inOrder(root.right, list);
	}
	
	
	
	public ArrayList<T> treeSort(ArrayList<T> array){
		ArrayList<T> list = new ArrayList<T> ();
		//create bst and insert array items
		BST<T> bst = new BST();
		for(T item: array){
			bst.insert(item);
		}
		inOrder(bst.root, list);
		return list;
	}
	
	
	
	
	
	
	public static void main(String[]args){
		int [] array = {6,9,2,1,56,30,41};
		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(6);
		a.add(9);
		a.add(11);
		a.add(56);
		a.add(25);
		a.add(20);
		
	}
	
	
	
	
	
	
}
