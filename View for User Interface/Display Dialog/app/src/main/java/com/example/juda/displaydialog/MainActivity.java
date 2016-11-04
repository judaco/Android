package com.example.juda.displaydialog;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements InputUserNameDialogFragment.UserNameListener, InputUserNameDialogFragment.PasswordListener, InputConfirmFragment.YesNoListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnShowInputUserNameDialog(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        InputUserNameDialogFragment inputUserNameDialogFragment = new InputUserNameDialogFragment();
        inputUserNameDialogFragment.setCancelable(false);
        inputUserNameDialogFragment.setDialogTitle("Enter Username");
        inputUserNameDialogFragment.setListener(this);
        inputUserNameDialogFragment.show(fragmentManager, "OK");
    }

    @Override
    public void hasPassword(String password) {
        Toast.makeText(this, "Password is: " + password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hasUserName(String userName) {
        Toast.makeText(this, "UserName is: " + userName, Toast.LENGTH_SHORT).show();
    }

    public void btnShowInputConfirm(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        InputConfirmFragment inputConfirmFragment = new InputConfirmFragment();
        inputConfirmFragment.setDialogTitle("Are you sure?");
        inputConfirmFragment.setListener(this);
        inputConfirmFragment.show(fragmentManager, "Fine");
    }

    @Override
    public void yesOrNo(boolean yesOrNo) {
        Toast.makeText(this, "your answer is: " + (yesOrNo ? "yes" : "No"), Toast.LENGTH_SHORT).show();
    }


}
