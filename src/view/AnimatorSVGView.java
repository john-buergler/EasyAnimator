package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import model.AnimatorModel;
import model.Shape;

/**
 * Renders the animation in an SVG view format. Writes a new file or overwrites an old file to
 * produce the animation in SVG.
 */
public class AnimatorSVGView implements IView {
  private final AnimatorModel model;
  private final PrintStream outputSystem;
  private final int speed;
  private final FileWriter outputFile;

  /**
   * The constructor for an AnimatorSVGView, which produces the given model as an animation in SVG
   * format.
   * @param m The AnimatorModel that contains the animation details.
   * @param fileName The name of the file that this SVG formatted text is being written to.
   * @param speed The speed of the animation in ticks per second.
   * @throws IOException In the event that createNewFile fails.
   */
  public AnimatorSVGView(AnimatorModel m, String fileName, int speed)throws IOException {
    if (m == null || fileName == null || fileName.equals("") || speed <= 0) {
      throw new IllegalArgumentException("Invalid model, filename, or speed.");
    }
    this.model = m;
    this.speed = speed;
    if (fileName.equals("System.out")) {
      outputSystem = System.out;
      outputFile = null;
    }
    else {
      File file = new File(fileName);
      file.createNewFile();
      outputFile = new FileWriter(file);
      outputSystem = null;
    }
  }

