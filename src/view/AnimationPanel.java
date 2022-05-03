package view;

import java.awt.*;
import java.awt.geom.Ellipse2D;
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
  private String renderType;
  private boolean discreteMode;

  /**
   * The constructor for the AnimationPanel, taking in a model to access the Shapes to render.
   *
   * @param m The AnimatorModel being rendered.
   */
  public AnimationPanel(AnimatorModel m) {
    this.currentTick = 1;
    this.model = m;
    this.shapes = new ArrayList<>();
    this.discreteMode = false;
    this.renderType = "fill";
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    if (discreteMode) {
      ArrayList discreteTicks = new ArrayList<List<Shape>>();
      for (List<Shape> tick : model.getShapesPerTick()) {
        for (Shape shape : tick) {
          if (shape.getStartingMotion()) {
            discreteTicks.add(tick);
          }
        }
      }
      if (currentTick == discreteTicks.size()) {
        currentTick = 1;
        this.repaint();
      }
      this.shapes = (List<Shape>) discreteTicks.get(currentTick);
      paintTick(g2d);
      currentTick++;
    }
    else {
      this.shapes = model.getShapesPerTick().get(currentTick);
      paintTick(g2d);
      currentTick += 1;
    }
  }

  private void paintTick(Graphics2D g2d) {
    for (Shape shape : shapes) {
      g2d.setColor(shape.getColor());
      if (renderType.equals("fill") || renderType.equals(" ")) {
        if (shape.getShapeType() == ShapeType.RECTANGLE) {
          g2d.fillRect(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                  shape.getWidth(), shape.getHeight());
        }
        if (shape.getShapeType() == ShapeType.PLUS) {
          g2d.fillRect(shape.getShapePosn().getX() + shape.getWidth() / 4,
                  shape.getShapePosn().getY(),
                  shape.getWidth() / 2, shape.getHeight());
          g2d.fillRect(shape.getShapePosn().getX(),
                  shape.getShapePosn().getY() + shape.getHeight() / 4,
                  shape.getWidth(), shape.getHeight() / 2);
        }
        else {
          g2d.fillOval(shape.getShapePosn().getX(), shape.getShapePosn().getY(),
                  shape.getWidth(), shape.getHeight());
        }
      }
      else if (renderType.equals("outline")) {
        if (shape.getShapeType() == ShapeType.RECTANGLE) {
          int x = shape.getShapePosn().getX();
          int y = shape.getShapePosn().getY();
          Rectangle rect = new Rectangle(x, y, shape.getWidth(), shape.getHeight());
          g2d.draw(rect);
        }
        else if (shape.getShapeType() == ShapeType.PLUS) {
          int initialX = shape.getShapePosn().getX();
          int initialY = shape.getShapePosn().getY();
          double firstX = initialX - (0.25 * shape.getWidth());
          double firstY = initialY - (0.25 * shape.getHeight());
          double secondX = firstX - (0.25 * shape.getWidth());
          g2d.drawLine((int) firstX, (int) firstY, (int) secondX, (int) firstY);
          double secondY = firstY + (0.5 * shape.getHeight());
          g2d.drawLine((int) secondX, (int) firstY, (int) secondX, (int) secondY);
          double thirdX = secondX + (0.25 * shape.getWidth());
          g2d.drawLine((int) secondX, (int) secondY, (int) thirdX, (int) secondY);
          double thirdY = secondY + (0.25 * shape.getHeight());
          g2d.drawLine((int) thirdX, (int) secondY, (int) thirdX, (int) thirdY);
          double fourthX = thirdX + (0.5 * shape.getWidth());
          g2d.drawLine((int) thirdX, (int) thirdY, (int) fourthX, (int) thirdY);
          double fourthY = thirdY - (0.25 * shape.getHeight());
          g2d.drawLine((int) fourthX, (int) thirdY, (int) fourthX, (int) fourthY);
          double fifthX = fourthX + (0.25 * shape.getWidth());
          g2d.drawLine((int) fourthX, (int) fourthY, (int) fifthX, (int) fourthY);
          double fifthY = fourthY - (0.5 * shape.getHeight());
          g2d.drawLine((int) fifthX, (int) fourthY, (int) fifthX, (int) fifthY);
          double sixthX = fifthX - (0.25 * shape.getWidth());
          g2d.drawLine((int) fifthX, (int) fifthY, (int) sixthX, (int) fifthY);
          double sixthY = fifthY - (0.25 * shape.getHeight());
          g2d.drawLine((int) sixthX, (int) fifthY, (int) sixthX, (int) sixthY);
          double seventhX = sixthX - (0.5 * shape.getWidth());
          g2d.drawLine((int) sixthX, (int) sixthY, (int) seventhX, (int) sixthY);
          double seventhY = sixthY + (0.25 * shape.getHeight());
          g2d.drawLine((int) seventhX, (int) sixthY, (int) seventhX, (int) sixthY);
        }
        else {
          int x = shape.getShapePosn().getX();
          int y = shape.getShapePosn().getY();
          Ellipse2D e = new Ellipse2D.Double(x, y, shape.getWidth(), shape.getHeight());
          g2d.draw(e);
        }
      }
    }
  }

  int getCurrentTick() {
    return currentTick;
  }

  void restartTick() {
    currentTick = 1;
  }

  void toggleDiscrete() {
    this.currentTick = 1;
    this.discreteMode = !discreteMode;
  }

  String getRenderType() {
    return this.renderType;
  }

  void setRenderType(String str) {
    this.renderType = str;
  }

}
