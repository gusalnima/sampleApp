package com.gusalnim.myfirstapp.demo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * Created by gusalnim on 30/09/2019.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    // 생성자
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /*
    * Database가 존재하지 않을 때, 딱 한번 실행된다.
    * DB를 만드는 역할을 한다.
    * @param db
    * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // String 보다 StringBuffer 가 Query 만들기 편하다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE TEST_TABLE (");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" NAME TEXT, ");
        sb.append(" AGE INTEGER, ");
        sb.append(" PHONE TEXT )");

        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());

        Toast.makeText(context,"Table 생성 완료", Toast.LENGTH_SHORT).show();
    }

    /*
    * Application 의 버전이 올라가서
    * Table 구조가 변경되었을 때 실행된다.
    * @param db
    * @param oldVersion
    * @param newVersion
    * */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context,"버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }

    public void testDB() {
        SQLiteDatabase db = getReadableDatabase();
    }
}
