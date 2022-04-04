package view;

import java.io.IOException;

public interface IView {

  /**
   * Renders an animation. Could be rendered as text, SVG, or as a JFrame graphical animation.
   * When prompting the main method, user can declare the output type that they prefer. If no
   * file exists with the given file name, the method will create a file in the given format that
   * contains the animation information.
   */
  public void renderAnimation() throws IOException;

}
