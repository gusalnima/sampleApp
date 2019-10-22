package com.gusalnim.myfirstapp.demo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;

import com.gusalnim.myfirstapp.BaseActivity;
import com.gusalnim.myfirstapp.R;
import com.gusalnim.myfirstapp.demo.adapter.LabelItem;
import com.gusalnim.myfirstapp.demo.adapter.LabelRenderer;
import com.gusalnim.myfirstapp.recycler.FlexAdapter;
import com.gusalnim.myfirstapp.recycler.OffsetItemDecoration;
import com.gusalnim.myfirstapp.recycler.OnItemClickEventHandler;
import com.gusalnim.myfirstapp.recycler.RendererFactory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

/**
 * Created by gusalnim on 24/05/2019.
 */
public class CustomRecyclerHorizenActivity extends BaseActivity {
    FlexAdapter flexAdapter;
    SnapHelper snapHelper;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.SmoothScroller smoothScroller;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_recyler_horizen);

        initMenu();
        initPage();
        Log.i("gusalnim","?");

    }

    private int currentPos = 0;
    private float offVal = 0.5f;
    private float onVal = 1f;
    private AlphaAnimation alphaAnimation;
    private void initMenu(){
        //rendererFactory
        RendererFactory rendererFactory = new RendererFactory();
        rendererFactory.put(LabelRenderer.class, R.layout.renderer_label);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        //adapter
        flexAdapter = new FlexAdapter(rendererFactory);
        //recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

        //add items
        for (int index = 0; index < 4; index++) {
            flexAdapter.addItem(new LabelItem(String.valueOf("메뉴메뉴apsb" + index)));
        }


        //snapHelper = new LinearSnapHelper(); // 기본 스냅헬퍼
        snapHelper = new SnapHelperOneByOne(); // 커스텀 스냅헬퍼 빠르게 스와이프시에 한단계만 이동하기
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(flexAdapter);
        recyclerView.addItemDecoration(new OffsetItemDecoration(this));
        flexAdapter.setOnItemClickEventHandler(itemEventHandler);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                View centerView = snapHelper.findSnapView(linearLayoutManager);
                currentPos = linearLayoutManager.getPosition(centerView);
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

        });

        smoothScroller = new CenterSmoothScroller(this) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };


        // 디폴트 메뉴 선택
        smoothScroller.setTargetPosition(0);
        linearLayoutManager.startSmoothScroll(smoothScroller);
    }


    // Custom RecyclerView.SmoothScroller 센터 포지션으로 이동해주기
    private static class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller(Context context) {
            super(context);
        }
        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }
    }


    public class SnapHelperOneByOne extends LinearSnapHelper{

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY){
            if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
                return RecyclerView.NO_POSITION;
            }

            final View currentView = findSnapView(layoutManager);

            if( currentView == null ){
                return RecyclerView.NO_POSITION;
            }

            final int currentPosition = layoutManager.getPosition(currentView);

            if (currentPosition == RecyclerView.NO_POSITION) {
                return RecyclerView.NO_POSITION;
            }
            changePosition(currentPosition);
            return currentPosition;
        }

        @Override
        public int[] calculateScrollDistance(int velocityX, int velocityY) {
            Log.i("gusalnim", "calculateScrollDistance");
            return super.calculateScrollDistance(velocityX, velocityY);
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
            changePosition(flexAdapter.getItemPosition(item));
        }

        @Override
        public void onChildViewLongClick(Object item, View rendererView, int viewId) {

        }
    };

    private ViewStub[] viewStubs;

    private void initPage(){
        viewStubs = new ViewStub[4];
        viewStubs[0] = findViewById(R.id.viewStub01);
        viewStubs[1] = findViewById(R.id.viewStub02);
        viewStubs[2] = findViewById(R.id.viewStub03);
        viewStubs[3] = findViewById(R.id.viewStub04);
        viewStubs[0].setVisibility(View.VISIBLE);
    }

    private void changePosition(int currentPosition){
        smoothScroller.setTargetPosition(currentPosition);
        linearLayoutManager.startSmoothScroll(smoothScroller);
        for(int i = 0; i< flexAdapter.getItemCount(); i++) {
            flexAdapter.removeSelectItem(flexAdapter.getItem(i));
            flexAdapter.notifyItemChanged(flexAdapter.getItem(i));
        }
        flexAdapter.addSelectItem(flexAdapter.getItem(currentPosition));
        flexAdapter.notifyItemChanged(flexAdapter.getItem(currentPosition));
    }

}