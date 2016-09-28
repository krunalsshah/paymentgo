package com.docusign.paysmart.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.docusign.paysmart.R;

/**
 * Created by krshah on 9/27/16.
 */

public class ErrorDialogFragment extends DialogFragment {

    public static final String MESSAGE = "msg";
    public ErrorDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static ErrorDialogFragment newInstance(String message) {
        ErrorDialogFragment fragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MESSAGE);
        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(
                        R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .create();
    }
}

