import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.AnimatorModel;
import model.EasyAnimatorModel;
import model.Oval;
import model.Posn;
import model.Rect;
import model.ShapeType;
import model.Shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for our animator model.
 */
public class AnimatorModelTest {

  @Test
  public void testAddShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    assertEquals(new ArrayList<Shape>(), model.getShapes());
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov2");
    model.addShape(ShapeType.RECTANGLE,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redrec12");
    assertEquals(new Oval(10, 10, Color.RED, new Posn(50, 50), "redov2",
                    ShapeType.OVAL),
            model.getShapes().get(0));
  }

  @Test
  public void testMoveShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov1");
    model.moveShape(10,
            12,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov1");
    assertEquals(model.getShape("redov1").getShapePosn(), new Posn(100, 100));
    assertEquals(model.getShapesPerTick().get(11).get(0).getShapePosn(), new Posn(75, 75));
    int x = model.getShapesPerTick().get(11).get(0).getShapePosn().getX();
    int x2 = model.getShapesPerTick().get(12).get(0).getShapePosn().getX();
    assertEquals(x, 75);
    assertEquals(x2, 100);
    assertEquals(model.getShapesPerTick().get(12).get(0).getShapePosn(), new Posn(100, 100));
  }

  @Test
  public void testMoveShapeDuringColorChange() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov1");
    model.changeColor("redov1", 1, 3, Color.RED, Color.BLUE);
    model.moveShape(1, 3, new Posn(50, 50), new Posn(100, 100),
            "redov1");
    Shape s1 = model.getShapesPerTick().get(2).get(0);
    Shape s2 = model.getShapesPerTick().get(3).get(0);
    assertEquals(new Color(128, 0, 127), s1.getColor());
    assertEquals(new Posn(75, 75), s1.getShapePosn());
    assertEquals(Color.BLUE, s2.getColor());
    assertEquals(new Posn(100, 100), s2.getShapePosn());
  }

  @Test
  public void testDeleteShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov3");
    model.moveShape(10,
            12,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov3");
    model.deleteShape("redov3");
    assertTrue(model.getShapes().isEmpty());
    assertTrue(model.getShapesPerTick().get(11).isEmpty());
    assertTrue(model.getShapesPerTick().get(12).isEmpty());
  }

  @Test
  public void testDisappearShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov4");
    model.moveShape(10,
            12,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov4");
    model.disappearShape(10, 11, "redov4");
    assertTrue(model.getShapes().get(0) instanceof Shape);
    assertTrue(model.getShapesPerTick().get(10).isEmpty());
    assertTrue(model.getShapesPerTick().get(11).isEmpty());
    assertTrue(model.getShapesPerTick().get(12).get(0) instanceof Shape);
  }

  @Test
  public void testMoveShapeStandsStill() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov5");
    model.moveShape(0,
            12,
            new Posn(50, 50),
            new Posn(50, 50),
            "redov5");
    assertEquals(model.getShape("redov5").getShapePosn(), new Posn(50, 50));
    assertEquals(model.getShapesPerTick().get(11).get(0).getShapePosn(), new Posn(50, 50));
    assertEquals(model.getShapesPerTick().get(12).get(0).getShapePosn(), new Posn(50, 50));
    /*
    assertEquals("Shape redov1 OVAL\n" +
            "Motion redov1 Starts: 0, Ends: 12, " +
            "moves from x= 50 to x= 50, and y= 50 to y= 50\n", model.getLog().toString());
     */

  }

  @Test
  public void testMultiMoveShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov6");
    model.moveShape(10,
            12,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov6");
    model.moveShape(13,
            15,
            new Posn(100, 100),
            new Posn(50, 50),
            "redov6");
    assertEquals(model.getShape("redov6").getShapePosn(), new Posn(50, 50));
    assertEquals(model.getShapesPerTick().get(11).get(0).getShapePosn(), new Posn(75, 75));
    assertEquals(model.getShapesPerTick().get(12).get(0).getShapePosn(), new Posn(100, 100));
    assertEquals(model.getShapesPerTick().get(14).get(0).getShapePosn(), new Posn(75, 75));
    assertEquals(model.getShapesPerTick().get(15).get(0).getShapePosn(), new Posn(50, 50));
    /*
    assertEquals("Shape redov1 OVAL\n" +
            "Motion redov1 Starts: 10, Ends: 12, " +
            "moves from x= 50 to x= 100, and y= 50 to y= 100\n" +
            "Motion redov1 Starts: 13, Ends: 15, " +
            "moves from x= 100 to x= 50, and y= 100 to y= 50\n", model.getLog().toString());

     */
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuildNegativeScene() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(-200, 200, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddOOBShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(250, 50),
            "redov7");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddNegativeDimensionsShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            -10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov8");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddRepeatID() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov10");
    model.addShape(ShapeType.OVAL,
            20,
            30,
            Color.BLUE,
            new Posn(100, 20),
            "redov10");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveShapeNegativeTime() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov11");
    model.moveShape(5,
            2,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov11");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetNonexistentShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov12");
    model.getShape("redov2");
  }

  @Test
  public void testGetShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov13");
    assertEquals(new Oval(10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov13",
            ShapeType.OVAL), (Oval) model.getShape("redov13"));
  }

  @Test
  public void testGetShapes() {
    Shape exShape = new Oval(10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov14",
            ShapeType.OVAL);
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    List<Shape> shapelist = new ArrayList<>();
    shapelist.add(exShape);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov14");
    assertEquals(shapelist.get(0), model.getShapes().get(0));
    assertEquals(shapelist, model.getShapes());
  }


  EasyAnimatorModel model;
  Shape shape;

  @Before
  public void setUp() {
    model = new EasyAnimatorModel();
    shape = new Rect(5, 5, Color.BLUE, new Posn(100, 100),
            "bitBlueR", ShapeType.RECTANGLE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTimeDifferenceChangeColor() {
    model.changeColor("bitBlueR", 4, 3, Color.BLUE, Color.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroTimeChangeColor() {
    model.changeColor("bitBlueR", 0, 1, Color.BLUE, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTimeChangeColor() {
    model.changeColor("bitBlueR", 1, -1, Color.BLUE, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartColorChangeColor() {
    model.changeColor("bitBlueR", 2, 3, Color.BLACK, Color.BLUE);
  }

  @Test
  public void testChangeColorInOneTick() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR");
    model.changeColor("bitBlueR", 4, 5, Color.BLUE, Color.BLACK);
    assertEquals(Color.BLACK, model.getShapesPerTick().get(5).get(0).getColor());
  }

  @Test
  public void testChangeColorInTwoTicks() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR");
    model.changeColor("bitBlueR", 6, 8, Color.BLUE, Color.RED);
    int blueRed = Color.BLUE.getRed();
    int blueGreen = Color.BLUE.getGreen();
    int blueBlue = Color.BLUE.getBlue();
    int redRed = Color.RED.getRed();
    int redGreen = Color.RED.getGreen();
    int redBlue = Color.RED.getBlue();
    int redRate = (redRed - blueRed) / 2;
    int greenRate = (redGreen - blueGreen) / 2;
    int blueRate = (redBlue - blueBlue) / 2;
    Color newColor = new Color(blueRed + redRate, blueGreen + greenRate,
            blueBlue + blueRate);
    assertEquals(Color.RED, model.getShapesPerTick().get(8).get(0).getColor());
    Shape s = model.getShapesPerTick().get(7).get(0);
    List<ArrayList<Shape>> list = model.getShapesPerTick();
    assertEquals(newColor,
            model.getShapesPerTick().get(7).get(0).getColor());
  }

  @Test
  public void testChangeSizeOneTick() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR");
    model.changeSize("bitBlueR", 2, 3, 5, 5,
            10, 5);
    assertEquals(10, model.getShapesPerTick().get(3).get(0).getHeight());
    assertEquals(5, model.getShapesPerTick().get(3).get(0).getWidth());
  }

  @Test
  public void testChangeSizeMultipleTicks() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 30);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR");
    model.changeSize("bitBlueR", 4, 6, 5,
            5, 15, 10);
    assertEquals(10, model.getShapesPerTick().get(5).get(0).getHeight());
    assertEquals(7, model.getShapesPerTick().get(5).get(0).getWidth());
    assertEquals(15, model.getShapesPerTick().get(6).get(0).getHeight());
    assertEquals(10, model.getShapesPerTick().get(6).get(0).getWidth());
  }

  @Test
  public void testToStringModelState() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 2);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR");
    model.moveShape(1, 2, new Posn(100, 100),
            new Posn(150, 150), "bitBlueR");
    String str = "At time t = 1:\n" +
            "Shape bitBlueR RECTANGLE\n" +
            "x=100 y=100 w=5 h=5 color=java.awt.Color[r=0,g=0,b=255]\n" +
            "At time t = 2:\n" +
            "Shape bitBlueR RECTANGLE\n" +
            "x=150 y=150 w=5 h=5 color=java.awt.Color[r=0,g=0,b=255]\n";
    assertEquals(str, model.toString());
  }

  @Test
  public void testToStringModelState2() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200, 2);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR");
    model.moveShape(1, 2, new Posn(100, 100),
            new Posn(150, 150), "bitBlueR");
    model.changeColor("bitBlueR", 1, 2, Color.BLUE, Color.RED);
    String str = "At time t = 1:\n" +
            "Shape bitBlueR RECTANGLE\n" +
            "x=100 y=100 w=5 h=5 color=java.awt.Color[r=0,g=0,b=255]\n" +
            "At time t = 2:\n" +
            "Shape bitBlueR RECTANGLE\n" +
            "x=150 y=150 w=5 h=5 color=java.awt.Color[r=255,g=0,b=0]\n";
    assertEquals(str, model.toString());
  }

}
