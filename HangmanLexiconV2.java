/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.*;
import java.util.*;
public class HangmanLexiconV2{
	
public ArrayList<String> lex = makeArray();

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lex.size();
	}
	/**makes an array out of the lexicon file**/
	private ArrayList<String> makeArray() {
		ArrayList<String> lex = new ArrayList<String>();
		String line="";
		try{
			BufferedReader rd =new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while (true) {
			line=rd.readLine();
			if(line==null) {
				break;
			}
			lex.add(line);
		}
		rd.close();
		}catch(IOException ex) {
			lex.add("YOUTHREWANEXEPTION");
		}
	return lex;
	}
	
/** Returns the word at the specified index. */
	public String getWord(int index) {
		return lex.get(index);
	}
}
