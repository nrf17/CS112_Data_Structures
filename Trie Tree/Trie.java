package structures;

import java.util.ArrayList;

/**
 * This class implements a compressed trie. Each node of the tree is a CompressedTrieNode, with fields for
 * indexes, first child and sibling.
 * 
 * 
 *
 */
public class Trie {

	/**
	 * Words indexed by this trie.
	 */
	ArrayList<String> words;

	/**
	 * Root node of this trie.
	 */
	TrieNode root;

	/**
	 * Initializes a compressed trie with words to be indexed, and root node set to
	 * null fields.
	 * 
	 * @param words
	 */
	public Trie() {
		root = new TrieNode(null, null, null);
		words = new ArrayList<String>();
	}

	/**
	 * Inserts a word into this trie. Converts to lower case before adding.
	 * The word is first added to the words array list, then inserted into the trie.
	 * 
	 * @param word Word to be inserted.
	 */

	
	private String newPreFix(int begin, String pre, String word1, String word2){
		for(int i = begin; i < word1.length(); i++){
			if(word1.charAt(i) == word2.charAt(i)){
				pre = pre + word1.charAt(i);
			} else{ break;}
		}
		return pre;
	}



	public void insertWord(String word) {
		word.toLowerCase();
		words.add(word);
		if(root.firstChild == null){//the tree is empty, adds very first node
			int newWordI = words.indexOf(word);
			short newEnd = (short)(word.length() - 1);
			Indexes ind1 = new Indexes(newWordI, (short)0, newEnd);
			TrieNode node = new TrieNode(ind1, null, null);
			root.firstChild = node;
		}else{
			TrieNode ptr = root.firstChild;
			int startI = 0;
			while(ptr != null){
				String comm = "";
				int wordI = ptr.substr.wordIndex;
				String compWord = words.get(wordI);
				int start = ptr.substr.startIndex;
				comm = newPreFix(start, comm, word, compWord);
				if(comm.length() > 0){ 
					if(ptr.firstChild == null){
						short startInd = (short)(startI);
						short endInd = (short)(startI + comm.length()-1);
						ptr.substr.startIndex = startInd;
						ptr.substr.endIndex = endInd;
						int newWordI1 = words.indexOf(compWord);
						short newStartI1 = (short)(startInd + comm.length());
						short newEndI1 = (short)(compWord.length() - 1);
						Indexes ind1 = new Indexes(newWordI1, newStartI1, newEndI1);
						TrieNode node1 = new TrieNode(ind1, null, null);
						ptr.firstChild = node1;
						int newWordI2 = words.indexOf(word);
						short newStartI2 = (short)(startInd + comm.length());
						short newEndI2 = (short)(word.length() - 1);
						Indexes ind2 = new Indexes(newWordI2, newStartI2, newEndI2);
						TrieNode node2 = new TrieNode(ind2, null, null);
						ptr.firstChild.sibling = node2;
						break;
					else if(ptr.firstChild.firstChild != null){
						ptr = ptr.firstChild;
						continue;
					else if(ptr.firstChild != null && comm.length() < (ptr.substr.endIndex - (ptr.substr.startIndex + 1))){
						short startInd = (short)(ptr.substr.startIndex + 1);
						ptr.substr.startIndex = startInd;
						int newWordI1 = words.indexOf(compWord);
						short newStartI1 = (short)(startI);
						short newEndI1 = (short)(startI + comm.length() - 1);
						Indexes ind1 = new Indexes(newWordI1, newStartI1, newEndI1);
						TrieNode node1 = new TrieNode(ind1, ptr, null);
						root.firstChild = node1;
						int newWordI2 = words.indexOf(word);
						short newStartI2 = (short)(startI + comm.length());
						short newEndI2 = (short)(word.length() - 1);
						Indexes ind2 = new Indexes(newWordI2, newStartI2, newEndI2);
						TrieNode node2 = new TrieNode(ind2, null, null);
						ptr.sibling = node2;
						break;
					else if(ptr.firstChild!=null && comm.length()>(ptr.substr.endIndex-ptr.substr.startIndex+1)){
						short newEndInd = (short)(ptr.substr.startIndex + comm.length() - 1);
						ptr.firstChild.substr.endIndex = newEndInd;
						int newWordI1 = words.indexOf(compWord);
						short newStartI1 = (short)(ptr.substr.startIndex + comm.length());
						short newEndI1 = (short)(compWord.length() - 1);
						Indexes ind1 = new Indexes(newWordI1, newStartI1, newEndI1);
						TrieNode node1 = new TrieNode(ind1, null, null);
						ptr.firstChild.firstChild = node1;
						int newWordI2 = words.indexOf(word);
						short newStartI2 = (short)(ptr.substr.startIndex + comm.length());
						short newEndI2 = (short)(word.length()-1);
						Indexes ind2 = new Indexes(newWordI2, newStartI2, newEndI2);
						TrieNode node2 = new TrieNode(ind2, null, null);
						ptr.firstChild.firstChild.sibling = node2;
						break;
					} else{ startI += comm.length(); ptr = ptr.firstChild; continue;}
					}
				else{
					if(ptr.sibling == null){
						int newWordI = words.indexOf(word);
						short newStartI = (short)(startI);
						short newEndI = (short)(word.length() - 1);
						Indexes ind1 = new Indexes(newWordI, newStartI, newEndI);
						TrieNode node = new TrieNode(ind1, null, null);
						ptr.sibling = node;
						break;
					} else { ptr = ptr.sibling; } 
				}
			}
		}
	}
	/**
	 * Given a string prefix, returns its "completion list", i.e. all the words in the trie
	 * that start with this prefix. For instance, if the tree had the words bear, bull, stock, and bell,
	 * the completion list for prefix "b" would be bear, bull, and bell; for prefix "be" would be
	 * bear and bell; and for prefix "bell" would be bell. (The last example shows that a prefix can be
	 * an entire word.) The order of returned words DOES NOT MATTER. So, if the list contains bear and
	 * bell, the returned list can be either [bear,bell] or [bell,bear]
	 * 
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all words in tree that start with the prefix, order of words in list does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	private ArrayList<String> search(TrieNode ptr, String preFix, ArrayList<String> newWords){
		//gets info at ptr and creates word
		int wordI = ptr.substr.wordIndex;
		short startI = ptr.substr.startIndex;
		short endI = ptr.substr.endIndex;
		String nodeWord = words.get(wordI);
		nodeWord = nodeWord.substring(startI, endI+1);
		if(preFix.charAt(0) == nodeWord.charAt(0)){
			if(preFix.equals(nodeWord)){
				addWord(ptr, newWords);
			}else{ 
				preFix = preFix.substring(nodeWord.length(), preFix.length()); 
				if(ptr.firstChild == null){
					newWords.add(words.get(ptr.substr.wordIndex));
				}else{ search(ptr.firstChild, preFix, newWords); }
			} 
		}else{ search(ptr.sibling, preFix, newWords); } 
		return newWords;
	}
	
	private ArrayList<String> addWord(TrieNode ptr, ArrayList<String> newWords){
			if(ptr.firstChild != null){
				ptr = ptr.firstChild;
			}
			if(ptr.firstChild == null && ptr.sibling == null){
				String word1 = words.get(ptr.substr.wordIndex);
				if(newWords.indexOf(word1) == -1){
					newWords.add(word1);
					return newWords;
				}else { return newWords;} 
			}
			else if(ptr.firstChild != null){
				addWord(ptr.firstChild, newWords);
			}//ends
			else if(ptr.sibling != null){
				addWord(ptr.sibling, newWords);
			}
		String word1 = words.get(ptr.substr.wordIndex);
		if(newWords.indexOf(word1) == -1){
			newWords.add(word1);
		}
		return newWords;
	}
	
	public ArrayList<String> completionList(String prefix) {
		ArrayList<String> newWords = new ArrayList<String>();
		if(root.firstChild == null){
			return newWords;
		} else{ return search(root.firstChild, prefix, newWords); }
	}


	public void print() {
		print(root, 1, words);
	}

	private static void print(TrieNode root, int indent, ArrayList<String> words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}

		if (root.substr != null) {
			System.out.println("      " + words.get(root.substr.wordIndex));
		}

		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		System.out.println("(" + root.substr + ")");

		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
}
