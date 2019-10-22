package com.gusalnim.myfirstapp.demo.adapter;

import android.view.View;
import android.widget.TextView;

import com.gusalnim.myfirstapp.demo.net.DemoMenu;
import com.gusalnim.myfirstapp.recycler.ItemRenderer;

/**
 * Created by gusalnim on 15/10/2019.
 */
public class DemoMenuRenderer extends ItemRenderer<DemoMenuItem> {

    private TextView txtLabel;

    public DemoMenuRenderer(View view) {
        super(view);
    }

    @Override
    protected void onBind(DemoMenuItem item) {
        txtLabel.setText(item.getTitle());

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
