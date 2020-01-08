package com.digger.guideview.widget;

import android.app.Activity;
import android.graphics.RectF;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.digger.guideview.widget.core.GuideView;
import com.digger.guideview.widget.model.HeightLightRectVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: DiggerZzz
 * Date: 2020/1/7 14:36
 * Desc:
 */
public class EasyGuide {

    private final int HEIGHT_LIGHT_PADDING = 15;

    //根布局
    private FrameLayout mParent;
    //新手引导控件
    private GuideView guideView;
    //高亮区域组
    private List<HeightLightRectVo> heightLightItems;

    private EasyGuide(Activity activity) {
        init(activity);
    }

    private EasyGuide(Fragment fragment) {
        init(fragment.getActivity());
    }

    /**
     * Set on activity
     * @param activity
     * @return
     */
    public static EasyGuide setOn(Activity activity) {
        return new EasyGuide(activity);
    }

    /**
     * Set on fragment
     * @param fragment
     * @return
     */
    public static EasyGuide setOn(Fragment fragment) {
        return new EasyGuide(fragment);
    }

    /**
     * 添加矩形高亮区
     * @param view
     * @param tip
     * @param direction
     * @return
     */
    public EasyGuide addRect(View view, String tip, @HeightLightRectVo.HeightLightTipDirection int direction) {

        heightLightItems.add(new HeightLightRectVo(HeightLightRectVo.HeightLightShape.RECT, tip, direction, getDrawRect(view)));
        return this;
    }

    /**
     * 添加圆形高亮区
     * @param view
     * @param tip
     * @param direction
     * @return
     */
    public EasyGuide addCircle(View view, String tip, @HeightLightRectVo.HeightLightTipDirection int direction) {
        heightLightItems.add(new HeightLightRectVo(HeightLightRectVo.HeightLightShape.CIRCLE, tip, direction, getDrawRect(view)));
        return this;
    }

    /**
     * 添加圆形高亮区
     * @param view
     * @param tip
     * @param direction
     * @return
     */
    public EasyGuide addOval(View view, String tip, @HeightLightRectVo.HeightLightTipDirection int direction) {
        heightLightItems.add(new HeightLightRectVo(HeightLightRectVo.HeightLightShape.OVAL, tip, direction, getDrawRect(view)));
        return this;
    }

    /**
     * 一起展示
     */
    public void showTogether() {
        bindGuidView(heightLightItems, true);
    }

    /**
     * 一个接一个展示
     */
    public void showSequentially() {
        bindGuidView(heightLightItems, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // private method
    ///////////////////////////////////////////////////////////////////////////
    private void init(Activity activity) {
        heightLightItems = new ArrayList<>();
        guideView = new GuideView(activity);
        View anchor = activity.findViewById(android.R.id.content);

        if(anchor instanceof FrameLayout) {
            mParent = (FrameLayout) anchor;
        } else {
            FrameLayout frameLayout = new FrameLayout(activity);
            ViewGroup parent = (ViewGroup) anchor.getParent();
            int indexOfAnchor = parent.indexOfChild(anchor);

            parent.removeView(anchor);

            if(indexOfAnchor >= 0) {
                parent.addView(frameLayout, indexOfAnchor, anchor.getLayoutParams());
            } else {
                parent.addView(frameLayout, anchor.getLayoutParams());
            }

            frameLayout.addView(
                    anchor,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )
            );
            mParent = frameLayout;
        }
    }

    private RectF getDrawRect(View view) {
        RectF rectF = new RectF();

        rectF.set(
                view.getLeft() - HEIGHT_LIGHT_PADDING,
                view.getTop() - HEIGHT_LIGHT_PADDING,
                view.getRight() + HEIGHT_LIGHT_PADDING,
                view.getBottom() + HEIGHT_LIGHT_PADDING
        );

        return rectF;
    }

    private void bindGuidView(List<HeightLightRectVo> heightLightItems, boolean isShowTogether) {
        guideView.setHeightLight(heightLightItems, isShowTogether);
        mParent.addView(
                guideView,
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                )
        );
    }

}
