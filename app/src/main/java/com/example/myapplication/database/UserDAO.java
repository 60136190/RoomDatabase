package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.ItemUser;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void inserUser(ItemUser itemUser);

    @Query("SELECT * FROM user")
    List<ItemUser> getListItemUser();

    @Query("SELECT * FROM user where userName= :userName")
    List<ItemUser> checkUser(String userName);

    @Update
    void updateUser(ItemUser itemUser);

    @Delete
    void deleteUser(ItemUser itemUser);

    @Query("DELETE FROM user")
    void deleteAllUser();

    @Query("SELECT * FROM user WHERE userName LIKE '%' || :name || '%'")
    List<ItemUser> searchUser(String name);

}
