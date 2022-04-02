package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.AnimatorModel;

public class AnimatorGraphicsView extends JFrame implements IView {
  private final AnimatorModel model;
  private final AnimationPanel panel;
  private final int speed;


  public AnimatorGraphicsView(AnimatorModel model, int speed) {
    this.model = model;
    this.speed = speed;
    this.setTitle("Your Animation");
    this.setSize(model.getSceneWidth(), model.getSceneHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel = new AnimationPanel(model);
    this.add(panel);
  }

  @Override
  public void renderAnimation() {
    this.setVisible(true);
    Timer timer = new Timer(1000 / speed, null);
    timer.addActionListener(e -> {
      if (panel.getCurrentTick() == model.getShapesPerTick().size()) {
        timer.stop();
      }
      else {
        panel.repaint();
      }
    });
    timer.start();
  }
}
