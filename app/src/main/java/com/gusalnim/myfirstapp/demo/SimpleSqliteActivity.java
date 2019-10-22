package com.gusalnim.myfirstapp.demo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.gusalnim.myfirstapp.R;
import com.gusalnim.myfirstapp.demo.adapter.LabelItem;
import com.gusalnim.myfirstapp.demo.adapter.LabelRenderer;
import com.gusalnim.myfirstapp.demo.adapter.SqlItem;
import com.gusalnim.myfirstapp.demo.adapter.SqlRenderer;
import com.gusalnim.myfirstapp.recycler.FlexAdapter;
import com.gusalnim.myfirstapp.recycler.RendererFactory;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by gusalnim on 27/09/2019.
 */
public class SimpleSqliteActivity extends AppCompatActivity {

    private final String dbName = "gusalnim";
    private final String tableName = "sqlSample";

    private String names[];

    {
        names = new String[]{"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "Kitkat"};
    }

    private String versions[];

    {
        versions = new String[]{"Android 1.5", "Android 1.6", "Android 2.0", "Android 2.2", "Android 2.3", "Android  3.0", "Android  4.0", "Android  4.1", "Android  4.4"};
    }

    private ArrayList<HashMap<String, String>> versionList;

    private static final String TAG_NAME = "name";
    private static final String TAG_VERSION = "version";

    private SQLiteDatabase sampleDB = null;

    private FlexAdapter flexAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt_layout_sqlite);
        initUi();
    }

    private void initUi() {
        RendererFactory rendererFactory = new RendererFactory();
        rendererFactory.put(SqlRenderer.class, R.layout.renderer_sql);

        flexAdapter = new FlexAdapter(rendererFactory);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(flexAdapter);

        versionList = new ArrayList<HashMap<String, String>>();

        try {
            // TODO: 27/09/2019 테이블이 있는지 없는지 체크하는 로직이 필요할듯
            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            // 테이블이 존재하지 않으면 새로 생성
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + "(name VARCHAR(20), version VARCHAR(20));");

            // 테이블이 존재하는 경우 기존 데이터를 지우기 위해 사용
            sampleDB.execSQL("DELETE FROM " + tableName);

            // 데이터를 테이블에 삽입
            for (int index = 0; index < names.length; index++) {
                sampleDB.execSQL("INSERT INTO " + tableName + "(name,version) Values ('" + names[index] + "' ,'" + versions[index] + "')");
            }

            sampleDB.close();
        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(),  e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("gusalnim", e.getMessage());
        }

        initData();

    }

    private void initData() {
        try {
            SQLiteDatabase readDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            Cursor c = readDB.rawQuery("SELECT * FROM " + tableName, null);

            if(null != c) {
                if(c.moveToFirst()) {
                    do {
                        String name = c.getString(c.getColumnIndex("name"));
                        String version = c.getString(c.getColumnIndex("version"));

                        HashMap<String,String> persons = new HashMap<>();
                        persons.put(TAG_NAME,name);
                        persons.put(TAG_VERSION,version);

                        versionList.add(persons);
                    }while (c.moveToNext());
                }
            }
            readDB.close();

            //add items
            for (int index = 0; index < versionList.size(); index++) {
                Log.e("gusalnim","versionList.get(index).get(TAG_NAME) : " + versionList.get(index).get(TAG_NAME));
                flexAdapter.addItem(new SqlItem(versionList.get(index).get(TAG_NAME),versionList.get(index).get(TAG_VERSION)));
            }
        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(),  e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("gusalnim", e.getMessage());
        }
    }
}


