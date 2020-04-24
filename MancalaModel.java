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
public class MancalaModel {
  private ArrayList<ChangeListener> cListeners;
  private int[] board; // the current board
  private int[] previousBoard; // the previous board (if undo is chosen)

  private boolean isPlayerATurn;
  private boolean isPlayerATurnPrevious;

  public static final int TOTAL_NUM_PITS = 14;
  public static final int NUM_PITS_PER_PLAYER = TOTAL_NUM_PITS / 2 - 1;
  public static final int A_MANCALA_POS = TOTAL_NUM_PITS / 2 - 1;
  public static final int B_MANCALA_POS = TOTAL_NUM_PITS - 1;

  private boolean isJustUndo = true;
  private int undoCount;
  private static final int MAX_UNDO_COUNT = 3;

  public static final int A_WINNER = 0;
  public static final int B_WINNER = 1;
  public static final int TIE = 2;

  /** Creates an instance of an empty Mancala Board. */
  public MancalaModel() {
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
    isPlayerATurnPrevious = isPlayerATurn;
  }

  /**
   * Creates the Mancala board with the players' choice of stones per pit.
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
   * Adds a ChangeListener.
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
    // Check if can undo
    if (!isJustUndo && undoCount < MAX_UNDO_COUNT) {
      isJustUndo = true;
      undoCount++;

      // Apply undo
      board = previousBoard.clone();
      isPlayerATurn = isPlayerATurnPrevious;

      // Update view
      updateBoard();
    }
  }

  /**
   * Determines if the game is over.
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
   * Returns the winner of the game.
   *
   * @return 0 if player A is winner, 1 if player B is winner, 2 if tie
   */
  public int getWinner() {
    int playerAStoneCount = board[A_MANCALA_POS];
    int playerBStoneCount = board[B_MANCALA_POS];

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
    // Don't allow moves if no marbles in chosen pit or not player's turn
    if (board[index] == 0
        || (isPlayerATurn && index > A_MANCALA_POS)
        || (!isPlayerATurn && index < A_MANCALA_POS)) {
      return;
    }

    // Save previous board for undo
    previousBoard = board.clone();

    boolean dropInOwnMancala = false;
    int stonesToDrop = board[index]; // number of stones in selected pit
    board[index] = 0; // set pit to 0 (get stones)

    // Step to the next pit
    index = (index + 1) % TOTAL_NUM_PITS;

    // Keep dropping stones until out of stones to drop
    while (stonesToDrop > 0) {
      // Player A turn
      if (isPlayerATurn()) {
        // Edge case 1: if last stone is in own Mancala => another turn
        if (index == A_MANCALA_POS && stonesToDrop == 1) {
          dropInOwnMancala = true;
        }

        // Edge case 2: if you reach opponent's Mancala => skip and go to your pos
        if (index == B_MANCALA_POS) {
          index = 0;
        }

        /* Edge case 3: if last stone is in empty pit on your side => take stone and stones from
         * opposite side of opponent's pit and put in your Mancala
         */
        int oppositeIndex = B_MANCALA_POS - 1 - index;
        if (stonesToDrop == 1
            && index < A_MANCALA_POS
            && board[oppositeIndex] > 0
            && board[index] == 0) {
          int oppositeStoneCount = board[oppositeIndex];
          board[oppositeIndex] = 0;
          board[A_MANCALA_POS] += (1 + oppositeStoneCount);
          board[index]--; // to cancel the add at the end
        }
      }

      // Player B turn
      else {
        // Edge case 1: if last stone is in own Mancala => another turn
        if (index == B_MANCALA_POS && stonesToDrop == 1) {
          dropInOwnMancala = true;
        }

        // Edge case 2: if you reach opponent's Mancala => skip and go to your pos
        if (index == A_MANCALA_POS) {
          index = A_MANCALA_POS + 1;
        }

        /* Edge case 3: if last stone is in empty pit on your side => take stone and stones from
         * opposite side of opponent's pit and put in your Mancala
         */
        int oppositeIndex = B_MANCALA_POS - 1 - index;
        if (stonesToDrop == 1
            && index > A_MANCALA_POS
            && index < B_MANCALA_POS
            && board[oppositeIndex] > 0
            && board[index] == 0) {
          int oppositeStoneCount = board[oppositeIndex];
          board[oppositeIndex] = 0;
          board[B_MANCALA_POS] += (1 + oppositeStoneCount);
          board[index]--; // to cancel the add at the end
        }
      }

      // Increase stone count for ith pit by one
      board[index]++;

      index = (index + 1) % TOTAL_NUM_PITS;
      stonesToDrop--;
    }

    // Game over, all marbles left in pits go to respective mancalas
    if (gameOver()) {
      for (int i = 0; i < A_MANCALA_POS; i++) {
        board[A_MANCALA_POS] += board[i];
        board[i] = 0;
      }
      for (int i = A_MANCALA_POS + 1; i < B_MANCALA_POS; i++) {
        board[B_MANCALA_POS] += board[i];
        board[i] = 0;
      }
    }

    // Reset undo count
    if (isPlayerATurn != isPlayerATurnPrevious) {
      undoCount = 0;
    }

    // Save previous turn
    isPlayerATurnPrevious = isPlayerATurn;

    // Change turns if not drop in own mancala
    if (!dropInOwnMancala) {
      isPlayerATurn = !isPlayerATurn;
    }

    // Allow undo
    isJustUndo = false;

    // Update view
    updateBoard();
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

  /**
   * Returns the number of remaining undos.
   *
   * @return the number of remaining undos
   */
  public int getUndoRemaining() {
    return MAX_UNDO_COUNT - undoCount;
  }
}
