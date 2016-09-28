package com.docusign.paysmart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.docusign.paysmart.R;
import com.docusign.paysmart.models.User;
import com.docusign.paysmart.utils.Helpers;
import com.docusign.paysmart.utils.RememberedData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by krshah on 9/24/16.
 */

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();
    private static String USER_EMAIL = "userEmail";
    private static String USER_PWD = "userPwd";
    private static String IS_REMEMBERED = "isRemembered";
    private static String SHOW_ERROR = "showError";
    private static String SHOW_U_ERROR = "userError";
    private static String SHOW_P_ERROR = "pwderror";
    @BindView(R.id.tvError)
    TextView mLoginError;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.cbRememberMe)
    CheckBox cbRememberMe;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.tvUserNameError)
    TextView tvUserNameError;
    @BindView(R.id.tvPwdError)
    TextView tvPwdError;
    private String mRememberedName;
    private LoginFragmentListener mCallback;
    private boolean mShowError;
    private boolean mShowUserError;
    private boolean mShowPwdError;
    private List<User> mUserStore;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, root);
        setRetainInstance(true);
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
            etUsername.setText(savedInstanceState.getString(USER_EMAIL));
            etPassword.setText(savedInstanceState.getString(USER_PWD));
            cbRememberMe.setChecked(savedInstanceState.getBoolean(IS_REMEMBERED));
            if (savedInstanceState.getBoolean(SHOW_ERROR) == true) {
                mLoginError.setVisibility(View.VISIBLE);
            }
            if (savedInstanceState.getBoolean(SHOW_U_ERROR) == true) {
                tvUserNameError.setVisibility(View.VISIBLE);
            }
            if (savedInstanceState.getBoolean(SHOW_P_ERROR) == true) {
                tvPwdError.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        setupFieldDefaults();
    }

    private void setupFieldDefaults() {
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        mRememberedName = RememberedData.getStoredEmail();
        boolean isRemembered = !TextUtils.isEmpty(mRememberedName);
        //Set checkbox for a remembered user
        cbRememberMe.setChecked(isRemembered);
        //if there is a remembered user, pre-populate the username field
        if (isRemembered) {
            etUsername.setText(mRememberedName);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (LoginFragmentListener) context;
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

    @OnClick(R.id.btPrimaryAction)
    public void signInClicked() {
        mLoginError.setVisibility(View.GONE);
        tvUserNameError.setVisibility(View.GONE);
        tvPwdError.setVisibility(View.GONE);
        mShowError = false;
        mShowUserError = false;
        mShowPwdError = false;
        final String userName = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            tvUserNameError.setVisibility(View.VISIBLE);
            etUsername.requestFocus();
            mShowUserError = true;
            return;
        }
        if (TextUtils.isEmpty(password)) {
            tvPwdError.setVisibility(View.VISIBLE);
            etPassword.requestFocus();
            mShowPwdError = true;
            return;
        }
        boolean rememberMe = cbRememberMe.isChecked();
        if (rememberMe) {
            RememberedData.setUserEmail(userName);
        } else {
            RememberedData.setUserEmail("");
        }
        mUserStore = Helpers.getSeedUserData(getActivity());
        mProgressBar.setVisibility(View.VISIBLE);
        Handler h = new Handler();
        //Delay to simulate n/w call
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
                if (checkIfUserIsValid(userName, password) && mCallback != null) {
                    mCallback.signInSuccess(userName);
                } else {
                    mLoginError.setVisibility(View.VISIBLE);
                    mShowError = true;
                }
            }
        }, 500);

    }

    private boolean checkIfUserIsValid(String userName, String pwd) {
        if (mUserStore != null) {
            for (User user : mUserStore) {
                return user.getEmail().equals(userName) && user.getPassword().equals(pwd);
            }
        }
        return false;
    }

    /**
     * Support Orientation Change
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_REMEMBERED, !TextUtils.isEmpty(mRememberedName));
        outState.putBoolean(SHOW_ERROR, mShowError);
        outState.putBoolean(SHOW_U_ERROR, mShowUserError);
        outState.putBoolean(SHOW_P_ERROR, mShowPwdError);
        outState.putString(USER_EMAIL, etUsername.getText().toString());
        outState.putString(USER_PWD, etPassword.getText().toString());
    }

    public interface LoginFragmentListener {
        void signInSuccess(String userName);
    }
}
