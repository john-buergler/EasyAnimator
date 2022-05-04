package controller;

import java.util.List;

import javax.swing.Timer;

import model.AnimatorModel;
import model.Tempo;
import view.InteractiveAnimatorView;

/**
 * This class represents an implementation of the animator interactive controller interface. This
 * class only works with the InteractiveAnimatorView class as it is only designed for having
 * interactive features. This controller adds itself as a listener to the interactive view,
 * this is so the controller can know that buttons or keys or mouse clicks are being pressed
 * and will react accordingly.
 */
public class AnimatorInteractiveControllerImpl implements AnimatorInteractiveController {
  private final int initialSpeed;
  private final Timer timer;
  private int speed;
  private boolean canLoop;
  private int tickCount;
  private Tempo currentTempo;

  /**
   * Constructor for class which initializes and takes care of many values.
   *
   * @param model an instance of the model that wants to be controlled.
   * @param view  an instance of the view for how the model wants to be displayed. Can only be an
   *              interactive view.
   * @param speed the speed at which this animation wants to be displayed initially.
   */
  public AnimatorInteractiveControllerImpl(AnimatorModel model,
                                           InteractiveAnimatorView view, int speed) {
    this.initialSpeed = speed;
    List<Tempo> tempos = model.getTempos();
    for (Tempo t : tempos) {
      System.out.println(t);
    }
    this.tickCount = 1;
    this.speed = speed;
    this.canLoop = false;
    this.currentTempo = null;
    this.timer = new Timer(1000 / speed, null);
    this.timer.addActionListener(e -> {
      for (Tempo t : tempos) {
        if (currentTempo == null) {
          if (t.getStartTime() == tickCount) {
            currentTempo = t;
            break;
          }
        }
        else if (currentTempo.getEndTime() == tickCount) {
          currentTempo = null;
          changeSpeed(initialSpeed);
        }
      }
      if (!(currentTempo == null)) {
        changeSpeed(currentTempo.getSpeed());
      }
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
    this.currentTempo = null;
    tickCount = 1;
    timer.restart();
  }

  @Override
  public void loop() {
    if (canLoop) {
      restart();
    }
  }

}
