package com.example.juda.listofusers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Juda on 26/11/2016.
 */

public class UserAdapter extends ArrayAdapter<User> {

    Activity activity;
    List<User> users;

    public UserAdapter(Activity activity, List<User> users) {
        super(activity, R.layout.item_user, users);
        this.activity = activity;
        this.users = users;
    }


    static class ViewContainer{
        ImageView imgUser;
        TextView lblUserName;
        TextView lblPassword;
        Button btnDelete;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        ViewContainer viewContainer;
        View rowView = convertView;
        if(rowView == null){
            rowView = inflater.inflate(R.layout.item_user, null);
            viewContainer = new ViewContainer();
            viewContainer.lblUserName = (TextView)rowView.findViewById(R.id.lblUserName);
            viewContainer.lblPassword = (TextView)rowView.findViewById(R.id.lblPassword);
            viewContainer.btnDelete = (Button) rowView.findViewById(R.id.btnDeleteUser);
            viewContainer.imgUser = (ImageView) rowView.findViewById(R.id.imgUser);
            rowView.setTag(viewContainer);
            viewContainer.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (Integer)v.getTag();
                    users.remove(position);
                    notifyDataSetChanged();
                }
            });

        }else{
            viewContainer = (ViewContainer) rowView.getTag();
        }
        User user = users.get(position);
        viewContainer.imgUser.setImageResource(user.getImage());
        viewContainer.lblUserName.setText(user.getUserName());
        viewContainer.lblPassword.setText(user.getPassword());
        viewContainer.btnDelete.setTag(position);
        return rowView;
    }
}
