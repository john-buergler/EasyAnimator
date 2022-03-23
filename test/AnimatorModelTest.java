import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.EasyAnimatorModel;
import model.Oval;
import model.Posn;
import model.ShapeType;
import model.Shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
            "redov1");
    model.addShape(ShapeType.RECTANGLE,
            10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redrec1");
    assertEquals(new Oval(10, 10, Color.RED, new Posn(50, 50), "redov1",
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
    int x =model.getShapesPerTick().get(11).get(0).getShapePosn().getX();
    int x2 =model.getShapesPerTick().get(12).get(0).getShapePosn().getX();
    assertEquals(x, 75);
    assertEquals(x2, 100);
    assertEquals(model.getShapesPerTick().get(12).get(0).getShapePosn(), new Posn(100, 100));
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
            "redov1");
    model.moveShape(10,
            12,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov1");
    model.deleteShape("redov1");
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
            "redov1");
    model.moveShape(10,
            12,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov1");
    model.disappearShape(10, 11, "redov1");
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
            "redov1");
    model.moveShape(0,
            12,
            new Posn(50, 50),
            new Posn(50, 50),
            "redov1");
    assertEquals(model.getShape("redov1").getShapePosn(), new Posn(50, 50));
    assertEquals(model.getShapesPerTick().get(11).get(0).getShapePosn(), new Posn(50, 50));
    assertEquals(model.getShapesPerTick().get(12).get(0).getShapePosn(), new Posn(50, 50));

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
            "redov1");
    model.moveShape(10,
            12,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov1");
    model.moveShape(13,
            15,
            new Posn(100, 100),
            new Posn(50, 50),
            "redov1");
    assertEquals(model.getShape("redov1").getShapePosn(), new Posn(50, 50));
    assertEquals(model.getShapesPerTick().get(11).get(0).getShapePosn(), new Posn(75, 75));
    assertEquals(model.getShapesPerTick().get(12).get(0).getShapePosn(), new Posn(100, 100));
    assertEquals(model.getShapesPerTick().get(14).get(0).getShapePosn(), new Posn(75, 75));
    assertEquals(model.getShapesPerTick().get(15).get(0).getShapePosn(), new Posn(50, 50));
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
            "redov1");
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
            "redov1");
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
            "redov1");
    model.addShape(ShapeType.OVAL,
            20,
            30,
            Color.BLUE,
            new Posn(100, 20),
            "redov1");
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
            "redov1");
    model.moveShape(5,
            2,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov1");
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
            "redov1");
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
            "redov1");
    assertEquals(new Oval(10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov1",
            ShapeType.OVAL), model.getShape("redov1"));
  }

  @Test
  public void testGetShapes() {
    Shape exShape = new Oval(10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov1",
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
            "redov1");
    assertEquals(shapelist, model.getShapes());
  }
}
