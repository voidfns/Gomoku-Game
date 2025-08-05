package zih.players;

import zih.game.Gomoku;
import zih.game.Stone;

import java.util.List;
import java.util.Random;

public class RandomPlayer implements Player {
  private static final String[] titles = {"Dr.", "Professor", "Chief Exec", "Specialist", "The Honorable",
          "Prince", "Princess", "The Venerable", "The Eminent"};
  private static final String[] names = {
          "Evelyn", "Wyatan", "Jud", "Danella", "Sarah", "Johnna",
          "Vicki", "Alano", "Trever", "Delphine", "Sigismundo",
          "Shermie", "Filide", "Daniella", "Annmarie", "Bartram",
          "Pennie", "Rafael", "Celine", "Kacey", "Saree", "Tu",
          "Erny", "Evonne", "Charita", "Anny", "Mavra", "Fredek",
          "Silvio", "Cam", "Hulda", "Nanice", "Iolanthe", "Brucie",
          "Kara", "Paco"};
  private static final String[] lastNames = {"Itch", "Potato", "Mushroom", "Grape", "Mouse", "Feet",
          "Nerves", "Sweat", "Sweet", "Bug", "Piles", "Trumpet", "Shark", "Grouper", "Flutes", "Showers",
          "Humbug", "Cauliflower", "Shoes", "Hopeless", "Zombie", "Monster", "Fuzzy"};

  private final Random random = new Random();
  private String name;

  public RandomPlayer(){
    name = String.format("%s %s %s",
            titles[random.nextInt(titles.length)],
            names[random.nextInt(names.length)],
            lastNames[random.nextInt(lastNames.length)]
    );
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Stone generateMove(List<Stone> previousStones) {

    boolean isBlack = true;
    if (previousStones != null && !previousStones.isEmpty()){
      Stone lastMove = previousStones.get(previousStones.size() - 1);
      isBlack = !lastMove.isBlack();
    }

    return new Stone(
            random.nextInt(Gomoku.WIDTH),
            random.nextInt(Gomoku.WIDTH),
            isBlack);
  }

}
