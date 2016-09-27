package com.docusign.paymentgo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.docusign.paymentgo.R;
import com.docusign.paymentgo.fragment.HomeFragment;
import com.docusign.paymentgo.fragment.PayFragment;
import com.stripe.android.model.Token;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        PayFragment.PayFragmentListener, HomeFragment.HomeFragmentListener {

    public static final String TAG = BaseActivity.class.getCanonicalName();
    public static final String EMAIL = "userEmail";
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    String mUserName;
    ActionBarDrawerToggle mDrawerToggle;
    Toolbar toolbar;
    HomeFragment mHomeFragment;
    PayFragment mPayFragment;

    public static Intent createIntent(Context frmCtx, String userName) {
        Intent intent = new Intent(frmCtx, HomeActivity.class);
        intent.putExtra(LoginActivity.EMAIL, userName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            mUserName = intent.getStringExtra(LoginActivity.EMAIL);
        }
        if (savedInstanceState == null) {
            initFragments();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initNavDrawer();
    }

    private void initFragments() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance(mUserName);
            loadFragment(mHomeFragment, false, R.id.flHome, HomeFragment.TAG);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
    }

    private void initNavDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView tvProfileEmail = ButterKnife.findById(header, R.id.tvProfileEmail);
        tvProfileEmail.setText(mUserName);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(mDrawerToggle);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.payment_history) {
            Toast.makeText(this, "Payment History", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_logout) {
            Handler h = new Handler();
            //Delay to simulate n/w call
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    logoutUser();
                }
            }, 500);
        }
        return true;
    }

    private void logoutUser() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, HomeFragment.TAG, mHomeFragment);
    }

    @Override
    public void paymentCompleted(Token token) {
        Log.d(TAG, "Token : " + token.getCard().getLast4());
        startActivity(PayResultActivity.createIntent(this));
        this.finish();
    }

    @Override
    public void onPayButtonClicked() {
        if (mPayFragment == null) {
            mPayFragment = PayFragment.newInstance();
            loadFragment(mPayFragment, false, R.id.flHome, PayFragment.TAG);
        }
    }
}
