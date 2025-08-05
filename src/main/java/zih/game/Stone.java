package zih.game;

public class Stone {

  private final int row;
  private final int column;
  private final boolean black;

  public Stone(int row, int column, boolean black) {
    this.row = row;
    this.column = column;
    this.black = black;
  }

  public int getRow(){return row;}

  public int getColumn() {
    return column;
  }

  public boolean isBlack() {
    return black;
  }
}
