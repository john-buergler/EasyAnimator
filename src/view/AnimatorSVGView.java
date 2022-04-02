package view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import model.AnimatorModel;
import model.Shape;

public class AnimatorSVGView implements IView {
  private final AnimatorModel model;
  private final String fileName;
  private final PrintStream outputSystem;
  private final FileWriter outputFile;

  public AnimatorSVGView(AnimatorModel m, String fileName) throws IOException {
    this.model = m;
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
                    model.getShapesPerTick() + // / speed
            "\"" +
            "attributeName=\"visibility\" from=\"hide\" to =\"hide\"></animate>\n</rect>");
    model.getShapesPerTick().size();
    for (Shape shape : model.getShapes()) {
      str.append(shape.toSVG());
    }
  }
}
