package com.kindy.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kindy.demo.R;
import com.kindy.library.view.FlowLayout;

/**
 * Created by Kindy on 2015/12/4.
 */
public class FlowFragment extends Fragment {
    private String[] mVals = new String[]
            { "Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "ButtonImageViewButtonImageViewButtonImageButton", "TextView", "Helloworld",
                    "Android"};

    private FlowLayout mFlowLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flow, container, false);

        mFlowLayout = (FlowLayout) view.findViewById(R.id.id_flowlayout);
        initData();

        return view;
    }


    public void initData() {
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < mVals.length; i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.item_tv, mFlowLayout, false);
            tv.setText(mVals[i]);
            tv.setOnClickListener(mOnClickListener);
            mFlowLayout.addView(tv);
        }

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
//			mFlowLayout.removeView(v);
        }
    };
}
