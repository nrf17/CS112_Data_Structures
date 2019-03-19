package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 *
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;
	
	/**
	 * Degree of term.
	 */
	public int degree;
	
	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * 
 *
 */
class Node {
	
	/**
	 * Term instance. 
	 */
	Term term;
	
	/**
	 * Next node in linked list. 
	 */
	Node next;
	
	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * 
 *
 */
public class Polynomial {
	
	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;
	
	/** 
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	
	
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
	
		Node ptr1 = this.poly;
		Node ptr2 = p.poly;
		Polynomial polyN = new Polynomial();
		Node ptr3 = polyN.poly;
		Node front = null;
		Node temp = null;
		while(ptr1 != null && ptr2 != null){
				if(ptr1.term.degree < ptr2.term.degree){
					//adds lower, ptr1 to list
					temp = new Node(ptr1.term.coeff, ptr1.term.degree, null);
					ptr1 = ptr1.next;
				} else if(ptr1.term.degree > ptr2.term.degree){
					//adds lower, ptr2 to list
					temp = new Node(ptr2.term.coeff, ptr2.term.degree, null);
					ptr2 = ptr2.next;
					} else {
						//both exp are equal combines coeff and keeps exp
						float sum = ptr1.term.coeff + ptr2.term.coeff;
						temp = new Node(sum, ptr1.term.degree, null);
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
						if(temp.term.coeff == 0){
							continue;
						}
				}
				if(front == null){
					front = temp;
					polyN.poly = front;
					ptr3 = front;
				} else{
					ptr3.next = temp;
					ptr3 = ptr3.next;
				}
			}
		while(ptr1 != null){
			//add rest ptr1 to list
			temp = new Node(ptr1.term.coeff, ptr1.term.degree, null);
			ptr1 = ptr1.next;
			if(front == null){
				front = temp;
				polyN.poly = front;
				ptr3 = front;
			} else {
				ptr3.next = temp;
				ptr3 = ptr3.next;
			}
		}
		while(ptr2 != null){
			//add rest ptr2 to list
			temp = new Node(ptr2.term.coeff, ptr2.term.degree, null);
			ptr2 = ptr2.next;
			if(front == null){
				front = temp;
				polyN.poly = front;
				ptr3 = front;
			} else {
				ptr3.next = temp;
				ptr3 = ptr3.next;
			}
		}
		return polyN;
	}
	
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) {
		
		Polynomial polyR = new Polynomial();
		Node ptr3 = polyR.poly;
		Node frontR = null;
		Node temp = null;
		Node temp2 = null; //???
		Polynomial polyF = new Polynomial();
		Polynomial tempA = new Polynomial();
		Node frontA = null;
		for(Node ptr1 = this.poly; ptr1 != null; ptr1 = ptr1.next){
			for(Node ptr2 = p.poly; ptr2 != null; ptr2 = ptr2.next){
				float coef = ptr1.term.coeff * ptr2.term.coeff;
				int deg = ptr1.term.degree + ptr2.term.degree;
				temp = new Node(coef, deg, null);
				if(frontR == null){
					frontR = temp;
					polyR.poly = frontR;
					ptr3 = frontR;
				}else{
					ptr3.next = temp;
					ptr3 = ptr3.next;
				}
			}
		}
		
		for(Node ptr4 = polyR.poly; ptr4 != null; ptr4 = ptr4.next){
			if(polyF.poly == null){
				temp = new Node(ptr4.term.coeff, ptr4.term.degree, null);
				polyF.poly = temp;
			}else{
				temp2 = new Node(ptr4.term.coeff, ptr4.term.degree, null);
				frontA = temp2;
				tempA.poly = frontA;
				polyF = polyF.add(tempA);
			}
		}
		return polyF;
	}
	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) {
		float sum = 0;
		for(Node ptr = this.poly; ptr != null; ptr = ptr.next){
			sum += ptr.term.coeff * Math.pow(x, ptr.term.degree);
		}
			return sum;
	}
	

	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
}
