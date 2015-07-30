package com.github.hongchae.coverflow.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import com.github.hongchae.coverflow.R;

public class CoverFlowTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.3f;
    private static final float MIN_ALPHA = 0.3f;
    private int visibleIndex;
    private int spacing;
    private int selectedSpacing;
    private float rotateFactor;
    private float rotateLimit;
    private float alphaFactor;
    private float scaleFactor;
    private float gravityFactor;
    private float listRadius;

    public CoverFlowTransformer(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CoverFlowPager, 0, 0);
        spacing = typedArray.getDimensionPixelSize(R.styleable.CoverFlowPager_listSpacing, 0);
        selectedSpacing = typedArray.getDimensionPixelSize(R.styleable.CoverFlowPager_selectedSpacing, 0);
        visibleIndex = typedArray.getInt(R.styleable.CoverFlowPager_visibleIndex, 4);
        rotateFactor = typedArray.getFloat(R.styleable.CoverFlowPager_rotateFactor, 0f);
        rotateLimit = typedArray.getFloat(R.styleable.CoverFlowPager_rotateLimit, 0f);
        alphaFactor = typedArray.getFloat(R.styleable.CoverFlowPager_alphaFactor, 0f);
        scaleFactor = typedArray.getFloat(R.styleable.CoverFlowPager_scaleFactor, 0f);
        gravityFactor = typedArray.getFloat(R.styleable.CoverFlowPager_gravityFactor, 0f);
        listRadius = typedArray.getFloat(R.styleable.CoverFlowPager_listRadius, 1f);
        listRadius *= listRadius;

        typedArray.recycle();
    }

    @Override
    public void transformPage(View view, float position) {
        if (position < -visibleIndex || position > visibleIndex) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);

            if (rotateFactor != 0f) {
                float rotateAngle = Math.min(rotateLimit, Math.abs(position * rotateFactor));
                view.setRotationY((position < 0) ? rotateAngle : -rotateAngle);
            }

            if (spacing != 0) {
                float hMargin = position * (spacing);
                if (selectedSpacing != 0) {
                    float ss = Math.min(selectedSpacing, Math.abs(position * selectedSpacing));
                    hMargin += (position > 0) ? ss : -ss;
                }
                System.out.println(hMargin);
                view.setTranslationX(hMargin);
            }

            float vMargin = position * position * gravityFactor / listRadius;
            view.setTranslationY(vMargin);


            if (scaleFactor != 0f) {
                float scale = Math.max(MIN_SCALE, 1 - Math.abs(position * scaleFactor));
                view.setScaleX(scale);
                view.setScaleY(scale);
            }

            if (alphaFactor != 0f) {
                view.setAlpha(Math.max(MIN_ALPHA, 1 - Math.abs(position * alphaFactor)));
            }


        }
    }
}
