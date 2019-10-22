package com.gusalnim.myfirstapp.demo.adapter;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by gusalnim on 03/06/2019.
 */
public class ViewPagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    private boolean mNeedsRedraw = false;

    public ViewPagerContainer(Context context) {
        super(context);
        init();
    }

    public ViewPagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewPagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setClipChildren(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onFinishInflate() {
        try {
            mPager = (ViewPager) getChildAt(0);
            mPager.setOnPageChangeListener(this);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public ViewPager getViewPager() {
        return mPager;
    }

    private Point mCenter = new Point();
    private Point mCenter_left = new Point();
    private Point mCenter_right = new Point();
    private Point mInitialTouch = new Point();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        /*mCenter.x = w / 2;
        mCenter.y = h / 2;
        mCenter_left.x = w / 5;
        mCenter_left.y = h / 5;
        mCenter_right.x = w - (w / 5);
        mCenter_right.y = w - (h / 5);*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
        }
        return mPager.dispatchTouchEvent(ev);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mNeedsRedraw) invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }
}