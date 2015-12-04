package com.kindy.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.kindy.demo.R;
import com.kindy.demo.model.OnSimpleItemClickListener;
import com.kindy.demo.model.SimpleString;

import java.util.ArrayList;

/**
 * Created by Kindy on 2015/12/4.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.StringHolder>{
    private ArrayList<SimpleString> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private OnSimpleItemClickListener mOnSimpleItemClickListener;

    public SimpleAdapter(Context context, ArrayList<SimpleString> data) {
        mContext = context;
        mData = data;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public StringHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.simple_item, parent, false);

        return new StringHolder(item);
    }

    @Override
    public void onBindViewHolder(StringHolder holder, int position) {
        SimpleString bean = mData.get(position);
        holder.title.setText(bean.name);
        holder.itemView.setTag(R.id.id_click, position);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setOnSimpleItemClickListener(OnSimpleItemClickListener l) {
        mOnSimpleItemClickListener = l;
    }
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mOnSimpleItemClickListener != null) {
                int position = (Integer)v.getTag(R.id.id_click);
                mOnSimpleItemClickListener.onSimpleItemClick(v, position);
            }
        }
    };

    public static class StringHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public StringHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.simple_text);
        }
    }
}
