package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.AnimatorModel;

public class AnimatorGraphicsView extends JFrame implements IView{
  private AnimatorModel model;
  private AnimationPanel panel;

  public AnimatorGraphicsView(AnimatorModel model) {
    this.model = model;
    this.setTitle("Your Animation");
    this.setSize(model.getSceneWidth(), model.getSceneHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void renderTick() {
    ActionListener renderTick = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      }
    };
  }
}
