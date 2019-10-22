package com.gusalnim.myfirstapp.demo.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gusalnim.myfirstapp.recycler.ItemRenderer;

/**
 * Created by gusalnim on 30/04/2019.
 */
public class LabelRenderer extends ItemRenderer<LabelItem> {
    private TextView txtLabel;
    private FrameLayout group;

    public LabelRenderer(View view) {
        super(view);
    }

    @Override
    protected void onBind(final LabelItem item) {
        txtLabel.setText(item.getMessage());
        if(isSelected()){
            Log.i("gusalnim","asdf");
            txtLabel.setTextColor(Color.parseColor("#094673"));
        } else {
            txtLabel.setTextColor(Color.GRAY);
        }
    }
    @Override
    protected void onAttachedRenderer() {
        addChildViewClickListener(txtLabel);
    }

    @Override
    protected void onDetachedRenderer() {
        removeChildViewClickListener(txtLabel);
    }


}