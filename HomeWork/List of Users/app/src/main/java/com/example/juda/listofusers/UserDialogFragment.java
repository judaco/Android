package com.example.juda.listofusers;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Juda on 26/11/2016.
 */

public class UserDialogFragment extends DialogFragment {

    private User user;
    private EditText txtUserName;
    private EditText txtPassword;
    private InputUserListener listener;

    public void setUser(User user){
        this.user = user;
    }

    public void setListener(InputUserListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        txtUserName = (EditText)view.findViewById(R.id.txtUserName);
        txtPassword = (EditText)view.findViewById(R.id.txtPassword);
        if(user != null){
            txtUserName.setText(user.getUserName());
            txtPassword.setText(user.getPassword());
        }
        Button btnDone = (Button)view.findViewById(R.id.btnDone);
        Button btnCancel = (Button)view.findViewById(R.id.btnCancel);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                if(user == null){
                    user = new User(userName, password, R.mipmap.ic_launcher);
                }else{
                    user.setUserName(userName);
                    user.setPassword(password);
                }
                if(listener != null)
                    listener.onDone(user);
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    interface InputUserListener{
        void onDone(User user);
    }
}
