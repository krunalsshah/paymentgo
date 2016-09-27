package com.docusign.paymentgo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.docusign.paymentgo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by krshah on 9/27/16.
 */

public class PayResultActivity extends BaseActivity {

    @BindView(R.id.btDone)
    Button mDone;

    public static Intent createIntent(Context fromCtx) {
        return new Intent(fromCtx, PayResultActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btDone)
    public void DoneClicked() {
        startActivity(LoginActivity.createIntent(this));
        this.finish();
    }

}
