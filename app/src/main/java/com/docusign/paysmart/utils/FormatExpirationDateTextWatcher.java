package com.docusign.paysmart.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by krshah on 9/27/16.
 */

public class FormatExpirationDateTextWatcher implements TextWatcher {

    private EditText et;

    public FormatExpirationDateTextWatcher(EditText et) {
        this.et = et;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        et.removeTextChangedListener(this);
        String input = et.getText().toString();
        if (input.length() == 2) {
            et.setText(input + "/");
        }
        et.setSelection(et.getText().length());
        et.addTextChangedListener(this);
    }
}
