package com.kindy.library.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kindy.library.utils.CommonUtils;

/**
 * Created by Kindy on 2015/12/9.
 */
public class KindySlidingLayout extends ViewGroup {
    /** menu宽度的最大比重 */
    private static final float MAX_MENU_WEIGHT = 0.6f;
    private ViewDragHelper mDragger;

    private View mMenuView;
    private View mContentView;

    private static final float OPEN  = 1.0f;
    private static final float CLOSE = 0.0f;
    private float mOffsetMenu = 0f;
    /** menu 打开、关闭的界线 */
    private float mLine38 = 0.6f;

    private OnSlidingListener mOnSlidingListener;

    public KindySlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDragger = ViewDragHelper.create(this, 1.0f,  new DragCallback(this));
        mDragger.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        //设置minVelocity
        final float density = getResources().getDisplayMetrics().density;
        final float minVel = 64 * density;
        mDragger.setMinVelocity(minVel);
    }

    /**
     * menu 打开、关闭的界线
     * @param value (0.1f - 0.9f)
     */
    public void setLine38(float value) {
        if(value < 0.1f || value > 0.9f) {
            return;
        }
        mLine38 = value;
    }

    public void setOnSlidingListener(OnSlidingListener l) {
        mOnSlidingListener = l;
    }

    public void openDrawer() {
        if(!isDrawerOpen()) {
            mDragger.smoothSlideViewTo(mContentView, getMenuRight() + getContentLeft(), mContentView.getTop());
            invalidate();
        }
    }

    public void closeDrawer() {
        if(!isDrawerClose()) {
            mDragger.smoothSlideViewTo(mContentView, getContentLeft(), mContentView.getTop());
            invalidate();
        }
    }

    public boolean isDrawerOpen() {
        return mOffsetMenu == OPEN;
    }

    public boolean isDrawerClose() {
        return mOffsetMenu == CLOSE;
    }

    private int getMenuRight() {
        MarginLayoutParams lp = (MarginLayoutParams)mMenuView.getLayoutParams();
        int right = lp.leftMargin + lp.rightMargin + getPaddingLeft() + getPaddingRight() + mMenuView.getWidth();
        return right;
    }
    private int getContentLeft() {
        MarginLayoutParams lp = (MarginLayoutParams)mContentView.getLayoutParams();
        int left = lp.leftMargin + getPaddingLeft();
        return left;
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);
        int heigthSize = MeasureSpec.getSize(heightMeasureSpec);

        MarginLayoutParams lp = (MarginLayoutParams)mMenuView.getLayoutParams();
        measureChild(
                mMenuView,
                MeasureSpec.makeMeasureSpec(widthSize - lp.leftMargin - lp.rightMargin, widthMode),
                MeasureSpec.makeMeasureSpec(heigthSize - lp.topMargin - lp.bottomMargin, heigthMode)
        );

        lp = (MarginLayoutParams)mMenuView.getLayoutParams();
        int menuWidth = (int) (CommonUtils.getInstance().getScreenWidth() * MAX_MENU_WEIGHT);
        if(mMenuView.getMeasuredWidth() > menuWidth) { // 重置宽度

            final int childWidthMeasureSpec = getChildMeasureSpec(
                    MeasureSpec.makeMeasureSpec(menuWidth - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY),
                    getPaddingLeft() + getPaddingRight(),
                    menuWidth);
            final int childHeightMeasureSpec = getChildMeasureSpec(
                    MeasureSpec.makeMeasureSpec(heigthSize - lp.topMargin - lp.bottomMargin, heigthMode),
                    getPaddingTop() + getPaddingBottom(),
                    lp.height);
            mMenuView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

        lp = (MarginLayoutParams)mContentView.getLayoutParams();
        measureChild(
                mContentView,
                MeasureSpec.makeMeasureSpec(widthSize - lp.leftMargin - lp.rightMargin, widthMode),
                MeasureSpec.makeMeasureSpec(heigthSize - lp.topMargin - lp.bottomMargin, heigthMode));

        setMeasuredDimension(widthSize, heigthSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        MarginLayoutParams lp = (MarginLayoutParams)mMenuView.getLayoutParams();
        int lc = lp.leftMargin + getPaddingLeft();
        int tc = lp.topMargin + getPaddingTop();
        int rc = lc + mMenuView.getMeasuredWidth();
        int bc = tc + mMenuView.getMeasuredHeight();
        mMenuView.layout(lc, tc, rc, bc);

        lp = (MarginLayoutParams)mContentView.getLayoutParams();
        lc = lp.leftMargin + getPaddingLeft();
        tc = lp.topMargin + getPaddingTop();
        rc = lc + mContentView.getMeasuredWidth();
        bc = tc + mContentView.getMeasuredHeight();
        mContentView.layout(lc, tc, rc, bc);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if(childCount != 2) {
            throw new IllegalArgumentException("DrawerLayout : childCount must be 2, but " + this + " childCount is " + childCount);
        }
        mMenuView = getChildAt(0);
        mContentView = getChildAt(1);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }


    private static class DragCallback extends ViewDragHelper.Callback {
        private KindySlidingLayout mParent;

        public DragCallback(KindySlidingLayout v) {
            mParent = v;
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
//            return true;
            if(mParent.isDrawerOpen()) {
                return child == mParent.mContentView;
            }
            return false;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return Math.min(Math.max(left, mParent.getContentLeft()), mParent.getMenuRight() + mParent.getContentLeft());
//            return left - dx;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top - dy; // 不允许垂直拖拽
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //mAutoBackView手指释放时可以自动回去
            if (releasedChild == mParent.mContentView) {
                if(mParent.mOffsetMenu < mParent.mLine38) {
                    mParent.closeDrawer();
                } else {
                    mParent.openDrawer();
                }
            }
        }

        //在边界拖动时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mParent.mDragger.captureChildView(mParent.mContentView, pointerId);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
//            mParent.mContentView.offsetLeftAndRight(dx);
            mParent.mOffsetMenu = (left - mParent.getContentLeft()) * 1.0f / (mParent.getMenuRight());
            if(mParent.mOffsetMenu == CLOSE) {
                mParent.mMenuView.setVisibility(View.INVISIBLE);
            } else {
                mParent.mMenuView.setVisibility(View.VISIBLE);
            }

            mParent.invalidate();

            if(mParent.mOnSlidingListener != null) {
                mParent.mOnSlidingListener.onSliding(mParent.mOffsetMenu);
            }
        }

        // 一下两个方法设置可拖拽的区域
        @Override
        public int getViewHorizontalDragRange(View child) {
            return mParent.getWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mParent.getHeight() - child.getMeasuredHeight();
        }
    }

    public interface OnSlidingListener {
        /**
         * 0.0f : close
         * <br>1.0f : open
         * @param value (0.0f-1.0f)
         */
        void onSliding(float value);
    }
}
