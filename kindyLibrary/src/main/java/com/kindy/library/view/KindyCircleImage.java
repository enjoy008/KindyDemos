package com.kindy.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kindy.library.R;

import com.kindy.library.utils.ImageHelper;

/**
 * Created by Kindy on 2015/12/4.
 */
public class KindyCircleImage extends ViewGroup {
    private ImageView mImageView;
    private int imageDrawableId;

    public KindyCircleImage(Context context) {
        this(context, null);
    }

    public KindyCircleImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KindyCircleImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        //Retrieve styles attributes
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KindyImageVIew, defStyle, 0);

        imageDrawableId = a.getResourceId(R.styleable.KindyImageVIew_img, 0);

        a.recycle();

        mImageView = new ImageView(getContext());
        this.addView(mImageView);

        if (imageDrawableId != 0) {
            setImage(imageDrawableId);
        }
    }

    public void setImage(int drawable) {
        if (imageDrawableId != drawable) {
            imageDrawableId = drawable;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), drawable);
        setImage(bitmap);
    }

    public void setImage(Bitmap bitmap) {

        Bitmap roundBitmap = ImageHelper.getCircleBitmap(bitmap);
        mImageView.setImageBitmap(roundBitmap);

        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//		int count = getChildCount();
//		if(count == 0) {
//			return;
//		}
//		if(count > 1) {
//			throw new IllegalArgumentException(" error : children count can not bigger than 1 !");
//		}
//
//		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//		View child = getChildAt(0);
//		measureChild(child, widthMeasureSpec, heightMeasureSpec);
//
//		int childWidth = child.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
//		int childHeight = child.getMeasuredHeight() + getPaddingTop() + getPaddingBottom();
//
//		int measuredWidth = MeasureSpec.EXACTLY == widthMode ? widthSize : childWidth;
//		int measuredHeight = MeasureSpec.EXACTLY == heightMode ? heightSize : childHeight;
//
//		setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count == 0) {
            return;
        }
        if (count > 1) {
            throw new IllegalArgumentException(" error : children count can not bigger than 1 !");
        }

        View child = getChildAt(0);

        int lc = getPaddingLeft();
        int tc = getPaddingTop();
        int rc = getWidth() - getPaddingRight();
        int bc = getHeight() - getPaddingBottom();

        child.layout(lc, tc, rc, bc);
    }

}
