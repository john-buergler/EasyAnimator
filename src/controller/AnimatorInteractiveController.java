package controller;

/**
 * This interface represents a controller that controls an interactive animator.
 * This interactive animator has features including changing speed, toggling if
 * the animation can loop, pausing, playing, restarting, and looping the animation. These are just
 * normal features that would go along with watching any movie or clip.
 */
public interface AnimatorInteractiveController {

  /**
   * Sets the speed of the animation.
   *
   * @param speed desired speed for the animation.
   * @throws IllegalArgumentException if speed is not positive.
   */
  public void changeSpeed(int speed);

  /**
   * Changes the loopback condition of the animation. If the loopback condition is true
   * the animation will restart when it ends. If false it just ends.
   */
  public void toggleLoopback();

  /**
   * Pauses the animation. Displays the current state of the animation when called.
   */
  public void pause();

  /**
   * Starts the animation. It can be started at any state of the animation. Starts from the current
   * state until the end.
   */
  public void play();

  /**
   * Sets the state of the animation back to its original state, and then plays from there.
   */
  public void restart();

  /**
   * If the condition for loopback is true this method restarts the animation. If false, it does
   * nothing.
   */
  public void loop();

}
