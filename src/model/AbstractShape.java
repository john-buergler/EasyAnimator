package model;

import java.awt.*;

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
    return this.position;
  }

  @Override
  public Color getColor() {
    return this.color;
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

}

