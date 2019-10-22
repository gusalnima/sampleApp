package com.gusalnim.myfirstapp.recycler;

import android.view.View;

/**
 * Created by gusalnim on 30/04/2019.
 */
public interface OnItemClickEventHandler {
    /**
     * @param item target model
     * @param rendererView renderer ItemView
     */
    void onItemClick(Object item, View rendererView);

    /**
     * @param item target model
     * @param rendererView renderer ItemView
     */
    void onItemLongClick(Object item, View rendererView);

    /**
     * @param item   target model
     * @param rendererView renderer ItemView
     * @param viewId target View Id
     */
    void onChildViewClick(Object item, View rendererView, int viewId);

    /**
     * @param item   target model
     * @param rendererView renderer ItemView
     * @param viewId target View
     */
    void onChildViewLongClick(Object item, View rendererView, int viewId);
}