package com.prembros.weatherize.view.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import java.util.Random;

import static android.graphics.Color.colorToHSV;

/**
 * Created by Prem$ on 3/1/2018.
 */

public class DynamicProgress extends ProgressBar {

  /**
   * All the material-400 colors.
   */
  private static int[] randomMaterialColors = new int[]{
      Color.parseColor("#ef5350"),
      Color.parseColor("#EC407A"),
      Color.parseColor("#AB47BC"),
      Color.parseColor("#7E57C2"),
      Color.parseColor("#5C6BC0"),
      Color.parseColor("#42A5F5"),
      Color.parseColor("#29B6F6"),
      Color.parseColor("#26C6DA"),
      Color.parseColor("#26A69A"),
      Color.parseColor("#66BB6A"),
      Color.parseColor("#9CCC65"),
      Color.parseColor("#D4E157"),
      Color.parseColor("#FFEE58"),
      Color.parseColor("#FFCA28"),
      Color.parseColor("#FFA726"),
      Color.parseColor("#FF7043"),
      Color.parseColor("#8D6E63"),
      Color.parseColor("#BDBDBD"),
      Color.parseColor("#78909C")
  };

  private Random random;
  private int endColor;

  public DynamicProgress(Context context) {
    super(context);
  }

  public DynamicProgress(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public DynamicProgress(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public DynamicProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  @Override
  public synchronized boolean isIndeterminate() {
    return true;
  }

  private void init() {
    final float[] start = new float[3];
    final float[] end = new float[3];

    colorToHSV(Color.parseColor("#26C6DA"), start);
    endColor = Color.parseColor("#ED3E51");
    colorToHSV(endColor, end);

    random = new Random();
    ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
    anim.setDuration(5000);
    anim.setRepeatMode(ValueAnimator.RESTART);
    anim.setRepeatCount(ValueAnimator.INFINITE);
    final float[] hsv = new float[3];

    anim.addUpdateListener(animation -> {
      hsv[0] = start[0] + (end[0] - start[0]) * animation.getAnimatedFraction();
      hsv[1] = start[1] + (end[1] - start[1]) * animation.getAnimatedFraction();
      hsv[2] = start[2] + (end[2] - start[2]) * animation.getAnimatedFraction();
      setIndeterminateTintList(ColorStateList.valueOf(Color.HSVToColor(hsv)));
    });
    anim.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationRepeat(Animator animation) {
        colorToHSV(endColor, start);
        endColor = getRandomMaterialColor();
        colorToHSV(endColor, end);
        super.onAnimationRepeat(animation);
      }
    });
    anim.start();
  }

  public int getRandomMaterialColor() {
    return randomMaterialColors[random.nextInt(randomMaterialColors.length - 1)];
  }
}