import java.io.IOException;

import controller.AnimatorControllerImpl;
import controller.AnimatorVisualControllerImpl;
import io.AnimationBuilder;
import io.AnimationFileReader;
import io.TweenModelBuilder;
import io.ViewsFactory;
import model.AnimatorModel;
import model.EasyAnimatorModel;
import view.AnimatorGraphicsView;
import view.IView;
import view.InteractiveAnimatorView;

/**
 * Run an animation on the console.
 */
public class Main {

  /**
   * Run an animation on the console.
   *
   * @param args the command line arguments that specify input file, output file, view, and speed.
   */
  public static void main(String[] args) throws IOException {
    String inputFile = "";
    String outputFile = "System.out";
    String view = "";
    String speed = "1";
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in":
          inputFile = args[i + 1];
          break;
        case "-out":
          outputFile = args[i + 1];
          break;
        case "-view":
          view = args[i + 1];
          break;
        case "-speed":
          speed = args[i + 1];
          break;
        default:
          break;
      }
    }
    AnimatorModel m = new EasyAnimatorModel();
    TweenModelBuilder<AnimatorModel> modelBuilder = new AnimationBuilder(m);
    AnimationFileReader reader = new AnimationFileReader();
    reader.readFile(inputFile, modelBuilder);
    AnimatorModel model = modelBuilder.build();
    int sp = Integer.parseInt(speed);
    IView v = new ViewsFactory().createView(view, model, sp, outputFile);
    if (v instanceof InteractiveAnimatorView) {
      AnimatorControllerImpl controller = new AnimatorControllerImpl(model,
              (InteractiveAnimatorView) v, sp);
    }
    else if (v instanceof AnimatorGraphicsView){
      AnimatorVisualControllerImpl controller = new AnimatorVisualControllerImpl(model,
              (AnimatorGraphicsView) v, sp);
      controller.play();
    }
    else {
      v.renderAnimation();
    }

  }

}
