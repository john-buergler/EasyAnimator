package view;

import model.AnimatorModel;

public class InteractiveAnimatorView extends AnimatorGraphicsView {
  /**
   * The constructor for the InteractiveAnimator. Takes in a model and a speed and then creates
   * an animation from the information contained. the same as the original graphics view, plus
   * other functionality including speed up, slow down, toggle loopback, pause, play, and rewind.
   *
   * @param model AnimatorModel that contains user-generated animation.
   * @param speed The speed of the animation being played in ticks per second.
   */
  public InteractiveAnimatorView(AnimatorModel model, int speed) {
    super(model, speed);

  }

  @Override
  public void renderAnimation() {

  }
}
