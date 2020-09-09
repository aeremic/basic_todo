package com.someapp.android.basictodo;

import androidx.fragment.app.Fragment;

public class TodoListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new TodoListFragment();
    }
}
