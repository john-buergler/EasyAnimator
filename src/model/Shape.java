package model;

import java.awt.*;

public interface Shape {

  /**
   * Moves the shape from the given starting position to the given end position.
   * @param startPos starting position for the shape.
   * @param endPos end position for the shape.
   * @throws IllegalArgumentException if the start position is not the same as the shape's current position.
   */
  public void moveShape(Posn startPos, Posn endPos);

  /**
   * Changes the color of the given shape.
   * @param color the color to which the shape will change.
   */
  public void changeColor(Color color);

  /**
   * Changes the dimensions of the shape.
   * @param height the new height for the shape.
   * @param width the new width for the shape.
   * @throws IllegalArgumentException if height or width are negative or zero values.
   */
  public void changeShapeDimensions(int height, int width);

  /**
   * Returns the shapeId of the shape.
   * @return the shapeID as a string.
   */
  public String getShapeID();
}
