package view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import model.AnimatorModel;
import model.Posn;
import model.Shape;
import model.ShapeType;

public class AnimatorSVGView implements IView {
  private final AnimatorModel model;
  private final String fileName;
  private final PrintStream outputSystem;
  private final int speed;
  private final FileWriter outputFile;

  public AnimatorSVGView(AnimatorModel m, String fileName, int speed) throws IOException {
    this.model = m;
    this.speed = speed;
    this.fileName = fileName;
    if (fileName.equals("System.out")) {
      outputSystem = System.out;
      outputFile = null;
    }
    else {
      outputFile = new FileWriter(fileName);
      outputSystem = null;
    }
  }

  @Override
  public void renderAnimation() throws IOException {
    StringBuilder str = new StringBuilder();
    str.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + model.getSceneWidth() + "\"" +
            " height=\"" + model.getSceneHeight() +"\" version=\"1.1\">");
    str.append("<rect>\n" + " <animate id=\"base\" begin=\"0;base.end\" dur=\"" +
            ((model.getShapesPerTick().size() / this.speed) * 1000) + "ms" + "\"" +
            "attributeName=\"visibility\" from=\"hide\" to =\"hide\"></animate>\n</rect>\n");
    for (Shape shape : model.getShapes()) {
      String shapeDef = shape.getLog().get(0);
      Scanner scan = new Scanner(shapeDef);
      scan.next();
      String id = scan.next();
      String type = scan.next();
      int height = scan.nextInt();
      int width = scan.nextInt();
      int r = scan.nextInt();
      int g = scan.nextInt();
      int b = scan.nextInt();
      int x = scan.nextInt();
      int y = scan.nextInt();
      str.append(toSVG(type, x, y, width, height, r, g, b, id));
      if (shape.getLog().size() > 1) {
        for (int i = 1; i < shape.getLog().size(); i++) {
          ArrayList<String> attributesChanging = new ArrayList<String>();
          ArrayList<String> valuesStarting = new ArrayList<String>();
          ArrayList<String> valuesEnding = new ArrayList<String>();
          String motion = shape.getLog().get(i);
          Scanner scan2 = new Scanner(motion);
          scan2.next();
          String motionType = scan2.next();
          scan2.next();
          int startTime = scan2.nextInt();
          int xs = scan2.nextInt();
          int ys = scan2.nextInt();
          int ws = scan2.nextInt();
          int hs = scan2.nextInt();
          int rs = scan2.nextInt();
          int gs = scan2.nextInt();
          int bs = scan2.nextInt();
          int endTime = scan2.nextInt();
          int xe = scan2.nextInt();
          int ye = scan2.nextInt();
          int he = scan2.nextInt();
          int we = scan2.nextInt();
          int re = scan2.nextInt();
          int ge = scan2.nextInt();
          int be = scan2.nextInt();
          switch (motionType) {
            case "move":
              if (type.equals("RECTANGLE")) {
                attributesChanging.add("cx");
                attributesChanging.add("cy");
                valuesStarting.add(String.valueOf(xs));
                valuesStarting.add(String.valueOf(ys));
                valuesEnding.add(String.valueOf(xe));
                valuesEnding.add(String.valueOf(ye));
              } else {
                attributesChanging.add("x");
                attributesChanging.add("y");
                valuesStarting.add(String.valueOf(xs));
                valuesStarting.add(String.valueOf(ys));
                valuesEnding.add(String.valueOf(xe));
                valuesEnding.add(String.valueOf(ye));
              }
            case "color":
              attributesChanging.add("fill");
              valuesStarting.add("rgb(" + String.valueOf(rs) + ", " + String.valueOf(gs) + ", " +
                      String.valueOf(bs) + ")");
              valuesEnding.add("rgb(" + String.valueOf(re) + ", " + String.valueOf(ge) + ", " +
                      String.valueOf(be) + ")");
            case "size":
              attributesChanging.add("height");
              attributesChanging.add("width");
              valuesStarting.add(String.valueOf(hs));
              valuesStarting.add(String.valueOf(ws));
              valuesEnding.add(String.valueOf(he));
              valuesEnding.add(String.valueOf(we));
          }
          for (int j = 0; j < attributesChanging.size(); j++) {
            str.append(SVGMove(startTime, endTime, valuesStarting.get(j), valuesEnding.get(j),
                    attributesChanging.get(j)));
          }
          attributesChanging = new ArrayList<String>();
          valuesStarting = new ArrayList<String>();
          valuesEnding = new ArrayList<String>();
        }
        if (type.equals("RECTANGLE")) {
          str.append("</rect>\n");
        }
        else {
          str.append("</ellipse>\n");
        }
      }
    }
    str.append("</svg>");
    outputSystem.append(str.toString());
  }

  private String toSVG(String type, int x, int y, int width, int height, int r, int g, int b,
                       String id) {
    StringBuilder str = new StringBuilder();
    switch (type) {
      case "RECTANGLE":
        str.append("<").append("rect").append(" id=").append('"').append(id)
                .append('"').append(" x=").append('"').append(x)
                .append('"').append(" y=").append('"').append(y)
                .append('"').append(" width=").append('"').append(width)
                .append('"').append(" height=").append('"').append(height)
                .append('"').append(" fill=").append('"').append("rgb(")
                .append(r + ", ").append(g + ", ")
                .append(b + ")").append('"').append(" visibility=")
                .append('"').append("visible").append('"').append(">").append('\n');
      case "OVAL":
        str.append("<").append("ellipse").append(" id=").append('"').append(id)
                .append('"').append(" cx=").append('"').append(x)
                .append('"').append(" cy=").append('"').append(y)
                .append('"').append(" rx=").append('"').append(width / 2)
                .append('"').append(" ry=").append('"').append(height / 2)
                .append('"').append(" fill=").append('"').append("rgb(")
                .append(r + ", ").append(g + ", ")
                .append(b + ")").append('"').append(" visibility=")
                .append('"').append("visible").append('"').append(">").append('\n');
    }
    return str.toString();
  }

  private String SVGMove(int startTime, int endTime, String startVal, String endVal,
                         String attribute) {
    StringBuilder str = new StringBuilder();
      str.append("  <animate attributeType=" + '"' + "xml" + '"' + " begin=" + '"' + "base.begin+" +
              startTime + "ms" + '"' + " dur=" + '"' + ((endTime - startTime) * 1000) + "ms" + '"'
              + " attributeName=" + '"' + attribute + '"' + " from=" + '"' + startVal + '"' +
              " to=" + '"' + endVal + '"' + " fill=" + '"' + "freeze" + '"' + "></animate>"
              + '\n');
    return str.toString();
  }
}
