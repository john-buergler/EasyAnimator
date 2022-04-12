package view;

import java.io.IOException;

public interface IEventListeners {

  public void changeSpeed(int speed);

  public void toggleLoopback();

  public void pause();

  public void play() throws IOException;

  public void restart();

}
