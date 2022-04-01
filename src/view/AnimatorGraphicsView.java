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
    class TimerListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
        //panel.updateShapes(1);
        panel.repaint();
      }
    }
    TimerListener listener = new TimerListener();
    Timer timer = new Timer(1000 / speed, listener);
    timer.start();
    if (panel.getCurrentTick() == model.getShapesPerTick().size() - 1) {
      timer.stop();
    }
  }
}
