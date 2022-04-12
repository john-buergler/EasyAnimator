package io;

import java.io.IOException;

import model.AnimatorModel;
import view.AnimatorGraphicsView;
import view.AnimatorSVGView;
import view.AnimatorTextView;
import view.IView;

/**
 * Factory class for animator views. Either visual, svg, or text. Done
 * by the singular method in the class.
 */
public class ViewsFactory {

  /**
   * Method returning an instance of a view, specified by the given view string description.
   *
   * @param view the view of choice.
   * @param model the model to pass to the view.
   * @param speed speed to pass to the desired view.
   * @param fileName file name if a view needs it.
   * @return an instance of an IView implementation.
   * @throws IOException in the case of a file issue.
   */
  public IView createView(String view, AnimatorModel model, int speed, String fileName)
          throws IOException {
    switch (view) {
      case "visual":
        return new AnimatorGraphicsView(model, speed);
      case "svg":
        return new AnimatorSVGView(model, fileName, speed);
      case "text":
        return new AnimatorTextView(model, fileName, speed);
      default:
        return null;
    }
  }

}
