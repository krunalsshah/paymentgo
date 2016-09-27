package com.docusign.paymentgo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.docusign.paymentgo.R;
import com.docusign.paymentgo.utils.AppendCurrencyTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

/**
 * Created by krshah on 9/26/16.
 */

public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private static String USER_EMAIL = "userEmail";
    private String mUserEmail;

    @BindView(R.id.tv_welcome)
    TextView mTvWelcome;
    @BindView(R.id.ivInvoice)
    ImageView mIvInvoice;
    @BindView(R.id.etAmount)
    EditText mEtAmt;
    @BindView(R.id.btPay)
    Button mBtPay;

    public interface HomeFragmentListener {
        void paymentResult();
    }

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
        mEtAmt.addTextChangedListener( new AppendCurrencyTextWatcher(mEtAmt));
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

}
