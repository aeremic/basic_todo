package com.someapp.android.basictodo.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.someapp.android.basictodo.Todo;
import java.util.Date;
import java.util.UUID;

public class TodoCursorWraper extends CursorWrapper {
    public TodoCursorWraper(Cursor cursor){
        super(cursor);
    }

    public Todo getTodo(){
        String uuidString = getString(getColumnIndex(TodoDbSchema.TodoTable.Cols.UUID));
        String text = getString(getColumnIndex(TodoDbSchema.TodoTable.Cols.TEXT));
        long date = getLong(getColumnIndex(TodoDbSchema.TodoTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(TodoDbSchema.TodoTable.Cols.SOLVED));

        Todo todo = new Todo(UUID.fromString(uuidString));
        todo.setText(text);
        todo.setDate(new Date(date));
        todo.setSolved(isSolved != 0);

        return todo;
    }
}