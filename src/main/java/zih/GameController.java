package zih;

import zih.game.Gomoku;
import zih.game.Result;
import zih.game.Stone;
import zih.players.HumanPlayer;
import zih.players.Player;
import zih.players.RandomPlayer;

import java.util.Scanner;

public class GameController {
  public static final int WIDTH = 15;
  private Scanner scanner;
  private Player player1, player2;
  Gomoku game;
  char[][] board;
  Result result;

  /**
   * Executes the main loop of the Gomoku game.
   * <p>
   * Initializes the game, handles player turns, checks for a winner,
   * and prompts to restart the game when it ends.
   */
  public void run(){
    setUp();
    printBoard();

    while (!game.isOver()){
      play(scanner);
//      printBoard();
      if (game.isOver()){
        System.out.println("\n" + game.getWinner().getName() + " wins!");

        if (playAgain()) run(); else break;
      } // end outer if
    } // end while

  }

  /**
   * Initializes the Gomoku game by setting up the board and players.
   * Prompts for player names if it's a human player and announces the starting player.
   */
  public void setUp(){
    //  set up game board
    board = new char[WIDTH][WIDTH];
    scanner = new Scanner(System.in);
    String playerName;

    // welcome message and sets up player1 and player2
    System.out.println("Welcome to Gomoku\n=================\n");
    player1 = getPlayer(1, scanner);
    // checks if player1 is a HumanPlayer
    if (player1 instanceof HumanPlayer){
      playerName = readRequiredString("\nPlayer " + 1 + ", enter your name: ");
      player1.setName(playerName);
    }

    player2 = getPlayer(2, scanner);
    // checks if player2 is a HumanPlayer
    if (player2 instanceof HumanPlayer){
      playerName = readRequiredString("\nPlayer " + 2 + ", enter your name: ");
      player2.setName(playerName);
    }

    // initializing Gomoku game and print the player that goes first
    game = new Gomoku(player1, player2);
    System.out.println();
    System.out.println("(Randomizing....)\n");
    System.out.println(game.getCurrent().getName());
  }

  /**
   * Prompts the user to select a player type (Human or Random) for the given player number.
   *
   * @param playerNumber the player number (1 or 2)
   * @param scanner the Scanner used to read user input
   * @return a Player instance based on the user's selection
   */
  public Player getPlayer(int playerNumber, Scanner scanner){
    // initializes the player
    Player player = null;
    int choice = 0;
    boolean valid = false;

    // loop to get a valid user input
    do {
      System.out.println("Player " + playerNumber + " is: ");
      System.out.println("1. Human");
      System.out.println("2. Random Player");
      System.out.print("Select [1-2]: ");

      try {
        choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1){
          player = new HumanPlayer();
          valid = true;
        } else if (choice == 2){
          player = new RandomPlayer();
          valid = true;
        } else if (choice < 0 || choice > 2){
          System.out.println("ERROR: you must select 1 or 2");
        }

      } catch (NumberFormatException exception){
        System.out.println("Please enter a valid number.");
      } // end try-catch

    } while (choice <= 0 || choice > 2 || !valid);

    return player;
  }

  /**
   * Displays the current state of the game board.
   *
   * Populates the board with placed stones and prints it to the console,
   * showing column and row headers, player moves ('B' for black, 'W' for white),
   * and underscores for empty spaces.
   */
  public void printBoard() {
    System.out.print("   "); // spacing for column headers
    for (int col = 1; col <= WIDTH; col++) {
      System.out.printf("%02d ", col);
    }
    System.out.println();

    for (int row = 0; row < WIDTH; row++) {
      System.out.printf("%02d ", row + 1);
      for (int col = 0; col < WIDTH; col++) {
        char cell = board[row][col];
        // print '_' for empty, otherwise 'B' or 'W'
        if (cell == '\0') {
          System.out.print("_  ");
        } else {
          System.out.printf("%c  ", cell);
        }
      }
      System.out.println();
    }

  }


  /**
   * Handles a single turn for the current player.
   *
   * @param scanner the Scanner used for input if the player is human
   * @return the result of the move
   *
   * If the player is human, prompts for a move; otherwise, generates one.
   * Updates the game state and prints the board after the move.
   */
  public Result play(Scanner scanner){
    int row;
    int col;
    Stone move;
    Player currentPlayer = game.getCurrent();

    System.out.println();
    System.out.println(game.getCurrent().getName() + "'s Turn\n\n");

    // checks whether the player is human, if not, generate random move
    if (currentPlayer instanceof HumanPlayer){
      System.out.print("Enter a row: ");
      row = Integer.parseInt(scanner.nextLine());
      System.out.print("Enter a column: ");
      col = Integer.parseInt(scanner.nextLine());

      move = new Stone(row, col, game.isBlacksTurn());
    } else {
      move = currentPlayer.generateMove(game.getStones());
    }

    // place current move into result instance
    result = game.place(move);
    // update the board by placing the moves made by current player
    row = move.row() - 1;
    col = move.column() - 1;
    board[row][col] = game.isBlacksTurn() ? 'B' : 'W';
    // display the result and print the board
    if (result.getMessage() == null){
      System.out.printf("Move successfully placed on row: %s, col: %s\n", move.row(), move.column());
    } else {
      System.out.println(result.getMessage());
    }

    printBoard();

    return result;
  }
  /**
   * Prompts the user until a non-empty, non-whitespace string is entered.
   *
   * @param message the message to display to the user
   * @return a validated, non-empty string entered by the user
   */
  public String readRequiredString(String message){
    scanner = new Scanner(System.in);
    String result;

    do {
      System.out.print(message);
      result = scanner.nextLine().trim();
    } while (result.isEmpty() || result.matches("\\d+"));

    return result;
  }

  /**
   * Prompts the user to play again.
   *
   * @return true if the user enters 'y' (case-insensitive), false otherwise
   */
  public boolean playAgain(){
    System.out.println();
    String result = readRequiredString("Play Again? [y/n]: ");
    System.out.println();

    return result.equalsIgnoreCase("y");
  }

}
