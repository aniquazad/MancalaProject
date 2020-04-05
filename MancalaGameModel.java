package ram;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * The MancalaGameModel is the model portion of the MVC pattern. It has data related to the game and
 * it updates the board when moves are made or undone.
 *
 * @author Aniqua Azad, Malaak Khalil, Ryan Tran
 */
public class MancalaGameModel {
  private ArrayList<ChangeListener> cListeners;
  private int[] board; // the current board
  private int[] previousBoard; // the previous board (if undo is chosen)

  private boolean isPlayerATurn;

  public static final int TOTAL_NUM_PITS = 14;
  public static final int NUM_PITS_PER_PLAYER = TOTAL_NUM_PITS / 2 - 1;
  public static final int A_MANCALA_POS = TOTAL_NUM_PITS / 2 - 1;
  public static final int B_MANCALA_POS = TOTAL_NUM_PITS - 1;

  private int aUndoCount;
  private int bUndoCount;
  private static final int MAX_UNDO_COUNT = 3;

  private static final int A_WINNER = 0;
  private static final int B_WINNER = 1;
  private static final int TIE = 2;

  /** Creates an instance of an empty Mancala Board */
  public MancalaGameModel() {
    /*
     * 	    12 11 10 9 8 7
     * 13                    6
     *      0  1  2  3 4 5
     *
     * Where 6 and 13 are the Mancala pits for A and B, respectively
     */
    board = new int[TOTAL_NUM_PITS];
    previousBoard = new int[TOTAL_NUM_PITS];
    cListeners = new ArrayList<>();

    // 50-50 chance for who starts
    isPlayerATurn = Math.random() < 0.5f;
  }

  /**
   * Creates the Mancala board with the players' choice of stones per pit
   *
   * @param numStones the number of stones per pit
   */
  public void setUpMancalaBoard(int numStones) {
    for (int i = 0; i < TOTAL_NUM_PITS; i++) {
      if (i != A_MANCALA_POS && i != B_MANCALA_POS) {
        board[i] = numStones;
        previousBoard[i] = numStones;
      } else {
        board[i] = 0;
        previousBoard[i] = 0;
      }
    }
  }

  /**
   * Adds a ChangeListener
   *
   * @param cl ChangeListener to be added
   */
  public void attach(ChangeListener cl) {
    cListeners.add(cl);
  }

  /** Updates the board based on moves made */
  public void updateBoard() {
    ChangeEvent e = new ChangeEvent(this);
    for (ChangeListener cl : cListeners) {
      cl.stateChanged(e);
    }
  }

  /**
   * By undoing the last move, it updates the board to the previous board (before the move was
   * made).
   */
  public void undoMove() {
    board = previousBoard.clone();
    updateBoard();
  }

  /** Increments player A's undo count */
  public void incrAUndoCount() {
    aUndoCount++;
  }

  /** Increments player B's undo count */
  public void incrBUndoCount() {
    bUndoCount++;
  }

  /** Resets player A's undo count */
  public void resetAUndoCount() {
    aUndoCount = 0;
  }

  /** Resets player B's undo count */
  public void resetBUndoCount() {
    bUndoCount = 0;
  }

  /**
   * Determines if player A can still undo
   *
   * @return if player A can undo
   */
  public boolean canAUndo() {
    return aUndoCount < MAX_UNDO_COUNT;
  }

  /**
   * Determines if player B can still undo
   *
   * @return if player B can undo
   */
  public boolean canBUndo() {
    return bUndoCount < MAX_UNDO_COUNT;
  }

  /**
   * Returns the current Mancala board
   *
   * @return the current Mancala board
   */
  public int[] getCurrentBoard() {
    return board;
  }

  /**
   * Returns the previous Mancala board
   *
   * @return the previous Mancala board
   */
  public int[] getPreviousBoard() {
    return previousBoard;
  }

  /**
   * Determines if the game is over
   *
   * @return if game is over
   */
  public boolean gameOver() {
    int playerAStoneCount = 0;
    int playerBStoneCount = 0;

    for (int i = 0; i < A_MANCALA_POS; i++) {
      playerAStoneCount += board[i];
    }
    for (int i = A_MANCALA_POS + 1; i < B_MANCALA_POS; i++) {
      playerBStoneCount += board[i];
    }

    // Game over if either player has all empty pits
    return playerAStoneCount == 0 || playerBStoneCount == 0;
  }

