package com.kindy.library.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Kindy on 2015/12/8.
 */
public class CommonUtils {
    private static CommonUtils instance;
    private static Context sAppContext;

    private CommonUtils() { }
    public static void init(Application app) {
        sAppContext = app.getApplicationContext();
    }
    public static CommonUtils getInstance() {
        if(sAppContext == null) {
            throw new NullPointerException("sAppContext is null, you should invoke method CommonUtils.init(Context appContext) first!");
        }
        if(instance == null) {
            synchronized (CommonUtils.class) {
                if(instance == null) {
                    instance = new CommonUtils();
                }
            }
        }
        return instance;
    }

    public Context getAppContext() {
        return sAppContext;
    }

    private DisplayMetrics mDisplayMetrics;
    public WindowManager getWindowManager() {
        WindowManager wm = (WindowManager) sAppContext.getSystemService(Context.WINDOW_SERVICE);
        return wm;
    }
    private void checkDisplayMetrics() {
        if(mDisplayMetrics == null) {
            mDisplayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        }
    }
    public int getScreenWidth() {
        checkDisplayMetrics();
        return mDisplayMetrics.widthPixels;
    }
    public int getScreenHeight() {
        checkDisplayMetrics();
        return mDisplayMetrics.heightPixels;
    }

    public int Dp2Px(float dp) {
//		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.getResources().getDisplayMetrics());
        checkDisplayMetrics();
        final float scale = mDisplayMetrics.density;
        return (int) (dp * scale + 0.5f);
    }
    public float Dp2PxF(float dp) {
        checkDisplayMetrics();
        final float scale = mDisplayMetrics.density;
        return dp * scale;
    }
    public int Px2Dp(float px) {
        checkDisplayMetrics();
        final float scale = mDisplayMetrics.density;
        return (int) (px / scale + 0.5f);
    }
    public int Px2Sp(float px) {
        checkDisplayMetrics();
        final float fontScale = mDisplayMetrics.scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }
    public float Px2SpF(float px) {
        checkDisplayMetrics();
        final float fontScale = mDisplayMetrics.scaledDensity;
        return px / fontScale;
    }
    public int Sp2Px(float sp) {
        checkDisplayMetrics();
        final float fontScale = mDisplayMetrics.scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    private Toast mToast;
    public void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }
    public void showToast(String msg, int duration) {
        if(mToast == null) {
            mToast = Toast.makeText(sAppContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.setDuration(duration);
        mToast.show();
    }
    public void showToast(int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }
    public void showToast(int resId, int duration) {
        if(mToast == null) {
            mToast = Toast.makeText(sAppContext, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(resId);
        mToast.setDuration(duration);
        mToast.show();
    }

    public boolean loadSDCard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            showToast("no SDCard");
        }
        return false;
    }

    public boolean isEmpty(final String s) {
        return (s==null || s.trim().equalsIgnoreCase("") || s.trim().equalsIgnoreCase("null"));
    }

    public String getString(int resid) {
        return sAppContext.getResources().getString(resid);
    }
    public String[] getStringArray(int resid) {
        return sAppContext.getResources().getStringArray(resid);
    }
    public int getDimen(int resid) {
        return sAppContext.getResources().getDimensionPixelSize(resid);
    }

}
