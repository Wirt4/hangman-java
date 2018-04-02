/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;

// 3.31 - test subject wanted to know what the word was that wasn't guessed
//added "your word was: as a a GLabel in the graphics window?
import acm.program.*;
import acm.util.*;

public class HangmanV2 extends ConsoleProgram {
	public static final int applicationWidth = 800; 
	public static final int applicationHeight = 600;
	/*Having the feedback messages as public vars makes it easier for a second party to edit the "voice" of the game.*/
	public static final String welcome = "Welcome to Hangman, Pard'ner!";
	public static final String numGuess1A ="You've got ";
	public static final String numGuess1B =" guesses to string up a varmit.";
	public static final String numGuess2B =" wrong guesses left";
	public static final String numGuess3B ="1 wrong guess left";
	public static final String winMsg = "Yee-Haw! You Win.";
	public static final String loseMsg = "Sorry, your word was ";
	public static final String oneLetterMsg= "Whoah, just one letter at a time.";
	public static final String mustBeLetterMsg ="It's gotta be a letter, pardner.";
	public static final String userInput = "Your guess:";
	public static final String plyAgn ="Want to play again?(y/n)";
	public static final String gdBye ="Thanks for playing, pard'ner.";
	public static final String wordState = "Your word now looks like this:";
	public static final String goAgain ="You got that one already. Try another.";
	public static final String correctGuess ="Your guess is correct, Pard'ner";
	public static final String wrongGuess1 = "There ain't no ";
	public static final String wrongGuess2 = "'s in the word.";
	
	HangmanCanvas n = new HangmanCanvas();
	
	int numGuess=8;
	String secretWord="";
	String playWord="";
	Character ch='a';
	/*Makes the split screen window/console effect per class instructions*/
	private HangmanCanvas canvas;
	public void init() {
        canvas = new HangmanCanvas();
        add(canvas);
	}
	
	public void run() {
		String playAgain="Y";
		while (playAgain.equalsIgnoreCase("Y")) {
			playGame();
			playAgain= readLine(plyAgn);
			println("");
		}
		println(gdBye);
	}
	
	/*sets up for a new game*/
	private void resetEverything() {
		numGuess=8;
		String secretWord="";
		String playWord="";
		Character ch='a';
		canvas.reset();
	}
	
	/*plays a game of hangman based on user input*/
	private void playGame() {
		this.resize(applicationWidth , applicationHeight);
		resetEverything();
		makeWord();
		makePlayWord(secretWord);
		canvas.displayWord(playWord);
		String guessedCharacters="";
		println(welcome);
		println(numGuess1A+numGuess+numGuess1B);
		while(numGuess > 0 && secretWord.equals(playWord)==false) {
			println("");
			println(wordState+playWord);
			ch = takeInCharacter();	
			if(isInWord(ch,secretWord)==true && isInWord(ch, playWord)==true) {
				println(goAgain);
			}
			if(isInWord(ch,secretWord)==true && isInWord(ch, playWord)==false) {
				println(correctGuess);
				updatePlayword(ch);
				canvas.displayWord(playWord);
			}
			if(isInWord(ch,secretWord)==false) {
				numGuess--;
				if(isInWord(ch,guessedCharacters)==false) {
					guessedCharacters+=ch;
				}
				canvas.noteIncorrectGuess(numGuess, guessedCharacters);
				println(wrongGuess1+ch+wrongGuess2 );
				if(numGuess==1) {
					println(numGuess1A+numGuess3B);
				} else {
					println(numGuess1A+numGuess+numGuess2B);
				}
			}
		}
		playWord="";
		println("");
		if (numGuess==0) {
			println(loseMsg+secretWord+".");
			canvas.realWord(loseMsg,secretWord);
		}else {
			println(winMsg);
			canvas.endMsg(winMsg);
			}
    }
	
	/*When a correct character is guessed, this updates the string that's displayed*/
	private void updatePlayword(char ch) {
		String newString="";
		for(int i=0; i< secretWord.length(); i++){
			if(secretWord.charAt(i)==ch){
				newString+=ch;
				}else{
					newString+=playWord.charAt(i);
				}
		}
		playWord=newString;
	}
	
	/*Wanted to decompose this into a separate method, since it gets called frequently in playGame()*/
    private boolean isInWord(char ch, String word) {
    		if(word.indexOf(ch)==-1) {
    			return false;
    		}else {
    			return true;
    		}
    }
    
    /* screens out inappropriate entries*/
	private char takeInCharacter() {
		String guess =getGuess();
		while(guess.length()==0) {
			guess= getGuess();
		}
		Character ch = guess.charAt(0);
		while(guess.length()>1||ch.isLetter(ch)==false) {
			if(guess.length()>1) {
				println(oneLetterMsg);
				guess =getGuess();
				ch = guess.charAt(0);
			}
			if (ch.isLetter(ch)==false){
				println(mustBeLetterMsg);
				guess=getGuess();
				ch = guess.charAt(0);
			}
		}
		ch = ch.toUpperCase(ch);
		return ch;
	}
	
	/*Gets user input*/
	private String getGuess() {
		return readLine(userInput);
	}
	
    private void makeWord() {
    	HangmanLexiconV2 word = new HangmanLexiconV2();
    	RandomGenerator r = new RandomGenerator();
    	int i=r.nextInt(0, word.getWordCount());
    	secretWord=word.getWord(i);
    }
    
    private void makePlayWord(String secretWord) {
    		for(int i=0; i<secretWord.length(); i++) {
    			playWord+="-";
    		}
    }
}
