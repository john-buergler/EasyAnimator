package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.AnimatorModel;

public class InteractiveAnimatorView extends AnimatorGraphicsView implements ActionListener,
        IntercativeView {
  private final JLabel playStatus;
  private final JButton playButton;
  private final JButton pauseButton;
  private final JButton toggleLoopback;
  private final JLabel loopbackStatus;
  private int countLoopback;
  private final JButton restartButton;
  private final JTextField speedSet;
  private final JButton speedSetButton;
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
    this.playStatus = new JLabel();
    this.playButton = new JButton("Play");
    this.pauseButton = new JButton("Pause");
    this.toggleLoopback = new JButton("Toggle Loopback");
    this.countLoopback = 0;
    this.loopbackStatus = new JLabel();
    this.restartButton = new JButton("Restart");
    this.speedSet = new JTextField(String.valueOf(speed), 10);
    this.speedSetButton = new JButton("Enter");
    this.initializeButtons();
  }

  protected void initializeButtons() {
    JPanel interactivePanel = new JPanel();
    interactivePanel.setLayout(new FlowLayout());
    interactivePanel.add(buttonPanel);
    interactivePanel.setBorder(BorderFactory.createTitledBorder("Interactions"));
    interactivePanel.setLayout(new BoxLayout(interactivePanel, BoxLayout.PAGE_AXIS));
    super.panel.add(interactivePanel);

    //playStatus.setIcon(new ImageIcon("pause_20x20.jpg"));
    playStatus.setPreferredSize(new Dimension(20, 20));
    buttonPanel.add(playStatus);

    // Initialize the play button.
    playButton.setActionCommand("Play");
    playButton.addActionListener(this);
    buttonPanel.add(playButton);

    // Initialize the pause button.
    pauseButton.setActionCommand("Pause");
    pauseButton.addActionListener(this);
    buttonPanel.add(pauseButton);

    // Initialize the loopback button.
    toggleLoopback.setActionCommand("Toggle Loopback");
    toggleLoopback.addActionListener(this);
    buttonPanel.add(toggleLoopback);

    //status of loopback
    loopbackStatus.setPreferredSize(new Dimension(40, 40));
    buttonPanel.add(loopbackStatus);

    // Initialize the restart button.
    restartButton.setActionCommand("Restart");
    restartButton.addActionListener(this);
    buttonPanel.add(restartButton);

    //Speed set box.
    speedSet.setBorder(BorderFactory.createTitledBorder("Set speed"));
    buttonPanel.add(speedSet);

    // Initialize the setSpeed button.
    speedSetButton.setActionCommand("Set");
    speedSetButton.addActionListener(this);
    buttonPanel.add(speedSetButton);
    this.setVisible(true);
  }


//  @Override
//  public void renderAnimation() {
//    panel.repaint();
//  }
  @Override
  public void renderAnimation() {
    if (panel.getCurrentTick() == model.getShapesPerTick().size()) {
      pause();
      loop();
    }
    else {
      panel.repaint();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    switch (action) {
      case "Play":
        play();
        break;
      case "Pause":
        pause();
        break;
      case "Toggle Loopback":
        toggleLoopback();
        break;
      case "Restart":
        restart();
        break;
      case "Set":
        int speed = Integer.parseInt(speedSet.getText());
        changeSpeed(speed);
        break;
      default:
        break;
    }
  }

  @Override
  public void addEventListener(IEventListeners listener) {
    listenersList.add(listener);
  }

  private void changeSpeed(int speed) {
    for (IEventListeners eventListener : listenersList) {
      eventListener.changeSpeed(speed);
    }
  }

  private void toggleLoopback() {
    for (IEventListeners eventListener : listenersList) {
      eventListener.toggleLoopback();
      eventListener.loop();
      if (this.countLoopback == 1) {
        loopbackStatus.setIcon(null);
        countLoopback = 0;
      }
      else {
        loopbackStatus.setIcon(new ImageIcon("check.jpg"));
        countLoopback = 1;
      }
    }
  }

  private void pause() {
    for (IEventListeners eventListener : listenersList) {
      eventListener.pause();
      this.statusPause();
    }
  }

  private void play() {
    for (IEventListeners eventListener : listenersList) {
      eventListener.play();
      this.statusPlay();
    }
  }

  private void statusPlay() {
    playStatus.setIcon(new ImageIcon("play.jpg"));
  }

  private void statusPause() {
    playStatus.setIcon(new ImageIcon("pause_20x20.jpg"));
  }

  private void restart() {
    panel.restartTick();
    for (IEventListeners eventListener : listenersList) {
      eventListener.restart();
    }
  }

  private void loop() {
    panel.restartTick();
    for (IEventListeners eventListener : listenersList) {
      eventListener.loop();
      this.statusPlay();
    }
  }

}