  /**
   * Returns the winner of the game
   *
   * @return 0 if player A is winner, 1 if player B is winner, 2 if tie
   */
  public int getWinner() {
    int playerAStoneCount = 0;
    int playerBStoneCount = 0;

    for (int i = 0; i <= A_MANCALA_POS; i++) {
      playerAStoneCount += board[i];
    }
    for (int i = A_MANCALA_POS + 1; i <= B_MANCALA_POS; i++) {
      playerBStoneCount += board[i];
    }

    // Winner is player with more stones
    if (playerAStoneCount == playerBStoneCount) {
      return TIE;
    }
    return playerAStoneCount > playerBStoneCount ? A_WINNER : B_WINNER;
  }

  /**
   * Performs a move on the specified pit.
   *
   * @param index the index of the pit
   */
  public void move(int index) {
    System.out.println("MOVE: " + index);
    
    boolean dropInOwnMancala = false;
    
    //clone board so players can undo moves
    previousBoard = board.clone();
    
    int stonesToDrop = getMarbles(index); //number of stones in selected pit
    board[index] = 0; //set pit to 0 (get stones)
    int lastIndex = index + stonesToDrop; //last index to reach
    
    for(int i = index + 1; i <= lastIndex; stonesToDrop--,i++)
    {
    	//Player A turn
    	if(isPlayerATurn() == true)
    	{
    		//edge case 1: if last stone is in own Mancala => another turn
    		if(i == A_MANCALA_POS && stonesToDrop == 1)
    		{
    			dropInOwnMancala = true;
    			//go again
    		}
    		//edge case 2: if you reach opponent's Mancala => skip and go to your pos
    		if(i == B_MANCALA_POS)
    		{
    			i = 0;
    		}
    	    /*edge case 3: if last stone is in empty pit on your side => take stone and stones from
    	     * opposite side of opponent's pit and put in your Mancala
    	     */
    		if(stonesToDrop == 1 && (i >= 0 && i <= A_MANCALA_POS-1))
    		{
    			if((board[12 - i] > 0) && (board[i] == 0))
    			{
    				int opponentStoneCount = getMarbles(12-i);
    				board[12-i] = 0;
    				board[A_MANCALA_POS] += (stonesToDrop + opponentStoneCount);
    		    	board[i] = 0;
    				//stonesToDrop--;
    			}
    		}	
    	}
    	//Player B turn
    	else
    	{
    		//edge case 1: if last stone is in own Mancala => another turn
    		if(i == B_MANCALA_POS && stonesToDrop == 1)
    		{
    			dropInOwnMancala = true;
    			//go again
    		}
    		//edge case 2: if you reach opponent's Mancala => skip and go to your pos
    		if(i == A_MANCALA_POS)
    		{
    			i = 0;
    		}
    	    /*edge case 3: if last stone is in empty pit on your side => take stone and stones from
    	     * opposite side of opponent's pit and put in your Mancala
    	     */
    		if(stonesToDrop == 1 && (i >= 0 && i <= B_MANCALA_POS-1))
    		{
    			if((board[12 - i] > 0) && (board[i] == 0))
    			{
    				int opponentStoneCount = getMarbles(12-i);
    				board[12-i] = 0;
    				board[B_MANCALA_POS] += (stonesToDrop + opponentStoneCount);
    		    	board[i] = 0;
    			}
    		}
    	}
    	board[i]++;
    }
    updateBoard();
    if(isPlayerATurn == true && dropInOwnMancala == true)
    	isPlayerATurn = true;
    else
    	isPlayerATurn = false;
  }

  /**
   * Returns the number of marbles in the specified pit.
   *
   * @param index the index of the pit
   * @return the number of marbles in the specified pit
   */
  public int getMarbles(int index) {
    return board[index];
  }

  /**
   * Returns whether it is Player A's turn.
   *
   * @return whether it is Player A's turn
   */
  public boolean isPlayerATurn() {
    return isPlayerATurn;
  }
}