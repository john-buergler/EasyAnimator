import org.junit.Test;

import java.awt.*;

import model.AnimatorModel;
import model.EasyAnimatorModel;
import model.Posn;
import model.ShapeType;

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
    assertEquals("<ellipse id=\"testov1\" cx=\"72\" cy=\"78\" rx=\"15\" ry=\"6\" " +
                    "fill=\"rgb(255, 0, 0)\" visibility=\"visible\">\n" +
                    "  <animate attributeType=\"xml\" begin=\"base.begin+10ms\" dur=\"20000ms\" " +
                    "attributeName=\"cx\" from=\"50\" to=\"100\" fill=\"freeze\"></animate>\n" +
                    "  <animate attributeType=\"xml\" begin=\"base.begin+10ms\" dur=\"20000ms\" " +
                    "attributeName=\"cy\" from=\"100\" to=\"50\" fill=\"freeze\"></animate>\n" +
                    "  <animate attributeType=\"xml\" begin=\"base.begin+31ms\" dur=\"9000ms\" " +
                    "attributeName=\"cx\" from=\"100\" to=\"75\" fill=\"freeze\"></animate>\n" +
                    "  <animate attributeType=\"xml\" begin=\"base.begin+31ms\" dur=\"9000ms\" " +
                    "attributeName=\"cy\" from=\"50\" to=\"75\" fill=\"freeze\"></animate>\n" +
                    "</ellipse>",
            model.getShapes().get(0).toSVG());
  }
}
