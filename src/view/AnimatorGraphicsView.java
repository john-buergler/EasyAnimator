package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.AnimatorModel;

/**
 * The view of the animation created with JFrame and JPanel graphics. Takes in a model that has a
 * pre-existing animation saved along with a speed to render the animation. This speed represents
 * the ticks per second of the animation.
 */
public class AnimatorGraphicsView extends JFrame implements IView {
  protected final AnimatorModel model;
  protected final AnimationPanel panel;
  protected final int speed;

  /**
   * The constructor for the AnimatorGraphicsView. Takes in a model and a speed and then creates
   * an animation from the information contained. Check AnimatorModel for more information on
   * the storage of the animation.
   *
   * @param model AnimatorModel that contains user-generated animation.
   * @param speed The speed of the animation being played in ticks per second.
   */
  public AnimatorGraphicsView(AnimatorModel model, int speed) {
    if (model == null || speed <= 0) {
      throw new IllegalArgumentException("Invalid model, filename, or speed.");
    }
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
    this.setVisible(true);
  }

  @Override
  public void renderAnimation() {
    panel.repaint();
  }
}
