package model;

import java.awt.Color;

/**
 * Class representing a Rectangle in our model.
 */
public class Rect extends AbstractShape {

  /**
   * Constructor for Rectangle class which inherits
   * every parameter from super class.
   * @param height of rectangle.
   * @param width of rectangle.
   * @param color of rectangle.
   * @param position of rectangle as a Posn.
   * @param shapeID which is a specific ID as a String for rectangle.
   * @param type ShpeType for rectangle.
   */
  public Rect(int height, int width, Color color, Posn position, String shapeID, ShapeType type) {
    super(height, width, color, position, shapeID, type);
  }

  public Rect(int height, int width, Color color, Posn position, String shapeID, ShapeType type,
              String trans) {
    super(height, width, color, position, shapeID, type, trans);
  }

  @Override
  public String toSVG() {
    StringBuilder str = new StringBuilder();
    str.append("<").append("rect").append(" id=").append('"').append(shapeID)
            .append('"').append(" x=").append('"').append(position.getX())
            .append('"').append(" y=").append('"').append(position.getY())
            .append('"').append(" width=").append('"').append(width / 2)
            .append('"').append(" height=").append('"').append(height / 2)
            .append('"').append(" fill=").append('"').append("rgb(")
            .append(color.getRed() + ", ").append(color.getGreen() + ", ")
            .append(color.getBlue() + ")").append('"').append(" visibility=")
            .append('"').append("visible").append('"').append(">").append('\n');
    str.append(this.SVGtransformations);
    str.append("</rect>");
    return str.toString();
  }

  @Override
  public void SVGMove(int startTime, int endTime, Posn startPos, Posn endPos, String shapeID) {
    StringBuilder str = new StringBuilder();
    if (startPos.getX() != endPos.getX()) {
      str.append("  <animate attributeType=" + '"' + "xml" + '"' + " begin=" + '"' + "base.begin+" +
              startTime + "ms" + '"' + " dur=" + '"' + (endTime - startTime) + "ms" + '"'
              + " attributeName=x" + '"' + " from=" + '"' + startPos.getX() + '"' + " to="
              + '"' + endPos.getX() + '"' + " fill=" + '"' + "freeze" + '"' + "></animate>" + '\n');
    }
    if (startPos.getY() != endPos.getY()) {
      str.append("  <animate attributeType=" + '"' + "xml" + '"' + " begin=" + '"' + "base.begin+" +
              startTime + "ms" + '"' + " dur=" + '"' + (endTime - startTime) + "ms" + '"'
              + " attributeName=y" + '"' + " from=" + '"' + startPos.getY() + '"' + " to="
              + '"' + endPos.getY() + '"' + " fill=" + '"' + "freeze" + '"' + "></animate>" + '\n');
    }
    SVGtransformations = SVGtransformations + str;
  }
}
