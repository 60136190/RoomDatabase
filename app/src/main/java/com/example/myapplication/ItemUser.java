package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user")
public class ItemUser implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userName;
    private String passWord;
    private String age;

    public ItemUser(String userName, String passWord, String age) {
        this.userName = userName;
        this.passWord = passWord;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
