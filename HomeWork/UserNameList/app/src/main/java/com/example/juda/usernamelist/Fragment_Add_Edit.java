package com.example.juda.usernamelist;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Juda on 18/11/2016.
 */

public class Fragment_Add_Edit extends DialogFragment {

    TextView txtFrag;
    EditText editUser;
    EditText editPass;
    String user;
    String password;
    Button btnFragment;
    boolean edit;
    FragmentResource listener;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public void setListener(FragmentResource listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_fragment, null);
        txtFrag = (TextView)view.findViewById(R.id.txtFrag);
        editUser = (EditText)view.findViewById(R.id.editUser);
        editPass = (EditText)view.findViewById(R.id.editPassword);
        btnFragment = (Button)view.findViewById(R.id.btnFragment);

        if (edit){
            txtFrag.setText("Edit username & password:");
            editUser.setHint(user);
            editPass.setHint(password);
            btnFragment.setText("Edit");
        }else{
            txtFrag.setText("New username or password:");
            editUser.setHint("username");
            editUser.setHint("password");
            btnFragment.setText("New");
        }
        btnFragment.setOnClickListener(onClickListener);
        editUser.requestFocus();

        return view;
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String user = editUser.getText().toString();
            String password = editPass.getText().toString();
            if (edit){
                if (listener != null)
                listener.editUser(user,password);
                dismiss();
            }else {
                if (listener!= null)
                    listener.addNewUser(user,password);
                dismiss();
            }
        }
    };

    public interface FragmentResource {
        void addNewUser (String username, String password);
        void editUser (String username, String  password);
    }
}
