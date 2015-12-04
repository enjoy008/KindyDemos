package com.kindy.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kindy.demo.adapter.SimpleAdapter;
import com.kindy.demo.model.OnSimpleItemClickListener;
import com.kindy.demo.model.SimpleString;
import com.kindy.library.utils.L;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String[] mVals = new String[] {
            "FlowFragment",
            "CircleViewFragment",
            " . . . 华丽的分割线 . . . "
    };

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private SimpleAdapter mSimpleAdapter;
    private ArrayList<SimpleString> mData;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
    }

    public void initData() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.srl);
        mRecyclerView = (RecyclerView)this.findViewById(R.id.recyclerView);
        mData = new ArrayList<>();
        for(int i=0; i<mVals.length; i++) {
            mData.add(new SimpleString(mVals[i]));
        }
        for(int i='A'; i<'z'; i++) {
            mData.add(new SimpleString("" + (char)i));
        }
        mSimpleAdapter = new SimpleAdapter(this, mData);
        mSimpleAdapter.setOnSimpleItemClickListener(mOnSimpleItemClickListener);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(mSimpleAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.gray_line);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

    }

    private OnSimpleItemClickListener mOnSimpleItemClickListener = new OnSimpleItemClickListener() {
        @Override
        public void onSimpleItemClick(View v, int position) {
            L.o(this, " position = " + position);

            Intent intent = new Intent(MainActivity.this, CommonActivity.class);
            if(position == 0) {
                intent.putExtra(CommonActivity.TARGET, CommonActivity.TARGET_FLOW);
            } else {
                intent.putExtra(CommonActivity.TARGET, CommonActivity.TARGET_CIRCLEVIEW);
            }
            startActivity(intent);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };

    private int lastVisibleItem;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mSimpleAdapter.getItemCount()) {
                mSwipeRefreshLayout.setRefreshing(true);
                mHandler.sendEmptyMessageDelayed(0, 3000);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//            lastVisibleItem = ((LinearLayoutManager)mLayoutManager).findLastCompletelyVisibleItemPosition();
//            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mSwipeRefreshLayout.setRefreshing(false);
            return false;
        }
    });
}
