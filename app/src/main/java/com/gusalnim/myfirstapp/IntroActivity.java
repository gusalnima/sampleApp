package com.gusalnim.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.gusalnim.myfirstapp.demo.DemoMainActivity;
import com.gusalnim.myfirstapp.util.AlertHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

/**
 * Created by gusalnim on 26/04/2019.
 */
public class IntroActivity extends BaseActivity {

    @Override
    protected void onStart() {
        super.onStart();

        if (!isNetworkConnected()) {
            AlertHelper.showMessage(
                    this,
                    null,
//                    BitmapFactory.decodeResource(getResources(), R.drawable.character_red),
                    "네트워크에 접속할 수 없거나 연결이 끊어졌습니다.\\n잠시 후에 다시 시도해주세요.",
                    new AlertHelper.Button(ContextCompat.getColor(this, R.color.colorPrimary), "확인", new AlertHelper.OnClickListener() {
                        @Override
                        public void onClick(AlertDialog alertDialog) {
                            //확인 버튼 눌렀을때
                            finish();
                            alertDialog.cancel();
                        }
                    })
            );
        }
        Log.e("gusalnim","asdf");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            AlertHelper.showMessage(
                    this,
                    null,
                    //BitmapFactory.decodeResource(getResources(), R.drawable.character_red),
                    "안드로이드 OS 버전 4.4 KITKAT 이하에서는 사용에 일부 제한이 있을 수 있습니다.\\n모든 기기를 대응해 드리기에는 어려움이 있는 점 너그러이 양해 부탁드립니다.",
                    new AlertHelper.Button(ContextCompat.getColor(this, R.color.colorPrimary), "확인", new AlertHelper.OnClickListener() {
                        @Override
                        public void onClick(AlertDialog alertDialog) {
                            //확인 버튼 눌렀을때
                            alertDialog.cancel();
                        }
                    })
            );
        } else {
            startActivity(new Intent(IntroActivity.this, DemoMainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
