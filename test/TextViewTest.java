import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import io.AnimationBuilder;
import io.AnimationFileReader;
import io.TweenModelBuilder;
import io.ViewsFactory;
import model.AnimatorModel;
import model.EasyAnimatorModel;
import model.Posn;
import model.ShapeType;
import view.AnimatorTextView;
import view.IView;

import static org.junit.Assert.assertEquals;

public class TextViewTest {
 AnimatorModel m;

  @Before
  public void setUp() {
    this.m = new EasyAnimatorModel();
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
  public void testRenderAnimation() throws IOException {
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

}
