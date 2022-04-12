package controller;

import java.io.IOException;
import javax.swing.Timer;

import model.AnimatorModel;
import view.IView;
import view.InteractiveAnimatorView;

public class AnimatorControllerImpl implements AnimatorController {
  private final AnimatorModel model;
  private final IView view;
  private final Timer timer;
  private final int speed;

  public AnimatorControllerImpl(AnimatorModel model) {
    this.speed = 1;
    this.model = model;
    this.view = new InteractiveAnimatorView(model, speed);
    this.timer = new Timer(1000 / speed, null);
  }


  @Override
  public void changeSpeed(int speed) {

  }

  @Override
  public void toggleLoopback() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void play() throws IOException {
    view.renderAnimation();
  }

  @Override
  public void restart() {

  }

}
