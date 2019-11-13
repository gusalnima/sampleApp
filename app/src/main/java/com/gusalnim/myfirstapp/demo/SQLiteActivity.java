package com.gusalnim.myfirstapp.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gusalnim.myfirstapp.R;
import com.gusalnim.myfirstapp.demo.adapter.sqlite.PersonListAdapter;
import com.gusalnim.myfirstapp.demo.net.dao.Person;
import com.gusalnim.myfirstapp.demo.sqlite.DBHelper;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.gusalnim.myfirstapp.demo.sqlite.DBHelper.EXTRA_DATABASE_NAME;

/**
 * Created by gusalnim on 01/10/2019.
 */
public class SQLiteActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnCreateButton;
    private Button btnInsertButton;
    private Button btnSelectButton;
    private Button btnDeleteButton;
    private ListView lvPeople;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        initUi();
    }

    private void initUi() {
        btnCreateButton = findViewById(R.id.btnCreateButton);
        btnCreateButton.setOnClickListener(this);
        btnInsertButton = findViewById(R.id.btnInsertButton);
        btnInsertButton.setOnClickListener(this);
        btnSelectButton = findViewById(R.id.btnSelectButton);
        btnSelectButton.setOnClickListener(this);
        btnDeleteButton = findViewById(R.id.btnDeleteButton);
        btnDeleteButton.setOnClickListener(this);
        lvPeople = findViewById(R.id.lvPeople);

        if(null == dbHelper){
            dbHelper = new DBHelper(SQLiteActivity.this,EXTRA_DATABASE_NAME,null,1);
            dbHelper.initDB();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreateButton:
                final EditText editText = new EditText(this);
                editText.setHint("디비명을 입력해주세요");

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("이름을 입력하세요.")
                        .setMessage("입력해라...")
                        .setView(editText)
                        .setPositiveButton("생성", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(editText.getText().toString().length() > 0) {
                                    dbHelper = new DBHelper(SQLiteActivity.this,EXTRA_DATABASE_NAME,null,1);

                                }
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();
                break;
            case R.id.btnInsertButton:
                LinearLayout layout = new LinearLayout(SQLiteActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText etName = new EditText(SQLiteActivity.this);
                etName.setHint("이름을 입력하세요.");

                final EditText etAge = new EditText(SQLiteActivity.this);
                etAge.setHint("나이를 입력하세요.");

                final EditText etPhone = new EditText(SQLiteActivity.this);
                etPhone.setHint("전화번호를 입력하세요.");

                layout.addView(etName);
                layout.addView(etAge);
                layout.addView(etPhone);

                AlertDialog.Builder dataDialog = new AlertDialog.Builder(this);
                dataDialog.setTitle("정보를 입력하세요.")
                        .setView(layout)
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = etName.getText().toString();
                                String age = etAge.getText().toString();
                                String phone = etPhone.getText().toString();

                                if(null != dbHelper) {
                                    dbHelper = new DBHelper(SQLiteActivity.this,EXTRA_DATABASE_NAME,null,1);
                                }

                                Person person = new Person();
                                person.setName(name);
                                person.setAge(age);
                                person.setPhone(phone);

                                dbHelper.addPerson(person);


                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();


                break;
            case R.id.btnSelectButton:
                lvPeople.setVisibility(View.VISIBLE);

                if(dbHelper == null) {
                    dbHelper = new DBHelper(SQLiteActivity.this,EXTRA_DATABASE_NAME,null,1);
                }

                // person data 가져오기
                List people = dbHelper.getAllPersonData();

                Log.e("gusalnim",people.toString());

                // listView 에 person 데이터 모두 보여주기.
                lvPeople.setAdapter(new PersonListAdapter(people,SQLiteActivity.this));
                break;
//            case R.id.btnDeleteButton:
//                if(dbHelper == null) {
//                    dbHelper = new DBHelper(SQLiteActivity.this,EXTRA_DATABASE_NAME,null,1);
//                }
//                Log.e("gusalnim","dbHelper.getProfilesCount() : " + dbHelper.getProfilesCount());
//                dbHelper.upDatePerson();
//                break;
        }
    }
}