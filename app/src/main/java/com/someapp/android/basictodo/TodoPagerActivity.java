package com.someapp.android.basictodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.List;
import java.util.UUID;

public class TodoPagerActivity extends AppCompatActivity {

    private static final String EXTRA_TODO_ID =
            "com.someapp.android.basictodo.todo_id";

    private ViewPager mViewPager;
    private List<Todo> mTodos;

    public static Intent newIntent(Context packageContext, UUID todoId){
        Intent intent = new Intent(packageContext, TodoPagerActivity.class);
        intent.putExtra(EXTRA_TODO_ID, todoId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_pager);

        mViewPager = (ViewPager) findViewById(R.id.todo_view_pager);

        mTodos = TodoLab.get(this).getTodos();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {


            @Override
            public Fragment getItem(int position) {
                Todo todo = mTodos.get(position);
                return TodoFragment.newInstance(todo.getId());
            }

            @Override
            public int getCount() {
                return mTodos.size();
            }
        });

        UUID todoId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_TODO_ID);

        for(int i=0;i<mTodos.size();i++){
            if(mTodos.get(i).getId().equals(todoId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
