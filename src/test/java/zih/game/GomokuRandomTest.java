package zih.game;

import org.junit.jupiter.api.Test;
import zih.players.Player;
import zih.players.RandomPlayer;

import static org.junit.jupiter.api.Assertions.*;

class GomokuRandomTest {

  @Test
  void shouldFinish(){
    /*
     * This test verifies that a game between two RandomPlayers will eventually complete.
     * Due to the non-deterministic nature of RandomPlayer, we do not assert specific outcomes,
     * but ensure that the game progresses and terminates without error.
     * */

    // create two random player
    RandomPlayer one = new RandomPlayer();
    RandomPlayer two = new RandomPlayer();

    // create a new game
    Gomoku game = new Gomoku(one, two);

    while(!game.isOver()){
      Player currentPlayer = game.getCurrent();

      // Keep attempting to place a stone until a valid move is made.
      Result result;
      do {
        Stone stone = currentPlayer.generateMove(game.getStones());
        result = game.place(stone);
        System.out.println(result);
      } while (!result.isSuccess()); // end do-while

    } // end outer-while
  }

  @Test
  void makeNames(){
    // simple test to exercise the logic within the 'RandomPlayer' class
    // for generating random names
    for (int i = 0; i < 100; i++){
      RandomPlayer player = new RandomPlayer();
      System.out.println(player.getName());
    }
  }

}