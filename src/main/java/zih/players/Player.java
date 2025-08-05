package zih.players;

import zih.game.Stone;

import java.util.List;

public interface Player {

  void setName(String name);
  String getName();

  Stone generateMove(List<Stone> previousStones);
}
