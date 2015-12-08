package com.kindy.demo;

import android.app.Application;

import com.kindy.library.utils.CommonUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Kindy on 2015/12/4.
 */
public class KindyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        LeakCanary.install(this);
        CommonUtils.init(this);
    }

}
