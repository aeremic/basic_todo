package com.someapp.android.basictodo.database;

public class TodoDbSchema {
    public static final class TodoTable {
        public static final String NAME = "todos";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TEXT = "text";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
