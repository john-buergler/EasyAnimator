package model;

import java.awt.*;
import java.util.Objects;

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
    this.isMoving = false;
    this.isChangingColor = false;
    this.isChangingSize = false;
  }

  @Override
  public void moveShape(int xChange, int yChange) {
    this.position.move(xChange, yChange);
    //this.isMoving = true;
  }

  @Override
  public void changeColor(Color color) {
    this.color = color;
    //this.isChangingColor = true;
  }

  @Override
  public void changeShapeDimensions(int height, int width) {
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Can't have zero or negative values for height or width");
    }
    this.height = height;
    this.width = width;
    //this.isChangingSize = true;
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
  public Color getColor() {
    return new Color(this.color.getRGB());
  }

  @Override
  public ShapeType getShapeType() {
    return this.type;
  }

  @Override
  public boolean getMovingStatus() {
    return this.isMoving;
  }

  @Override
  public boolean getChangingColorStatus() {
    return this.isChangingColor;
  }

  @Override
  public boolean getChangingSizeStatus() {
    return this.isChangingSize;
  }

  @Override
  public void setPos(Posn pos) {
    this.position = pos;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Oval) {
      return this.height == ((Oval) o).height
              && this.width == ((Oval) o).width
              && this.position.equals(((Oval) o).position)
              && this.color == ((Oval) o).color
              && this.shapeID.equals(((Oval) o).shapeID);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.height, this.width, this.position, this.color, this.shapeID);
  }

}

