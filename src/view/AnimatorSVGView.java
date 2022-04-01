package view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import model.AnimatorModel;
import model.Shape;
import model.ShapeType;

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
    for (Shape shape : model.getShapes()) {
      str.append(shape.toSVG());
    }
  }
}
