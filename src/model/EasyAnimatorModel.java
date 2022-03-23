package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EasyAnimatorModel implements AnimatorModel {
  private int sceneHeight;
  private int sceneWidth;
  private final List<Shape> shapes;
  private final List<ArrayList<Shape>> shapesPerTick;

  public EasyAnimatorModel() {
    this.shapes = new ArrayList<>();
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
      shape = new Rect(height, width, color, posn, shapeID, shapeType);
    }
    else {
      shape = new Oval(height, width, color, posn, shapeID, shapeType);
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

    /*
    The idea here is to create a List of List that holds the positional information for shapes
  that are actively being transformed. The first if statement checks whether the StringID is already
  contained within the motion timeline. This could happen in the instance that change color is
  currently in the process of transforming the shape. If so, it modifies it rather than creating
  a new one and duplicating the shape.
     */

    for (int t = startTime + 1; t <= endTime; t++) {
      if (shapesPerTick.get(t).stream().anyMatch(s -> s.getShapeID().equals(shapeID))) {
        Optional<Shape> optional =
                shapesPerTick.get(t).stream().filter(s -> s.getShapeID().equals(shapeID)).findFirst();
        if (optional.isPresent()) {
          Shape movingShape = optional.get();
          if (movingShape.getMovingStatus()) {
            throw new IllegalArgumentException("Shape is already moving.");
          }
          movingShape.moveShape(xPerTick, yPerTick);
          getShape(shapeID).moveShape(xPerTick, yPerTick);
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
        if (shape instanceof Rect) {
          shapesPerTick.get(t).add(new Rect(((Rect) shape).height,
                  ((Rect) shape).width,
                  shape.getColor(),
                  moved,
                  ((Rect) shape).shapeID,
                  ShapeType.OVAL));
        }
        shape.moveShape(xPerTick, yPerTick);
      }
    }

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
  public void changeColor(String shapeID, int startTime, int endTime,
                          Color startColor, Color endColor) {
    int time = endTime - startTime;
    if (time <= 0) {
      throw new IllegalArgumentException("Time can't be negative.");
    }

    int rgb = endColor.getRGB() - startColor.getRGB();
    int rgbRate = rgb / time;

    for (int t = startTime + 1; t <= endTime; t++) {
      if (shapesPerTick.get(t).stream().anyMatch(s -> s.getShapeID().equals(shapeID))) {
        Optional<Shape> optional =
                shapesPerTick.get(t).stream().filter(s -> s.getShapeID().equals(shapeID)).findFirst();
        if (optional.isPresent()) {
          Shape changingColor = optional.get();
          if (changingColor.getChangingColorStatus()) {
            throw new IllegalArgumentException("Shape is already changing color.");
          }
          int newRGB = rgb + rgbRate;
          changingColor.changeColor(new Color(newRGB));
          getShape(shapeID).changeColor(new Color(newRGB));
        }
        else {
          Shape originalShape = getShape(shapeID);
          int newRGB = originalShape.getColor().getRGB() + rgbRate;
          if (originalShape.getShapeType() == ShapeType.OVAL) {
            shapesPerTick.get(t).add(new Oval(originalShape.getHeight(), originalShape.getWidth(),
                    new Color(newRGB), originalShape.getShapePosn(),
                    shapeID, ShapeType.OVAL));
          }
          if (originalShape.getShapeType() == ShapeType.RECTANGLE) {
            shapesPerTick.get(t).add(new Rect(originalShape.getHeight(), originalShape.getWidth(),
                    new Color(newRGB), originalShape.getShapePosn(),
                    shapeID, ShapeType.RECTANGLE));
          }
          originalShape.changeColor(new Color(newRGB));
        }
      }
    }
  }

  @Override
  public void changeSize(String shapeID, int startTime, int endTime,
                         int startHeight, int startWidth, int endHeight, int endWidth) {
    if (startHeight <= 0 || startWidth <= 0 || endHeight <= 0 || endWidth <= 0) {
      throw new IllegalArgumentException("Invalid height or width arguments");
    }

    int time = endTime - startTime;
    if (time <= 0) {
      throw new IllegalArgumentException("Time can't be negative.");
    }


    int changeHeight = endHeight - startHeight;
    int changeWidth = endWidth - startWidth;
    int changeHRate = changeHeight / time;
    int changeWRate = changeWidth / time;

    for (int t = startTime + 1; t <= endTime; t++) {
      if (shapesPerTick.get(t).stream().anyMatch(s -> s.getShapeID().equals(shapeID))) {
        Optional<Shape> optional =
                shapesPerTick.get(t).stream().filter(s -> s.getShapeID().equals(shapeID)).findFirst();
        if (optional.isPresent()) {
          Shape changingShape = optional.get();
          if (changingShape.getChangingSizeStatus()) {
            throw new IllegalArgumentException("Shape is already changing size.");
          }
          int newHeight = changingShape.getHeight() + changeHRate;
          int newWidth = changingShape.getWidth() + changeWRate;
          changingShape.changeShapeDimensions(newHeight, newWidth);
          getShape(shapeID).changeShapeDimensions(newHeight, newWidth);
        }
      }
      else {
        Shape originalShape = getShape(shapeID);
        int newHeight = originalShape.getHeight() + changeHRate;
        int newWidth = originalShape.getWidth() + changeWRate;
        if (originalShape.getShapeType() == ShapeType.OVAL) {
          shapesPerTick.get(t).add(new Oval(newHeight, newWidth, originalShape.getColor(),
                  originalShape.getShapePosn(), shapeID, ShapeType.OVAL));
        }
        if (originalShape.getShapeType() == ShapeType.RECTANGLE) {
          shapesPerTick.get(t).add(new Rect(newHeight, newWidth, originalShape.getColor(),
                  originalShape.getShapePosn(), shapeID, ShapeType.RECTANGLE));
        }
        originalShape.changeShapeDimensions(newHeight, newWidth);
      }
    }

  }

  @Override
  public int getNumShapes() {
    return shapes.size();
  }

  @Override
  public List<ArrayList<Shape>> getShapesPerTick() {
    return this.shapesPerTick;
  }

  @Override
  public String toString() {
    return "";
  }
}
