import java.awt.*;

abstract class Shape {
  protected ShapeType type;
  protected int height;
  protected int width;
  protected Color color;
  protected Posn position;

  public Shape(ShapeType type, int height, int width, Color color, Posn position) {
    this.width = width;
    this.height = height;
    this.type = type;
    this.color = color;
    this.position = position;
  }


}
