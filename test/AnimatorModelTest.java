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
import model.Tempo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for our animator model.
 */
public class AnimatorModelTest {

  @Test
  public void testCreateEmptyAnimation() {
    AnimatorModel m = new EasyAnimatorModel();
    m.buildScene(200, 200);
    assertEquals(m.getShapes().size(), 0);
    assertEquals(m.getShapesPerTick().size(), 0);
  }

  @Test
  public void testAddShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    assertEquals(new ArrayList<Shape>(), model.getShapes());
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov2", 1, 30);
    model.addShape(ShapeType.RECTANGLE,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redrec12", 1, 30);
    assertEquals(new Oval(10, 10, Color.RED, new Posn(50, 50), "redov2",
                    ShapeType.OVAL),
            model.getShapes().get(0));
  }

  @Test
  public void testMoveShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov1", 1, 30);
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
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov1", 1, 30);
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
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov3", 1, 30);
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
  public void testMoveShapeStandsStill() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov5", 1, 30);
    model.moveShape(1,
            12,
            new Posn(50, 50),
            new Posn(50, 50),
            "redov5");
    assertEquals(model.getShape("redov5").getShapePosn(), new Posn(50, 50));
    assertEquals(model.getShapesPerTick().get(11).get(0).getShapePosn(), new Posn(50, 50));
    assertEquals(model.getShapesPerTick().get(12).get(0).getShapePosn(), new Posn(50, 50));
  }

  @Test
  public void testMultiMoveShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov6", 1, 30);
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
  }

  @Test
  public void testMoveShapeWhileChangingSize() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov6", 1, 30);
    model.moveShape(1, 5, new Posn(50, 50), new Posn(90,90),
            "redov6");
    model.changeSize("redov6", 1, 5, 10, 10,
            14, 14);
    assertEquals(new Posn(70, 70), model.getShapesPerTick().get(3).get(0).getShapePosn());
    assertEquals(12, model.getShapesPerTick().get(3).get(0).getHeight());
    assertEquals(12, model.getShapesPerTick().get(3).get(0).getWidth());



  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuildNegativeScene() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(-200, 200);
  }

  @Test
  public void testAddOOBShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    Posn pos = new Posn(250, 250);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            pos,
            "redov7", 1, 30);
    Shape shape = new Oval(10, 10, Color.RED, pos, "redov7", ShapeType.OVAL);
    assertEquals(model.getShapesPerTick().get(15).get(0), shape);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddNegativeDimensionsShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            -10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov8", 0, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddRepeatID() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov10", 1, 30);
    model.addShape(ShapeType.OVAL,
            20,
            30,
            Color.BLUE,
            new Posn(100, 20),
            "redov10", 0, 30);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveShapeNegativeTime() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov11", 1, 30);
    model.moveShape(5,
            2,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov11");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetNonexistentShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov12", 1, 30);
    model.getShape("redov2");
  }

  @Test
  public void testGetShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov13", 1, 30);
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
    model.buildScene(200, 200);
    List<Shape> shapelist = new ArrayList<>();
    shapelist.add(exShape);
    model.addShape(ShapeType.OVAL,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov14", 1, 30);
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
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR", 1, 30);
    model.changeColor("bitBlueR", 2, 3, Color.BLACK, Color.BLUE);
  }

  @Test
  public void testChangeColorInOneTick() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR", 1, 30);
    model.changeColor("bitBlueR", 4, 5, Color.BLUE, Color.BLACK);
    assertEquals(Color.BLACK, model.getShapesPerTick().get(5).get(0).getColor());
  }

  @Test
  public void testChangeColorInTwoTicks() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR", 1, 30);
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
    assertEquals(newColor,
            model.getShapesPerTick().get(7).get(0).getColor());
  }

  @Test
  public void testChangeColorWhileChangingSize() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.GREEN,
            new Posn(100, 100), "greenS", 1, 30);
    model.changeColor("greenS", 10, 14, Color.GREEN, Color.PINK);
    model.changeSize("greenS", 12, 14, 5,
            5, 11, 15);
    int greenRed = Color.GREEN.getRed();
    int greenGreen = Color.GREEN.getGreen();
    int greenBlue = Color.GREEN.getBlue();
    int pinkRed = Color.PINK.getRed();
    int pinkGreen = Color.PINK.getGreen();
    int pinkBlue = Color.PINK.getBlue();
    int redRate = (pinkRed - greenRed) / 4;
    int greenRate = (pinkGreen - greenGreen) / 4;
    int blueRate = (pinkBlue - greenBlue) / 4;
    Color newColor1 = new Color(greenRed + redRate,
            greenGreen + greenRate, greenBlue + blueRate);
    assertEquals(newColor1, model.getShapesPerTick().get(11).get(0).getColor());
    Color newColor2 = new Color(greenRed + (3 * redRate),
            greenGreen + (3 * greenRate), greenBlue + (3 * blueRate));
    assertEquals(newColor2, model.getShapesPerTick().get(13).get(0).getColor());
    assertEquals(8, model.getShapesPerTick().get(13).get(0).getHeight());
    assertEquals(10, model.getShapesPerTick().get(13).get(0).getWidth());
    assertEquals(Color.PINK, model.getShapesPerTick().get(14).get(0).getColor());
    assertEquals(15, model.getShapesPerTick().get(14).get(0).getWidth());
    assertEquals(11, model.getShapesPerTick().get(14).get(0).getHeight());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveAlreadyMoving() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR", 1, 30);
    model.moveShape(1, 6,
            new Posn(100, 100), new Posn(500, 500), "bitBlueR");
    model.moveShape(3, 6, new Posn(260, 260),
            new Posn(500, 500), "bitBlueR");
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidChangeColorAlreadyChangingColor() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, new Color(1, 1, 1),
            new Posn(100, 100), "bitBlueR", 1, 30);
    model.changeColor("bitBlueR", 2, 4,
            new Color(1, 1, 1), new Color(5, 5, 5));
    model.changeColor("bitBlueR", 3, 4,
            new Color(3, 3, 3), new Color(5, 5, 5));
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidChangeSizeAlreadyChangingSize() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 8, 8, new Color(1, 1, 1),
            new Posn(100, 100), "bitBlueR", 1, 30);
    model.changeSize("bitBlueR", 1, 5,
            8, 8, 12, 12);
    model.changeSize("bitBlueR", 3, 5,
            10, 12, 12, 12);
  }


  @Test
  public void testChangeSizeOneTick() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR", 1, 30);
    model.changeSize("bitBlueR", 2, 3, 5, 5,
            10, 5);
    assertEquals(10, model.getShapesPerTick().get(3).get(0).getHeight());
    assertEquals(5, model.getShapesPerTick().get(3).get(0).getWidth());
  }

  @Test
  public void testChangeSizeMultipleTicks() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR", 1, 30);
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
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR", 1, 2);
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
  public void testNoGapsInTicksNoMotions() {
    model.buildScene(200, 200);
    Shape shape = new Rect(10, 10, Color.RED, new Posn(50, 50),
            "rect1", ShapeType.RECTANGLE);
    model.addShape(shape.getShapeType(), shape.getHeight(), shape.getWidth(), shape.getColor(),
            shape.getShapePosn(), shape.getShapeID(), 1, 10);
    for (int i = 1; i < 10; i++) {
      assertEquals(model.getShapeAt("rect1", i).get(), shape);
    }
  }

  @Test
  public void testNoGapsInTicksMove() {
    model.buildScene(200, 200);
    Shape shape = new Rect(10, 10, Color.RED, new Posn(50, 50),
            "rect1", ShapeType.RECTANGLE);
    model.addShape(shape.getShapeType(), shape.getHeight(), shape.getWidth(), shape.getColor(),
            shape.getShapePosn(), shape.getShapeID(), 1, 6);
    model.moveShape(2, 4, shape.getShapePosn(),
            new Posn(62, 62), "rect1");
    assertEquals(model.getShapeAt("rect1", 1).get(), shape);
    assertEquals(model.getShapeAt("rect1", 2).get(), shape);
    assertEquals(model.getShapeAt("rect1", 3).get().getShapePosn(),
            new Posn(56, 56));
    assertEquals(model.getShapeAt("rect1", 4).get().getShapePosn(),
            new Posn(62, 62));
    assertEquals(model.getShapeAt("rect1", 5).get().getShapePosn(),
            new Posn(62, 62));
    assertEquals(model.getShapeAt("rect1", 6).get().getShapePosn(),
            new Posn(62, 62));
  }

  @Test
  public void testNoGapsInTicksChangeColor() {
    model.buildScene(200, 200);
    Shape shape = new Rect(10, 10, new Color(0, 0, 0),
            new Posn(50, 50), "rect1", ShapeType.RECTANGLE);
    model.addShape(shape.getShapeType(), shape.getHeight(), shape.getWidth(), shape.getColor(),
            shape.getShapePosn(), shape.getShapeID(), 1, 6);
    model.changeColor("rect1", 2, 4, shape.getColor(),
            new Color(4, 4, 4));
    assertEquals(model.getShapeAt("rect1", 1).get(), shape);
    assertEquals(model.getShapeAt("rect1", 2).get(), shape);
    assertEquals(model.getShapeAt("rect1", 3).get().getColor(),
            new Color(2, 2, 2));
    assertEquals(model.getShapeAt("rect1", 4).get().getColor(),
            new Color(4, 4, 4));
    assertEquals(model.getShapeAt("rect1", 5).get().getColor(),
            new Color(4, 4, 4));
    assertEquals(model.getShapeAt("rect1", 6).get().getColor(),
            new Color(4, 4, 4));
  }

  @Test
  public void testNoGapsInTicksChangeSize() {
    model.buildScene(200, 200);
    Shape shape = new Rect(10, 10, new Color(0, 0, 0),
            new Posn(50, 50), "rect1", ShapeType.RECTANGLE);
    model.addShape(shape.getShapeType(), shape.getHeight(), shape.getWidth(), shape.getColor(),
            shape.getShapePosn(), shape.getShapeID(), 1, 6);
    model.changeSize("rect1", 2, 4, 10,
            10, 20, 20);
    assertEquals(model.getShapeAt("rect1", 1).get(), shape);
    assertEquals(model.getShapeAt("rect1", 2).get(), shape);
    assertEquals(model.getShapeAt("rect1", 3).get().getHeight(), 15);
    assertEquals(model.getShapeAt("rect1", 3).get().getWidth(), 15);
    assertEquals(model.getShapeAt("rect1", 4).get().getHeight(), 20);
    assertEquals(model.getShapeAt("rect1", 4).get().getWidth(), 20);
    assertEquals(model.getShapeAt("rect1", 5).get().getHeight(), 20);
    assertEquals(model.getShapeAt("rect1", 5).get().getWidth(), 20);
    assertEquals(model.getShapeAt("rect1", 6).get().getHeight(), 20);
    assertEquals(model.getShapeAt("rect1", 6).get().getWidth(), 20);
  }

  @Test
  public void testToStringModelState2() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    model.addShape(ShapeType.RECTANGLE, 5, 5, Color.BLUE,
            new Posn(100, 100), "bitBlueR", 1, 2);
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

  @Test
  public void testMoveColorChangeAndSizeChange() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(500, 500);
    model.addShape(ShapeType.OVAL, 10 , 50, Color.RED, new Posn(60, 60),
            "blackov1", 1, 50);
    model.moveShape(10, 34, new Posn(60, 60), new Posn(300, 300),
            "blackov1");
    model.changeColor("blackov1", 10, 24, Color.RED, Color.BLUE);
    model.changeSize("blackov1", 10, 34, 10, 50,
            34, 74);
    Posn midMove = model.getShapesPerTick().get(15).get(0).getShapePosn();
    Color colorMove = model.getShapesPerTick().get(15).get(0).getColor();
    int heightMove = model.getShapesPerTick().get(15).get(0).getHeight();
    int widthMove = model.getShapesPerTick().get(15).get(0).getWidth();

    int redRed = Color.RED.getRed();
    int redGreen = Color.RED.getGreen();
    int redBlue = Color.RED.getBlue();
    int blueRed = Color.BLUE.getRed();
    int blueGreen = Color.BLUE.getGreen();
    int blueBlue = Color.BLUE.getBlue();
    int redRate = (blueRed - redRed) / 14;
    int greenRate = (blueGreen - redGreen) / 14;
    int blueRate = (blueBlue - redBlue) / 14;
    Color newColor = new Color(redRed + (5 * redRate), redGreen + (5 * greenRate),
            redBlue + (5 * blueRate));

    assertEquals(15, heightMove);
    assertEquals(55, widthMove);
    assertEquals(new Posn(110, 110), midMove);
    assertEquals(colorMove, newColor);
  }

  @Test
  public void testMoveMultipleShapes() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(500, 500);
    model.addShape(ShapeType.OVAL, 500 , 50, Color.RED, new Posn(60, 60),
            "redov1", 1, 50);
    model.addShape(ShapeType.RECTANGLE, 10 , 50, Color.BLACK, new Posn(60, 60),
            "blackrect1", 1, 50);
    model.addShape(ShapeType.OVAL, 10 , 10, Color.RED, new Posn(100, 100),
            "redov2", 1, 50);
    model.moveShape(1, 3, new Posn(60, 60), new Posn(100, 100),
            "redov1");
    model.moveShape(1, 3, new Posn(100, 100), new Posn(60, 60),
            "redov2");
    assertEquals(new Posn(80, 80), model.getShapesPerTick().get(2).get(0).getShapePosn());
    assertEquals(new Posn(60, 60), model.getShapesPerTick().get(2).get(1).getShapePosn());
  }

  @Test
  public void testToString() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(500, 500);
    model.addShape(ShapeType.OVAL, 50 , 50, Color.RED, new Posn(60, 60),
            "redov1", 1, 50);
    assertEquals("50506060java.awt.Color[r=255,g=0,b=0]redov1OVAL",
            model.getShapes().get(0).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTempoOverlap1() {
    Tempo t1 = new Tempo(20, 50, 100);
    Tempo t2 = new Tempo(10, 80, 100);
    model.addTempo(t1);
    model.addTempo(t2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddTempoOverlapEndTime() {
    Tempo t1 = new Tempo(20, 50, 100);
    Tempo t2 = new Tempo(10, 40, 55);
    model.addTempo(t1);
    model.addTempo(t2);
  }

  @Test
  public void testAddMultipleTempos() {
    Tempo t1 = new Tempo(20, 50, 60);
    Tempo t2 = new Tempo(10, 60, 80);
    Tempo t3 = new Tempo(5, 100, 190);
    model.addTempo(t1);
    model.addTempo(t2);
    model.addTempo(t3);
    assertEquals(3, model.getTempos().size());
  }


}
