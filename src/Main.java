import java.io.FileNotFoundException;
import java.io.IOException;

import controller.AnimatorInteractiveControllerImpl;
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
    try {
      reader.readFile(inputFile, modelBuilder);
    } catch (FileNotFoundException fnf) {
      System.out.println(fnf.getMessage());
      return;
    }
    AnimatorModel model = modelBuilder.build();
    int sp = Integer.parseInt(speed);
    if (sp <= 0) {
      System.out.println("The speed of the animation has to be positive.");
      return;
    }
    IView v = new ViewsFactory().createView(view, model, sp, outputFile);
    if (v == null) {
      System.out.println("The view type specified is not valid.");
      return;
    }
    if (v instanceof InteractiveAnimatorView) {
      AnimatorInteractiveControllerImpl controller = new AnimatorInteractiveControllerImpl(model,
              (InteractiveAnimatorView) v, sp);
    } else if (v instanceof AnimatorGraphicsView) {
      AnimatorVisualControllerImpl controller = new AnimatorVisualControllerImpl(model,
              (AnimatorGraphicsView) v, sp);
      controller.play();
    } else {
      v.renderAnimation();
    }

  }

}
