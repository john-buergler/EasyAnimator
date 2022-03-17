package model;

/**
 * Represents the position of an object.
 */
public class Posn {
  private int x;
  private int y;

  /**
   * Constructs a model.Posn with given x and y coordinates.
   * @param x represents the x coord.
   * @param y represents the y coord.
   */
  public Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
