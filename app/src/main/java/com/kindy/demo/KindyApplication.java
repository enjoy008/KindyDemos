package com.kindy.demo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Kindy on 2015/12/4.
 */
public class KindyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
    }
}
