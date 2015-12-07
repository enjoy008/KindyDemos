package com.kindy.library.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Kindy on 2015/12/7.
 */
public class DragLayout extends LinearLayout {
    private ViewDragHelper mDragger;

    public View mDragView;
    public View mAutoBackView;
    public View mEdgeTrackerView;
    public Point mAutoBackOriginPos = new Point();

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDragger = ViewDragHelper.create(this, 1.0f,  new DragCallback(this));
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT | ViewDragHelper.EDGE_RIGHT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

    private static class DragCallback extends ViewDragHelper.Callback {
        private DragLayout mParent;

        public DragCallback(DragLayout v) {
            mParent = v;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
//            return true;
            //mEdgeTrackerView禁止直接移动
            return child != mParent.mEdgeTrackerView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
//            return left;

            final int leftBound = mParent.getPaddingLeft();
            final int rightBound = mParent.getWidth() - child.getWidth() - leftBound;

            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
//            return top;

            final int topBound = mParent.getPaddingTop();
            final int bottomBound = mParent.getHeight() - child.getHeight() - topBound;

            final int newTop = Math.min(Math.max(top, topBound), bottomBound);

            return newTop;
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //mAutoBackView手指释放时可以自动回去
            if (releasedChild == mParent.mAutoBackView) {
                mParent.mDragger.settleCapturedViewAt(mParent.mAutoBackOriginPos.x, mParent.mAutoBackOriginPos.y);
                mParent.invalidate();
            }
        }

        //在边界拖动时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mParent.mDragger.captureChildView(mParent.mEdgeTrackerView, pointerId);
        }

        // 一下两个方法设置可拖拽的区域
        @Override
        public int getViewHorizontalDragRange(View child) {
//            return super.getViewHorizontalDragRange(child);
            return mParent.getWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
//            return super.getViewVerticalDragRange(child);
            return mParent.getHeight() - child.getMeasuredHeight();
        }
    }
}
