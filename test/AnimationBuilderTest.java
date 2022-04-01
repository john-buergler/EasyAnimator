import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import io.AnimationBuilder;
import model.AnimatorModel;
import model.EasyAnimatorModel;
import model.Oval;
import model.Posn;
import model.Rect;
import model.ShapeType;

import static org.junit.Assert.assertEquals;

public class AnimationBuilderTest {
  AnimatorModel m;
  AnimationBuilder ab;
  @Before
  public void setUp() {
    this.m = new EasyAnimatorModel();
    this.ab = new AnimationBuilder(m);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSceneBuilt() {
    ab.setBounds(-10, 100);
  }

  @Test
  public void testSceneBuilt() {
    ab.setBounds(100, 125);
    assertEquals(m.getSceneWidth(), 100);
    assertEquals(m.getSceneHeight(), 125);
  }

  @Test
  public void testAddOval() {
    ab.addOval("oval1", 200, 200, 10, 10, 0,
            0, 0, 2, 10);
    Oval oval1 = new Oval(10, 10, new Color(0, 0, 0),
            new Posn(200, 200), "oval1", ShapeType.OVAL);
    assertEquals(m.getShapes().get(0), oval1);
  }

  @Test
  public void testAddRectangle() {
    ab.addRectangle("rect1", 250, 200, 15, 10, 0,
            0, 1, 2, 10);
    float r = 0.0F;
    float g = 0.0F;
    float b = 1.0F;

    Rect rect1 = new Rect(10, 15, new Color(r, g, b),
            new Posn(250, 200), "rect1", ShapeType.RECTANGLE);
    assertEquals(m.getShapes().get(0), rect1);
  }

  @Test
  public void testAddMoveShape() {
    ab.addRectangle("rect1", 250, 200, 15, 10, 0,
            0, 1, 2, 10);
    ab.addMove("rect1", 250, 200, 310,
            300, 4, 6);
  }


}
