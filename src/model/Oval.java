package model;

import java.awt.Color;

/**
 * Class for the Oval shape used in our model.
 */
public class Oval extends AbstractShape {

  /**
   * Constructor for Oval class which inherits
   * every parameter from super class.
   * @param height of Oval.
   * @param width of Oval.
   * @param color of Oval.
   * @param position of Oval as a Posn.
   * @param shapeID the specific ID of Oval as a string.
   * @param type the ShapeType of Oval.
   */
  public Oval(int height, int width, Color color, Posn position, String shapeID, ShapeType type) {
    super(height, width, color, position, shapeID, type);
  }
  public Oval(int height, int width, Color color, Posn position, String shapeID, ShapeType type,
              boolean startingMotion) {
    super(height, width, color, position, shapeID, type, startingMotion);
  }
}
