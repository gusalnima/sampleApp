package com.gusalnim.myfirstapp.demo.util;

import android.content.Context;
import android.os.Build;

import com.google.gson.Gson;
import com.gusalnim.myfirstapp.demo.net.DemoMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;

/**
 * Created by gusalnim on 15/10/2019.
 */
public class GsonHelper {
    public static ArrayList<DemoMenu> getDemoMenu(Context context){
        ArrayList<DemoMenu> demoMenus = new ArrayList<>();
        Gson gson = new Gson();

        try {
            InputStream in = context.getAssets().open("demoMenu.json");
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();

            String json = new String(buffer, "UTF-8");

            //JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = new JSONArray(json);
            int index = 0;
            while (index < jsonArray.length()) {
                DemoMenu demoMenu = gson.fromJson(jsonArray.get(index).toString(),DemoMenu.class);
                demoMenus.add(demoMenu);
                index++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return demoMenus;
    }
}
