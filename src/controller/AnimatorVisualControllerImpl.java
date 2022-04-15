package controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;

import model.AnimatorModel;
import view.AnimatorGraphicsView;

public class AnimatorVisualControllerImpl implements AnimatorVisualController {
  private final AnimatorModel model;
  private final AnimatorGraphicsView view;
  private final Timer timer;
  private final int speed;

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
      }
      else {
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
