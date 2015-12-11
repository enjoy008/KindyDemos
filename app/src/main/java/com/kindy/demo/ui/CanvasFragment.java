package com.kindy.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kindy.demo.R;
import com.kindy.demo.view.CanvasClipView;
import com.kindy.demo.view.CanvasSampleView;
import com.kindy.demo.view.CanvasViewTest;

/**
 * Created by Kindy on 2015/12/4.
 */
public class CanvasFragment extends Fragment {

    private FrameLayout mLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canvas, container, false);

        mLayout = (FrameLayout)view.findViewById(R.id.canvas_layout);
        view.findViewById(R.id.btn_next).setOnClickListener(mOnClickListener);

        mLayout.addView(new CanvasSampleView(getActivity()));

        return view;
    }

    private int index = 0;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            mLayout.removeAllViews();
            if(index == 0) {
                mLayout.addView(new CanvasViewTest(getActivity()));
            } else if(index == 1) {
                mLayout.addView(new CanvasClipView(getActivity()));
            } else if(index == 2) {
                mLayout.addView(new CanvasSampleView(getActivity()));
            }
            index ++;
            if(index > 2) {
                index = 0;
            }
        }

    };

}
