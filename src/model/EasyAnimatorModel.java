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
    boolean incorrectX = posn.getX() < 0 || posn.getX() > sceneWidth;
    boolean incorrectY = posn.getY() < 0 || posn.getY() > sceneHeight;
    boolean heightWidthBad = height <= 0 || width <= 0;
    if (incorrectX || incorrectY) {
      throw new IllegalArgumentException("Shape position not within model scene.");
    }
    if (heightWidthBad) {
      throw new IllegalArgumentException("Height or width can't be negative or zero values.");
    }

    boolean isIdUsed = false;
    for (Shape s : shapes) {
      if (s.getShapeID().equals(shapeID)) {
        isIdUsed = true;
      }
    }

    if (isIdUsed) {
      throw new IllegalArgumentException("Shape Id has already been used.");
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
    int time = endTime - startTime;
    Posn dist = new Posn(endPos.getX() - startPos.getX(), endPos.getY() - startPos.getY());
    int xPerTick = dist.getX() / time;
    int yPerTick = dist.getY() / time;
    if (time <= 0) {
      throw new IllegalArgumentException("Time can't be negative.");
    }
    for (int t = 1; t <= time; t++) {
      Shape movingShape = getShape(shapeID);
      movingShape.moveShape(xPerTick, yPerTick);
    }

    // Need more info on the functionality of ticks in Java when animating. with this functionality,
    // the ticks will easily be incorporated with the t variable within the loop.

  }

  @Override
  public Shape getShape(String shapeId) {
    for (Shape s : shapes) {
      if (s.getShapeID().equals(shapeId)) {
        return s;
      }
    }
    throw new IllegalArgumentException("No shape found for this ID.");
  }

  @Override
  public List<Shape> getShapes() {
    return shapes;
  }

  @Override
  public List<Shape> getShapeAt(Posn posn) {
    List<Shape> shapes = new ArrayList<Shape>();
    for (Shape s : shapes) {
      if (s.getShapePosn().equals(posn)) {
        shapes.add(s);
      }
    }
    return shapes;
  }

  @Override
  public void changeColor(String shapeID, int startTime, int endTime, Color color) {
    getShape(shapeID).changeColor(color);
  }

  @Override
  public int getNumShapes() {
    return shapes.size();
  }
}
