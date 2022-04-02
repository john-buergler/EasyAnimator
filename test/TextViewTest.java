import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

import io.AnimationBuilder;
import io.AnimationFileReader;
import io.TweenModelBuilder;
import io.ViewsFactory;
import model.AnimatorModel;
import model.EasyAnimatorModel;
import view.AnimatorTextView;
import view.IView;

import static org.junit.Assert.assertEquals;

public class TextViewTest {
 AnimatorModel m;

  @Before
  public void setUp() {
    this.m = new EasyAnimatorModel();
  }

  @Test
  public void testRenderAnimation() throws IOException {
    TweenModelBuilder<AnimatorModel> modelBuilder = new AnimationBuilder(m);
    AnimationFileReader reader = new AnimationFileReader();
    reader.readFile("buildings.txt", modelBuilder);
    AnimatorModel model = modelBuilder.build();
    IView textView = new AnimatorTextView(m, "TextViewTest", 10);
    textView.renderAnimation();
    FileReader fr = new FileReader("TextViewTest");
    assertEquals(str, "");
  }
}
