package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.database.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;
    private EditText edt_user;
    private EditText edt_pass;
    private EditText edt_age;
    private Button btn_add;
    private TextView tv_deleteall;
    private EditText edt_search;
    private RecyclerView recyclerView;
    private ItemUserAdappter itemUserAdappter;
    private List<ItemUser> itemUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        itemUserAdappter = new ItemUserAdappter(new ItemUserAdappter.IClickItemUser() {
            @Override
            public void updateUser(ItemUser itemUser) {
                clickUpdateUser(itemUser);
            }

            @Override
            public void deteleUser(ItemUser itemUser) {
                clickDeleteUser(itemUser);
            }
        });
        itemUsers = new ArrayList<>();
        itemUserAdappter.setData(itemUsers);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemUserAdappter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        tv_deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDeleteAllUser();
            }
        });

        // search dữ liệu
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    // logic action search
                    handleSearchUser();
                }
                return false;
            }
        });
        loadData();
    }



    private void initUi() {
        edt_user = findViewById(R.id.edt_username);
        edt_pass = findViewById(R.id.edt_password);
        edt_age = findViewById(R.id.edt_age);
        btn_add = findViewById(R.id.btn_add);
        edt_search = findViewById(R.id.edt_search);
        tv_deleteall = findViewById(R.id.tv_delete_all);
        recyclerView = findViewById(R.id.rcv_user);
    }
    private void addUser() {
        String strUserName = edt_user.getText().toString().trim();
        String strPassWord = edt_pass.getText().toString().trim();
        String strAge = edt_age.getText().toString().trim();
        if(TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strPassWord)){
            return;
        }
        ItemUser itemUser = new ItemUser(strUserName,strPassWord,strAge);

        if (isUserExist(itemUser)){
            Toast.makeText(this,"Insert Data is wrong!",Toast.LENGTH_SHORT).show();
            return;
        }

        UserDatabase.getInstance(this).userDAO().inserUser(itemUser);
        Toast.makeText(this,"Add User Success",Toast.LENGTH_LONG).show();

        edt_user.setText("");
        edt_pass.setText("");
        edt_age.setText("");
        hideSoftKeyboard();
        loadData();

    }
    // Hide Keyboard
    public void hideSoftKeyboard(){
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (NullPointerException ex){
            ex.printStackTrace();

        }
    }
    public  void loadData(){
        itemUsers= UserDatabase.getInstance(this).userDAO().getListItemUser();
        itemUserAdappter.setData(itemUsers);
    }
    private boolean isUserExist(ItemUser itemUser){
        List<ItemUser>list = UserDatabase.getInstance(this).userDAO().checkUser(itemUser.getUserName());
        return list!= null && !list.isEmpty();
    }

    // Xự kiện update ở list recycler view
    private void clickUpdateUser(ItemUser itemUser) {

        Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user",itemUser);
        intent.putExtras(bundle);
        startActivityForResult(intent,MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            loadData();
        }
    }

    private void clickDeleteUser(ItemUser itemUser) {
        new AlertDialog.Builder(this)
                .setTitle("Comfirm delete user")
                .setMessage("Are you sure")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete user
                        UserDatabase.getInstance(MainActivity.this).userDAO().deleteUser(itemUser);
                        Toast.makeText(MainActivity.this,"Delete user is  success",Toast.LENGTH_SHORT).show();
                        loadData();

                    }
                })
                .setNegativeButton("No",null)
                .show();

    }
    private void clickDeleteAllUser() {
        new AlertDialog.Builder(this)
                .setTitle("Comfirm delete all user")
                .setMessage("Are you sure")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete user
                        UserDatabase.getInstance(MainActivity.this).userDAO().deleteAllUser();
                        Toast.makeText(MainActivity.this,"Delete all user is  success",Toast.LENGTH_SHORT).show();
                        loadData();

                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    private void handleSearchUser() {
        String strkeyword = edt_search.getText().toString().trim();
        itemUsers = new ArrayList<>();
        itemUsers = UserDatabase.getInstance(this).userDAO().searchUser(strkeyword);
        itemUserAdappter.setData(itemUsers);
        hideSoftKeyboard();
    }


}