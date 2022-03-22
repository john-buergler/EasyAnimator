package model;

import java.awt.*;
import java.util.Objects;

public class Oval extends AbstractShape {

  public Oval(int height, int width, Color color, Posn position, String shapeID, ShapeType type){
    super(height, width, color, position, shapeID, type);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Oval) {
      return this.height == ((Oval) o).height
              && this.width == ((Oval) o).width
              && this.position.equals(((Oval) o).position)
              && this.color == ((Oval) o).color
              && this.shapeID.equals(((Oval) o).shapeID);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.height, this.width, this.position, this.color, this.shapeID);
  }

}
