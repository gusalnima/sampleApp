package com.gusalnim.myfirstapp.demo;

import android.os.Bundle;

import com.gusalnim.myfirstapp.BaseActivity;
import com.gusalnim.myfirstapp.R;

import androidx.annotation.Nullable;

/**
 * Created by gusalnim on 10/06/2019.
 */
public class CustomButtonActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_button);
    }
}
