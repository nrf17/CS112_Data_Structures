package apps;

/**
 * This class encapsulates a (name, integer value) pair for a scalar (non-array) variable. 
 * The variable name is a sequence of one or more letters.
 * 
 * 
 *
 */
public class ScalarSymbol {
	
	/**
	 * Name, sequence of letters
	 */
	public String name;
	
	/**
	 * Integer value
	 */
	public int value;

	/**
	 * Initializes this symbol with given name, and zero value
	 * 
	 * @param name Variable name
	 */
	public ScalarSymbol(String name) {
		this.name = name; 
		value = 0;
	}
	
	
	public String toString() {
		return name + "=" + value;
	}
	
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ScalarSymbol)) {
			return false;
		}
		ScalarSymbol ss = (ScalarSymbol)o;
		return name.equals(ss.name);
	}
}

