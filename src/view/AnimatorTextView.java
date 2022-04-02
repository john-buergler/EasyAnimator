package view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import model.AnimatorModel;
import model.Shape;

public class AnimatorTextView implements IView {
  private final AnimatorModel model;
  private final int speed;
  private final String fileName;
  private final PrintStream outputSystem;
  private final FileWriter outputFile;

  public AnimatorTextView(AnimatorModel m, String fileName, int speed) throws IOException {
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
    this.speed = speed;
  }

  @Override
  public void renderAnimation() throws IOException {
    StringBuilder str = new StringBuilder();
    int convasHeight = model.getSceneHeight();
    int canvasWidth = model.getSceneWidth();
    for (int t = 1; t < model.getShapesPerTick().size(); t++) {
      double time = (double) t / speed;
      str.append("At time t = ").append(time).append(":\n");
      for (int i = 0; i < model.getShapesPerTick().get(t).size(); i++) {
        Shape s = model.getShapesPerTick().get(t).get(i);
        str.append("Shape ").append(s.getShapeID()).append(" ").append(s.getShapeType())
                .append("\n");
        str.append("x=").append(s.getShapePosn().getX()).append(" y=")
                .append(s.getShapePosn().getY()).append(" w=").append(s.getWidth()).append(" h=")
                .append(s.getHeight()).append(" color=").append(s.getColor()).append("\n");
      }
    }
    if (outputSystem == null) {
      outputFile.append(str.toString());
    }
    else {
      outputSystem.append(str.toString());
    }
  }

}
