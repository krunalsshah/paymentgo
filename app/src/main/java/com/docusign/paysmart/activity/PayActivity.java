package com.docusign.paysmart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.docusign.paysmart.R;
import com.docusign.paysmart.fragment.PayFragment;
import com.stripe.android.model.Token;

import butterknife.ButterKnife;

/**
 * Created by krshah on 9/27/16.
 */

public class PayActivity extends BaseActivity implements com.docusign.paysmart.fragment.PayFragment.PayFragmentListener {
    public static final String TAG = PayActivity.class.getCanonicalName();
    public static final String AMOUNT = "amount";
    String mAmount;
    PayFragment mPayFragment;

    public static Intent createIntent(Context frmCtx, String amount) {
        Intent intent = new Intent(frmCtx, PayActivity.class);
        intent.putExtra(AMOUNT, amount);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            mAmount = intent.getStringExtra(AMOUNT);
        }
        if (savedInstanceState == null) {
            initFragments();
        }
    }

    private void initFragments() {
        if (mPayFragment == null) {
            mPayFragment = PayFragment.newInstance(mAmount);
            loadFragment(mPayFragment, false, R.id.flPay, PayFragment.TAG);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPayFragment = (PayFragment) getSupportFragmentManager().findFragmentByTag(PayFragment.TAG);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, PayFragment.TAG, mPayFragment);
    }

    @Override
    public void paymentCompleted(Token token) {
        Log.d(TAG, "Token : " + token.getCard().getLast4());
        startActivity(PayResultActivity.createIntent(this));
        this.finish();
    }

}
