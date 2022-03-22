package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EasyAnimatorModel implements AnimatorModel {
  private int sceneHeight;
  private int sceneWidth;
  private final List<Shape> shapes;
  private ArrayList<ArrayList<Shape>> shapesPerTick;
  private final StringBuilder log;

  public EasyAnimatorModel() {
    this.shapes = new ArrayList<>();
    this.log = new StringBuilder();
    this.shapesPerTick = new ArrayList<ArrayList<Shape>>();

  }

  @Override
  public void buildScene(int width, int height, int time) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Scene cannot be built with negative values.");
    }
    this.sceneHeight = height;
    this.sceneWidth = width;
    for (int i = 0; i <= time; i++) {
      shapesPerTick.add(new ArrayList<Shape>());
    }
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
      shape = new Rectangle(height, width, color, posn, shapeID, shapeType);
    }
    else {
      shape = new Oval(height, width, color, posn, shapeID, shapeType);
    }
    shapes.add(shape);
    log.append("Shape " + shapeID + " " + shapeType.toString() + '\n');
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

    /*
    The idea here is to create a List of List that holds the positional information for shapes
  that are actively being transformed. The first if statement checks whether the StringID is already
  contained within the motion timeline. This could happen in the instance that change color is
  currently in the process of transforming the shape. If so, it modifies it rather than creating
  a new one and duplicating the shape.
     */

    for (int t = startTime + 1; t <= endTime; t++) {
      if (shapesPerTick.get(t).stream().anyMatch(s -> s.getShapeID() == shapeID)) {
        Optional<Shape> optional =
                shapesPerTick.get(t).stream().filter(s -> s.getShapeID() == shapeID).findFirst();
        if (optional.isPresent()) {
          Shape movingShape = optional.get();
          movingShape.moveShape(xPerTick, yPerTick);
        }
      }
      else {
        Shape shape = getShape(shapeID);
        Posn pos = getShape(shapeID).getShapePosn();
        Posn moved = new Posn(pos.getX() + xPerTick, pos.getY() + yPerTick);
        if (shape instanceof Oval) {
          shapesPerTick.get(t).add(new Oval(((Oval) shape).height,
                  ((Oval) shape).width,
                  shape.getColor(),
                  moved,
                  ((Oval) shape).shapeID,
                  ShapeType.OVAL));
        }
        if (shape instanceof Rectangle) {
          shapesPerTick.get(t).add(new Rectangle(((Rectangle) shape).height,
                  ((Rectangle) shape).width,
                  shape.getColor(),
                  moved,
                  ((Rectangle) shape).shapeID,
                  ShapeType.OVAL));
        }
        shape.moveShape(xPerTick, yPerTick);
      }
    }
    log.append("Motion " + shapeID + " Starts: " + startTime + ", " + "Ends: " + endTime + ", "
            + "moves from x= " + startPos.getX() + " to x= " + endPos.getX() + ", and y= " +
            startPos.getY() + " to y= " + endPos.getY() + '\n');

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
    log.append("Motion " + shapeID + " from color " + getShape(shapeID).getColor() + " to color "
    + color + " from time = " + startTime + " to time = " + endTime + '\n');
  }

  @Override
  public int getNumShapes() {
    return shapes.size();
  }

  @Override
  public ArrayList<ArrayList<Shape>> getShapesPerTick() {
    return this.shapesPerTick;
  }
  @Override
  public StringBuilder getLog() {
    return this.log;
  }
}
