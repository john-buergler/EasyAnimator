package model;

import java.awt.*;

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
  public Oval(int height, int width, Color color, Posn position, String shapeID, ShapeType type){
    super(height, width, color, position, shapeID, type);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Oval){
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
