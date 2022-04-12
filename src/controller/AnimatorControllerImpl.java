package controller;

import java.io.IOException;
import javax.swing.Timer;

import model.AnimatorModel;
import view.IEventListeners;
import view.IView;
import view.InteractiveAnimatorView;

public class AnimatorControllerImpl implements IEventListeners {
  private final AnimatorModel model;
  private final InteractiveAnimatorView view;
  private final Timer timer;
  private final int speed;

  public AnimatorControllerImpl(AnimatorModel model, InteractiveAnimatorView view) {
    this.speed = 1;
    this.model = model;
    this.view = view;
    this.timer = new Timer(1000 / speed, null);
    view.addEventListener(this);
  }


  @Override
  public void changeSpeed(int speed) {
    timer.setDelay(1000 / speed);
  }

  @Override
  public void toggleLoopback() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void play() {
    timer.start();
    view.renderAnimation();
  }

  @Override
  public void restart() {

  }

}
