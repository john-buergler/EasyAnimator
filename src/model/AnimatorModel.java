package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for the text-based animator. User inputs text commands to describe a shape and its
 * motion in the animator, which then produces a visual animation based on the description
 * provided.
 */
public interface AnimatorModel {

  /**
   * Creates the location at which the animation takes place.
   * @param height the height dimension of the scene.
   * @param width the width of the scene.
   * @param time the duration of the animation.
   * @throws IllegalArgumentException if width or height are negative values.
   */
  public void buildScene(int width, int height, int time);

  /**
   * Creates and adds shape to a scene based on user input.
   * @param shapeType the type of shape to create.
   * @param height the height of the new shape.
   * @param width the width of the new shape.
   * @param color the color of the new shape.
   * @param posn the position of the new shape.
   * @throws IllegalArgumentException if the height or width are negative or zero values.
   * @throws IllegalArgumentException if the position is outside the scene.
   * @throws IllegalArgumentException if the shapeID has already been used.
   *
   */
  public void addShape(ShapeType shapeType, int height, int width, Color color,
                       Posn posn, String shapeID);

  /**
   * Moves provided shape ID.
   * @param endPos the ending position of the shape.
   * @param shapeID the shape being chosen to move.
   * @param startTime When to begin the movement.
   * @param endTime When to end the movement.
   * @throws IllegalArgumentException if endTime - startTime <= 0.
   * @throws IllegalArgumentException if the start position is not the current position of the shape.
   *
   */
  public void moveShape(int startTime, int endTime, Posn startPos, Posn endPos, String shapeID);

  /**
   * Gets the model Shape from a given model.Shape ID.
   * @param shapeId the shape's ID.
   * @throws IllegalArgumentException if given shapeID doesn't correspond to any shape in the animation.
   */
  public Shape getShape(String shapeId);

  /**
   * Gets the shapes in the model.
   */
  public List<Shape> getShapes();

  /**
   * Get the shape(s) at the given Posn.
   * @param posn Provided position of the shape.
   */
  public List<Shape> getShapeAt(Posn posn);

  /**
   * Change color of given shape.
   * @param startTime when to start changing the color.
   * @param endTime when the color finishes changing.
   * @param shapeID the shape to change the color of.
   * @param startColor the color we want to change from.
   * @param endColor the new color we want the shape to be.
   * @throws IllegalArgumentException if start or end time are negative values.
   * @throws IllegalArgumentException if endTime - startTime <= 0.
   */
  public void changeColor(String shapeID, int startTime, int endTime, Color startColor, Color endColor);

  /**
   * Changes the size of the shape.
   * @param shapeID the ID of the shape that wants to be changed.
   * @param startTime the start time of the transformation.
   * @param endTime the end time of the transformation.
   * @param height the new height of the shape.
   * @param width the new width of the shape.
   * @throws IllegalArgumentException if the heigt or width values are negative or zero.
   * @throws IllegalArgumentException if the change in time is negative.
   * @throws IllegalArgumentException if the start dimensions are not the current shape dimensions.
   */
  public void changeSize(String shapeID, int startTime, int endTime,
                         int startHeight, int startWidth, int endHeight, int endWidth);

  /**
   * Gets the number of Shapes in the animation.
   */
  public int getNumShapes();

  /**
   * Gets the timeline.
   * @return
   */
  public List<ArrayList<Shape>> getShapesPerTick();

  /**
   * Renders the current state of the model as a string.
   * @return the state of the model as a string.
   */
  public String toString();
}
