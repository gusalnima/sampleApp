package com.gusalnim.myfirstapp.demo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gusalnim.kotlinsample.KtMainActivity;
import com.gusalnim.myfirstapp.BaseActivity;
import com.gusalnim.myfirstapp.R;
import com.gusalnim.myfirstapp.demo.adapter.DemoMenuItem;
import com.gusalnim.myfirstapp.demo.adapter.DemoMenuRenderer;
import com.gusalnim.myfirstapp.demo.adapter.LabelItem;
import com.gusalnim.myfirstapp.demo.adapter.LabelRenderer;
import com.gusalnim.myfirstapp.demo.net.DemoMenu;
import com.gusalnim.myfirstapp.demo.util.GsonHelper;
import com.gusalnim.myfirstapp.recycler.FlexAdapter;
import com.gusalnim.myfirstapp.recycler.OnItemClickEventHandler;
import com.gusalnim.myfirstapp.recycler.RendererFactory;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by gusalnim on 26/04/2019.
 */
public class DemoMainActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FlexAdapter flexAdapter;
    private RendererFactory rendererFactory;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
        findViewById(R.id.recycle1).setOnClickListener(this);
        findViewById(R.id.recycle2).setOnClickListener(this);
        findViewById(R.id.viewpager).setOnClickListener(this);
        findViewById(R.id.simpleSqlite).setOnClickListener(this);
        findViewById(R.id.sqlite).setOnClickListener(this);
        findViewById(R.id.ktbtn).setOnClickListener(this);
        initUi();
    }

    private void initUi() {
        //rendererFactory
        rendererFactory = new RendererFactory();
        rendererFactory.put(DemoMenuRenderer.class, R.layout.renderer_label);

        //adapter
        flexAdapter = new FlexAdapter(rendererFactory);
        flexAdapter.setOnItemClickEventHandler(itemEventHandler);

        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(flexAdapter);

        List<DemoMenu> menus = GsonHelper.getDemoMenu(this);

        for(DemoMenu menu : menus) {
            flexAdapter.addItem(new DemoMenuItem(menu.getTitle(), menu.getLink()));
        }
    }

    private OnItemClickEventHandler itemEventHandler = new OnItemClickEventHandler() {
        @Override
        public void onItemClick(Object item, View rendererView) {


        }

        @Override
        public void onItemLongClick(Object item, View rendererView) {

        }

        @Override
        public void onChildViewClick(Object item, View rendererView, int viewId) {
            if(item instanceof DemoMenuItem) {
                DemoMenuItem d = (DemoMenuItem) item;
                switch (viewId) {
                    case R.id.txtLabel:
                        Log.e("gusalnim", item + " / " + d.getTitle());
                        try {
                            startActivity(new Intent(DemoMainActivity.this, Class.forName(d.getLink())));
                        } catch (ClassNotFoundException e) {
                            Log.e("gusalnim", e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }

        @Override
        public void onChildViewLongClick(Object item, View rendererView, int viewId) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recycle1:
                rendererFactory.put(DemoMenuRenderer.class, R.layout.renderer_label2);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(flexAdapter);
                //startActivity(new Intent(DemoMainActivity.this,CustomRecyclerActivity.class));
                break;
            case R.id.recycle2:
                rendererFactory.put(DemoMenuRenderer.class, R.layout.renderer_label);
                recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                recyclerView.setAdapter(flexAdapter);
                //startActivity(new Intent(DemoMainActivity.this,CustomRecyclerHorizenActivity.class));
                break;
            case R.id.viewpager:
                startActivity(new Intent(DemoMainActivity.this,MultiViewPagerActivity.class));
                break;
            case R.id.simpleSqlite:
                startActivity(new Intent(DemoMainActivity.this, SimpleSqliteActivity.class));
                break;
            case R.id.sqlite:
                startActivity(new Intent(DemoMainActivity.this, SQLiteActivity.class));
                break;
            case R.id.ktbtn:
                startActivity(new Intent(DemoMainActivity.this, KtMainActivity.class));
                break;
        }
    }
}
