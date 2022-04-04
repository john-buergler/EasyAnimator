import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import model.AnimatorModel;
import model.EasyAnimatorModel;
import model.Posn;
import model.ShapeType;
import view.AnimatorSVGView;
import view.AnimatorTextView;
import view.IView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TextViewTest {
 AnimatorModel m;

  @Before
  public void setUp() {
    this.m = new EasyAnimatorModel();
  }

  @Test (expected = IllegalArgumentException.class)
  public void nullModelException() throws IOException {
    AnimatorTextView view = new AnimatorTextView(null, "aa", 20);
  }

  @Test (expected = IllegalArgumentException.class)
  public void badNameException() throws IOException {
    AnimatorTextView view = new AnimatorTextView(null, "", 20);
  }

  @Test (expected = IllegalArgumentException.class)
  public void negativeSpeed() throws IOException {
    AnimatorTextView view = new AnimatorTextView(null, "aa", -1);
  }

  @Test
  public void testRenderEmptyAnimation() throws IOException {
    int sceneWidth = 300;
    int sceneHeight = 299;
    m.buildScene(sceneWidth, sceneHeight);
    File file = new File("TextViewTest");
    IView textView = new AnimatorTextView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String canvas = scan.next();
    assertEquals(canvas, "canvas");
    assertEquals(scan.nextInt(), sceneHeight);
    assertEquals(scan.nextInt(), sceneWidth);
  }


  @Test
  public void testRenderAnimationMove() throws IOException {
    int sceneWidth = 200;
    int sceneHeight = 250;
    m.buildScene(sceneWidth, sceneHeight);
    m.addShape(ShapeType.RECTANGLE, 10, 15, new Color(0, 0, 0),
            new Posn(100, 125), "rect1", 1, 5);
    m.moveShape(2, 3, new Posn(100, 125),
            new Posn(150, 150), "rect1");
    File file = new File("TextViewTest");
    IView textView = new AnimatorTextView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String canvas = scan.next();
    assertEquals(canvas, "canvas");
    assertEquals(scan.nextInt(), sceneHeight);
    assertEquals(scan.nextInt(), sceneWidth);
    scan.next();
    assertEquals(scan.nextLine(), " rect1 RECTANGLE 10 15 0 0 0 100 125 1 5");
    assertEquals(scan.nextLine(),
            "motion move rect1 0.2 100 125 10 15 0 0 0 0.3 150 150 10 15 0 0 0 ");
  }

  @Test
  public void testRenderAnimationSizeChange() throws IOException {
    int sceneWidth = 200;
    int sceneHeight = 250;
    m.buildScene(sceneWidth, sceneHeight);
    m.addShape(ShapeType.RECTANGLE, 10, 15, new Color(0, 0, 0),
            new Posn(100, 125), "rect1", 1, 5);
    m.changeSize("rect1", 2, 3, 10, 15, 20,
            20);
    File file = new File("TextViewTest");
    IView textView = new AnimatorTextView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String canvasDimensions = scan.nextLine();
    String rect1 = scan.nextLine();
    String rect1SizeChange = scan.nextLine();
    assertEquals("canvas 250 200", canvasDimensions);
    assertEquals("shape rect1 RECTANGLE 10 15 0 0 0 100 125 1 5", rect1);
    assertEquals("motion size rect1 0.2 100 125 10 15 0 0 0 0.3 100 125 20 20 0 0 0 ",
            rect1SizeChange);
    assertFalse(scan.hasNext());
  }

  @Test
  public void testRenderAnimationColorChange() throws IOException {
    int sceneWidth = 200;
    int sceneHeight = 250;
    m.buildScene(sceneWidth, sceneHeight);
    m.addShape(ShapeType.RECTANGLE, 10, 15, new Color(0, 0, 0),
            new Posn(100, 125), "rect1", 1, 5);
    m.changeColor("rect1", 2, 3, new Color(0, 0, 0),
            new Color(255, 255, 255));
    File file = new File("TextViewTest");
    IView textView = new AnimatorTextView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String canvasDimensions = scan.nextLine();
    String rect1 = scan.nextLine();
    String rect1ColorChange = scan.nextLine();
    assertEquals("canvas 250 200", canvasDimensions);
    assertEquals("shape rect1 RECTANGLE 10 15 0 0 0 100 125 1 5", rect1);
    assertEquals("motion color rect1 0.2 100 125 10 15 0 0 0 0.3 100 125 10 " +
            "15 255 255 255 ", rect1ColorChange);
    assertFalse(scan.hasNext());
  }

  @Test
  public void testRenderAnimationAllThree() throws IOException {
    int sceneWidth = 200;
    int sceneHeight = 250;
    m.buildScene(sceneWidth, sceneHeight);
    m.addShape(ShapeType.RECTANGLE, 10, 15, new Color(0, 0, 0),
            new Posn(100, 125), "rect1", 1, 5);
    m.moveShape(2, 3, new Posn(100, 125),
            new Posn(150, 150), "rect1");
    m.changeColor("rect1", 2, 3, new Color(0, 0, 0),
            new Color(255, 255, 255));
    m.changeSize("rect1", 2, 3, 10, 15, 20,
            20);
    File file = new File("TextViewTest");
    IView textView = new AnimatorTextView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String canvas = scan.nextLine();
    String rect1 = scan.nextLine();
    String rect1Move = scan.nextLine();
    String rect1Color = scan.nextLine();
    String rect1Size = scan.nextLine();
    assertEquals("canvas 250 200", canvas);
    assertEquals("shape rect1 RECTANGLE 10 15 0 0 0 100 125 1 5", rect1);
    assertEquals("motion move rect1 0.2 100 125 10 15 0 0 0 0.3 150 150 10 15 0 0 0 ",
            rect1Move);
    assertEquals("motion color rect1 0.2 150 150 10 15 0 0 0 0.3 150 150 10 15 " +
            "255 255 255 ", rect1Color);
    assertEquals("motion size rect1 0.2 150 150 10 15 0 0 0 0.3 150 150 20 " +
            "20 0 0 0 ", rect1Size);
    assertFalse(scan.hasNext());
  }

  @Test
  public void testRenderAnimation2ShapeMotion() throws IOException {
    int sceneWidth = 200;
    int sceneHeight = 250;
    m.buildScene(sceneWidth, sceneHeight);
    m.addShape(ShapeType.RECTANGLE, 10, 15, new Color(0, 0, 0),
            new Posn(100, 125), "rect1", 1, 5);
    m.moveShape(2, 3, new Posn(100, 125),
            new Posn(150, 150), "rect1");
    m.addShape(ShapeType.OVAL, 30, 40, new Color(0, 0, 0),
            new Posn(300, 125), "oval1", 1, 5);
    m.moveShape(2, 3, new Posn(300, 125),
            new Posn(150, 150), "oval1");
    File file = new File("TextViewTest");
    IView textView = new AnimatorTextView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String canvas = scan.nextLine();
    String rect1 = scan.nextLine();
    String rect1Move = scan.nextLine();
    String oval1 = scan.nextLine();
    String oval1Move = scan.nextLine();
    assertEquals("canvas 250 200", canvas);
    assertEquals("shape rect1 RECTANGLE 10 15 0 0 0 100 125 1 5", rect1);
    assertEquals("motion move rect1 0.2 100 125 10 15 0 0 0 0.3 150 150 10 15 0 0 0 ",
            rect1Move);
    assertEquals("shape oval1 OVAL 30 40 0 0 0 300 125 1 5", oval1);
    assertEquals("motion move oval1 0.2 300 125 30 40 0 0 0 0.3 150 150 " +
            "30 40 0 0 0 ", oval1Move);
    assertFalse(scan.hasNext());
  }

}
