package controller;

/**
 * This interface represents the functionality of a controller for a visual view of an animation.
 * This visual view doesn't include any interactive features. This controller is mainly used
 * to control the timer of the animation. Includes a singular method to start the animation.
 */
public interface AnimatorVisualController {

  /**
   * This method starts the animation and therefore the timer that runs the animation.
   */
  public void play();
}
