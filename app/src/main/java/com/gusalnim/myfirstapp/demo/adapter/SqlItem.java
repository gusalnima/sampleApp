package com.gusalnim.myfirstapp.demo.adapter;

import com.gusalnim.myfirstapp.recycler.FlexAdapter;

/**
 * Created by gusalnim on 27/09/2019.
 */
@FlexAdapter.Item
public class SqlItem {
    private String name;
    private String version;

    public SqlItem(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
