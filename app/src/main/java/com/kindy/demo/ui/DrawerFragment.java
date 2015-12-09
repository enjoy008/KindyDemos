package com.kindy.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kindy.demo.R;
import com.kindy.library.utils.CommonUtils;
import com.kindy.library.view.KindyDrawerLayout;

/**
 * Created by Kindy on 2015/12/4.
 */
public class DrawerFragment extends Fragment {
    private KindyDrawerLayout mDrawerLayout;

    private View mContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);

        mDrawerLayout = (KindyDrawerLayout)view.findViewById(R.id.drawerlayout);
        mDrawerLayout.setOnDrawerSlideListener(mOnDrawerSlideListener);

        view.findViewById(R.id.btn_open).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_close).setOnClickListener(mOnClickListener);
        mContent = view.findViewById(R.id.content);

        return view;
    }

    private KindyDrawerLayout.OnDrawerSlideListener mOnDrawerSlideListener = new KindyDrawerLayout.OnDrawerSlideListener() {

        @Override
        public void onDrawerSlide(float value) {
            mContent.setAlpha(1.0f-value*0.6f);
            if(value == 0.0f) {
                CommonUtils.getInstance().showToast("关闭");
            } else if(value == 1.0f) {
                CommonUtils.getInstance().showToast("打开");
            }
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.btn_open:
                    mDrawerLayout.openDrawer();
                break;
                case R.id.btn_close:
                    mDrawerLayout.closeDrawer();
                    break;
            }
        }
    };

}
