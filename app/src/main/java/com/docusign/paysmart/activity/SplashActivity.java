package com.docusign.paysmart.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.docusign.paysmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getCanonicalName();

    @BindView(R.id.ivAppLogo)
    ImageView mIvAppLogo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        rotateCircular();
        Handler h = new Handler();
        //Delay to simulate n/w call
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchSetup();
            }
        }, 1000);
    }

    private void launchSetup() {
        startActivity(LoginActivity.createIntent(this));
        this.finish();
    }

    private void rotateCircular() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        mIvAppLogo.startAnimation(rotateAnimation);
    }
}