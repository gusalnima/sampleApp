package com.gusalnim.myfirstapp.demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gusalnim.myfirstapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by gusalnim on 03/06/2019.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private String[] menuList;
    private Context context;

    public ViewPagerAdapter(Context context) {
        this.context = context;
        this.menuList = context.getResources().getStringArray(R.array.menu_list);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.renderer_label, null);
        TextView title = view.findViewById(R.id.txtLabel);
        title.setText(getPageTitle(position));
        title.setTextColor(Color.BLACK);
        title.setTag(position);

        container.addView(view);
       container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        Log.i("gusalnim",v.findViewById(R.id.txtLabel).getTag() + " / ");
                        Log.i("gusalnim",container.getChildAt(0).findViewById(R.id.txtLabel).getTag() + " / ");
                        Log.i("gusalnim",container.getChildAt(1).findViewById(R.id.txtLabel).getTag() + " / ");
                        Log.i("gusalnim",container.indexOfChild(container.getFocusedChild()) + " / ");
                        Log.e("gusalnim",": " + container.getChildAt(0).hashCode() + " ===/=== " + v.hashCode());
                        Log.e("gusalnim",": " + container.getChildAt(1).hashCode() + " ===/=== " + v.hashCode());

                        //Log.i("gusalnim"," / " + container.getChildAt(0).getMeasuredWidth());
                       Log.i("gusalnim"," / container " + container.getChildAt(0).getX());
                        Log.i("gusalnim"," / container " + container.getChildAt(1).getX());
                        Log.i("gusalnim"," / container " + container.getChildAt(2).getX());
                        Log.i("gusalnim"," / container " + container.getChildAt(3).getX());
                         /*Log.i("gusalnim"," / container " + container.getX());
                        Log.i("gusalnim"," / container " + container.getPivotX());
                        Log.i("gusalnim"," / container " + container.getTranslationX());
                        Log.i("gusalnim"," / " + event.getX());
                        Log.i("gusalnim"," / " + event.getRawX());*/
                        //Log.i("gusalnim",v.getId() + " / " + " / " + container.getChildAt(container.indexOfChild(v)).hashCode());
                        break;
                }
                return false;
            }
        });

      /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("gusalnim",v.hashCode() + " / " + container.getChildAt(0).hashCode());
                Log.i("gusalnim",v.hashCode() + " / " + container.getChildAt(1).hashCode());
            }
        });*/


        //Log.i("gusalnim","asdfasdfasdfasdfasdfdsafdsaf" + getPageTitle(position));
        return view;
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //int menuPosition = Integer.parseInt(menuList[position]);
        return menuList[position];
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }
}
