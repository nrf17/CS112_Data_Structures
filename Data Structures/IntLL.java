
public class IntLL {
	
	public static IntNode addToFront (IntNode front, int newData){
		return new IntNode(newData, front);
	}
	

	public static void traverse(IntNode front){
		if(front == null){
			System.out.println("Linked list is empty");
			return;
		}
		for (IntNode ptr = front; ptr != null; ptr = ptr.next){
			System.out.print(ptr.data + " - ");
		}
	}
	
	
	public static boolean search (IntNode front, int target){
		for (IntNode ptr = front; ptr != null; ptr = ptr.next){
			if(ptr.data == target){
				return true;
			}
		}
		return false;
	}
	
	
	
	public static IntNode delete(IntNode front, int target){
		IntNode prev = null;
		IntNode ptr = front;
		
		while(ptr != null && ptr.data == target){
			prev = ptr;
			ptr = ptr.next;
		}
		if (ptr == null){
			return front;
		} else if (ptr == front){
			return ptr.next;
		} else {
			prev.next = ptr.next;
			return front;
		}
	}
	
	
	
	public static boolean addAfter (IntNode front, int target, int newData){
		for (IntNode ptr = front; ptr != null; ptr = ptr.next){
		if (ptr.data == target){
			IntNode newNode = new IntNode (newData, ptr.next);
			ptr.next = newNode;
			return true;
			}
		}
		return false;
	}
	
	
	
	
	public static void main(String [] args){
		IntNode L = null;
		L = addToFront(L, 8);
		L = addToFront(L, 7);
		L = addToFront(L, 3);
		L = addToFront (L, 2);
		traverse(L);
		System.out.println(search(L, 6));
		System.out.println(search(L, 7));
		L = delete(L, 10);
		traverse(L);
		L= delete(L, 2);
		traverse(L);
		addAfter(L, 3, 4);
		traverse(L);
	}
}