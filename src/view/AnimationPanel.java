package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.AnimatorModel;
import model.Shape;
import model.ShapeType;

/**
 * Represents a panel in the animation. Panels are built one tick at a time as the model works
 * through its ShapesPerTick. Each item in the ShapesPerTick list is a list of shapes, which
 * contains one panel in the animation. As the Animation is rendered, the panel renders the shapes
 * at a given tick.
 */
public class AnimationPanel extends JPanel {
  private final AnimatorModel model;
  private List<Shape> shapes;
  private int currentTick;

  /**
   * The constructor for the AnimationPanel, taking in a model to access the Shapes to render.
   * @param m The AnimatorModel being rendered.
   */
  public AnimationPanel(AnimatorModel m) {
    this.currentTick = 1;
    this.model = m;
    this.shapes = new ArrayList<>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    this.shapes = model.getShapesPerTick().get(currentTick);
    for (Shape shape : shapes) {
      g2d.setColor(shape.getColor());
      if (shape.getShapeType() == ShapeType.RECTANGLE) {
        g2d.fillRect(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                shape.getWidth(), shape.getHeight());
      }
      if (shape.getShapeType() == ShapeType.PLUS) {
        g2d.fillRect(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                shape.getWidth() / 2, shape.getHeight());
        g2d.fillRect(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                shape.getWidth(), shape.getHeight() / 2);
      }
      else {
        g2d.fillOval(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                shape.getWidth(), shape.getHeight());
      }
    }
    currentTick += 1;
  }

  int getCurrentTick() {
    return currentTick;
  }

  void restartTick() {
    currentTick = 1;
  }
}
