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

  /**
   * Gets x coordinate for the Posn.
   * @return the x coordinate of this position.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets y coordinate for the Posn.
   * @return the y coordinate of this position.
   */
  public int getY() {
    return y;
  }

  /**
   * Changes the x-coordinate and y-coordinate by the given amounts.
   * @param xChange amount to change x-coordinate.
   * @param yChange amount to change y-coordinate.
   */
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

  @Override
  public String toString() {
    return String.valueOf(x) + String.valueOf(y);
  }

}
