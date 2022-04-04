import org.junit.Test;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.AnimationBuilder;
import io.AnimationFileReader;
import io.TweenModelBuilder;
import model.AnimatorModel;
import model.EasyAnimatorModel;
import model.Oval;
import model.Posn;
import model.ShapeType;
import view.AnimatorSVGView;
import view.IView;

import static org.junit.Assert.assertEquals;

public class SVGViewTest {
  @Test
  public void testMoveSVG() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(500, 500);
    model.addShape(ShapeType.OVAL, 12, 30, Color.RED, new Posn(50, 100),
            "testov1", 10, 40);
    model.moveShape(10, 30, new Posn(50, 100), new Posn(100, 50),
            "testov1");
    model.moveShape(31, 40, new Posn(100, 50), new Posn(75, 75),
            "testov1");
  }

  @Test
  public void testEmptyAnimation() {
    AnimatorModel model = new EasyAnimatorModel();
    model.buildScene(800, 800);
    model.addShape(ShapeType.OVAL, 5,5, Color.RED, new Posn(20, 20),
            "testov1", 0, 20);
    Appendable out = new StringBuilder();
    try {
      AnimatorSVGView view = new AnimatorSVGView(model, "testFile", 20);
      assertEquals("", view.getOutputFile().getEncoding());
    } catch (IOException ioException){
      throw new IllegalArgumentException("bad file");
    }
  }

  @Test
  public void testSVG() throws IOException {
    AnimatorModel m = new EasyAnimatorModel();
    TweenModelBuilder<AnimatorModel> modelBuilder = new AnimationBuilder(m);
    AnimationFileReader reader = new AnimationFileReader();
    reader.readFile("toh-8.txt", modelBuilder);
    AnimatorModel model = modelBuilder.build();
    IView view = new AnimatorSVGView(m, "System.out", 20);
  }
}
