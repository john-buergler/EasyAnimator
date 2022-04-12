package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.AnimatorModel;

public class InteractiveAnimatorView extends AnimatorGraphicsView implements ActionListener,
        IntercativeView {
  private final JButton playButton;
  private final JPanel buttonPanel;
  private final List<IEventListeners> listenersList;
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
    this.listenersList = new ArrayList<>();
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
    listenersList.add(listener);
  }

  private void changeSpeed(int speed) {

  }

  private void toggleLoopback() {

  }

  private void pause() {

  }

  private void play() {
    for (IEventListeners eventListener : listenersList) {
      eventListener.play();
    }
  }

  private void restart() {

  }
}
