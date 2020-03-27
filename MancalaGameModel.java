package ram;

import java.util.*;
import javax.swing.event.*;

/**
 * The MancalaGameModel is the model portion of the 
 * MVC pattern. It has data related to the game and it updates the
 * board when moves are made or undone.
 * @author Aniqua Azad, Malaak Khalil, Ryan Tran
 *
 */
public class MancalaGameModel 
{
	private ArrayList<ChangeListener> cListeners;
	private int[] board; //the current board
	private int[] previousBoard; //the previous board (if undo is chosen)
	private static final int A_MANCALA_POS = 0; //position of player A Mancala
	private static final int B_MANCALA_POS = 13; //position of player B Mancala
	private static final int TOTAL_NUM_PITS = 14;
	private int aUndoCount; //player A undo counter
	private int bUndoCount; //player B undo counter
	
	/**
	 * Creates an instance of an empty Mancala Board
	 */
	public MancalaGameModel()
	{
		/*
		 * 		1 3 5 7 9 11
		 * 0					13
		 * 		2 4 6 8 10 12
		 * 
		 * Where 0 and 13 are the Mancala pits for A and B, respectively
		 */
		board = new int[TOTAL_NUM_PITS];
		previousBoard = new int[TOTAL_NUM_PITS];
		cListeners = new ArrayList<>();
		aUndoCount = 0;
		bUndoCount = 0;
		for(int i = 0; i < TOTAL_NUM_PITS; i++)
			board[i] = 0;
		for(int i = 0; i < TOTAL_NUM_PITS; i++)
			previousBoard[i] = 0;
	}
	
	/**
	 * Creates the Mancala board with the players' choice of 
	 * stones per pit
	 * @param numStones the number of stones per pit
	 */
	public void setUpMancalaBoard(int numStones)
	{
		for(int i = 0; i < TOTAL_NUM_PITS; i++)
		{
			if(i != A_MANCALA_POS && i != B_MANCALA_POS)
				board[i] = numStones;
			else
				board[i] = 0;
		}
		
		for(int i = 0; i < TOTAL_NUM_PITS; i++)
		{
			if(i != A_MANCALA_POS && i != B_MANCALA_POS)
				previousBoard[i] = numStones;
			else
				previousBoard[i] = 0;
		}
	}
	
	/**
	 * Adds a ChangeListener 
	 * @param cl ChangeListener to be added
	 */
	public void attach(ChangeListener cl)
	{
		cListeners.add(cl);
	}
	
	/**
	 * Updates the board based on moves made
	 */
	public void updateBoard()
	{
		for(ChangeListener cl : cListeners)
		{
			cl.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * By undoing the last move, it updates the board
	 * to the previous board (before the move was made).
	 */
	public void undoMove()
	{
		board = previousBoard.clone();
		updateBoard();
	}
	
	/**
	 * Increments player A's undo count
	 */
	public void incrAUndoCount()
	{
		aUndoCount++;
	}
	
	/**
	 * Increments player B's undo count
	 */
	public void incrBUndoCount()
	{
		bUndoCount++;
	}
	
	/**
	 * Resets player A's undo count
	 */
	public void resetAUndoCount()
	{
		aUndoCount = 0;
	}
	
	/**
	 * Resets player B's undo count
	 */
	public void resetBUndoCount()
	{
		bUndoCount = 0;
	}
	
	/**
	 * Determines if player A can still undo
	 * @return if player A can undo
	 */
	public boolean canAUndo()
	{
		boolean canUndo = true;
		if(aUndoCount > 3)
			canUndo = false;
		return canUndo;
	}
	
	/**
	 * Determines if player B can still undo
	 * @return if player B can undo
	 */
	public boolean canBUndo()
	{
		boolean canUndo = true;
		if(bUndoCount > 3)
			canUndo = false;
		return canUndo;
	}
	
	/**
	 * Returns the current Mancala board
	 * @return the current Mancala board
	 */
	public int[] getCurrentBoard()
	{
		return board;
	}
	
	/**
	 * Returns the previous Mancala board
	 * @return the previous Mancala board
	 */
	public int[] getPreviousBoard()
	{
		return previousBoard;
	}
	
	/**
	 * Determines if the game is over
	 * @return if game is over
	 */
	public boolean gameOver()
	{
		int playerAStoneCount = 0;
		int playerBStoneCount = 0;
		
		for(int i = 1; i <= 11; i += 2)
			playerAStoneCount = playerAStoneCount + board[i];
		for(int i = 2; i <= 12; i += 2)
			playerBStoneCount = playerBStoneCount + board[i];
		
		//if player A or player B has empty pits, game is over
		if(playerAStoneCount == 0 || playerBStoneCount == 0)
			return true;
		
		return false;
	}
	
	/**
	 * Returns the winner of the game
	 * @return 0 if player A is winner, 1 if player B is winner
	 */
	public int getWinner()
	{
		return 0;
	}
	
	/**
	 * Moves the stones from the pit index
	 */
	public void move(int index)
	{
		
	}
}
