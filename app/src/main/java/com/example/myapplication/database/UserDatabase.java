package com.example.myapplication.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.ItemUser;

@Database(entities = {ItemUser.class},version = 2) // khi muon chinh sua database => version se thay doi
public abstract class UserDatabase extends RoomDatabase {
    // Another query with databse

    // Insert many column in a table
    //ALTER TABLE ten_bang ADD cot1 kieu_du_lieu, cot2 kieu_du_lieu, cotn kieu_du_lieu

    //Chỉnh sữa kiểu dữ liệu cột trong một bảng
    // ALTER TABLE ten_bang ALTER COLUMN ten_cot kieu_du_lieu

    //Delete colum in a table
    //ALTER TABLE ten_bang DROP COLUMN ten_cot;

    // them mot cot vao database
    static Migration migration_from_1_to_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //ALTERT TABLE ten_bang ADD COLUMN ten_cot kieu_du_lieu
            database.execSQL("ALTER TABLE user ADD COLUMN age TEXT");
        }
    };

    private static final String DATABASE_NAME= "user.db";
    private static UserDatabase instance;

    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    // them cot moi vao database
                    .addMigrations(migration_from_1_to_2)
                    .build();
        }
        return instance;
    }
    public abstract UserDAO userDAO();


}
