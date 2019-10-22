package com.gusalnim.myfirstapp.demo.adapter;

import com.gusalnim.myfirstapp.recycler.FlexAdapter;

/**
 * Created by gusalnim on 15/10/2019.
 */
@FlexAdapter.Item
public class DemoMenuItem {
    private String title;
    private String link;

    public DemoMenuItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
