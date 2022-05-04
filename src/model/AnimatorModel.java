package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Model for an animator software. This model is responsible for organizing
 * the information of an animation. This model stores both the shapes and
 * the shape's motions during an animation. This model holds a general timeline of
 * the animation which is measured in ticks, and the state of every shape
 * during each tick is stored. When the model is fully stored with the animation information
 * this timeline will hold everything needed to render the full animation.
 */
public interface AnimatorModel {

  /**
   * Creates the location at which the animation takes place.
   * @param height the height dimension of the scene.
   * @param width the width of the scene.
   * @throws IllegalArgumentException if width or height are negative values.
   */
  public void buildScene(int width, int height);

  /**
   * Creates and adds Shape to a scene based on user input.
   * @param shapeType the type of shape to create.
   * @param height the height of the new shape.
   * @param width the width of the new shape.
   * @param color the color of the new shape.
   * @param posn the position of the new shape.
   * @param startoflife what tick the shape begins.
   * @param endoflife what tick the shape's animation ends.
   * @throws IllegalArgumentException if the height or width are negative or zero values.
   * @throws IllegalArgumentException if the shapeID has already been used.
   *
   */
  public void addShape(ShapeType shapeType, int height, int width, Color color,
                       Posn posn, String shapeID, int startoflife, int endoflife);

  /**
   * Deletes a shape from the model and animation.
   * @param shapeID the shapeID of the shape to delete.
   * @throws IllegalArgumentException if shape to delete does not exist in the animation.
   */
  public void deleteShape(String shapeID);


  /**
   * Moves provided shape ID over a given amount of time. This is done by calculating the rate at
   * which the shape needs to move per tick and adjusting the shapes position based on that.
   * Important to note that the final movement of the Shape may be bigger than the others to make up
   * for any gap caused by imprecision in the calculation.
   * @param endPos the ending position of the shape.
   * @param shapeID the shape being chosen to move.
   * @param startTime When to begin the movement.
   * @param endTime When to end the movement.
   * @throws IllegalArgumentException if endTime - startTime <= 0.
   * @throws IllegalArgumentException if the start position is not the current position of the
   *     shape.
   * @throws IllegalStateException if shape to be moved is already moving during this time.
   * @throws IllegalStateException if shape to be moved doesn't exist in the animation during a
   *     certain tick in the given time interval.
   *
   */
  public void moveShape(int startTime, int endTime, Posn startPos, Posn endPos, String shapeID);

  /**
   * Gets the model Shape from a given model.
   * @param shapeId the shape's ID.
   * @throws IllegalArgumentException if given shapeID doesn't correspond to any shape in the
   *     animation.
   */
  public Shape getShape(String shapeId);

  /**
   * Gets the shapes involved in the animation as their current state in the animation.
   */
  public List<Shape> getShapes();

  /**
   * Change color of given shape.
   * @param startTime when to start changing the color.
   * @param endTime when the color finishes changing.
   * @param shapeID the shape to change the color of.
   * @param startColor the color we want to change from.
   * @param endColor the new color we want the shape to be.
   * @throws IllegalArgumentException if start or end time are negative or zero values.
   * @throws IllegalArgumentException if endTime - startTime <= 0.
   * @throws IllegalArgumentException if start color isn't shape's current color.
   * @throws IllegalStateException if shape is already changing color during this time.
   * @throws IllegalStateException if shape to be modified doesn't exist in the animation during a
   *     certain tick in the given time interval.
   */
  public void changeColor(String shapeID, int startTime, int endTime, Color startColor,
                          Color endColor);

  /**
   * Changes the size of the shape over a given duration of time. Size is adjusted in a similar
   * manner to moveShape, finding the rate of change per tick.
   * @param shapeID the ID of the shape that wants to be changed.
   * @param startTime the start time of the transformation.
   * @param endTime the end time of the transformation.
   * @param startHeight the height of the shape at the start.
   * @param endHeight the height of the shape at the end.
   * @param startWidth the width of the shape at the beginning of motion.
   * @param endWidth the height of the shape at the end of the motion.
   * @throws IllegalArgumentException if the heigt or width values are negative or zero.
   * @throws IllegalArgumentException if the change in time is negative.
   * @throws IllegalArgumentException if the start dimensions are not the current shape dimensions.
   * @throws IllegalStateException if the shape is already changing size during this time.
   * @throws IllegalStateException if shape to be modified doesn't exist in the animation during a
   *     certain tick in the given time interval.
   */
  public void changeSize(String shapeID, int startTime, int endTime,
                         int startHeight, int startWidth, int endHeight, int endWidth);

  /**
   * Gets the timeline.
   * @return
   */
  public List<ArrayList<Shape>> getShapesPerTick();

  /**
   * Renders the current state of the model as a string. This includes transformations and shape
   * additions.
   * @return the state of the model as a string.
   */
  public String toString();

  /**
   * Gets the scene width.
   */
  public int getSceneWidth();

  /**
   * Gets the scene height.
   */
  public int getSceneHeight();

  /**
   * Gets a shape at a specified tick in the animation.
   *
   * @param shapeID the shape id of the desired shape.
   * @param tick the tick at which to look for the desired shape.
   * @return an Optional object as if the shape is not there it will not throw an exception.
   *     Instead, it will return a non-present optional object.
   */
  public Optional<Shape> getShapeAt(String shapeID, int tick);

  public void addTempo(Tempo tempo);

  public List<Tempo> getTempos();
}
