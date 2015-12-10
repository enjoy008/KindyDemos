package com.kindy.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kindy.demo.R;
import com.kindy.demo.adapter.SimpleAdapter;
import com.kindy.demo.model.SimpleString;
import com.kindy.library.utils.CommonUtils;
import com.kindy.library.view.KindySlidingLayout;

import java.util.ArrayList;

/**
 * Created by Kindy on 2015/12/4.
 */
public class SlidingFragment extends Fragment {
    private KindySlidingLayout mSlidingLayout;

    private RecyclerView mMenu;
    private RecyclerView mContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding, container, false);

        mSlidingLayout = (KindySlidingLayout)view.findViewById(R.id.slidinglayout);
        mSlidingLayout.setOnSlidingListener(mOnSlidingListener);

        view.findViewById(R.id.btn_open).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_close).setOnClickListener(mOnClickListener);
        mMenu = (RecyclerView)view.findViewById(R.id.menu_recyclerview);
        mContent = (RecyclerView)view.findViewById(R.id.content_recyclerview);

        init();

        return view;
    }

    private void init() {
        ArrayList<SimpleString> data = new ArrayList<>();
        for(int i='1'; i<='9'; i++) {
            data.add(new SimpleString("Menu__" + (char)i));
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getContext(), data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mMenu.setAdapter(simpleAdapter);
        mMenu.setLayoutManager(layoutManager);
        mMenu.setAlpha(0);

        data = new ArrayList<>();
        for(int i='A'; i<='z'; i++) {
            data.add(new SimpleString("Content__" + (char)i));
        }
        simpleAdapter = new SimpleAdapter(this.getContext(), data);
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mContent.setAdapter(simpleAdapter);
        mContent.setLayoutManager(layoutManager);
    }

    private void contentEffects(float value) {
        int contentMargin = (int) (value * 80);
        FrameLayout.LayoutParams contentParams = (FrameLayout.LayoutParams) mContent.getLayoutParams();
        contentParams.setMargins(0, contentMargin, 0, contentMargin);
        mContent.setLayoutParams(contentParams);
    }
    private void menuEffects(float value) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams)mMenu.getLayoutParams();
        int width = mMenu.getWidth();
        int height = mMenu.getHeight();
        float scale = 1 - (1-value) * 0.2f;

        mMenu.setPivotX(0);// 设置缩放和选择的点
        mMenu.setPivotY(height / 2);
        mMenu.setScaleX(scale);//设置缩放的基准点
        mMenu.setScaleY(scale);// 设置缩放的基准点
        mMenu.setX(mSlidingLayout.getPaddingLeft() + lp.leftMargin + (value - 1) * width / 3);
        mMenu.setAlpha(value);
    }

    private KindySlidingLayout.OnSlidingListener mOnSlidingListener = new KindySlidingLayout.OnSlidingListener() {

        @Override
        public void onSliding(float value) {
            if(value == 0.0f) {
                CommonUtils.getInstance().showToast("关闭");
            } else if(value == 1.0f) {
                CommonUtils.getInstance().showToast("打开");
            }
            contentEffects(value);
            menuEffects(value);
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
