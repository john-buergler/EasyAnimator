package view;

import controller.AnimatorInteractiveController;

/**
 * AnimatorGraphicsView decorator that allows the user to interact with the animation.
 */
public interface InteractiveView {
  public void addEventListener(AnimatorInteractiveController listener);
}
