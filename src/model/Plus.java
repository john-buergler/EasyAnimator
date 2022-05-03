package model;

import java.awt.*;

public class Plus extends Rect {
  /**
   * Constructor for shapes with all the same parameters.
   *
   * @param height   of shape.
   * @param width    of shape.
   * @param color    of shape.
   * @param position of shape as a Posn.
   * @param shapeID  specific ID of shape as a String.
   * @param type     ShapeType of shape.
   */
  public Plus(int height, int width, Color color, Posn position, String shapeID, ShapeType type) {
    super(height, width, color, position, shapeID, type);
  }
  public Plus(int height, int width, Color color, Posn position, String shapeID, ShapeType type,
              boolean startingMotion) {
    super(height, width, color, position, shapeID, type, startingMotion);
  }
}
