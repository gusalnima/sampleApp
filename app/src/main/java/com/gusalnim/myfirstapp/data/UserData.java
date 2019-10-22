package com.gusalnim.myfirstapp.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gusalnim on 26/04/2019.
 */

public class UserData {

    private static final String TITLE = UserData.class.getSimpleName();

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(TITLE, Activity.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }
}
