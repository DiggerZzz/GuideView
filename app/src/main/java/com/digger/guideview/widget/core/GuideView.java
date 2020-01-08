package com.digger.guideview.widget.core;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digger.guideview.R;
import com.digger.guideview.widget.model.HeightLightRectVo;

import java.util.List;

/**
 * Author: DiggerZzz
 * Date: 2020/1/7 14:16
 * Desc:
 */
public class GuideView extends FrameLayout implements View.OnClickListener {

    private final int BG_COLOR = 0x88000000;
    private final int TIP_MARGIN = 20;

    private Paint mPaint;

    //高亮区域组
    private List<HeightLightRectVo> heightLightItems;
    //是否一起展示
    private boolean isShowTogether;
    //逐一显示下标
    private int heightLightIndex;

    public GuideView(@NonNull Context context) {
        super(context);

        init();
    }

    private GuideView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private GuideView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(BG_COLOR);

        if(isShowTogether) {
            for(HeightLightRectVo rectVo : heightLightItems) {
                drawHeightLight(rectVo, canvas);
            }
        } else {
            drawHeightLight(heightLightItems.get(heightLightIndex), canvas);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(isShowTogether) {
            for(HeightLightRectVo rectVo : heightLightItems) {
                addTip(rectVo);
            }
            addNext();
        } else {
            addTip(heightLightItems.get(heightLightIndex));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void onClick(View v) {
        if(isShowTogether) {
            ((ViewGroup) getParent()).removeView(this);
        } else {
            heightLightIndex++;

            if(heightLightIndex >= heightLightItems.size()) {
                ((ViewGroup) getParent()).removeView(this);
            } else {
                removeAllViews();
                addTip(heightLightItems.get(heightLightIndex));
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // private method
    ///////////////////////////////////////////////////////////////////////////
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.INNER));

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
    }

    private void drawHeightLight(HeightLightRectVo rectVo, Canvas canvas) {
        switch(rectVo.getShape()) {
            case HeightLightRectVo.HeightLightShape.RECT:
                canvas.drawRect(rectVo.getRectF(), mPaint);
                break;
            case HeightLightRectVo.HeightLightShape.CIRCLE:
                int width = (int) (rectVo.getRectF().right - rectVo.getRectF().left);
                int height = (int) (rectVo.getRectF().bottom - rectVo.getRectF().top);
                int cx = (int) (rectVo.getRectF().left + width / 2);
                int cy = (int) (rectVo.getRectF().top + height / 2);
                int radius = Math.max(width, height) / 2;
                canvas.drawCircle(cx, cy, radius, mPaint);
                break;
            case HeightLightRectVo.HeightLightShape.OVAL:
                canvas.drawOval(rectVo.getRectF(), mPaint);
                break;
        }
    }

    private void addNext() {
        Button btnNext = (Button) LayoutInflater.from(getContext()).inflate(R.layout.view_guide_next, null);
        btnNext.setText("知道了");
        btnNext.setOnClickListener(this);
        addView(
                btnNext, new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER
                )
        );
    }

    private void addTip(HeightLightRectVo rectVo) {
        View tipView;

        if(isShowTogether) {
            tipView = LayoutInflater.from(getContext()).inflate(R.layout.view_guide_tip, null);
        } else {
            tipView = LayoutInflater.from(getContext()).inflate(R.layout.view_guide, null);
        }

        setTip(tipView, rectVo.getTip());
        addView(tipView, locationTip(tipView, rectVo));
    }

    private void setTip(View tipView, String txt) {
        ((TextView) tipView.findViewById(R.id.tvTip)).setText(txt);

        if(tipView.findViewById(R.id.btnNext) != null) {
            if(heightLightIndex == (heightLightItems.size() - 1)) {
                ((Button) tipView.findViewById(R.id.btnNext)).setText("知道了");
            } else {
                ((Button) tipView.findViewById(R.id.btnNext)).setText("下一个");
            }
            tipView.findViewById(R.id.btnNext).setOnClickListener(this);
        }
    }

    private LayoutParams locationTip(View tipView, HeightLightRectVo rectVo) {
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );

        tipView.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        );

        RectF rectF = rectVo.getRectF();
        int width = tipView.getMeasuredWidth();
        int height = tipView.getMeasuredHeight();
        int topMargin = 0;
        int leftMargin = 0;

        switch(rectVo.getDirection()) {
            case HeightLightRectVo.HeightLightTipDirection.TOP:
                topMargin = (int) (rectF.top - height - TIP_MARGIN);
                leftMargin = (int) (rectF.left + (rectF.right - rectF.left - width) / 2);
                break;
            case HeightLightRectVo.HeightLightTipDirection.BOTTOM:
                topMargin = (int) (rectF.bottom + TIP_MARGIN);
                leftMargin = (int) (rectF.left + (rectF.right - rectF.left - width) / 2);
                break;
            case HeightLightRectVo.HeightLightTipDirection.LEFT:
                topMargin = (int) (rectF.top + (rectF.bottom - rectF.top - height) / 2);
                leftMargin = (int) (rectF.left - width - TIP_MARGIN);
                break;
            case HeightLightRectVo.HeightLightTipDirection.RIGHT:
                topMargin = (int) (rectF.top + (rectF.bottom - rectF.top - height) / 2);
                leftMargin = (int) (rectF.right + TIP_MARGIN);
                break;
            case HeightLightRectVo.HeightLightTipDirection.TOP_LEFT:
                topMargin = (int) (rectF.top - height - TIP_MARGIN);
                leftMargin = (int) (rectF.left - width - TIP_MARGIN);
                break;
            case HeightLightRectVo.HeightLightTipDirection.TOP_RIGHT:
                topMargin = (int) (rectF.top - height - TIP_MARGIN);
                leftMargin = (int) (rectF.right + TIP_MARGIN);
                break;
            case HeightLightRectVo.HeightLightTipDirection.BOTTOM_LEFT:
                topMargin = (int) (rectF.bottom + TIP_MARGIN);
                leftMargin = (int) (rectF.left - width - TIP_MARGIN);
                break;
            case HeightLightRectVo.HeightLightTipDirection.BOTTOM_RIGHT:
                topMargin = (int) (rectF.bottom + TIP_MARGIN);
                leftMargin = (int) (rectF.right + TIP_MARGIN);
                break;
        }

        layoutParams.setMargins(leftMargin, topMargin, 0, 0);
        return layoutParams;
    }

    ///////////////////////////////////////////////////////////////////////////
    // public method
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 设置需要绘制的高亮区
     * @param heightLightItems
     * @param isShowTogether
     */
    public void setHeightLight(List<HeightLightRectVo> heightLightItems, boolean isShowTogether) {
        this.heightLightItems = heightLightItems;
        this.isShowTogether = isShowTogether;
        heightLightIndex = 0;

        invalidate();
    }

}
