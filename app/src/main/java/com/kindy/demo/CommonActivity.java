package com.kindy.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kindy.demo.ui.CanvasFragment;
import com.kindy.demo.ui.CircleViewFragment;
import com.kindy.demo.ui.DragFragment;
import com.kindy.demo.ui.DrawerFragment;
import com.kindy.demo.ui.FlowFragment;
import com.kindy.demo.ui.LargeImageFragment;
import com.kindy.demo.ui.SlidingFragment;
import com.kindy.demo.ui.WeiChatFragment;

/**
 * Created by Kindy on 2015/12/4.
 */
public class CommonActivity extends AppCompatActivity {
    public static String TARGET = "target";

    public static final int TARGET_FLOW       = 1;
    public static final int TARGET_CIRCLEVIEW = 2;
    public static final int TARGET_LARGEIMAGE = 3;
    public static final int TARGET_DRAG       = 4;
    public static final int TARGET_DRAWER     = 5;
    public static final int TARGET_SLIDING    = 6;
    public static final int TARGET_CANVAS     = 7;
    public static final int TARGET_WEICHAT    = 8;

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
            case TARGET_LARGEIMAGE:
                fragment = new LargeImageFragment();
                break;
            case TARGET_DRAG:
                fragment = new DragFragment();
                break;
            case TARGET_DRAWER:
                fragment = new DrawerFragment();
                break;
            case TARGET_SLIDING:
                fragment = new SlidingFragment();
                break;
            case TARGET_CANVAS:
                fragment = new CanvasFragment();
                break;
            case TARGET_WEICHAT:
                fragment = new WeiChatFragment();
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
