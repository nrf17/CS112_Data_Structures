
public class StringLLApp {

	
	public static void main(String [] args){
		StringLL ll1 = new StringLL();
		ll1.addToFront("352");
		ll1.addToFront("206");
		ll1.addToFront("112");
		ll1.addToFront("111");
		ll1.traverse();
		ll1.delete("111");
		ll1.traverse();
		ll1.delete("206");
		ll1.traverse();
		
		
		StringLL ll2 = new StringLL();
		ll2.addToFront("Pear");
		ll2.addToFront("Apple");
		ll2.traverse();
		ll1.traverse();
	}
}
