package view;

import model.AnimatorModel;

public class InteractiveAnimator extends AnimatorGraphicsView implements InteractiveGraphicsView {
  /**
   * The constructor for the AnimatorGraphicsView. Takes in a model and a speed and then creates
   * an animation from the information contained. Check AnimatorModel for more information on
   * the storage of the animation.
   *
   * @param model AnimatorModel that contains user-generated animation.
   * @param speed The speed of the animation being played in ticks per second.
   */
  public InteractiveAnimator(AnimatorModel model, int speed) {
    super(model, speed);
  }


}
