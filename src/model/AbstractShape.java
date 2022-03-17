package model;

import java.awt.*;

public abstract class AbstractShape implements Shape {
  protected final String shapeID;
  protected final int height;
  protected final int width;
  protected final Color color;
  protected final Posn position;

  public AbstractShape(int height, int width, Color color, Posn position, String shapeID) {
    this.shapeID = shapeID;
    this.width = width;
    this.height = height;
    this.color = color;
    this.position = position;
  }


}
