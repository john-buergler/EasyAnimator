package model;

import java.awt.Color;
import java.util.List;

/**
 * The Interface for a Shape object. Shape objects are the objects that are rendered on the screen.
 * The user assigns them size, shapetype, and color depending on what they want the animation to
 * look like. The model also helps modify the shapes' characteristics to create and animation.
 */
public interface Shape {

  /**
   * Moves the shape from the given starting position to the given end position.
   * @param xChange how far shape moves on x axis.
   * @param yChange how far shape moves on y axis.
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
   * Sets the position of a shape object.
   * @param startPos the position being set
   */
  public void setPos(Posn startPos);

  /**
   * Gets the log of the motion of the shape.
   *
   * @return an array of strings describing the motion of the shape during animation.
   */
  public List<String> getLog();
}
