package com.digger.guideview.widget.model;

import android.graphics.RectF;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: DiggerZzz
 * Date: 2020/1/7 16:42
 * Desc:
 */
public class HeightLightRectVo {

    private int shape;
    private String tip;
    private int direction;
    private RectF rectF;

    public HeightLightRectVo() {

    }

    public HeightLightRectVo(@HeightLightShape int shape, String tip, int direction, RectF rectF) {
        this.shape = shape;
        this.tip = tip;
        this.direction = direction;
        this.rectF = rectF;
    }

    public int getShape() {
        return shape;
    }

    public String getTip() {
        return tip;
    }

    public int getDirection() {
        return direction;
    }

    public RectF getRectF() {
        return rectF;
    }

    @IntDef({HeightLightShape.RECT, HeightLightShape.CIRCLE, HeightLightShape.OVAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HeightLightShape {
        int RECT = 0;
        int CIRCLE = 1;
        int OVAL = 2;
    }

    @IntDef({HeightLightTipDirection.TOP, HeightLightTipDirection.BOTTOM,
            HeightLightTipDirection.LEFT, HeightLightTipDirection.RIGHT,
            HeightLightTipDirection.TOP_LEFT, HeightLightTipDirection.TOP_RIGHT,
            HeightLightTipDirection.BOTTOM_LEFT, HeightLightTipDirection.BOTTOM_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HeightLightTipDirection {
        int TOP = 0;
        int BOTTOM = 1;
        int LEFT = 2;
        int RIGHT = 3;
        int TOP_LEFT = 4;
        int TOP_RIGHT = 5;
        int BOTTOM_LEFT = 6;
        int BOTTOM_RIGHT = 7;
    }
}
