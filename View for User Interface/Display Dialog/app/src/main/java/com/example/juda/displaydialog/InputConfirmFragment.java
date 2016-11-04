package com.example.juda.displaydialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Juda on 04/11/2016.
 */

public class InputConfirmFragment extends DialogFragment {

    Button btnYes, btnNo;
    String dialogTitle = "";
    YesNoListener listener;

    public void setListener(YesNoListener listener) {
        this.listener = listener;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_confirm, container);
        btnYes = (Button)view.findViewById(R.id.btnYes);
        btnNo = (Button)view.findViewById(R.id.btnNo);
        btnYes.setOnClickListener(btnListener);
        btnNo.setOnClickListener(btnListener);
        getDialog().setTitle(dialogTitle);

        return view;
    }
    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean yesOrNo = v == btnYes;//comparing the pointers
            if (listener != null)
                listener.yesOrNo(yesOrNo);
            dismiss();
        }
    };

    public static interface YesNoListener {
        void yesOrNo (boolean yesOrNo);
    }
}
