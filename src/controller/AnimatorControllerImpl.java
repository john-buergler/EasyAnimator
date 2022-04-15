package controller;

import javax.swing.Timer;

import model.AnimatorModel;
import view.InteractiveAnimatorView;

public class AnimatorControllerImpl implements AnimatorInteractiveController {
  private final AnimatorModel model;
  private final InteractiveAnimatorView view;
  private final Timer timer;
  private int speed;
  private boolean canLoop;
  private int tickCount;

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
