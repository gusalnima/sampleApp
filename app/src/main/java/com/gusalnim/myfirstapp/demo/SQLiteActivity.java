package com.gusalnim.myfirstapp.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gusalnim.myfirstapp.R;
import com.gusalnim.myfirstapp.demo.sqlite.DBHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by gusalnim on 01/10/2019.
 */
public class SQLiteActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCreateButton;
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
                                    dbHelper = new DBHelper(SQLiteActivity.this,editText.getText().toString(),null,1);
                                    dbHelper.testDB();

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
        }
    }
}