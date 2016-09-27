package com.docusign.paysmart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.docusign.paysmart.R;
import com.docusign.paysmart.fragment.LoginFragment;

import butterknife.ButterKnife;

/**
 * Created by krshah on 9/24/16.
 * Activity to handle Login flow
 */

public class LoginActivity extends BaseActivity implements LoginFragment.LoginFragmentListener {

    public static final String TAG = LoginActivity.class.getCanonicalName();
    public static final String EMAIL = "userEmail";
    private LoginFragment mLoginFragment;

    public static Intent createIntent(Context fromCtx) {
        return new Intent(fromCtx, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if(savedInstanceState == null){
            initFragments();
        }
    }

    private void initFragments() {
        if (mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance();
            loadFragment(mLoginFragment, false, R.id.flSignIn, LoginFragment.TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag(LoginFragment.TAG);
    }

    @Override
    public void signInSuccess(String userName) {
        Log.d(TAG, "signInSuccess : " + userName);
        startActivity(HomeActivity.createIntent(this, userName));
        this.finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, LoginFragment.TAG, mLoginFragment);
    }
}
