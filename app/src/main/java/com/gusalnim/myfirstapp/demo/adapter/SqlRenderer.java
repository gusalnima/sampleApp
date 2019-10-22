package com.gusalnim.myfirstapp.demo.adapter;

import android.view.View;
import android.widget.TextView;

import com.gusalnim.myfirstapp.recycler.ItemRenderer;

/**
 * Created by gusalnim on 27/09/2019.
 */
public class SqlRenderer extends ItemRenderer<SqlItem> {
    private TextView txtName;
    private TextView txtVersion;

    public SqlRenderer(View view) {
        super(view);
    }

    @Override
    protected void onBind(SqlItem item) {
        txtName.setText(item.getName());
        txtVersion.setText(item.getVersion());
    }
}