  @Override
  public void renderAnimation() throws IOException {
    StringBuilder str = new StringBuilder();
    initSVG(str);
    for (Shape shape : model.getShapes()) {
      ArrayList ids = new ArrayList<String>();
      ArrayList height = new ArrayList<Integer>();
      ArrayList width = new ArrayList<Integer>();
      ArrayList x = new ArrayList<Integer>();
      ArrayList y = new ArrayList<Integer>();
      int r;
      int g;
      int b;
      int start;
      int end;
      String shapeDef = shape.getLog().get(0);
      Scanner scan = new Scanner(shapeDef);
      scan.next();
      String id = scan.next();
      String type = scan.next();
      if (!type.equals("PLUS")) {
        ids.add(id);
        height.add(scan.nextInt());
        width.add(scan.nextInt());
        r = scan.nextInt();
        g = scan.nextInt();
        b = scan.nextInt();
        x.add(scan.nextInt());
        y.add(scan.nextInt());
        start = scan.nextInt();
        end = scan.nextInt();
      }
      else {
        ids.add(id);
        ids.add(id + "(2)");
        int h = scan.nextInt();
        int w = scan.nextInt();
        height.add(h / 2);
        height.add(h);
        width.add(w);
        width.add(w / 2);
        r = scan.nextInt();
        g = scan.nextInt();
        b = scan.nextInt();
        int x1 = scan.nextInt();
        int y1 = scan.nextInt();
        x.add(x1);
        x.add(x1 + (h / 4));
        y.add(y1 + (w / 4));
        y.add(y1);
        start = scan.nextInt();
        end = scan.nextInt();
      }
      for (int k = 0; k < height.size(); k++) {
        str.append(toSVG(type, (Integer) x.get(k), (Integer) y.get(k), (Integer) width.get(k),
                (Integer) height.get(k), r, g, b,
                (String) ids.get(k)));
        str.append("  <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"" +
                ((start * 1000) / speed) + "ms\" attributeName=\"visibility\" from=\"hidden\" to=\"" +
                "visible\"></animate>\n");
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
            int hs = scan2.nextInt();
            int ws = scan2.nextInt();
            int rs = scan2.nextInt();
            int gs = scan2.nextInt();
            int bs = scan2.nextInt();
            int endTime = scan2.nextInt();
            int xe = scan2.nextInt();
            int ye = scan2.nextInt();
            if (k == 1 && type.equals("PLUS")) {
              xs = xs + ((Integer) height.get(k) / 4);
              xe = xe + ((Integer) height.get(k) / 4);
            }
            if (k == 0 && type.equals("PLUS")) {
              ys = ys + ((Integer) width.get(k) / 4);
              ye = ye + ((Integer) width.get(k) / 4);
            }
            int he = scan2.nextInt();
            int we = scan2.nextInt();
            if (k == 1 && type.equals("PLUS")) {
              ws = ws / 2;
            }
            if (k == 0 && type.equals("PLUS")) {
              he = he / 2;
            }
            int re = scan2.nextInt();
            int ge = scan2.nextInt();
            int be = scan2.nextInt();
            assignAttributes(motionType, attributesChanging, valuesStarting, valuesEnding, startTime,
                    xs, ys, hs, ws, rs, gs, bs, endTime, xe, ye, he, we, re, ge, be, type);
            for (int j = 0; j < attributesChanging.size(); j++) {
              str.append(svgmove(startTime, endTime, valuesStarting.get(j), valuesEnding.get(j),
                      attributesChanging.get(j)));
            }
          }
        }
        if (type.equals("RECTANGLE") || type.equals("PLUS")) {
          str.append("</rect>\n");
        } else {
          str.append("</ellipse>\n");
        }
      }
    }
    str.append("</svg>");

    if (outputSystem == null) {
      outputFile.write(str.toString());
      outputFile.close();
    }
    else {
      outputSystem.append(str.toString());
    }
  }

  private void assignAttributes(String motionType, ArrayList<String> attributesChanging,
                                ArrayList<String> valuesStarting, ArrayList<String> valuesEnding,
                                int startTime, int xs, int ys, int hs, int ws, int rs, int gs,
                                int bs, int endTime, int xe, int ye, int he, int we, int re, int ge,
                                int be, String type) {
    switch (motionType) {
      case "move":
        if (type.equals("RECTANGLE") ||
                type.equals("PLUS")) {
          attributesChanging.add("x");
          attributesChanging.add("y");
        }
        else {
          attributesChanging.add("cx");
          attributesChanging.add("cy");
        }
        valuesStarting.add(String.valueOf(xs));
        valuesStarting.add(String.valueOf(ys));
        valuesEnding.add(String.valueOf(xe));
        valuesEnding.add(String.valueOf(ye));
        break;
      case "color":
        attributesChanging.add("fill");
        valuesStarting.add("rgb(" + String.valueOf(rs) + ", " + String.valueOf(gs) + ", " +
                String.valueOf(bs) + ")");
        valuesEnding.add("rgb(" + String.valueOf(re) + ", " + String.valueOf(ge) + ", " +
                String.valueOf(be) + ")");
        break;
      case "size":
        attributesChanging.add("height");
        attributesChanging.add("width");
        valuesStarting.add(String.valueOf(hs));
        valuesStarting.add(String.valueOf(ws));
        valuesEnding.add(String.valueOf(he));
        valuesEnding.add(String.valueOf(we));
        break;
      default:
        break;
    }
  }

  private void initSVG(StringBuilder str) {
    str.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + 800 + "\"" +
            " height=\"" + 800 + "\" version=\"1.1\">\n");
    str.append("<rect>\n" + " <animate id=\"base\" begin=\"0;base.end\" dur=\"" +
            ((model.getShapesPerTick().size() * 1000) / this.speed) + "ms" + "\"" +
            " attributeName=\"visibility\" from=\"hide\" to =\"hide\"></animate>\n</rect>\n");
  }

  private String toSVG(String type, int x, int y, int width, int height, int r, int g, int b,
                       String id) {
    StringBuilder str = new StringBuilder();
    switch (type) {
      case "RECTANGLE":
      case "PLUS":
        str.append("<").append("rect").append(" id=").append('"').append(id)
                .append('"').append(" x=").append('"').append(x)
                .append('"').append(" y=").append('"').append(y)
                .append('"').append(" width=").append('"').append(width)
                .append('"').append(" height=").append('"').append(height)
                .append('"').append(" fill=").append('"').append("rgb(")
                .append(r + ", ").append(g + ", ")
                .append(b + ")").append('"').append(" visibility=")
                .append('"').append("visible").append('"').append(">").append('\n');
        break;
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
        break;
      default:
        break;
    }
    return str.toString();
  }

  private String svgmove(int startTime, int endTime, String startVal, String endVal,
                         String attribute) {
    StringBuilder str = new StringBuilder();
    str.append("  <animate attributeType=" + '"' + "xml" + '"' + " begin=" + '"' + "base.begin+" +
            ((1000 * startTime) / speed) + "ms" + '"' + " dur=" + '"' +
            (((endTime - startTime) * 1000) / speed)
            + "ms" + '"' + " attributeName=" + '"' + attribute + '"' + " from=" + '"' +
            startVal + '"' +
            " to=" + '"' + endVal + '"' + " fill=" + '"' + "freeze" + '"' + "></animate>"
            + '\n');
    return str.toString();
  }
}
