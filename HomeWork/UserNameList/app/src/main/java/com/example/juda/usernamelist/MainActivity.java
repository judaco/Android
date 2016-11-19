package com.example.juda.usernamelist;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements Fragment_Add_Edit.FragmentResource{

    ListView userNameList;
    List <User> users;
    int p;
    AdapterList adapterList;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inflate the array list with date = add user into the array list
        userNameList = (ListView)findViewById(R.id.userNameList);
        users = new ArrayList<>();
        users.add(new User("Juda", "juda"));
        users.add(new User("Fabi", "fabi"));

        //set user into the adapter = telling the listview to set as the adapter
        //This is our custom adapter = AdapterList
        adapterList = new AdapterList(this,users);
        userNameList.setAdapter(adapterList);
        //get on item click listener
        userNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                fragmentManager= getFragmentManager();
                Fragment_Add_Edit fragmentAddEdit = new Fragment_Add_Edit();
                fragmentAddEdit.setEdit(true);
                fragmentAddEdit.setListener(MainActivity.this);
                p = i;
                fragmentAddEdit.setPassword(users.get(i).getPassword());
                fragmentAddEdit.setUser(users.get(i).getUsername());
                fragmentAddEdit.show(fragmentManager, "");
            }
        });

    }

    @Override
    public void addNewUser(String username, String password) {
        users.add(new User(username,password));
        userNameList.setAdapter(adapterList);
        adapterList.notifyDataSetChanged();
    }

    @Override
    public void editUser(String username, String password) {
        users.set(p, new User(username,password));
        adapterList.notifyDataSetChanged();
    }

    public void btnAdd(View view) {
        fragmentManager = getFragmentManager();
        Fragment_Add_Edit fragment_add_edit = new Fragment_Add_Edit();
        fragment_add_edit.setEdit(false);
        fragment_add_edit.setCancelable(false);
        fragment_add_edit.setListener(this);
        fragment_add_edit.show(fragmentManager,"");
    }

    private class AdapterList extends ArrayAdapter<User> {
        Activity activity;
        List<User> users;

        public AdapterList(Activity activity, List<User> users) {
            super(activity, R.layout.item_user, users);
            this.activity = activity;
            this.users = users;
        }

        class ViewContainer{
            TextView txtUser;
            Button Delete;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            View view = convertView;
            ViewContainer viewContainer = null;
            if (view == null){
                LayoutInflater inflater = activity.getLayoutInflater();
                view = inflater.inflate(R.layout.item_user, null);
                viewContainer = new ViewContainer();
                viewContainer.txtUser = (TextView)view.findViewById(R.id.txtUser);
                viewContainer.Delete = (Button)view.findViewById(R.id.btnDelete);
                view.setTag(viewContainer);
            }else {
                viewContainer = (ViewContainer)view.getTag();
            }
            viewContainer.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        users.remove(position);
                    adapterList.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "You deleted: " + users.get(position).getUsername(), Toast.LENGTH_SHORT).show();
                }
            });

            viewContainer.txtUser.setText(users.get(position).getUsername());
            return view;
        }
    }
}
