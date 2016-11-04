package com.example.juda.displaydialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Juda on 04/11/2016.
 */

public class InputUserNameDialogFragment extends DialogFragment {
    EditText txtUserName;
    EditText txtPassword;
    Button btnDone;
    UserNameListener listener;
    PasswordListener pswlistener;
    String dialogTitle = "";

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public void setListener(UserNameListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_username_dialog, container);
        txtUserName = (EditText)view.findViewById(R.id.txtUserName);
        txtPassword = (EditText)view.findViewById(R.id.txtPassword);
        btnDone = (Button)view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDone(v);
            }
        });
        txtUserName.requestFocus();//saman
        txtPassword.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);//keyboard upload automatically
        getDialog().setTitle(dialogTitle);
        txtUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    btnDone(null);
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    public void btnDone (View view) {
        String userName = txtUserName.getText().toString();
        if (listener != null) {
            listener.hasUserName(userName);
        }
        String password = txtPassword.getText().toString();
        if (pswlistener != null){
            pswlistener.hasPassword(password);
        }
        dismiss();
    }

    public static interface UserNameListener {
        void hasUserName(String userName);
    }
    public static interface PasswordListener{
        void hasPassword(String password);
    }
}
