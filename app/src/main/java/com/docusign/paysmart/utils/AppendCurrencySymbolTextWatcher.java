package com.docusign.paysmart.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by krshah on 9/27/16.
 */

public class AppendCurrencySymbolTextWatcher implements TextWatcher {

    private EditText et;

    public AppendCurrencySymbolTextWatcher(EditText et) {
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
        if (input.length() > 1) {
            et.setText("$" + input.substring(1));
        } else if(input.length() ==  1 && !input.equals("$")) {
            et.setText("$" + input);
        } else {
            et.setText("");
        }
        et.setSelection(et.getText().length());
        et.addTextChangedListener(this);
    }
}
