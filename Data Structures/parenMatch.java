import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;


public class parenMatch {
	
	//return true for a parenthesis matched expression
	//false otherwise
	
	public static boolean parenMatch (String expr) {
		
		Stack<Character> stack = new Stack<Character>();
		for(int i = 0; i < expr.length(); i++){
			char ch = expr.charAt(i);
			if(ch == '('){
				stack.push(ch);
			} else if(ch == ')'){
				stack.pop();
				try{
					stack.pop();
				}catch (NoSuchElementException e){ //problem here with parameter
				return false;	
				}
			}
		}
		return stack.isEmpty();
	}
	/*
	 * System.in is the standard input a.k.a. the console.
	 * The Operating System handles the standard input just as any 
	 * file by reading it bytes at a time.
	 * 
	 * In order to convert from a stream of bytes to characters 
	 * we'll use InputStreamReader.
	 * 
	 * I'd rather deal with a whole line of characters (the expression)
	 * than single characters at a time. For that we'll need to
	 * wrap the InputStreamReader using the BufferedReader, 
	 * that buffers a character stream and lets me deal with it in
	 * a easier way.
	 * 
	 */
	public static void main (String[] args) {
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter expression: ");
			String expr = br.readLine();
			
			if (parenMatch(expr)) {
				System.out.println("Matched");
			} else {
				System.out.println("Not matched");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
