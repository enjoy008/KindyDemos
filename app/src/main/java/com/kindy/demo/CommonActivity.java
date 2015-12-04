package com.kindy.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kindy.demo.ui.CircleViewFragment;
import com.kindy.demo.ui.FlowFragment;

/**
 * Created by Kindy on 2015/12/4.
 */
public class CommonActivity extends AppCompatActivity {
    public static String TARGET = "target";

    public static final int TARGET_FLOW       = 1;
    public static final int TARGET_CIRCLEVIEW = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_main);

        showFragment();
    }

    private void showFragment() {
        Intent intent = getIntent();
        if(intent == null) {
            return;
        }

        int target = intent.getIntExtra(TARGET, 0);
        Fragment fragment = null;
        switch(target) {
            case TARGET_FLOW:
                fragment = new FlowFragment();
            break;
            case TARGET_CIRCLEVIEW:
                fragment = new CircleViewFragment();
            break;
        }
        if(fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.layout_common, fragment);
            transaction.commit();
        }
    }
}
