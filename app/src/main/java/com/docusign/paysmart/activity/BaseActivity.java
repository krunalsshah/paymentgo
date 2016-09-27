package com.docusign.paysmart.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by krshah on 9/25/16.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected void loadFragment(Fragment fragment, boolean addToBackStack, int resourceId, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(resourceId, fragment, tag);
        transaction = addToBackStack ?
                transaction.addToBackStack(fragment.getClass().getSimpleName())
                : transaction.disallowAddToBackStack();
        transaction.commit();
    }
}
