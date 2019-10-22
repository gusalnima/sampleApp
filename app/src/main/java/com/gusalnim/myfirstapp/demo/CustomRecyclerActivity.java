package com.gusalnim.myfirstapp.demo;

import android.os.Bundle;

import com.gusalnim.myfirstapp.BaseActivity;
import com.gusalnim.myfirstapp.R;
import com.gusalnim.myfirstapp.demo.adapter.LabelItem;
import com.gusalnim.myfirstapp.demo.adapter.LabelRenderer;
import com.gusalnim.myfirstapp.recycler.FlexAdapter;
import com.gusalnim.myfirstapp.recycler.RendererFactory;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by gusalnim on 30/04/2019.
 */
public class CustomRecyclerActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_recyler);

        //rendererFactory
        RendererFactory rendererFactory = new RendererFactory();
        rendererFactory.put(LabelRenderer.class, R.layout.renderer_label);

        //adapter
        FlexAdapter flexAdapter = new FlexAdapter(rendererFactory);

        //recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(flexAdapter);

        //add items
        for (int index = 0; index < 20; index++) {
            flexAdapter.addItem(new LabelItem(String.valueOf(index)));
        }
    }
}
