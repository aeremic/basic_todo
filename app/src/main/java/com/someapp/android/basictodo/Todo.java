package com.someapp.android.basictodo;

import java.util.Date;
import java.util.UUID;

public class Todo {
    private UUID mId;
    private String mText;
    private Date mDate;
    private boolean mSolved;

    public Todo(){
        this(UUID.randomUUID());
    }

    public Todo(UUID id){
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
