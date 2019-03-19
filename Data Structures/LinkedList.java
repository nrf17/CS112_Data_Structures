import java.util.NoSuchElementException;

public class LinkedList <T> {
	

	
	
	Node <T> front;
	int size;
	LinkedList (){
		front = null;
		size = 0;
	}
	
	void addToFront(T newData){
		front = new Node <T> (newData, front);
		size++;
	}
	
	
	T deleteFront () {
		if(front == null){
			//exceptions
			throw new NoSuchElementException("Linked List is empty");

		}
		T temp = front.data;
		front = front.next;
		size--;
		return temp;
	}
	
	void traverse(){
		for(Node<T> ptr = front; ptr != null; ptr = ptr.next){
			System.out.print(ptr.data + " -> ");
		}
		System.out.println("\\");
	}
}
