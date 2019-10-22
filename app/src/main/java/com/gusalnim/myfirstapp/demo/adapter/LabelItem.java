package com.gusalnim.myfirstapp.demo.adapter;

import com.gusalnim.myfirstapp.recycler.FlexAdapter;

/**
 * Created by gusalnim on 30/04/2019.
 */
@FlexAdapter.Item
public class LabelItem {

    private String message;

    public LabelItem(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

