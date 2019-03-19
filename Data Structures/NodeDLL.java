
public class NodeDLL <T> {
	
	
	NodeDLL <T> previous;
	T data;
	NodeDLL <T> next;
	
	
	NodeDLL (T data, NodeDLL<T> previous, NodeDLL<T> next){
		this.data = data;
		this.previous = previous;
		this.next = next;
	}

}
