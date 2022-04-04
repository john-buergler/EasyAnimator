package view;

import java.awt.*;

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
    panel.setPreferredSize(new Dimension(model.getSceneWidth(), model.getSceneHeight()));
    JScrollPane scroll = new JScrollPane(panel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.add(scroll);
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
