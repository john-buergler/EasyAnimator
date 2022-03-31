package io;

import java.io.IOException;

import model.AnimatorModel;
import view.AnimatorGraphicsView;
import view.AnimatorTextView;
import view.IView;

/**
 * Factory class for animator views. Either visual, svg, or text.
 */
public class ViewsFactory {

  /**
   * Method returning a view, specified by the given view string description.
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
        return null;
      case "text":
        return new AnimatorTextView(model, fileName, speed);
      default:
        return null;
    }
  }

}
