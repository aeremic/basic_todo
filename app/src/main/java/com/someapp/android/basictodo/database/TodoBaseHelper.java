package com.someapp.android.basictodo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "todoBase.db";

    public TodoBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + TodoDbSchema.TodoTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TodoDbSchema.TodoTable.Cols.UUID + ", " +
                TodoDbSchema.TodoTable.Cols.TEXT + ", " +
                TodoDbSchema.TodoTable.Cols.DATE + ", " +
                TodoDbSchema.TodoTable.Cols.SOLVED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
