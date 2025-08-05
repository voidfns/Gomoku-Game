package zih.players;

import zih.game.Stone;

import java.util.List;

public class HumanPlayer implements Player {
  private String name;

  public HumanPlayer(String name){
    this.name = name;
  }
  public HumanPlayer(){};

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Stone generateMove(List<Stone> previousStones) {
    return null;
  }
}
