package model;

import java.awt.*;
import java.util.Objects;

/**
 * An Abstract class for shapes in the model.
 */
public abstract class AbstractShape implements Shape {
  protected final String shapeID;
  protected int height;
  protected int width;
  protected Color color;
  protected Posn position;
  protected ShapeType type;
  protected boolean isMoving;
  protected boolean isChangingColor;
  protected boolean isChangingSize;

  /**
   * Constructor for shapes with all the same parameters.
   * @param height of shape.
   * @param width of shape.
   * @param color of shape.
   * @param position of shape as a Posn.
   * @param shapeID specific ID of shape as a String.
   * @param type ShapeType of shape.
   */
  public AbstractShape(int height,
                       int width,
                       Color color,
                       Posn position,
                       String shapeID,
                       ShapeType type) {
    this.shapeID = shapeID;
    this.width = width;
    this.height = height;
    this.color = color;
    this.position = position;
    this.type = type;
  }

  @Override
  public void moveShape(int xChange, int yChange) {
    this.position.move(xChange, yChange);
  }

  @Override
  public void changeColor(Color color) {
    this.color = color;
  }

  @Override
  public void changeShapeDimensions(int height, int width) {
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Can't have zero or negative values for height or width");
    }
    this.height = height;
    this.width = width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public String getShapeID() {
    return this.shapeID;
  }

  @Override
  public Posn getShapePosn() {
    return new Posn(this.position.getX(), this.position.getY());
  }

  @Override
  public Color getColor() { return new Color(this.color.getRGB()); }

  @Override
  public ShapeType getShapeType() {
    return this.type;
  }

  @Override
  public void setPos(Posn pos) {
    this.position = pos;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.height, this.width, this.position, this.color, this.shapeID,
            this.type);
  }

}

