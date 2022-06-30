package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.database.UserDatabase;

public class UpdateActivity extends AppCompatActivity {
    private EditText edt_user;
    private EditText edt_pass;
    private EditText edt_age;
    private Button btn_updateuser;
    private ItemUser itemUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initUi();
        itemUser = (ItemUser) getIntent().getExtras().get("object_user");
        if(itemUser != null){
            edt_user.setText(itemUser.getUserName());
            edt_pass.setText(itemUser.getPassWord());
            edt_age.setText(itemUser.getAge());
        }
        btn_updateuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }
    private void initUi() {
        edt_user = findViewById(R.id.edt_username);
        edt_pass = findViewById(R.id.edt_password);
        edt_age = findViewById(R.id.edt_age);
        btn_updateuser = findViewById(R.id.btn_update_user);
    }

    private void updateUser() {
        String strusername = edt_user.getText().toString().trim();
        String strpassword = edt_pass.getText().toString().trim();
        String strage = edt_age.getText().toString().trim();
        if (TextUtils.isEmpty(strusername) || TextUtils.isEmpty(strpassword) || TextUtils.isEmpty(strage)){
            return;
        }

        //Update User
        itemUser.setUserName(strusername);
        itemUser.setPassWord(strpassword);
        itemUser.setAge(strage);
        UserDatabase.getInstance(this).userDAO().updateUser(itemUser);

        Toast.makeText(UpdateActivity.this,"Update is Success!",Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK,intentResult);
        finish();
    }
}