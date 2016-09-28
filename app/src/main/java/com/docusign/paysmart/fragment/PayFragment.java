package com.docusign.paysmart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.docusign.paysmart.R;
import com.docusign.paysmart.dialog.ErrorDialogFragment;
import com.docusign.paysmart.utils.FormatExpirationDateTextWatcher;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.util.TextUtils;
import com.stripe.exception.AuthenticationException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by krshah on 9/27/16.
 */

public class PayFragment extends Fragment {
    public static final String TAG = PayFragment.class.getSimpleName();
    public static final String PUBLISHABLE_KEY = "pk_test_6pRNASCoBOKtIshFeQd4XMUh";
    @BindView(R.id.cardNumber)
    EditText mEditTextCardNum;
    @BindView(R.id.expMonth)
    EditText mEtExpMonth;
    @BindView(R.id.cvc)
    EditText mcvc;
    @BindView(R.id.paystripe)
    Button mStripePay;
    private PayFragmentListener mCallback;

    public static PayFragment newInstance(String amount) {
        return new PayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pay, container, false);
        ButterKnife.bind(this, root);
        setRetainInstance(true);
        mEtExpMonth.addTextChangedListener(new FormatExpirationDateTextWatcher(mEtExpMonth));
        return root;
    }

    @OnClick(R.id.paystripe)
    public void paymentClicked() {
        if (!isFormValid()) {
            return;
        }
        String[] tokens = mEtExpMonth.getText().toString().split("/");
        Card card = new Card(mEditTextCardNum.getText().toString(), Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), mcvc.getText().toString());
        Stripe stripe = null;
        try {
            stripe = new Stripe(PUBLISHABLE_KEY);
        } catch (AuthenticationException e) {
            Log.e(TAG, e.getMessage());
        }
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(Token token) {
                        // Send token to your server
                        mCallback.paymentCompleted(token);
                    }

                    public void onError(Exception error) {
                        // Show localized error message
                        handleError(error.getLocalizedMessage());
                    }
                }
        );
    }

    //TODO Migrate to Material Error Handling
    private boolean isFormValid() {
        if (TextUtils.isBlank(mEditTextCardNum.getText().toString())) {
            mEditTextCardNum.setError(getResources().getString(R.string.cardNumber_error));
            mEditTextCardNum.requestFocus();
            return false;
        }
        if (TextUtils.isBlank(mEtExpMonth.getText().toString())) {
            mEtExpMonth.setError(getResources().getString(R.string.expDate_error));
            mEtExpMonth.requestFocus();
            return false;
        }
        if (TextUtils.isBlank(mcvc.getText().toString())) {
            mcvc.setError(getResources().getString(R.string.cvc_error));
            mcvc.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (PayFragmentListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, "Cannot Cast Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mCallback != null) {
            mCallback = null;
        }
    }

    private void handleError(String error) {
        DialogFragment fragment = ErrorDialogFragment.newInstance(error);
        fragment.show(getActivity().getSupportFragmentManager(), "error");
    }

    public interface PayFragmentListener {
        void paymentCompleted(Token token);
    }

}

