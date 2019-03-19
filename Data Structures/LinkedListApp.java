
public class LinkedListApp {
	
	public static void main (String [] args){ 
	
		LinkedList <Integer> llI = new LinkedList<Integer>();
		llI.addToFront(8);
		llI.addToFront(7);
		llI.addToFront(6);
		llI.traverse();
		
		
		LinkedList<String> llS = new LinkedList<String>();
		llS.addToFront("red delicous");
		llS.addToFront("gala");
		llS.addToFront("macintosh");
		llS.traverse();
		System.out.println("Just deleted " + llS.deleteFront());
		llS.traverse();
		System.out.println("Just deleted " + llS.deleteFront());
		llS.traverse();
		System.out.println("Just deleted " + llS.deleteFront());
		llS.traverse();

	}
}
