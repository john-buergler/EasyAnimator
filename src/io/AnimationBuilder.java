package io;

import java.awt.*;

import model.AnimatorModel;
import model.EasyAnimatorModel;
import model.Oval;
import model.Posn;
import model.ShapeType;

public class AnimationBuilder implements TweenModelBuilder<AnimatorModel> {
  private final AnimatorModel m;

  public AnimationBuilder(AnimatorModel m) {
    this.m = m;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> setBounds(int width, int height) {
    return null;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addOval(String name, float cx, float cy, float xRadius, float yRadius, float red, float green, float blue, int startOfLife, int endOfLife) {
    Color color = new Color(red, green, blue);
    Posn posn = new Posn((int) cx, (int) cy);
    int width = (int) xRadius;
    int height = (int) yRadius;
    m.addShape(ShapeType.OVAL, height, width, color, posn, name);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addRectangle(String name, float lx, float ly, float width, float height, float red, float green, float blue, int startOfLife, int endOfLife) {
    Color color = new Color(red, green, blue);
    Posn posn = new Posn((int) lx, (int) ly);
    int w = (int) width;
    int h = (int) height;
    m.addShape(ShapeType.RECTANGLE, h, w, color, posn, name);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addMove(String name, float moveFromX, float moveFromY, float moveToX, float moveToY, int startTime, int endTime) {
    Posn startPos = new Posn((int) moveFromX, (int) moveFromY);
    Posn endPos = new Posn((int) moveToX, (int) moveToY);
    m.moveShape(startTime, endTime, startPos, endPos, name);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addColorChange(String name, float oldR, float oldG, float oldB, float newR, float newG, float newB, int startTime, int endTime) {
    Color oldColor = new Color(oldR, oldG, oldB);
    Color newColor = new Color(newR, newG, newB);
    m.changeColor(name, startTime, endTime, oldColor, newColor);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addScaleToChange(String name, float fromSx, float fromSy, float toSx, float toSy, int startTime, int endTime) {
    int oldWidth = (int) fromSx;
    int oldHeight = (int) fromSy;
    int newWidth = (int) toSx;
    int newHeight = (int) toSy;
    m.changeSize(name, startTime, endTime, oldHeight, oldWidth, newHeight, newWidth);
    return this;
  }

  @Override
  public AnimatorModel build() {
    return m;
  }

}
