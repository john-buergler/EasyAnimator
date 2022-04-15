package controller;

import javax.swing.Timer;

import model.AnimatorModel;
import view.InteractiveAnimatorView;

/**
 * This class represents an implementation of the animator interactive controller interface. This
 * class only works with the InteractiveAnimatorView class as it is only designed for having
 * interactive features. This controller adds itself as a listener to the interactive view,
 * this is so the controller can know that buttons or keys or mouse clicks are being pressed
 * and will react accordingly.
 */
public class AnimatorControllerImpl implements AnimatorInteractiveController {
  private final AnimatorModel model;
  private final InteractiveAnimatorView view;
  private final Timer timer;
  private int speed;
  private boolean canLoop;
  private int tickCount;

  /**
   * Constructor for class which initializes and takes care of many values.
   *
   * @param model an instance of the model that wants to be controlled.
   * @param view  an instance of the view for how the model wants to be displayed. Can only be an
   *              interactive view.
   * @param speed the speed at which this animation wants to be displayed initially.
   */
  public AnimatorControllerImpl(AnimatorModel model, InteractiveAnimatorView view, int speed) {
    this.tickCount = 1;
    this.speed = speed;
    this.model = model;
    this.view = view;
    this.canLoop = false;
    this.timer = new Timer(1000 / speed, null);
    this.timer.addActionListener(e -> {
      view.renderAnimation();
      tickCount += 1;
    });
    view.addEventListener(this);
  }

  @Override
  public void changeSpeed(int speed) {
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed of the animation has to be positive.");
    }
    this.speed = speed;
    timer.setDelay(1000 / this.speed);
  }

  @Override
  public void toggleLoopback() {
    this.canLoop = !canLoop;
  }

  @Override
  public void pause() {
    timer.stop();
  }

  @Override
  public void play() {
    timer.start();
  }

  @Override
  public void restart() {
    timer.restart();
  }

  @Override
  public void loop() {
    if (canLoop) {
      play();
    }
  }
}
