package model;

import java.util.Objects;

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

  public void move(int xChange, int yChange) {
    x = x + xChange;
    y = y + yChange;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Posn) {
      return this.x == ((Posn) o).x
              && this.y == ((Posn) o).y;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

}
