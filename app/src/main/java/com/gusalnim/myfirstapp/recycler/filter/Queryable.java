package com.gusalnim.myfirstapp.recycler.filter;

/**
 * Created by gusalnim on 30/04/2019.
 */
public interface Queryable {
    /**
     * @param constraint the constraint used to filter the data
     *
     * @return False if the Item should be hided
     */
    boolean onQuery(CharSequence constraint);
}