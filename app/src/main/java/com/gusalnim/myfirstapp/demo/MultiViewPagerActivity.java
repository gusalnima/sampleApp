package com.gusalnim.myfirstapp.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gusalnim.myfirstapp.BaseActivity;
import com.gusalnim.myfirstapp.R;
import com.gusalnim.myfirstapp.demo.adapter.MainViewPager;
import com.gusalnim.myfirstapp.demo.adapter.ViewPagerAdapter;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by gusalnim on 03/06/2019.
 */
public class MultiViewPagerActivity extends BaseActivity {

    MainViewPager menuPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_viewpager);
        initUi();
    }

    private void initUi() {
        menuPager = findViewById(R.id.menuPager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        menuPager.setAdapter(viewPagerAdapter);
        menuPager.setOffscreenPageLimit(3);
        menuPager.setCurrentItem(0);
        menuPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        /*menuPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });*/
    }
}
