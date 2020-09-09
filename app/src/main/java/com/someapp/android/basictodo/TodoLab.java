package com.someapp.android.basictodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.someapp.android.basictodo.database.TodoBaseHelper;
import com.someapp.android.basictodo.database.TodoCursorWraper;
import com.someapp.android.basictodo.database.TodoDbSchema;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TodoLab {
    private static TodoLab sTodoLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TodoLab get(Context context){
        if(sTodoLab == null) {
            sTodoLab = new TodoLab(context);
        }
        return sTodoLab;
    }

    private TodoLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new TodoBaseHelper(context)
                .getWritableDatabase();
    }

    public List<Todo> getTodos(){
        List<Todo> todos = new ArrayList<>();

        TodoCursorWraper cursor = queryTodos(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                todos.add(cursor.getTodo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return todos;
    }

    public Todo getTodo(UUID id){
        TodoCursorWraper cursor = queryTodos(
                TodoDbSchema.TodoTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTodo();
        } finally {
            cursor.close();
        }
    }

    public void updateTodo(Todo todo){
        String uuidString = todo.getId().toString();
        ContentValues values = getContentValues(todo);

        mDatabase.update(TodoDbSchema.TodoTable.NAME, values,
                TodoDbSchema.TodoTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private TodoCursorWraper queryTodos(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                TodoDbSchema.TodoTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new TodoCursorWraper(cursor);
    }

    private static ContentValues getContentValues(Todo todo){
        ContentValues values = new ContentValues();
        values.put(TodoDbSchema.TodoTable.Cols.UUID, todo.getId().toString());
        values.put(TodoDbSchema.TodoTable.Cols.TEXT, todo.getText());
        values.put(TodoDbSchema.TodoTable.Cols.DATE, todo.getDate().getTime());
        values.put(TodoDbSchema.TodoTable.Cols.SOLVED, todo.isSolved() ? 1 : 0);

        return values;
    }

    public void addTodo(Todo t){
        ContentValues values = getContentValues(t);

        mDatabase.insert(TodoDbSchema.TodoTable.NAME, null, values);
    }

    public void deleteTodo(Todo t){
        mDatabase.delete(TodoDbSchema.TodoTable.NAME,
                TodoDbSchema.TodoTable.Cols.UUID + " = ?",
                new String[] { t.getId().toString() });
    }

}
