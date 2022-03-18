package model;

import java.awt.*;

public abstract class AbstractShape implements Shape {
  protected final String shapeID;
  protected int height;
  protected int width;
  protected Color color;
  protected Posn position;

  public AbstractShape(int height, int width, Color color, Posn position, String shapeID) {
    this.shapeID = shapeID;
    this.width = width;
    this.height = height;
    this.color = color;
    this.position = position;
  }

  @Override
  public void moveShape(Posn startPos, Posn endPos) {
    if (!startPos.equals(this.position)) {
      throw new IllegalArgumentException("Start position of the move isn't the shapes current position.");
    }

    this.position = endPos;
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
  public String getShapeID() {
    return shapeID;
  }


}
