import org.junit.Test;

import view.AnimatorGraphicsView;

/**
 * Tests the exceptions being thrown by the AnimatorGraphicsView.
 */
public class AnimatorGraphicsViewTest {
  @Test (expected = IllegalArgumentException.class)
    public void testNullModel() {
    AnimatorGraphicsView view = new AnimatorGraphicsView(null, 20);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeSpeed() {
    AnimatorGraphicsView view = new AnimatorGraphicsView(null, -1);
  }
}
