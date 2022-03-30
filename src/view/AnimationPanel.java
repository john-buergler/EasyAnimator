package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.Shape;

public class AnimationPanel extends JPanel {
  List<Shape> shapes;

  public AnimationPanel() {
    this.shapes = new ArrayList<Shape>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    for (Shape shape : shapes) {
      g2d.setColor(shape.getColor());
      switch (shape.getShapeType()) {
        case RECTANGLE:
          g2d.fillRect(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                  shape.getWidth(), shape.getHeight());
        case OVAL:
          g2d.fillOval(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                  shape.getWidth(), shape.getHeight());
      }
    }
  }
}
