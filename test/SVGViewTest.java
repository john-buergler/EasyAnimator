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
import view.IView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SVGViewTest {
  AnimatorModel m;


  @Before
  public void setUp() {
    this.m = new EasyAnimatorModel();
  }

  @Test
  public void testMoveSVG() throws IOException {
    int sceneWidth = 200;
    int sceneHeight = 250;
    m.buildScene(sceneWidth, sceneHeight);
    m.addShape(ShapeType.RECTANGLE, 10, 15, new Color(0, 0, 0),
            new Posn(100, 125), "rect1", 1, 5);
    m.moveShape(2, 3, new Posn(100, 125),
            new Posn(150, 150), "rect1");
    File file = new File("TextViewTest");
    IView textView = new AnimatorSVGView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String establishSVG = scan.nextLine();
    String establishBase = scan.nextLine();
    String establishAnimateBaseHidden = scan.nextLine();
    String closeBase = scan.nextLine();
    String establishRect = scan.nextLine();
    String showRect = scan.nextLine();
    String moveRectX = scan.nextLine();
    String moveRectY = scan.nextLine();
    String closeRect = scan.nextLine();
    String closeSVG = scan.nextLine();
    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"800\" height=\"800\"" +
            " version=\"1.1\">", establishSVG);
    assertEquals("<rect>", establishBase);
    assertEquals(" <animate id=\"base\" begin=\"0;base.end\" dur=\"600ms\" " +
            "attributeName=\"visibility\" from=\"hide\" to =\"hide\"></animate>",
            establishAnimateBaseHidden);
    assertEquals("</rect>", closeBase);
    assertEquals("<rect id=\"rect1\" x=\"100\" y=\"125\" width=\"15\" height=\"10\" " +
            "fill=\"rgb(0, 0, 0)\" visibility=\"visible\">", establishRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"100ms\" " +
            "attributeName=\"visibility\" from=\"hidden\" to=\"visible\"></animate>", showRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
            "dur=\"100ms\" attributeName=\"x\" from=\"100\" to=\"150\" fill=\"freeze\"></animate>",
            moveRectX);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
                    "dur=\"100ms\" attributeName=\"y\" from=\"125\" to=\"150\" " +
                    "fill=\"freeze\"></animate>", moveRectY);
    assertEquals("</rect>", closeRect);
    assertEquals("</svg>", closeSVG);
    assertFalse(scan.hasNext());

  }

  @Test
  public void testSizeChangeSVG() throws IOException {
    int sceneWidth = 200;
    int sceneHeight = 250;
    m.buildScene(sceneWidth, sceneHeight);
    m.addShape(ShapeType.RECTANGLE, 10, 15, new Color(0, 0, 0),
            new Posn(100, 125), "rect1", 1, 5);
    m.changeSize("rect1", 2, 3, 10, 15, 20,
            20);
    File file = new File("TextViewTest");
    IView textView = new AnimatorSVGView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String establishSVG = scan.nextLine();
    String establishBase = scan.nextLine();
    String establishAnimateBaseHidden = scan.nextLine();
    String closeBase = scan.nextLine();
    String establishRect = scan.nextLine();
    String showRect = scan.nextLine();
    String changeRectHeight = scan.nextLine();
    String changeRectWidth = scan.nextLine();
    String closeRect = scan.nextLine();
    String closeSVG = scan.nextLine();
    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"800\" height=\"800\"" +
            " version=\"1.1\">", establishSVG);
    assertEquals("<rect>", establishBase);
    assertEquals(" <animate id=\"base\" begin=\"0;base.end\" dur=\"600ms\" " +
                    "attributeName=\"visibility\" from=\"hide\" to =\"hide\"></animate>",
            establishAnimateBaseHidden);
    assertEquals("</rect>", closeBase);
    assertEquals("<rect id=\"rect1\" x=\"100\" y=\"125\" width=\"15\" height=\"10\" " +
            "fill=\"rgb(0, 0, 0)\" visibility=\"visible\">", establishRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"100ms\" " +
            "attributeName=\"visibility\" from=\"hidden\" to=\"visible\"></animate>", showRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
                    "dur=\"100ms\" attributeName=\"height\" from=\"10\" to=\"20\" " +
                    "fill=\"freeze\"></animate>",
            changeRectHeight);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
            "dur=\"100ms\" attributeName=\"width\" from=\"15\" to=\"20\" " +
            "fill=\"freeze\"></animate>", changeRectWidth);
    assertEquals("</rect>", closeRect);
    assertEquals("</svg>", closeSVG);
    assertFalse(scan.hasNext());

  }

  @Test
  public void testColorChangeSVG() throws IOException {
    int sceneWidth = 200;
    int sceneHeight = 250;
    m.buildScene(sceneWidth, sceneHeight);
    m.addShape(ShapeType.RECTANGLE, 10, 15, new Color(0, 0, 0),
            new Posn(100, 125), "rect1", 1, 5);
    m.changeColor("rect1", 2, 3, new Color(0, 0, 0),
            new Color(255, 255, 255));
    File file = new File("TextViewTest");
    IView textView = new AnimatorSVGView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String establishSVG = scan.nextLine();
    String establishBase = scan.nextLine();
    String establishAnimateBaseHidden = scan.nextLine();
    String closeBase = scan.nextLine();
    String establishRect = scan.nextLine();
    String showRect = scan.nextLine();
    String changeColorRect = scan.nextLine();
    String closeRect = scan.nextLine();
    String closeSVG = scan.nextLine();
    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"800\" height=\"800\"" +
            " version=\"1.1\">", establishSVG);
    assertEquals("<rect>", establishBase);
    assertEquals(" <animate id=\"base\" begin=\"0;base.end\" dur=\"600ms\" " +
                    "attributeName=\"visibility\" from=\"hide\" to =\"hide\"></animate>",
            establishAnimateBaseHidden);
    assertEquals("</rect>", closeBase);
    assertEquals("<rect id=\"rect1\" x=\"100\" y=\"125\" width=\"15\" height=\"10\" " +
            "fill=\"rgb(0, 0, 0)\" visibility=\"visible\">", establishRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"100ms\" " +
            "attributeName=\"visibility\" from=\"hidden\" to=\"visible\"></animate>", showRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
                    "dur=\"100ms\" attributeName=\"fill\" from=\"rgb(0, 0, 0)\" " +
                    "to=\"rgb(255, 255, 255)\" fill=\"freeze\"></animate>",
            changeColorRect);
    assertEquals("</rect>", closeRect);
    assertEquals("</svg>", closeSVG);
    assertFalse(scan.hasNext());

  }

  @Test
  public void testAllThreeInSVG() throws IOException {
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
    IView textView = new AnimatorSVGView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String establishSVG = scan.nextLine();
    String establishBase = scan.nextLine();
    String establishAnimateBaseHidden = scan.nextLine();
    String closeBase = scan.nextLine();
    String establishRect = scan.nextLine();
    String showRect = scan.nextLine();
    String moveRectX = scan.nextLine();
    String moveRectY = scan.nextLine();
    String changeColorRect = scan.nextLine();
    String changeRectHeight = scan.nextLine();
    String changeRectWidth = scan.nextLine();
    String closeRect = scan.nextLine();
    String closeSVG = scan.nextLine();
    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"800\" height=\"800\"" +
            " version=\"1.1\">", establishSVG);
    assertEquals("<rect>", establishBase);
    assertEquals(" <animate id=\"base\" begin=\"0;base.end\" dur=\"600ms\" " +
                    "attributeName=\"visibility\" from=\"hide\" to =\"hide\"></animate>",
            establishAnimateBaseHidden);
    assertEquals("</rect>", closeBase);
    assertEquals("<rect id=\"rect1\" x=\"100\" y=\"125\" width=\"15\" height=\"10\" " +
            "fill=\"rgb(0, 0, 0)\" visibility=\"visible\">", establishRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"100ms\" " +
            "attributeName=\"visibility\" from=\"hidden\" to=\"visible\"></animate>", showRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
                    "dur=\"100ms\" attributeName=\"x\" from=\"100\" to=\"150\" " +
                    "fill=\"freeze\"></animate>",
            moveRectX);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
            "dur=\"100ms\" attributeName=\"y\" from=\"125\" to=\"150\" " +
            "fill=\"freeze\"></animate>", moveRectY);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
                    "dur=\"100ms\" attributeName=\"fill\" from=\"rgb(0, 0, 0)\" " +
                    "to=\"rgb(255, 255, 255)\" fill=\"freeze\"></animate>",
            changeColorRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
                    "dur=\"100ms\" attributeName=\"height\" from=\"10\" to=\"20\" " +
                    "fill=\"freeze\"></animate>",
            changeRectHeight);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
            "dur=\"100ms\" attributeName=\"width\" from=\"15\" to=\"20\" " +
            "fill=\"freeze\"></animate>", changeRectWidth);
    assertEquals("</rect>", closeRect);
    assertEquals("</svg>", closeSVG);
    assertFalse(scan.hasNext());

  }

  @Test
  public void testEmptyAnimation() throws IOException {
    int sceneWidth = 200;
    int sceneHeight = 250;
    m.buildScene(sceneWidth, sceneHeight);
    File file = new File("TextViewTest");
    IView textView = new AnimatorSVGView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String establishSVG = scan.nextLine();
    String establishBase = scan.nextLine();
    String establishAnimateBaseHidden = scan.nextLine();
    String closeBase = scan.nextLine();
    String closeSVG = scan.nextLine();
    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"800\" height=\"800\"" +
            " version=\"1.1\">", establishSVG);
    assertEquals("<rect>", establishBase);
    assertEquals(" <animate id=\"base\" begin=\"0;base.end\" dur=\"0ms\" " +
                    "attributeName=\"visibility\" from=\"hide\" to =\"hide\"></animate>",
            establishAnimateBaseHidden);
    assertEquals("</rect>", closeBase);
    assertEquals("</svg>", closeSVG);
    assertFalse(scan.hasNext());
  }

  @Test
  public void test2ShapeMoveSVG() throws IOException {
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
    IView textView = new AnimatorSVGView(m, "TextViewTest", 10);
    textView.renderAnimation();
    Scanner scan = new Scanner(file);
    String establishSVG = scan.nextLine();
    String establishBase = scan.nextLine();
    String establishAnimateBaseHidden = scan.nextLine();
    String closeBase = scan.nextLine();
    String establishRect = scan.nextLine();
    String showRect = scan.nextLine();
    String moveRectX = scan.nextLine();
    String moveRectY = scan.nextLine();
    String closeRect = scan.nextLine();
    String establishOval = scan.nextLine();
    String showOval = scan.nextLine();
    String moveOvalX = scan.nextLine();
    String moveOvalY = scan.nextLine();
    String closeOval = scan.nextLine();
    String closeSVG = scan.nextLine();
    assertEquals("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"800\" height=\"800\"" +
            " version=\"1.1\">", establishSVG);
    assertEquals("<rect>", establishBase);
    assertEquals(" <animate id=\"base\" begin=\"0;base.end\" dur=\"600ms\" " +
                    "attributeName=\"visibility\" from=\"hide\" to =\"hide\"></animate>",
            establishAnimateBaseHidden);
    assertEquals("</rect>", closeBase);
    assertEquals("<rect id=\"rect1\" x=\"100\" y=\"125\" width=\"15\" height=\"10\" " +
            "fill=\"rgb(0, 0, 0)\" visibility=\"visible\">", establishRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"100ms\" " +
            "attributeName=\"visibility\" from=\"hidden\" to=\"visible\"></animate>", showRect);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
                    "dur=\"100ms\" attributeName=\"x\" from=\"100\" to=\"150\" " +
                    "fill=\"freeze\"></animate>",
            moveRectX);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
            "dur=\"100ms\" attributeName=\"y\" from=\"125\" to=\"150\" " +
            "fill=\"freeze\"></animate>", moveRectY);
    assertEquals("</rect>", closeRect);

    assertEquals("<ellipse id=\"oval1\" cx=\"300\" cy=\"125\" rx=\"20\" ry=\"15\" " +
            "fill=\"rgb(0, 0, 0)\" visibility=\"visible\">", establishOval);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"100ms\" " +
            "attributeName=\"visibility\" from=\"hidden\" to=\"visible\"></animate>", showOval);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
                    "dur=\"100ms\" attributeName=\"cx\" from=\"300\" to=\"150\" " +
                    "fill=\"freeze\"></animate>",
            moveOvalX);
    assertEquals("  <animate attributeType=\"xml\" begin=\"base.begin+200ms\" " +
            "dur=\"100ms\" attributeName=\"cy\" from=\"125\" to=\"150\" " +
            "fill=\"freeze\"></animate>", moveOvalY);
    assertEquals("</ellipse>", closeOval);
    assertEquals("</svg>", closeSVG);
    assertFalse(scan.hasNext());

  }
}
