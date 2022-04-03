import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.AnimatorModel;
import model.EasyAnimatorModel;
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

  @Test
  public void testGetLogAddShape() {
    AnimatorModel m = new EasyAnimatorModel();
    m.addShape(shape.getShapeType(), shape.getHeight(), shape.getWidth(), shape.getColor(),
            shape.getShapePosn(), shape.getShapeID(), 2, 3);
    List<String> str = new ArrayList<>(List.of("shape s RECTANGLE 10 10 0 0 255 1 1 2 3"));
    assertEquals(m.getShapes().get(0).getLog(), str);
  }

  @Test
  public void testAddMultipleShapes() {
    AnimatorModel m = new EasyAnimatorModel();
    m.addShape(shape.getShapeType(), shape.getHeight(), shape.getWidth(), shape.getColor(),
            shape.getShapePosn(), shape.getShapeID(), 2, 3);
    Shape shape2 = new Oval(5, 5, Color.RED,
            new Posn(50, 50),"oval1", ShapeType.OVAL);
    m.addShape(shape2.getShapeType(), shape2.getHeight(), shape2.getWidth(), shape2.getColor(),
            shape2.getShapePosn(), shape2.getShapeID(), 1, 2);
    List<String> str1 = new ArrayList<>(List.of("shape s RECTANGLE 10 10 0 0 255 1 1"));
    List<String> str2 = new ArrayList<>(List.of("shape oval1 OVAL 5 5 255 0 0 50 50"));
    assertEquals(m.getShapes().get(0).getLog(), str1);
    assertEquals(m.getShapes().get(1).getLog(), str2);
  }

  @Test
  public void testGetLogAddAndMove() {
    AnimatorModel m = new EasyAnimatorModel();
    m.addShape(shape.getShapeType(), shape.getHeight(), shape.getWidth(), shape.getColor(),
            shape.getShapePosn(), shape.getShapeID(), 1, 4);
    m.moveShape(2, 3, shape.getShapePosn(),
            new Posn(100, 100), shape.getShapeID());
    List<String> str = new ArrayList<>(Arrays.asList("shape s RECTANGLE 10 10 0 0 255 1 1 1 4",
            "motion move s 2 1 1 10 10 0 0 255 3 100 100 10 10 0 0 255"));
    assertEquals(m.getShapes().get(0).getLog(), str);
  }

  @Test
  public void testGetLogAddandChangeColor() {
    AnimatorModel m = new EasyAnimatorModel();
    m.addShape(shape.getShapeType(), shape.getHeight(), shape.getWidth(), shape.getColor(),
            shape.getShapePosn(), shape.getShapeID(), 1, 4);
    m.changeColor(shape.getShapeID(), 1, 3, Color.BLUE, Color.RED);
    List<String> str = new ArrayList<>(Arrays.asList("shape s RECTANGLE 10 10 0 0 255 1 1 1 4",
            "motion color s 1 1 1 10 10 0 0 255 3 1 1 10 10 255 0 0"));
    assertEquals(m.getShapes().get(0).getLog(), str);
  }

  @Test
  public void testGetLogAddandChangeSize() {
    AnimatorModel m = new EasyAnimatorModel();
    m.addShape(shape.getShapeType(), shape.getHeight(), shape.getWidth(), shape.getColor(),
            shape.getShapePosn(), shape.getShapeID(), 1, 4);
    m.changeSize(shape.getShapeID(), 1, 3, 10,
            10, 15, 15);
    List<String> str = new ArrayList<>(Arrays.asList("shape s RECTANGLE 10 10 0 0 255 1 1 1 4",
            "motion size s 1 1 1 10 10 0 0 255 3 1 1 15 15 0 0 255"));
    assertEquals(m.getShapes().get(0).getLog(), str);
  }

}
