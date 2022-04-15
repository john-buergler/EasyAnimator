package view;

import controller.AnimatorInteractiveController;

/**
 * AnimatorGraphicsView decorator that allows the user to interact with the animation. This
 * interface has a singular method which serves to add controllers as listeners. This allows for
 * the animation to be played interactively as it sends action that have occurred to the given
 * listeners.
 */
public interface InteractiveView {

  /**
   * Adds a listener so that actions that are executed interactively can be told to all the
   * listeners, and they can act accordingly. Connects the view and the controller of interactive
   * animations.
   *
   * @param listener the desired controller that wants to be added as one of the listeners.
   */
  public void addEventListener(AnimatorInteractiveController listener);

}
