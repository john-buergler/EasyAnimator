package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import model.AnimatorModel;

public class InteractiveAnimatorView extends AnimatorGraphicsView implements ActionListener, IntercativeView, IEventListeners {
  private final JButton playButton;
  private final JPanel buttonPanel;
  /**
   * The constructor for the InteractiveAnimator. Takes in a model and a speed and then creates
   * an animation from the information contained. the same as the original graphics view, plus
   * other functionality including speed up, slow down, toggle loopback, pause, play, and rewind.
   *
   * @param model AnimatorModel that contains user-generated animation.
   * @param speed The speed of the animation being played in ticks per second.
   */
  public InteractiveAnimatorView(AnimatorModel model, int speed) {
    super(model, speed);
    this.buttonPanel = new JPanel();
    this.playButton = new JButton("Play");
  }

  protected void initializeButtons() {
    playButton.setActionCommand("Play");
    playButton.addActionListener(this);
    buttonPanel.add(playButton);
  }


  @Override
  public void renderAnimation() {

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    switch (action) {
      case "Play":
        play();
    }
  }

  @Override
  public void addEventListener(IEventListeners listener) {

  }

  @Override
  public void changeSpeed(int speed) {

  }

  @Override
  public void toggleLoopback() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void play() {
    //subscriber pattern with eventListeners java
    //maybe called observer pattern
  }

  @Override
  public void restart() {

  }
}
