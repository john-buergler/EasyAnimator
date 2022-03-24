package model;

import java.awt.*;

/**
 * Class representing a Rectangle in our model.
 */
public class Rect extends AbstractShape {

  /**
   * Constructor for Rectangle class which inherits
   * every parameter from super class.
   * @param height of rectangle.
   * @param width of rectangle.
   * @param color of rectangle.
   * @param position of rectangle as a Posn.
   * @param shapeID which is a specific ID as a String for rectangle.
   * @param type ShpeType for rectangle.
   */
  public Rect(int height, int width, Color color, Posn position, String shapeID, ShapeType type){
    super(height, width, color, position, shapeID, type);
  }
}
