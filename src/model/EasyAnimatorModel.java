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
    StringBuilder shapeLog = new StringBuilder();
    shapeLog.append("shape " + shapeID + " " + shapeType + " " + height + " " + width + " " +
            color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + posn.getX() +
            " " + posn.getY() + " " + startoflife + " " + endoflife);
    shape.getLog().add(shapeLog.toString());

    for (int i = startoflife; i <= endoflife; i++) {
      Shape s = getActualShape(shapeID);
      Shape s1;
      if (shapeType == ShapeType.RECTANGLE) {
        s1 = new Rect(s.getHeight(), s.getWidth(), s.getColor(), s.getShapePosn(),
                s.getShapeID(), s.getShapeType());
      }
      else {
        s1 = new Oval(s.getHeight(), s.getWidth(), s.getColor(), s.getShapePosn(),
                s.getShapeID(), s.getShapeType());
      }
      shapesPerTick.get(i).add(s1);
    }
  }

  private void addTime(List<ArrayList<Shape>> shapesPerTick, int endoflife) {
    for (int i = shapesPerTick.size(); i <= endoflife; i++) {
      shapesPerTick.add(new ArrayList<Shape>());
    }
  }

  private void disappearShape(int endTime, String shapeID) {
    for (int i = 0; i <= endTime; i++) {
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
      disappearShape(shapesPerTick.size() - 1, shapeID);
    }
  }

  private void addAtTimeOne(String shapeID) {
    shapesPerTick.get(1).add(getShape(shapeID));
  }

  @Override
  public void moveShape(int startTime, int endTime, Posn startPos, Posn endPos, String shapeID) {
    int time = endTime - startTime;
    if (time <= 0) {
      throw new IllegalArgumentException("The difference " +
              "between endTime and startTime has to be positive.");
    }
    Posn dist = new Posn(endPos.getX() - startPos.getX(), endPos.getY() - startPos.getY());
    int xPerTick = dist.getX() / time;
    int yPerTick = dist.getY() / time;

    /*
    The idea here is to create a List of List that holds the positional information for shapes
  that are actively being transformed. The first if statement checks whether the StringID is already
  contained within the motion timeline. This could happen in the instance that change color is
  currently in the process of transforming the shape. If so, it modifies it rather than creating
  a new one and duplicating the shape.
     */

    Shape transformingShape = getActualShape(shapeID);
    int h = transformingShape.getHeight();
    int w = transformingShape.getWidth();
    Color color = transformingShape.getColor();
    transformingShape.getLog().add(addMotionToLog(transformingShape, "move",
            startTime, endTime, startPos, endPos, h, h, w, w, color, color));
    getActualShape(shapeID).SVGMove(startTime, endTime, startPos, endPos, shapeID);

    for (int t = startTime + 1; t <= endTime; t++) {
      if (shapesPerTick.get(t).stream().anyMatch(s -> s.getShapeID().equals(shapeID))) {
        Optional<Shape> optional =
                shapesPerTick.get(t).stream().filter(s -> s.getShapeID().equals(shapeID))
                        .findFirst();
        if (optional.isPresent()) {
          if (t == endTime) {
            Shape movingShape = optional.get();
            movingShape.setPos(endPos);
            Posn newEndPos = new Posn(endPos.getX(), endPos.getY());
            getActualShape(shapeID).setPos(newEndPos);
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
        int logSize = transformingShape.getLog().size();
        transformingShape.getLog().remove(logSize - 1);
        throw new IllegalArgumentException("No shape exists at this point in time.");
      }
    }
    setNewPosn(endPos, shapeID, endTime + 1);
  }

  private String addMotionToLog(Shape s, String motionType,
                                int startTime, int endTime, Posn startPos, Posn endPos,
                                int startHeight, int endHeight, int startWidth, int endWidth,
                                Color startColor, Color endColor) {
    StringBuilder shapeLog = new StringBuilder();
    shapeLog.append("motion " + motionType + " " + s.getShapeID() + " " + startTime + " " +
            startPos.getX() + " " + startPos.getY() + " " + startHeight + " " + startWidth + " " +
            startColor.getRed() + " " + startColor.getGreen() + " " + startColor.getBlue() + " " +
            endTime + " " + endPos.getX() + " " + endPos.getY() + " " + endHeight + " " + endWidth
            + " " + endColor.getRed() + " " + endColor.getGreen() + " " + endColor.getBlue());
    return shapeLog.toString();
  }

  private void setNewPosn(Posn newPosn, String shapeId, int startTime) {
    for (int i = startTime; i < shapesPerTick.size(); i++) {
      Posn actualNewPosn = new Posn(newPosn.getX(), newPosn.getY());
      if (shapesPerTick.get(i).stream().anyMatch(s -> s.getShapeID().equals(shapeId))) {
        Optional<Shape> optional =
                shapesPerTick.get(i).stream().filter(s -> s.getShapeID().equals(shapeId))
                        .findFirst();
        Shape s = optional.get();
        s.setPos(actualNewPosn);
      }
      else {
        break;
      }
    }
  }

  private void setNewColor(Color newColor, String shapeId, int startTime) {
    for (int i = startTime; i < shapesPerTick.size(); i++) {
      Color actualNewColor = new Color(newColor.getRed(), newColor.getGreen(), newColor.getBlue());
      if (shapesPerTick.get(i).stream().anyMatch(s -> s.getShapeID().equals(shapeId))) {
        Optional<Shape> optional =
                shapesPerTick.get(i).stream().filter(s -> s.getShapeID().equals(shapeId))
                        .findFirst();
        Shape s = optional.get();
        s.changeColor(actualNewColor);
      }
      else {
        break;
      }
    }
  }

  private void setNewSize(int newHeight, int newWidth, String shapeId, int startTime) {
    for (int i = startTime; i < shapesPerTick.size(); i++) {
      if (shapesPerTick.get(i).stream().anyMatch(s -> s.getShapeID().equals(shapeId))) {
        Optional<Shape> optional =
                shapesPerTick.get(i).stream().filter(s -> s.getShapeID().equals(shapeId))
                        .findFirst();
        Shape s = optional.get();
        s.changeShapeDimensions(newHeight, newWidth);
      }
      else {
        break;
      }
    }
  }

  @Override
  public Shape getShape(String shapeId) {
    for (Shape s : shapes) {
      if (s.getShapeID().equals(shapeId)) {
        Shape shape;
        if (s.getShapeType() == ShapeType.OVAL) {
          shape = new Oval(s.getHeight(), s.getWidth(), s.getColor(),
                  s.getShapePosn(), s.getShapeID(), s.getShapeType(),
                  s.getTransformations());
          for (int i = 0; i < s.getLog().size(); i++) {
            shape.getLog().add(s.getLog().get(i));
          }
          return shape;
        }
        else {
          shape = new Rect(s.getHeight(), s.getWidth(), s.getColor(),
                  s.getShapePosn(), s.getShapeID(), s.getShapeType(),
                  s.getTransformations());
          for (int i = 0; i < s.getLog().size(); i++) {
            shape.getLog().add(s.getLog().get(i));
          }
          return shape;
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

    Shape shape = getActualShape(shapeID);
    Color originalColor = shape.getColor();
    if (!startColor.equals(originalColor)) {
      throw new IllegalArgumentException("The starting color has to be the same as the " +
              "shape's current color.");
    }
    Posn p = shape.getShapePosn();
    int h = shape.getHeight();
    int w = shape.getWidth();
    shape.getLog().add(addMotionToLog(shape, "color", startTime,
            endTime, p, p, h, h, w, w, startColor, endColor));
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
        int logSize = shape.getLog().size();
        shape.getLog().remove(logSize - 1);
        throw new IllegalArgumentException("Shape does not exist at this time");
      }
    }

    setNewColor(endColor, shapeID, endTime + 1);
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

    Shape s1 = getActualShape(shapeID);
    Color color = s1.getColor();
    Posn p = s1.getShapePosn();
    s1.getLog().add(addMotionToLog(s1, "size", startTime, endTime, p, p,
            startHeight, endHeight, startWidth, endWidth, color, color));

    int time = endTime - startTime;
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
          if (t == endTime) {
            changingShape.changeShapeDimensions(endHeight, endWidth);
            getActualShape(shapeID).changeShapeDimensions(endHeight, endWidth);
          }
          else {
            int newHeight = getActualShape(shapeID).getHeight() + changeHRate;
            int newWidth = getActualShape(shapeID).getWidth() + changeWRate;
            changingShape.changeShapeDimensions(newHeight, newWidth);
            getActualShape(shapeID).changeShapeDimensions(newHeight, newWidth);
          }
        }
      }
      else {
        int logSize = s1.getLog().size();
        s1.getLog().remove(logSize - 1);
        throw new IllegalArgumentException("Shape doesn't exist at this time.");
      }
    }
    setNewSize(endHeight, endWidth, shapeID, endTime + 1);
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