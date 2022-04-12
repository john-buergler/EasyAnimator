package controller;

import java.io.IOException;

public interface AnimatorController {

  public void changeSpeed(int speed);

  public void toggleLoopback();

  public void pause();

  public void play() throws IOException;

  public void restart();

}
