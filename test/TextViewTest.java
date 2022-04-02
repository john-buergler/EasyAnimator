import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.AnimatorModel;
import model.EasyAnimatorModel;
import view.AnimatorTextView;
import view.IView;

public class TextViewTest {
 AnimatorModel m;

  @Before
  public void setUp() {
    this.m = new EasyAnimatorModel();
  }

  @Test
  public void testRenderAnimation() throws IOException {
    IView textView = new AnimatorTextView(m, "TextViewTest", 10);

  }
}
