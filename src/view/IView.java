package view;

import java.io.IOException;

public interface IView {

  /**
   * Renders an animation. Could be rendered as text, SVG, or as a Jframe graphical animation.
   */
  public void renderAnimation() throws IOException;

}
