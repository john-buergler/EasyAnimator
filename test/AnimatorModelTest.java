import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import model.AbstractShape;
import model.EasyAnimatorModel;
import model.Oval;
import model.Posn;
import model.ShapeType;

import static org.junit.Assert.assertEquals;

public class AnimatorModelTest {
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
            "redov1");
    assertEquals(new Oval(10, 10, Color.RED, new Posn(50, 50), "redov1"),
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
            "redov1");
    model.moveShape(0,
            2,
            new Posn(50, 50),
            new Posn(100, 100),
            "redov1");
    int y = model.getShape("redov1").getShapePosn().getY();
    int x = model.getShape("redov1").getShapePosn().getX();
    assertEquals(model.getShape("redov1").getShapePosn(), new Posn(100, 100));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBuildNegativeScene() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(-200, 200);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddOOBShape() {
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
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
    model.buildScene(200, 200);
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
    model.buildScene(200, 200);
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
    model.buildScene(200, 200);
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
    model.buildScene(200, 200);
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
    model.buildScene(200, 200);
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
            "redov1"), model.getShape("redov1"));
  }

  @Test
  public void testGetShapes() {
    AbstractShape exShape = new Oval(10,
            10,
            Color.RED,
            new Posn(50, 50),
            "redov1");
    EasyAnimatorModel model = new EasyAnimatorModel();
    model.buildScene(200, 200);
    List<AbstractShape> shapelist = new ArrayList<AbstractShape>();
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
