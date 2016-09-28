package com.docusign.paysmart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.docusign.paysmart.R;
import com.docusign.paysmart.utils.AppendCurrencySymbolTextWatcher;
import com.stripe.android.util.TextUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by krshah on 9/26/16.
 */

public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private static String USER_EMAIL = "userEmail";
    @BindView(R.id.tv_welcome)
    TextView mTvWelcome;
    @BindView(R.id.ivInvoice)
    ImageView mIvInvoice;
    @BindView(R.id.etAmount)
    EditText mEtAmt;
    @BindView(R.id.btPay)
    Button mBtPay;
    private String mUserEmail;
    private HomeFragmentListener mCallback;

    public static HomeFragment newInstance(String userName) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USER_EMAIL, userName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserEmail = getArguments().getString(USER_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        setRetainInstance(true);
        if (mUserEmail != null) {
            mTvWelcome.setText(getResources().getText(R.string.welcome_msg) + " " + mUserEmail.substring(0, mUserEmail.indexOf("@")) + "!!");
        }
        mEtAmt.addTextChangedListener(new AppendCurrencySymbolTextWatcher(mEtAmt));
        return root;
    }

    /**
     * Retain State
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mUserEmail = savedInstanceState.getString(USER_EMAIL);
            if (mUserEmail != null) {
                mTvWelcome.setText(getResources().getText(R.string.welcome_msg) + " " + mUserEmail.substring(0, mUserEmail.indexOf("@")) + "!!");
            }
        }
    }

    /**
     * Support Orientation Change
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(USER_EMAIL, mUserEmail);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (HomeFragmentListener) context;
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

    @OnClick(R.id.btPay)
    public void payClicked() {
        if (!isFormValid()) {
            return;
        }
        if (mCallback != null) {
            mCallback.onPayButtonClicked(mEtAmt.getText().toString());
        }
    }

    private boolean isFormValid() {
        if (TextUtils.isBlank(mEtAmt.getText().toString())) {
            mEtAmt.setError(getResources().getString(R.string.amount_error));
            mEtAmt.requestFocus();
            return false;
        }
        return true;
    }

    public interface HomeFragmentListener {
        void onPayButtonClicked(String amount);
    }
}
