package model;

public class Tempo {
  private final int speed;
  private final int startTime;
  private final int endTime;

  public Tempo(int speed, int startTime, int endTime) {
    if (speed < 1 || startTime < 1 || endTime < 1) {
      throw new IllegalArgumentException("Invalid tempo.");
    }
    this.speed = speed;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * @return the speed of the tempo.
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * @return at what time the tempo starts.
   */
  public int getStartTime() {
    return startTime;
  }

  /**
   * @return at what time the tempo ends.
   */
  public int getEndTime() {
    return endTime;
  }

}
