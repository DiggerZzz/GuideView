package com.digger.guideview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.digger.guideview.widget.EasyGuide;
import com.digger.guideview.widget.core.GuideView;
import com.digger.guideview.widget.model.HeightLightRectVo;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openGuide(View view) {
        EasyGuide.setOn(this)
                .addRect(findViewById(R.id.btnTop), "你瞅啥", HeightLightRectVo.HeightLightTipDirection.BOTTOM)
                .addRect(findViewById(R.id.btnBottom), "瞅你咋地", HeightLightRectVo.HeightLightTipDirection.TOP)
                .addCircle(findViewById(R.id.btnLeft), "再瞅一个试试", HeightLightRectVo.HeightLightTipDirection.TOP_RIGHT)
                .addOval(findViewById(R.id.btnRight), "试试就试试", HeightLightRectVo.HeightLightTipDirection.BOTTOM_LEFT)
                .showSequentially();
    }
}
