package model;

/**
 * A class representing a tempo, which is a specified speed for a time interval.
 */
public class Tempo {
  private final int speed;
  private final int startTime;
  private final int endTime;

  /**
   * @param speed the desired speed of the tempo.
   * @param startTime the start time for this tempo.
   * @param endTime the end time for this tempo.
   * @throws IllegalArgumentException if speed, startTime, and endTime are less than one.
   * @throws IllegalArgumentException if time is invalid.
   */
  public Tempo(int speed, int startTime, int endTime) {
    if (speed < 1 || startTime < 1 || endTime < 1) {
      throw new IllegalArgumentException("Invalid tempo.");
    }
    if (endTime < startTime) {
      throw new IllegalArgumentException("End time can't be less than start time.");
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
