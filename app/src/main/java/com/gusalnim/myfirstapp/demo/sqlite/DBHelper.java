package com.gusalnim.myfirstapp.demo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.gusalnim.myfirstapp.demo.net.dao.Person;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by gusalnim on 30/09/2019.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public final static String EXTRA_DATABASE_NAME = "SCGLOCK_DB";
    public final static String EXTRA_TABLE_NAME = "VERSION_TABLE";

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
        sb.append(" CREATE TABLE " + EXTRA_TABLE_NAME);
        sb.append(" ( _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" NAME TEXT, ");
        sb.append(" AGE INTEGER, ");
        sb.append(" PHONE TEXT )");

        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());

        Toast.makeText(context,EXTRA_TABLE_NAME + " 생성 완료", Toast.LENGTH_SHORT).show();
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

    public void initDB() { SQLiteDatabase db = getReadableDatabase(); }


    // table row cont
    public long getProfilesCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, EXTRA_TABLE_NAME);
        db.close();
        return count;
    }

    // data update
    public void upDatePerson() {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("NAME","Bob");
        cv.put("AGE","19");
        cv.put("PHONE","Male");

        db.update(EXTRA_TABLE_NAME,cv,"_id="+1, null);

        // table row all delete
        //db.execSQL("delete from "+ EXTRA_TABLE_NAME);
    }


    // data insert
    public void addPerson(Person person) {

        if(getProfilesCount() == 0) {
            // 1. 쓸 수 있는 DB 객체를 가져온다.
            SQLiteDatabase db = getWritableDatabase();
            // 2. Person Data를 Insert한다.
            // _id는 자동으로 증가하기 때문에 넣지 않습니다.

            StringBuffer sb = new StringBuffer();
            sb.append(" INSERT INTO ");
            sb.append(EXTRA_TABLE_NAME);
            sb.append(" ( NAME, AGE, PHONE ) ");
            sb.append(" VALUES ( ?, ?, ? ) ");
            Log.e("gusalnim",sb.toString());
            db.execSQL(sb.toString(),
                    new Object[]{
                            person.getName(),
                            Integer.parseInt(person.getAge()),
                            person.getPhone()
                    });

            Toast.makeText(context,"Insert 완료", Toast.LENGTH_SHORT).show();
        } else {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("NAME","Bob");
            cv.put("AGE","19");
            cv.put("PHONE","Male");

            Log.e("gusalnim",cv.toString());

            db.update(EXTRA_TABLE_NAME,cv,"_id="+1, null);

            Toast.makeText(context,"update 완료", Toast.LENGTH_SHORT).show();
        }

    }

    // data select
    public List getAllPersonData() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, NAME, AGE, PHONE FROM " + EXTRA_TABLE_NAME);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(),null);
        List people = new ArrayList();
        Person person = null;

        while (cursor.moveToNext()) {
            person = new Person();
            person.set_id(cursor.getInt(0));
            person.setName(cursor.getString(1));
            person.setAge(cursor.getString(2));
            person.setPhone(cursor.getString(3));

            people.add(person);
        }

        return people;
    }

}
