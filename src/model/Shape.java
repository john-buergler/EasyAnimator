package model;

import java.awt.*;

public interface Shape {

  /**
   * Moves the shape from the given starting position to the given end position.
   * @param xChange how far shape moves on x axis.
   * @param yChange how far shape moves on y axis.
   * @throws IllegalArgumentException if the start position is not the same as the shape's current position.
   */
  public void moveShape(int xChange, int yChange);

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

  /**
   * Gets the position of the shape.
   * @return the position of the shape as a Posn.
   */
  public Posn getShapePosn();

  /**
   * Gets the color of the shape.
   * @return the color of the shape as a Color.
   */
  public Color getColor();

  /**
   * Gets the type of the shape.
   * @return the ShapeType of the shape.
   */
  public ShapeType getShapeType();

  /**
   * Gets the height of the shape.
   * @return the height of shape as an int.
   */
  public int getHeight();

  /**
   * Gets the width of the shape.
   * @return the width of shape as an int.
   */
  public int getWidth();

  /**
   * Looks if the shape is currently moving.
   * @return true if the shape is moving, false otherwise.
   */
  public boolean getMovingStatus();

  /**
   * Looks if the shape is currently changing color.
   * @return true if it is changing color, false otherwise.
   */
  public boolean getChangingColorStatus();

  /**
   * Looks if the shape is currently changing size.
   * @return true if it is changing size, false otherwise.
   */
  public boolean getChangingSizeStatus();

  /**
   * Sets the position of a shape object.
   * @param startPos the position being set
   */
  public void setPos(Posn startPos);
}
