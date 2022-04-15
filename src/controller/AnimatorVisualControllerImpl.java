package controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;

import model.AnimatorModel;
import view.AnimatorGraphicsView;

/**
 * This class represents an implementation of the animator visual controller interface. This class
 * only deals with the AnimatorGraphicsView class as it is deigned for only dealing with a basic
 * display of the animation. Merely displays the animation from start to end, with no interactive
 * features.
 */
public class AnimatorVisualControllerImpl implements AnimatorVisualController {
  private final AnimatorModel model;
  private final AnimatorGraphicsView view;
  private final Timer timer;
  private final int speed;

  /**
   * Represents the controller for this class which initializes the timer as well as other useful
   * values for the class.
   *
   * @param model an instance of the model which wants to be controlled.
   * @param view  an instance of the view that wants to display the model. Can only be the graphical
   *              view as that is what is designed for this controller.
   * @param speed the speed at which this animation will be displayed.
   */
  public AnimatorVisualControllerImpl(AnimatorModel model,
                                      AnimatorGraphicsView view, int speed) {
    this.model = model;
    this.view = view;
    this.speed = speed;
    AtomicInteger currentTick = new AtomicInteger(1);
    this.timer = new Timer(1000 / speed, null);
    timer.addActionListener(e -> {
      if (currentTick.get() == model.getShapesPerTick().size() - 1) {
        timer.stop();
      } else {
        view.renderAnimation();
        currentTick.getAndIncrement();
      }
    });
  }

  @Override
  public void play() {
    timer.start();
  }

}
