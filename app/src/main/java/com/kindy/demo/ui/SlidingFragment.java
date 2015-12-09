package com.kindy.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kindy.demo.R;
import com.kindy.library.utils.CommonUtils;
import com.kindy.library.view.KindySlidingLayout;

/**
 * Created by Kindy on 2015/12/4.
 */
public class SlidingFragment extends Fragment {
    private KindySlidingLayout mSlidingLayout;

    private View mMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding, container, false);

        mSlidingLayout = (KindySlidingLayout)view.findViewById(R.id.slidinglayout);
        mSlidingLayout.setOnSlidingListener(mOnSlidingListener);

        view.findViewById(R.id.btn_open).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_close).setOnClickListener(mOnClickListener);
        mMenu = view.findViewById(R.id.menu);

        return view;
    }

    private KindySlidingLayout.OnSlidingListener mOnSlidingListener = new KindySlidingLayout.OnSlidingListener() {

        @Override
        public void onSliding(float value) {
            mMenu.setAlpha(value);
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
                    mSlidingLayout.openDrawer();
                break;
                case R.id.btn_close:
                    mSlidingLayout.closeDrawer();
                    break;
            }
        }
    };

}
