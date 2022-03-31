package view;

public interface IView {

  /**
   * Make the view visible after constructing animation.
   */
  public void makeVisible();

  /**
   * Renders each of the shapes in a given tick provided by the model's timeline.
   */
  public void renderTick();

}
