package com.example.juda.listofusers;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends Activity{

    List<User> users;
    UserAdapter adapter;
    ListView lstUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users = new ArrayList<>();
        adapter = new UserAdapter(this, users);
        lstUsers = (ListView)findViewById(R.id.lstUsers);
        lstUsers.setAdapter(adapter);
        lstUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDialogFragment fragment = new UserDialogFragment();
                fragment.setUser(users.get(position));
                fragment.setListener(new UserDialogFragment.InputUserListener() {
                    @Override
                    public void onDone(User user) {
                        //user fields already get updated inside the fragment
                        adapter.notifyDataSetChanged();
                    }
                });
                fragment.show(getFragmentManager(), "fragment");
            }
        });
    }

    public void btnAddUser(View view) {
        UserDialogFragment fragment = new UserDialogFragment();
        fragment.setListener(new UserDialogFragment.InputUserListener() {
            @Override
            public void onDone(User user) {
                users.add(user);
                adapter.notifyDataSetChanged();
            }
        });
        fragment.show(getFragmentManager(), "fragment");
    }

}
