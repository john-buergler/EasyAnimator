package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.AnimatorModel;
import model.Shape;

public class AnimationPanel extends JPanel {
  private final AnimatorModel model;
  private List<Shape> shapes;
  private int currentTick;

  public AnimationPanel(AnimatorModel m) {
    this.currentTick = 0;
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
        switch (shape.getShapeType()) {
          case RECTANGLE:
            g2d.fillRect(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                    shape.getWidth(), shape.getHeight());
            break;
          case OVAL:
            g2d.fillOval(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                    shape.getWidth(), shape.getHeight());
            break;
        }
      }
      repaint();
      currentTick += 1;
  }

  int getCurrentTick() {
   return currentTick;
  }


}
