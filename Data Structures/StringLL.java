
public class StringLL {

	
	private StringNode front;
	private int size;
	
	
	StringLL (){
		front = null;
		size = 0;
	}
	
	
	public void addToFront (String newData){
		
		front = new StringNode(newData, front);
		size++;
	}
	
	public void traverse (){
		for(StringNode ptr = front; ptr != null; ptr = ptr.next){
			System.out.print(ptr.data + " -> ");
		}
		System.out.println("\\");
	}
	
	
	public void delete (String target){
		StringNode ptr = front;
		StringNode prev = null;
		while (ptr != null && ptr.data.equals(target)){
			prev = ptr;
			ptr = ptr.next;
		}
		if(ptr == null){
			System.out.println(target + " not found");
		} else if (ptr == front){
			front = front.next;
			size--;
		} else {
			prev.next = ptr.next;
			size--;
		}
	}
	
}
