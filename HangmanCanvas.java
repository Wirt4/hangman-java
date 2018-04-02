/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;
import acm.util.RandomGenerator;

public class HangmanCanvas extends GCanvas {
	GLabel disWord = new GLabel("");
/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		disWord.setLabel("");
		GLine scaffold = new GLine(scaffX,scaffY,scaffX,scaffY+SCAFFOLD_HEIGHT);
        GLine beam = new GLine(scaffX, scaffY,scaffX+BEAM_LENGTH, scaffY);
        GLine rope = new GLine(bodyX, scaffY, bodyX, scaffY+ROPE_LENGTH);
        add(scaffold);
        add(beam);
        add(rope);
        disWord.setColor(Color.BLUE);
        disWord.setFont("Helvetica-24");
        add(disWord, bodyX-(disWord.getWidth()/2), SCAFFOLD_HEIGHT+scaffY+disWord.getAscent());
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		//word=dashesToUnderscores(word);
        disWord.setLabel(word);
	}
	
/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(int numGuess,String guessedLetters) {
		
        int armOffSetY=scaffY+ROPE_LENGTH+HEAD_RADIUS*2+ARM_OFFSET_FROM_HEAD ;
        int leftElbowX = bodyX-UPPER_ARM_LENGTH;
        int rightElbowX = bodyX+UPPER_ARM_LENGTH;
        int wristY=armOffSetY+LOWER_ARM_LENGTH;
        int hipY= ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH+ARM_OFFSET_FROM_HEAD;
        int leftLegX=bodyX-HIP_WIDTH;
        int rightLegX=bodyX+HIP_WIDTH;
        int ankleY= hipY+LEG_LENGTH ;
        GOval head = new GOval(bodyX-(HEAD_RADIUS), scaffY+ROPE_LENGTH,HEAD_RADIUS*2, HEAD_RADIUS*2);
        GLine body = new GLine(bodyX,scaffY+ROPE_LENGTH+HEAD_RADIUS*2,bodyX,hipY);
        GLine upperLeftArm = new GLine(bodyX,armOffSetY,leftElbowX,armOffSetY);
        GLine upperRightArm = new GLine(bodyX,armOffSetY,rightElbowX,armOffSetY);
        GLine lowerLeftArm = new GLine(leftElbowX, armOffSetY, leftElbowX, wristY);
        GLine lowerRightArm = new GLine(rightElbowX,armOffSetY,rightElbowX,wristY);
        GLine leftHip = new GLine(bodyX, hipY,leftLegX,hipY);
        GLine rightHip = new GLine(bodyX, hipY, bodyX+HIP_WIDTH, hipY);
        GLine leftLeg =new GLine(leftLegX, hipY, leftLegX, ankleY);
        GLine rightLeg = new GLine(rightLegX, hipY, rightLegX, ankleY);
        GLine leftFoot = new GLine(leftLegX, ankleY,leftLegX-FOOT_LENGTH,ankleY);
        GLine rightFoot = new GLine(rightLegX, ankleY,rightLegX+FOOT_LENGTH,ankleY);
     
        GLabel guesses = new GLabel(guessedLetters);
        GLabel loseMsg = new GLabel(randomCuss());
        GLine balloonTail = new GLine(((rightElbowX-bodyX)/2)+bodyX,scaffY+ROPE_LENGTH+HEAD_RADIUS*2, rightElbowX, 2*scaffY);
        guesses.setColor(Color.RED);
        guesses.setFont("Helvetica-24");
        add(guesses, scaffX+margin, guesses.getAscent()+scaffY-margin-guesses.getAscent());
        
        switch (numGuess) {
        		case 7:
        			add(head); 
        			break;
        		case 6: 
        			add(body);
        			break;
        		case 5: 
        			add(upperLeftArm);
                add(lowerLeftArm);
        			break;
        		case 4: 
        			add(upperRightArm);
        	        add(lowerRightArm);
        			break;
        		case 3:
        			add(leftHip);
        	        add(leftLeg);
        			break;
        		case 2:
        			add(rightHip);
                add(rightLeg);
        			break;
        		case 1: 
        			add(leftFoot);
        			break;
        		case 0: 
                add(rightFoot);
                //The stick figure dies a lot, so I thought it would be amusing to have him curse
                add(loseMsg, rightElbowX, 2*scaffY-loseMsg.getAscent());
                add(balloonTail);
        			break;
        		default:
        			GLabel i = new GLabel("note: issue with noteIncorrectGuessMethod"); 
        			break;
        } 
	}
	
	/*displays the user's word on the canvas. Needs to be a different method than endMsg, since the message needs to be on two lines*/
	public void realWord(String line1,String line2) {
		GLabel msg = new GLabel(line1);
		GLabel word = new GLabel(line2+".");
		msg.setColor(Color.RED);
		msg.setFont("Helvetica-24");
		word.setColor(Color.RED);
		word.setFont("Helvetica-24");
		add(msg, bodyX-(msg.getWidth()/2), SCAFFOLD_HEIGHT+scaffY+msg.getAscent()*2);
		add(word, bodyX-(word.getWidth()/2), SCAFFOLD_HEIGHT+scaffY+word.getAscent()*3.5);
	}
	
	/*Since the player will lose more often than win, I thought it would enrich the experience to have random feedback*/
	private String randomCuss() {
		RandomGenerator r = new RandomGenerator();
		String str ="";
		int i=r.nextInt(0, 9);
		switch(i) {
		case 0: 
			str = "Gorram it!";
			break;
		case 1: 
			str = "Shazbot!";
			break;
		case 3:
			str ="Curses!";
			break;
		case 4:
			str = "Mother Puss Bucket!";
			break;
		case 5:
			str = "Belgium!";
			break;
		case 6:
			str = "Oh, Coitus!";
			break;
		case 7:
			str ="Frack!";
			break;
		case 8: 
			str = "Hippikaloric!";
			break;
		case 9:
			str = "Smeg!";
			break;
		}
		return str;
	}
	/*displays a one-line string from the console prgram in the graphics window*/
	public void endMsg(String line) {
		 GLabel msg= new GLabel(line);
		 msg.setColor(Color.RED);
	     msg.setFont("Georgia-24");
	     add(msg, bodyX-(msg.getWidth()/2), SCAFFOLD_HEIGHT+scaffY+msg.getAscent()*2);
	}
/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int scaffX=60;
	private static final int scaffY=80;
	private static final int bodyX=scaffX+BEAM_LENGTH;
	private static final int margin=10;

}
