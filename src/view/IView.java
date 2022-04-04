package view;

import java.io.IOException;

public interface IView {

  /**
   * Renders an animation. Could be rendered as text, SVG, or as a JFrame graphical animation.
   * When prompting the main method, user can declare the output type that they prefer. If no
   * file exists with the given file name, the method will create a file in the given format that
   * contains the animation information.
   * </p>
   * SVG view is outputted in standard SVG format. Creates a rect or an ellipse and animates for
   * a given time.
   * </p>
   * Text view is outputted in this format for motion : "motion startTime xPosStart yPosStart width
   * height red green blue endTime xPosEnd yPosEnd widthEnd heightEnd redEnd greenEnd blueEnd".
   * and this format for adding a shape : "shape ShapeID ShapeType height width red green blue xPos
   * yPos startOfLife endOfLife".
   */
  public void renderAnimation() throws IOException;

}
