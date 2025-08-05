package zih.game;

import zih.players.Player;

import java.util.ArrayList;

public class Gomoku {
  public static final int WIDTH = 15;

  private final Player playerOne;
  private final Player playerTwo;
  private final char[][] board = new char[WIDTH][WIDTH];

  private ArrayList<Stone> stones = new ArrayList<>();
  private boolean over;
  private Player current;
  private Player winner;
  private boolean blacksTurn = true;

  public Gomoku(Player playerOne, Player playerTwo) {
    this.playerOne = playerOne;
    this.playerTwo = playerTwo;

    // generates random number between 0.0 and 1.0
    // 50% chance it will be less than 0.5
    // current turn can be assigned to either playerOne or playerTwo
    if (Math.random() < 0.5){
      current = playerOne;
    } else {
      current = playerTwo;
    }
  }

  public ArrayList<Stone> getStones() {
    return stones;
  }

  public boolean isOver() {
    return over;
  }

  public Player getCurrent() {
    return current;
  }

  public Player getWinner() {
    return winner;
  }

  public boolean isBlacksTurn() {
    return blacksTurn;
  }

  public Result place(Stone stone){

    if (isOver()){
      return new Result("Game is over.");
    }
    if (!isValid(stone)){
      return new Result("Stone is off the board.");
    }
    if (blacksTurn != stone.black()) {
      return new Result("Wrong player.");
    }
    // checks if the cells on the board array are already occupied
    // if it's not equal to 0, means occupied
    if (board[stone.row()][stone.column()] != 0){
      return new Result("Duplicate move.");
    }

    // getRow and getColumn returns the row and column where the player
    // wants to place their stone
    // if it's black's turn place 'B' on the board, else 'W'
    board[stone.row()][stone.column()] = blacksTurn ? 'B' : 'W';
    stones.add(stone);

    if (isWin(stone)){
      over = true;
      winner = current;
      return new Result(current.getName() + " wins.", true);
    }

    // checks if board is completely full - no more moves are possible
    // no winner was found, declares a draw result.
    if (stones.size() == WIDTH * WIDTH){
      over = true;
      return new Result("Game ends in a draw.", true);
    }

    blacksTurn = !blacksTurn;
    swap();
    return new Result(null, true);
  }

  public void swap(){
    // if current player is playerOne, set it to player Two
    // otherwise, set it to playerOne
    current = current == playerOne ? playerTwo : playerOne;
  }
  // ensures there are no null stone or invalid range of row and column
  private boolean isValid(Stone stone){
    return stone != null
            && stone.row() >= 0 && stone.row() < WIDTH
            && stone.column() >= 0 && stone.column() < WIDTH;
  }

  private boolean isWin(Stone stone){
    char symbol = board[stone.row()][stone.column()];
    return isHorizontalWin(stone.row(), stone.column(), symbol)
            || isVerticalWin(stone.row(), stone.column(), symbol)
            || isDiagonalDownWin(stone.row(), stone.column(), symbol)
            || isDiagonalUpWin(stone.row(), stone.column(), symbol);
  }

  private boolean isHorizontalWin(int row, int column, char symbol) {
    return count(row, column, 1, 0, symbol)
            + count(row, column, -1, 0, symbol) == 4;
  }
  private boolean isVerticalWin(int row, int column, char symbol) {
    return count(row, column, 0, 1, symbol)
            + count(row, column, 0, -1, symbol) == 4;
  }
  private boolean isDiagonalDownWin(int row, int column, char symbol) {
    return count(row, column, 1, 1, symbol)
            + count(row, column, -1, -1, symbol) == 4;
  }
  private boolean isDiagonalUpWin(int row, int column, char symbol) {
    return count(row, column, -1, 1, symbol)
            + count(row, column, 1, -1, symbol) == 4;
  }

  /*
  * Parameters:
    row, col: Starting cell coordinates.

    deltaRow, deltaCol: Direction of movement (e.g., horizontal, vertical, diagonal).
    > (1, 0) → downward
    > (0, 1) → right
    > (-1, -1) → up-left (diagonal)
    > (1, -1) → down-left (anti-diagonal)

    symbol: The player's stone to match ('B' for black, 'W' for white).
  * */
  private int count(int row, int col, int deltaRow, int deltaCol, char symbol) {

    int result = 0;
    int r = row + deltaRow;
    int c = col + deltaCol;

    // checks how many identical pieces are lined up from a given cell in a given direction.
    // detecting winning conditions (4 in a row).

    // Keep moving in the given direction as long as:
    // stay within board boundaries.
    // The current cell matches the symbol.
    // For every matching cell, increment the count.
    while (r >= 0 && r < WIDTH && c >= 0 && c < WIDTH && board[r][c] == symbol) {
      result++;
      r += deltaRow;
      c += deltaCol;
    }

    return result;
  }
}
