package view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import model.AnimatorModel;

/**
 * A view implementation for animations to display the animation in text format.
 */
public class AnimatorTextView implements IView {
  private final AnimatorModel model;
  private final int speed;
  private final PrintStream outputSystem;
  private final FileWriter outputFile;

  /**
   * Constructor for class, initializing the following fields.
   *
   * @param m the model that is to be displayed.
   * @param fileName the file to write to, default is System.out.
   * @param speed the speed of the animation, used to calculate the real time of the motions.
   * @throws IOException
   */
  public AnimatorTextView(AnimatorModel m, String fileName, int speed) throws IOException {
    this.model = m;
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
    this.speed = speed;
  }

  @Override
  public void renderAnimation() throws IOException {
    StringBuilder str = new StringBuilder();
    int canvasHeight = model.getSceneHeight();
    int canvasWidth = model.getSceneWidth();
    str.append("canvas " + canvasHeight + " " + canvasWidth + "\n");
    for (int t = 0; t < model.getShapes().size(); t++) {
      List<String> log = model.getShapes().get(t).getLog();
      for (int j = 0; j < log.size(); j++) {
        String logStr = log.get(j);
        if (j == 0) {
          str.append(logStr + "\n");
        }
        else {
          Scanner scan = new Scanner(logStr);
          String str1 = scan.next();
          str.append(str1 + " ");
          String str2 = scan.next();
          str.append(str2 + " ");
          String str3 = scan.next();
          str.append(str3 + " ");
          int num = scan.nextInt();
          double time = (double) num / speed;
          str.append(time + " ");
          int count = 5;
          while (count < 12) {
            str.append(scan.next() + " ");
            count ++;
          }
          int num2 = scan.nextInt();
          double time2 = (double) num2 / speed;
          str.append(time2 + " ");
          while (scan.hasNext()) {
            str.append(scan.next() + " ");
          }
          str.append("\n");
        }
      }

    }
    if (outputSystem == null) {
      outputFile.write(str.toString());
      outputFile.close();
    }
    else {
      outputSystem.append(str.toString());
    }
  }

}
