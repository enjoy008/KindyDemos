package com.kindy.library.impl;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kindy on 2015/12/7.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder  {
    private SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <T extends View> T findView(int viewId) {
        View view = mViews.get(viewId);
        if(view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

    /**
     * for TextView
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        TextView tv = findView(viewId);
        if(tv == null) {
            throw new IllegalArgumentException("setText(viewId=" + viewId + " ,text=" + text);
        }
        tv.setText(text);
    }

    /**
     * for TextView
     * @param viewId
     * @param resId
     */
    public void setText(int viewId, int resId) {
        TextView tv = findView(viewId);
        if(tv == null) {
            throw new IllegalArgumentException("setText(viewId=" + viewId + " ,resId=" + resId);
        }
        tv.setText(resId);
    }

    /**
     * for ImageView
     * @param viewId
     * @param resId
     */
    public void setImage(int viewId, int resId) {
        ImageView iv = findView(viewId);
        if(iv == null) {
            throw new IllegalArgumentException("setText(viewId=" + viewId + " ,resId=" + resId);
        }
        iv.setImageResource(resId);
    }

}
