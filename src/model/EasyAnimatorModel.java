package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EasyAnimatorModel implements AnimatorModel {
  private int sceneHeight;
  private int sceneWidth;
  private final List<Shape> shapes;

  public EasyAnimatorModel() {
    this.shapes = new ArrayList<>();
  }

  @Override
  public void buildScene(int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Scene cannot be built with negative values.");
    }
    this.sceneHeight = height;
    this.sceneWidth = width;
  }

  @Override
  public void addShape(ShapeType shapeType, int height, int width, Color color,
                       Posn posn, String shapeID) {
    boolean incorrectX = posn.getX() < sceneWidth || posn.getX() > sceneWidth;
    boolean incorrectY = posn.getY() < sceneHeight || posn.getY() > sceneHeight;
    if (incorrectX || incorrectY) {
      throw new IllegalArgumentException("Shape position not within model scene.");
    }

    Shape shape;
    if (shapeType == ShapeType.RECTANGLE) {
      shape = new Rectangle(height, width, color, posn, shapeID);
    }
    else {
      shape = new Oval(height, width, color, posn, shapeID);
    }
    shapes.add(shape);
  }

  @Override
  public void moveShape(int startTime, int endTime, Posn startPos, Posn endPos, String shapeID) {
    if (startTime - endTime <= 0) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Shape getShape(String shapeId) {
    return null;
  }

  @Override
  public List<Shape> getShapes() {
    return null;
  }

  @Override
  public List<Shape> getShapeAt(Posn posn) {
    return null;
  }

  @Override
  public void changeColor(String shapeID, int startTime, int endTime) {

  }

  @Override
  public int getNumShapes() {
    return 0;
  }
}
