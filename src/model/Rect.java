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

  @Override
  public boolean equals(Object o) {
    if (o instanceof Rect){
      return this.height == ((Oval) o).height
              && this.width == ((Oval) o).width
              && this.position.equals(((Oval) o).position)
              && this.color == ((Oval) o).color
              && this.shapeID.equals(((Oval) o).shapeID);
    }
    else {
      return false;
    }
  }
}
