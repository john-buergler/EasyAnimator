import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import io.AnimationBuilder;
import io.AnimationFileReader;
import io.TweenModelBuilder;
import model.AnimatorModel;
import model.EasyAnimatorModel;
import view.AnimatorGraphicsView;
import view.IView;

public class ProgrammaticAnimation {
  private final StringBuilder str;
  private final int timeBetweenSquares;
  private final int moveTime;
  private final int countMoves;

  public ProgrammaticAnimation() {
    str = new StringBuilder();
    timeBetweenSquares = 5;
    moveTime = 20;
    countMoves = 6;
  }

  private void writeAnimation(int numSquares) throws IOException {
    int totalTime = (moveTime * 6) + (timeBetweenSquares * numSquares);
    if (numSquares < 5) {
      throw new IllegalArgumentException("At least 5 shapes!");
    }

    str.append("canvas 800 800\n");
    for (int i = 1; i <= numSquares; i++) {
      str.append("rectangle name S" + i + " min-x 900 min-y 400 width 30 height 30 color 0 0 0 " +
              "from 1 to " + totalTime + "\n");
    }

    for (int i = 1; i <= numSquares; i++) {
      int time = (timeBetweenSquares * i);
      for (int j = 1; j <= countMoves; j++) {
        int xStart;
        int yStart;
        int xEnd;
        int yEnd;
        switch (j) {
          case 1:
            xStart = 900;
            yStart = 400;
            xEnd = 600;
            yEnd = 400;
            break;
          case 2:
            xStart = 600;
            yStart = 400;
            xEnd = 400;
            yEnd = 200;
            break;
          case 3:
            xStart = 400;
            yStart = 200;
            xEnd = 200;
            yEnd = 400;
            break;
          case 4:
            xStart = 200;
            yStart = 400;
            xEnd = 400;
            yEnd = 600;
            break;
          case 5:
            xStart = 400;
            yStart = 600;
            xEnd = 600;
            yEnd = 400;
            break;
          case 6:
            xStart = 600;
            yStart = 400;
            xEnd = 900;
            yEnd = 400;
            break;
          default:
            xStart = 0;
            yStart = 0;
            xEnd = 0;
            yEnd = 0;
            break;
        }
        str.append("move name S" + i + " moveto " + xStart + " " + yStart + " "
                + xEnd + " " + yEnd + " from " + time + " to " +
                (time + moveTime) + "\n");
        time = time + moveTime;
      }
    }

    File file = new File("Diamond.txt");
    file.createNewFile();
    FileWriter fw = new FileWriter(file);
    fw.write(str.toString());
    fw.close();
  }

  public static void main(String[] args) throws IOException {
    int numShapes = Integer.parseInt(args[0]);
    ProgrammaticAnimation animation = new ProgrammaticAnimation();
    try {
      animation.writeAnimation(numShapes);
      AnimatorModel m = new EasyAnimatorModel();
      TweenModelBuilder<AnimatorModel> modelBuilder = new AnimationBuilder(m);
      AnimationFileReader reader = new AnimationFileReader();
      reader.readFile("Diamond.txt", modelBuilder);
      AnimatorModel model = modelBuilder.build();
      IView view = new AnimatorGraphicsView(model, 100);
      view.renderAnimation();
    } catch (IllegalArgumentException iae) {
      System.out.println(iae.getMessage());
    }
  }

}
