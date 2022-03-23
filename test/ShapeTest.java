import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import model.Oval;
import model.Posn;
import model.Rect;
import model.Shape;
import model.ShapeType;

import static org.junit.Assert.assertEquals;

/**
 * Test class for our shape interface.
 */
public class ShapeTest {
  Shape shape;

  @Before
  public void setUp() {
    shape = new Rect(10, 10, Color.BLUE, new Posn(1, 1),
            "s", ShapeType.RECTANGLE);
  }

  @Test
  public void testGetDs() {
    assertEquals(10, shape.getWidth());
    assertEquals(10, shape.getHeight());
  }

  @Test
  public void testEquals() {
    Shape shape1 = new Oval(10, 10, Color.BLUE,
            new Posn(50, 50), "blue", ShapeType.OVAL);
    Shape shape2 = new Oval(10, 10, Color.BLUE,
            new Posn(50, 50), "blue", ShapeType.OVAL);

    Shape shape3 = new Rect(5, 5, Color.RED,
            new Posn(50, 50), "red", ShapeType.RECTANGLE);
    Shape shape4 = new Rect(5, 5, Color.RED,
            new Posn(50, 50), "red", ShapeType.RECTANGLE);

    assertEquals(shape1, shape2);
    assertEquals(shape3, shape4);
  }

  @Test
  public void testGetColor() {
    assertEquals(Color.BLUE, shape.getColor());
  }

  @Test
  public void testGetShapePosition() {
    assertEquals(shape.getShapePosn(), new Posn(1, 1));
  }

  @Test
  public void testGetShapeID() {
    assertEquals(shape.getShapeID(), "s");
  }

  @Test
  public void testMoveShape() {
    shape.moveShape(10, 10);
    assertEquals(new Posn(11, 11), shape.getShapePosn());
    shape.moveShape(-1, 5);
    assertEquals(new Posn(10, 16), shape.getShapePosn());
  }

  @Test
  public void testChangeColor() {
    shape.changeColor(Color.RED);
    assertEquals(Color.RED, shape.getColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidChangeShapeDimensions() {
    shape.changeShapeDimensions(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidChangeShapeDimensions2() {
    shape.changeShapeDimensions(4, 0);
  }

  @Test
  public void testChangeShapeDimensions() {
    shape.changeShapeDimensions(5, 7);
    assertEquals(5, shape.getHeight());
    assertEquals(7, shape.getWidth());
  }

}
