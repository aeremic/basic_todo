package com.someapp.android.basictodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TodoListFragment extends Fragment {

    private RecyclerView mTodoRecycleView;
    private TodoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mTodoRecycleView = (RecyclerView) v.findViewById(R.id.todo_recycler_view);
        mTodoRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_todo:
                Todo todo = new Todo();
                TodoLab.get(getActivity()).addTodo(todo);
                Intent intent = TodoPagerActivity
                        .newIntent(getActivity(), todo.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        TodoLab todoLab = TodoLab.get(getActivity());
        List<Todo> todos = todoLab.getTodos();

        if(mAdapter == null) {
            mAdapter = new TodoAdapter(todos);
            mTodoRecycleView.setAdapter(mAdapter);
        } else {
            mAdapter.setTodos(todos);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Todo mTodo;
        private TextView mTextTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public TodoHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_todo, parent, false));

            itemView.setOnClickListener(this);

            mTextTextView = (TextView) itemView.findViewById(R.id.todo_text);
            mDateTextView = (TextView) itemView.findViewById(R.id.todo_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.todo_solved);
        }

        public void bind(Todo todo){
            mTodo = todo;
            mTextTextView.setText(mTodo.getText());
            mDateTextView.setText(mTodo.getDate().toString());
            mSolvedImageView.setVisibility(todo.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view){
            Intent intent = TodoPagerActivity.newIntent(getActivity(), mTodo.getId());
            startActivity(intent);
        }
    }

    private class TodoAdapter extends RecyclerView.Adapter<TodoHolder>{

        private List<Todo> mTodos;

        public TodoAdapter(List<Todo> todos){
            mTodos = todos;
        }

        @Override
        public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new TodoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position){
            Todo todo = mTodos.get(position);
            holder.bind(todo);
        }

        @Override
        public int getItemCount(){
            return mTodos.size();
        }

        public void setTodos(List<Todo> todos){
            mTodos = todos;
        }
    }
}
