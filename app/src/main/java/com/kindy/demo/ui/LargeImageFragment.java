package com.kindy.demo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kindy.demo.R;
import com.kindy.library.view.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Kindy on 2015/12/4.
 */
public class LargeImageFragment extends Fragment {
    private LargeImageView mLargeImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_largeimage, container, false);

        mLargeImageView = (LargeImageView) view.findViewById(R.id.id_largetImageview);

        initData();

        return view;
    }


    public void initData() {
        try {
//            InputStream inputStream = getContext().getAssets().open("qm.jpg");
            InputStream inputStream = getContext().getAssets().open("world.jpg");
            mLargeImageView.setInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
