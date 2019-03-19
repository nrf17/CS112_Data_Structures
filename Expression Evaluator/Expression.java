package apps;

import java.io.*;
import java.util.*;

import structures.Stack;

public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;                
    
	/**
	 * Scalar symbols in the expression 
	 */
	ArrayList<ScalarSymbol> scalars;   
	
	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;
    
    /**
     * String containing all delimiters (characters other than variables and constants), 
     * to be used with StringTokenizer
     */
    public static final String delims = " \t*+-/()[]";
    
    /**
     * Initializes this Expression object with an input expression. Sets all other
     * fields to null.
     * 
     * @param expr Expression
     */
    public Expression(String expr) {
        this.expr = expr;
    }

    /**
     * Populates the scalars and arrays lists with symbols for scalar and array
     * variables in the expression. For every variable, a SINGLE symbol is created and stored,
     * even if it appears more than once in the expression.
     * At this time, values for all variables are set to
     * zero - they will be loaded from a file in the loadSymbolValues method.
     */
    public void buildSymbols() {
    	scalars = new ArrayList<ScalarSymbol>();
    	arrays = new ArrayList<ArraySymbol>();
    	StringTokenizer str = new StringTokenizer(expr, delims);
    	while(str.hasMoreTokens()){
    		String token = str.nextToken();
    		try{
    			Float.parseFloat(token);
    			continue;
    		} catch (NumberFormatException e){
    			if(expr.indexOf(token) + token.length() < expr.length() && expr.charAt(expr.indexOf(token) + token.length()) == '['){
    			if(!arrays.isEmpty()){
    				boolean inAL = false;
    				for(ArraySymbol i : arrays){
    					if(i.name.equals(token)){
    						inAL = true;
    						break;
    					}
    					if(inAL != true){
    						arrays.add(new ArraySymbol(token));
    					}
    				}
    			}else{
    				arrays.add(new ArraySymbol(token));
    			}

    			} else{
    				if(!scalars.isEmpty()){
    					boolean inSL = false;
    					for(ScalarSymbol i : scalars){
    						if(i.name.equals(token)){
    							inSL = true;
    							break;
    						}
    					}
    					if(inSL!= true){
    						scalars.add(new ScalarSymbol(token));
    					}
    				}else{
    				scalars.add(new ScalarSymbol(token));
    				}
    			}
    		}
    	}
    }
    
    /**
     * Loads values for symbols in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     */
    public void loadSymbolValues(Scanner sc) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String sym = st.nextToken();
            ScalarSymbol ssymbol = new ScalarSymbol(sym);
            ArraySymbol asymbol = new ArraySymbol(sym);
            int ssi = scalars.indexOf(ssymbol);
            int asi = arrays.indexOf(asymbol);
            if (ssi == -1 && asi == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                scalars.get(ssi).value = num;
            } else { // array symbol
            	asymbol = arrays.get(asi);
            	asymbol.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    asymbol.values[index] = val;              
                }
            }
        }
    }
    
    
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array 
     * subscript expressions.
     * 
     * @return Result of evaluation
     */
    
    
    
    private String scalarValue(String token){
    	float val = 0;
    	for(ScalarSymbol i : scalars){
    		if(i.name.equals(token)){
    			val = i.value;
    		}
    	}
    	return String.valueOf(val); 
    }
    
    
    
    private String arrayValue(String token, int index){
    	float val = 0;
    	for(ArraySymbol i: arrays){
    		if(i.name.equals(token)){
    			val = i.values[index];
    		}
    	}
    	return String.valueOf(val);
    }
    
    
    
    
    private boolean isNum(String token){
    	try{
			Float.parseFloat(token);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
    }
    
    
    
    
    private float solve(float a, String b, float c){
    	float result;
    	if (b.equals("+")){
    		result = c + a;
    	} else if(b.equals("-")){
    		result = c - a;
    	} else if(b.equals("*")){
    		result = c * a;
    	} else{
    		result = c/a;
    	}
    	return result;
    }
    
    
    
    private float moveOps(String str){
    	Stack<String> num = new Stack<String>();
    	Stack<String> ops = new Stack<String>();
    	StringTokenizer str1 = new StringTokenizer(str, delims, true);
    	String comp = str;
    	if(ops.isEmpty() && !str1.hasMoreTokens()){
    		return Float.parseFloat(num.pop());
    	}
    	
    	while (str1.hasMoreTokens()){ 
    		String token = str1.nextToken();
    		int len = token.length();
    		
    		//pushes operators in ops stack
    		if(delims.contains(token)){
    			
    			//paren recursion
    			if(token.contains("(")){
            		String substr = str.substring(str.indexOf("(")+1, str.lastIndexOf(")"));
            		float returnz = moveOps(substr);  
            		num.push(String.valueOf(returnz));
            		int count = 1;
            		while(str1.hasMoreTokens() && count != 0){
            			if(comp.length() > 0){
    						comp = comp.substring(len);
    						}
            			token = str1.nextToken();
            			len = token.length();
            			if(token.contains("(")){
            				count++;
            			}
            			if(token.contains(")")){
            				count--;
            			}
            		}
            		if(str1.hasMoreTokens()){
            			if(comp.length() > 0){
    						comp = comp.substring(len);
    						}
            			token = str1.nextToken();
            			len = token.length();
            		}
    			}
    			
    			
    			
    			if(!token.equals(")") || !token.equals("]")){
    			ops.push(token);
    			if(comp.length() > 0){
					comp = comp.substring(len);
					}
    			}
    		}
    		
    		
    		
    		
    		//pushing numbers and viarables in the num stack
    		else{
    			if(isNum(token)){
    				num.push(token);
    				if(comp.length() > 0){
						comp = comp.substring(len);
					}
    			} else{
    				if(str.length() > 1 && str.charAt(str.indexOf(token)+1) == '['){
    					String sub = str.substring(str.indexOf("[")+1, str.lastIndexOf("]"));
    					float ar = moveOps(sub);
    					num.push(String.valueOf(ar));
    					String p = num.pop();
    					int ind = Integer.parseInt(p.substring(0,p.indexOf('.')));
    					num.push(arrayValue(token, ind));
    					if(comp.length() > 0){
    						comp = comp.substring(len);
    						}
    					token = str1.nextToken();
    					len = token.length();
    					int count2 = 1;
    					while(str1.hasMoreTokens() && count2 != 0){
    						if(comp.length() > 0){
        						comp = comp.substring(len);
        						}
    						token = str1.nextToken();
    						len = token.length();
    						if(token.contains("[")){
                				count2++;
                			}
                			if(token.contains("]")){
                				count2--;
                			}
    					}
    					if(comp.length() > 0){
    						comp = comp.substring(len);
    						}
    				}else {
    					num.push(scalarValue(token));
    					if(comp.length() > 0){
    						comp = comp.substring(len);
    						}
    				}
    			}
    		}

    		
    		
    		
    		
    		
    		
    		
    		
    		if(!ops.isEmpty()){
    			if(ops.peek().equals("*") || ops.peek().equals("/")){
    				if(comp.charAt(0) == '('){
    					String sub = str.substring(str.indexOf("(")+1, str.lastIndexOf(")"));
    					float result = moveOps(sub);
    					num.push(String.valueOf(result));
    					float one = Float.parseFloat(num.pop());
    					String two = ops.pop();
    					float three = Float.parseFloat(num.pop());
    					float four = solve(one, two, three);
    					String five = "" + four;
    					num.push(five);
    					int count = 1;
    					while(str1.hasMoreTokens() && count != 0){
    						if(comp.length() > 0){
        						comp = comp.substring(len);
        						}
    						token = str1.nextToken();
    						len = token.length();
    						if(token.contains("(")){
                				count++;
                			}
                			if(token.contains(")")){
                				count--;
                			}
    					}
    					if(str1.hasMoreTokens()){
    						if(comp.length() > 0){
        						comp = comp.substring(len);
        						}
    						token = str1.nextToken();
    						len = token.length();
    					}
    					if(!token.equals(")")){
    						if(comp.length() > 0){
        						comp = comp.substring(len);
        						}
    						ops.push(token);
    					}
    				}
    				
    				
    				
    				
    				else {
    					if(comp.length() > 0){
    						comp = comp.substring(len);
    						}
    					token = str1.nextToken();
    					len = token.length();
    					if(isNum(token)){
    						if(comp.length() > 0){
        						comp = comp.substring(len);
        						}
    						num.push(token);
    					} else{
    						if(str.length() > 1 && str.charAt(str.indexOf(token)+1) == '['){
    							String sub = str.substring(str.indexOf("[")+1, str.lastIndexOf("]"));
    							float ar = moveOps(sub);
    							num.push(String.valueOf(ar));
    							String p = num.pop();
    							int ind = Integer.parseInt(p.substring(0, p.indexOf('.')));
    							num.push(arrayValue(token, ind));
    							if(comp.length() > 0){
            						comp = comp.substring(len);
    							}
    							token = str1.nextToken();
    							int count2 = 1;
    							while(str1.hasMoreTokens() && count2 != 0){
    								if(comp.length() > 0){
    									comp = comp.substring(len);
    	        					}
    								token = str1.nextToken();
    								if(token.contains("[")){
    									count2++;
    								}
    								if(token.contains("]")){
    									count2--;
    								}
    							}
    							if(comp.length() > 0){
    								comp = comp.substring(len);
            					}
    						} else {
    							num.push(scalarValue(token));
    						}
    					}
    					float a = Float.parseFloat(num.pop());
    					String b = ops.pop();
    					float c = Float.parseFloat(num.pop());
    					float d = solve(a,b,c);
    					String e = "" + d;
    					num.push(e);
    					}
    				}
    			}
    		}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	while (!str1.hasMoreTokens() && !ops.isEmpty()){
    		Stack<String> temp = new Stack<String>();
    		Stack<String> temp2 = new Stack<String>();
    		while(!num.isEmpty()){
    			temp.push(num.pop());
    		}
    		while(!ops.isEmpty()){
    			temp2.push(ops.pop());
    		}
    		while(!temp2.isEmpty()){
    		float c = Float.parseFloat(temp.pop());
    		String b = temp2.pop();
    		float a = Float.parseFloat(temp.pop());
    		float d = solve(a,b,c);
    		String e = "" + d;
    		temp.push(e);
    		}
    		num.push(temp.pop());
    	}
    	
    	

    	
    	return Float.parseFloat(num.pop());
    }
    
    
    
    
    
    public float evaluate() {
    		return moveOps(expr);
    }

    /**
     * Utility method, prints the symbols in the scalars list
     */
    public void printScalars() {
        for (ScalarSymbol ss: scalars) {
            System.out.println(ss);
        }
    }
    
    /**
     * Utility method, prints the symbols in the arrays list
     */
    public void printArrays() {
    		for (ArraySymbol as: arrays) {
    			System.out.println(as);
    		}
    }

}
