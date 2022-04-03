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
  public void testRenderAnimation() throws IOException {
    //TweenModelBuilder<AnimatorModel> modelBuilder = new AnimationBuilder(m);
    //AnimationFileReader reader = new AnimationFileReader();
    //reader.readFile("buildings.txt", modelBuilder);
    //AnimatorModel model = modelBuilder.build();
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
    assertEquals(scan.nextInt(), sceneWidth);
    assertEquals(scan.nextInt(), sceneHeight);

  }
}
