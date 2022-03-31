package view;

import java.io.IOException;

import model.AnimatorModel;

public class AnimatorTextView implements IView {
  private final AnimatorModel model;
  private final Appendable ap;

  public AnimatorTextView(AnimatorModel m, Appendable ap) {
    this.model = m;
    this.ap = ap;
  }

  public AnimatorTextView(AnimatorModel m) {
    this.model = m;
    this.ap = System.out;
  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void renderTick() {
    ap.append(model.toString());
  }
}
