package com.example.juda.weathy;

import android.widget.RadioButton;

/**
 * Created by Juda on 05/08/2017.
 */

public class RadioButtonObject {

    private RadioButton button;
    private String buttonName;

    public RadioButtonObject(RadioButton button, String buttonName) {
        this.button = button;
        this.buttonName = buttonName;
    }

    public RadioButton getButton() {
        return button;
    }

    public void setButton(RadioButton button) {
        this.button = button;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }
}
