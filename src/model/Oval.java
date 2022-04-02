package model;

import java.awt.Color;

/**
 * Class for the Oval shape used in our model.
 */
public class Oval extends AbstractShape {

  /**
   * Constructor for Oval class which inherits
   * every parameter from super class.
   * @param height of Oval.
   * @param width of Oval.
   * @param color of Oval.
   * @param position of Oval as a Posn.
   * @param shapeID the specific ID of Oval as a string.
   * @param type the ShapeType of Oval.
   */
  public Oval(int height, int width, Color color, Posn position, String shapeID, ShapeType type) {
    super(height, width, color, position, shapeID, type);
  }

  public Oval(int height, int width, Color color, Posn position, String shapeID, ShapeType type,
              String trans) {
    super(height, width, color, position, shapeID, type, trans);
  }

  @Override
  public String toSVG() {
    StringBuilder str = new StringBuilder();
    str.append("<").append("ellipse").append(" id=").append('"').append(shapeID)
            .append('"').append(" cx=").append('"').append(position.getX())
            .append('"').append(" cy=").append('"').append(position.getY())
            .append('"').append(" rx=").append('"').append(width / 2)
            .append('"').append(" ry=").append('"').append(height / 2)
            .append('"').append(" fill=").append('"').append("rgb(")
            .append(color.getRed() + ", ").append(color.getGreen() + ", ")
            .append(color.getBlue() + ")").append('"').append(" visibility=")
            .append('"').append("visible").append('"').append(">").append('\n');
    str.append(this.SVGtransformations);
    str.append("</ellipse>");
    return str.toString();
  }

  @Override
  public String SVGMove(int startTime, int endTime, Posn startPos, Posn endPos, String shapeID) {
    StringBuilder str = new StringBuilder();
    if (startPos.getX() != endPos.getX()) {
      str.append("  <animate attributeType=" + '"' + "xml" + '"' + " begin=" + '"' + "base.begin+" +
              startTime + "ms" + '"' + " dur=" + '"' + ((endTime - startTime) * 1000) + "ms" + '"'
              + " attributeName=" + '"' + "cx" + '"' + " from=" + '"' + startPos.getX() + '"' +
              " to=" + '"' + endPos.getX() + '"' + " fill=" + '"' + "freeze" + '"' + "></animate>"
              + '\n');
    }
    if (startPos.getY() != endPos.getY()) {
      str.append("  <animate attributeType=" + '"' + "xml" + '"' + " begin=" + '"' + "base.begin+" +
              startTime + "ms" + '"' + " dur=" + '"' + ((endTime - startTime) * 1000) + "ms" + '"'
              + " attributeName=" + '"' + "cy" + '"' + " from=" + '"' + startPos.getY() + '"' + "" +
              " to=" + '"' + endPos.getY() + '"' + " fill=" + '"' + "freeze" + '"' + "></animate>"
              + '\n');
    }
    return str.toString();
  }
}
