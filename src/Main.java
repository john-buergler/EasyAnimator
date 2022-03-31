import java.io.FileNotFoundException;

import io.AnimationBuilder;
import io.AnimationFileReader;
import io.TweenModelBuilder;
import model.AnimatorModel;
import model.EasyAnimatorModel;

/**
 * Run an animation on the console.
 */
public class Main {

  /**
   * Run an animation on the console.
   *
   * @param args the command line arguments that specify input file, output file, view, and speed.
   */
  public static void main(String[] args) throws FileNotFoundException {
    String inputFile = "";
    String outputFile = "System.out";
    String view = "";
    String speed = "1";
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in":
          inputFile = args[i+1];
          break;
        case "-out":
          outputFile = args[i+1];
          break;
        case "-view":
          view = args[i+1];
          break;
        case "-speed":
          speed = args[i+1];
          break;
        default:
          break;
      }
    }
    TweenModelBuilder<AnimatorModel> modelBuilder = new AnimationBuilder();
    AnimationFileReader reader = new AnimationFileReader();
    reader.readFile(inputFile, modelBuilder);
    AnimatorModel model = modelBuilder.build();

  }
}
