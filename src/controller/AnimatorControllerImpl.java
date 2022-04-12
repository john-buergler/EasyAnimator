package controller;

import javax.swing.Timer;

import model.AnimatorModel;
import view.IEventListeners;
import view.InteractiveAnimatorView;

public class AnimatorControllerImpl implements IEventListeners {
  private final AnimatorModel model;
  private final InteractiveAnimatorView view;
  private final Timer timer;
  private int speed;
  private boolean canLoop;

  public AnimatorControllerImpl(AnimatorModel model, InteractiveAnimatorView view) {
    this.speed = 1;
    this.model = model;
    this.view = view;
    this.canLoop = false;
    this.timer = new Timer(1000 / speed, null);
    this.timer.addActionListener(e -> {
      view.renderAnimation();
    });
    view.addEventListener(this);
  }

  @Override
  public void changeSpeed(int speed) {
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

}
