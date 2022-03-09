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
   */
  public void buildScene(int width, int height);

  /**
   * Creates and adds shape to a scene based on user input.
   * @param shapeType the type of shape to create
   */
  public void addShape(ShapeType shapeType);

  /**
   * Moves provided shape ID.
   * @param endPos the ending position of the shape.
   * @param shapeID the shape being chosen to move.
   * @param startTime When to begin the movement.
   * @param endTime When to end the movement.
   * @throws IllegalArgumentException if endTime - startTime <= 0.
   */
  public void moveShape(int startTime, int endTime, Posn endPos, String shapeID);

  /**
   * Gets the Shape from a given Shape ID.
   * @param shapeId the shape's ID.
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
   */
  public void changeColor(String shapeID, int startTime, int endTime);

  /**
   * Gets the number of Shapes in the animation.
   */
  public int getNumShapes();
}
