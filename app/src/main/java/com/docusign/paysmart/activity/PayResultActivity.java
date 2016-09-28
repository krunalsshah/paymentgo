package com.docusign.paysmart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.docusign.paysmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by krshah on 9/27/16.
 */

public class PayResultActivity extends BaseActivity {

    @BindView(R.id.btDone)
    Button mBtnDone;
    @BindView(R.id.ivSuccess)
    ImageView mivSuccess;

    public static Intent createIntent(Context fromCtx) {
        return new Intent(fromCtx, PayResultActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        ButterKnife.bind(this);
        rotateCircular(mivSuccess, 1);
    }

    @OnClick(R.id.btDone)
    public void DoneClicked() {
        startActivity(LoginActivity.createIntent(this));
        this.finish();
    }
}
