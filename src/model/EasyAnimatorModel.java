package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class representing an implementation of our animator model.
 */
public class EasyAnimatorModel implements AnimatorModel {
  private int sceneHeight;
  private int sceneWidth;
  private final List<Shape> shapes;
  private final List<ArrayList<Shape>> shapesPerTick;

  /**
   * Constructor for class, which only initializes the shapes and shapes per tick of our model.
   */
  public EasyAnimatorModel() {
    this.shapes = new ArrayList<>();
    this.shapesPerTick = new ArrayList<ArrayList<Shape>>();

  }

  @Override
  public void buildScene(int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Scene cannot be built with negative values.");
    }
    this.sceneHeight = height;
    this.sceneWidth = width;
    shapesPerTick.add(new ArrayList<Shape>());
  }

  @Override
  public void addShape(ShapeType shapeType, int height, int width, Color color,
                       Posn posn, String shapeID, int startoflife, int endoflife) {
    /*
    boolean incorrectX = posn.getX() < 0 || posn.getX() > sceneWidth;
    boolean incorrectY = posn.getY() < 0 || posn.getY() > sceneHeight;
    if (incorrectX || incorrectY) {
      throw new IllegalArgumentException("Shape position not within model scene.");
    }

     */
    boolean heightWidthBad = height <= 0 || width <= 0;
    if (heightWidthBad) {
      throw new IllegalArgumentException("Height or width can't be negative or zero values.");
    }

    if (endoflife > shapesPerTick.size()) {
      addTime(shapesPerTick, endoflife);
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

  private void addTime(List<ArrayList<Shape>> shapesPerTick, int endoflife) {
    for (int i = shapesPerTick.size(); i <= endoflife; i++) {
      shapesPerTick.add(new ArrayList<Shape>());
    }
  }

  @Override
  public void disappearShape(int startTime, int endTime, String shapeID) {
    for (int i = startTime; i <= endTime; i++) {
      if (shapesPerTick.get(i).stream().anyMatch(s -> s.getShapeID().equals(shapeID))) {
        Optional<Shape> optional =
                shapesPerTick.get(i).stream()
                        .filter(s -> s.getShapeID().equals(shapeID)).findFirst();
        if (optional.isPresent()) {
          Shape delTickShape = optional.get();
          shapesPerTick.get(i).remove(delTickShape);
        }
      }
    }
  }


  @Override
  public void deleteShape(String shapeID) {
    Shape delShape = null;
    for (Shape shape : shapes) {
      if (shape.getShapeID().equals(shapeID)) {
        delShape = shape;
      }
    }
    if (delShape == null) {
      throw new IllegalArgumentException("Shape does not exist");
    }
    else {
      shapes.remove(delShape);
      disappearShape(0, shapesPerTick.size() - 1, shapeID);
    }
  }

  private void addAtTimeOne(String shapeID) {
    shapesPerTick.get(1).add(getShape(shapeID));
  }

  @Override
  public void moveShape(int startTime, int endTime, Posn startPos, Posn endPos, String shapeID) {
    int time = endTime - startTime;
    Posn dist = new Posn(endPos.getX() - startPos.getX(), endPos.getY() - startPos.getY());
    int xPerTick = dist.getX() / time;
    int yPerTick = dist.getY() / time;

    setTimeOne(shapeID, startTime, time);

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
                shapesPerTick.get(t).stream().filter(s -> s.getShapeID().equals(shapeID))
                        .findFirst();
        if (optional.isPresent()) {
          if (t == endTime) {
            Shape movingShape = optional.get();
            movingShape.setPos(endPos);
            getActualShape(shapeID).setPos(endPos);
          } else {
            Shape movingShape = optional.get();
            int newXMove = getActualShape(shapeID).getShapePosn().getX() + xPerTick;
            int newYMove = getActualShape(shapeID).getShapePosn().getY() + yPerTick;
            Posn newPosn = new Posn(newXMove, newYMove);
            movingShape.setPos(newPosn);
            getActualShape(shapeID).moveShape(xPerTick, yPerTick);
          }
        }
      }
      else {
        Shape shape = getActualShape(shapeID);
        Posn pos = getActualShape(shapeID).getShapePosn();
        Posn moved = new Posn(pos.getX() + xPerTick, pos.getY() + yPerTick);
        if (t == endTime) {
          moved = endPos;
        }
        if (shape instanceof Oval) {
          shapesPerTick.get(t).add(new Oval(shape.getHeight(),
                  shape.getWidth(),
                  shape.getColor(),
                  moved,
                  shape.getShapeID(),
                  ShapeType.OVAL));
        }
        if (shape instanceof Rect) {
          shapesPerTick.get(t).add(new Rect(shape.getHeight(),
                  shape.getWidth(),
                  shape.getColor(),
                  moved,
                  shape.getShapeID(),
                  ShapeType.RECTANGLE));
        }
        shape.moveShape(xPerTick, yPerTick);
      }
    }

  }

  @Override
  public Shape getShape(String shapeId) {
    for (Shape s : shapes) {
      if (s.getShapeID().equals(shapeId)) {
        if (s.getShapeType() == ShapeType.OVAL) {
          return new Oval(s.getHeight(), s.getWidth(), s.getColor(),
                  s.getShapePosn(), s.getShapeID(), s.getShapeType());
        } else {
          return new Rect(s.getHeight(), s.getWidth(), s.getColor(),
                  s.getShapePosn(), s.getShapeID(), s.getShapeType());
        }
      }
    }
    throw new IllegalArgumentException("No shape found for this ID.");
  }

  @Override
  public List<Shape> getShapes() {
    List<Shape> returnList = new ArrayList<>();
    for (Shape s : shapes) {
      String id = s.getShapeID();
      Shape shape = getShape(id);
      returnList.add(shape);
    }

    return returnList;
  }

  @Override
  public void changeColor(String shapeID, int startTime, int endTime,
                          Color startColor, Color endColor) {
    int time = endTime - startTime;
    if (time <= 0 || startTime <= 0 || endTime <= 0) {
      throw new IllegalArgumentException("Time can't be negative.");
    }

    Shape shape = getShape(shapeID);
    Color originalColor = shape.getColor();
    if (!startColor.equals(originalColor)) {
      throw new IllegalArgumentException("The starting color has to be the same as the " +
              "shape's current color.");
    }

    boolean notInStartTick = true;
    for (Shape s : shapesPerTick.get(1)) {
      if (s.getShapeID().equals(shapeID)) {
        notInStartTick = false;
      }
    }
    if (startTime == 1 && notInStartTick) {
      addAtTimeOne(shapeID);
    }

    int startRed = startColor.getRed();
    int startGreen = startColor.getGreen();
    int startBlue = startColor.getBlue();
    int endRed = endColor.getRed();
    int endGreen = endColor.getGreen();
    int endBlue = endColor.getBlue();
    int redRate = (endRed - startRed) / time;
    int greenRate = (endGreen - startGreen) / time;
    int blueRate = (endBlue - startBlue) / time;

    for (int t = startTime + 1; t <= endTime; t++) {
      if (shapesPerTick.get(t).stream().anyMatch(s -> s.getShapeID().equals(shapeID))) {
        Optional<Shape> optional =
                shapesPerTick.get(t).stream().filter(s -> s.getShapeID().equals(shapeID))
                        .findFirst();
        if (optional.isPresent()) {
          Shape changingColor = optional.get();
          if (t == endTime) {
            changingColor.changeColor(endColor);
            getShape(shapeID).changeColor(endColor);
          } else {
            int newRed = getActualShape(shapeID).getColor().getRed() + redRate;
            int newGreen = getActualShape(shapeID).getColor().getGreen() + greenRate;
            int newBlue = getActualShape(shapeID).getColor().getBlue() + blueRate;
            changingColor.changeColor(new Color(newRed, newGreen, newBlue));
            getActualShape(shapeID).changeColor(new Color(newRed, newGreen, newBlue));
          }
        }
      }
      else {
        Shape originalShape = getShape(shapeID);
        int newRed = originalShape.getColor().getRed() + redRate;
        int newGreen = originalShape.getColor().getGreen() + greenRate;
        int newBlue = originalShape.getColor().getBlue() + blueRate;
        if (t == endTime) {
          newRed = endColor.getRed();
          newGreen = endColor.getGreen();
          newBlue = endColor.getBlue();
        }
        if (originalShape.getShapeType() == ShapeType.OVAL) {
          shapesPerTick.get(t).add(new Oval(originalShape.getHeight(), originalShape.getWidth(),
                  new Color(newRed, newGreen, newBlue), originalShape.getShapePosn(),
                  shapeID, ShapeType.OVAL));
        }
        if (originalShape.getShapeType() == ShapeType.RECTANGLE) {
          shapesPerTick.get(t).add(new Rect(originalShape.getHeight(), originalShape.getWidth(),
                  new Color(newRed, newGreen, newBlue), originalShape.getShapePosn(),
                  shapeID, ShapeType.RECTANGLE));
        }
        getActualShape(shapeID).changeColor(new Color(newRed, newGreen, newBlue));
      }
    }
  }

  private Shape getActualShape(String shapeID) {
    for (Shape s : shapes) {
      if (s.getShapeID().equals(shapeID)) {
        return s;
      }
    }
    throw new IllegalArgumentException("No shape with this ID.");
  }

  @Override
  public void changeSize(String shapeID, int startTime, int endTime,
                         int startHeight, int startWidth, int endHeight, int endWidth) {
    if (startHeight <= 0 || startWidth <= 0 || endHeight <= 0 || endWidth <= 0) {
      throw new IllegalArgumentException("Invalid height or width arguments");
    }

    int time = endTime - startTime;
    setTimeOne(shapeID, startTime, time);

    int changeHeight = endHeight - startHeight;
    int changeWidth = endWidth - startWidth;
    int changeHRate = changeHeight / time;
    int changeWRate = changeWidth / time;

    for (int t = startTime + 1; t <= endTime; t++) {
      if (shapesPerTick.get(t).stream().anyMatch(s -> s.getShapeID().equals(shapeID))) {
        Optional<Shape> optional =
                shapesPerTick.get(t).stream().filter(s -> s.getShapeID().equals(shapeID))
                        .findFirst();
        if (optional.isPresent()) {
          Shape changingShape = optional.get();
          //int newHeight = changingShape.getHeight() + changeHRate;
          //int newWidth = changingShape.getWidth() + changeWRate;
          int newHeight = getActualShape(shapeID).getHeight() + changeHRate;
          int newWidth = getActualShape(shapeID).getWidth() + changeWRate;
          changingShape.changeShapeDimensions(newHeight, newWidth);
          getActualShape(shapeID).changeShapeDimensions(newHeight, newWidth);
        }
      }
      else {
        Shape originalShape = getActualShape(shapeID);
        int newHeight = originalShape.getHeight() + changeHRate;
        int newWidth = originalShape.getWidth() + changeWRate;
        if (t == endTime) {
          newHeight = endHeight;
          newWidth = endWidth;
        }
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

  private void setTimeOne(String shapeID, int startTime, int time) {
    if (time <= 0) {
      throw new IllegalArgumentException("Time can't be negative.");
    }

    boolean notInStartTick = true;
    for (Shape s : shapesPerTick.get(1)) {
      if (s.getShapeID().equals(shapeID)) {
        notInStartTick = false;
      }
    }
    if (startTime == 1 && notInStartTick) {
      addAtTimeOne(shapeID);
    }
  }

  @Override
  public List<ArrayList<Shape>> getShapesPerTick() {
    List<ArrayList<Shape>> returnList = new ArrayList<ArrayList<Shape>>();
    for (List<Shape> ls : this.shapesPerTick) {
      ArrayList<Shape> innerList = new ArrayList<>();
      for (Shape s : ls) {
        Shape shape;
        if (s.getShapeType() == ShapeType.OVAL) {
          shape = new Oval(s.getHeight(), s.getWidth(), s.getColor(),
                  s.getShapePosn(), s.getShapeID(), s.getShapeType());
        }
        else {
          shape = new Rect(s.getHeight(), s.getWidth(), s.getColor(),
                  s.getShapePosn(), s.getShapeID(), s.getShapeType());
        }
        innerList.add(shape);
      }
      returnList.add(innerList);
    }
    return returnList;
  }


  @Override
  public String toString() {
    StringBuilder modelState = new StringBuilder();
    for (int t = 1; t < this.shapesPerTick.size(); t++) {
      modelState.append("At time t = ").append(t).append(":\n");
      for (int i = 0; i < shapesPerTick.get(t).size(); i++) {
        Shape s = shapesPerTick.get(t).get(i);
        modelState.append("Shape ").append(s.getShapeID()).append(" ").append(s.getShapeType())
                .append("\n");
        modelState.append("x=").append(s.getShapePosn().getX()).append(" y=")
                .append(s.getShapePosn().getY()).append(" w=").append(s.getWidth()).append(" h=")
                .append(s.getHeight()).append(" color=").append(s.getColor()).append("\n");
      }
    }
    return modelState.toString();
  }

  @Override
  public int getSceneWidth() {
    return this.sceneWidth;
  }

  @Override
  public int getSceneHeight() {
    return this.sceneHeight;
  }

}
