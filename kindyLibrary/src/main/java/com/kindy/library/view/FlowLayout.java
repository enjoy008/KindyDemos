package com.kindy.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.kindy.library.utils.L;

import java.util.ArrayList;

public class FlowLayout extends ViewGroup {

	public FlowLayout(Context context) {
		this(context, null);
	}
	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//

	}

	private ArrayList<LineInfo> lineInfoList = new ArrayList<LineInfo>();
	private LineInfo lineInfo;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		lineInfoList.clear();
		lineInfo = new LineInfo();

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width = 0;
		int height = 0;
		int lineWidth = 0;
		int lineHeight = 0;

		int childCount = getChildCount();
		for(int i=0; i<childCount; i++) {
			View child = getChildAt(i);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

			measureChild(
					child,
					MeasureSpec.makeMeasureSpec(widthSize - lp.leftMargin - lp.rightMargin, widthMode),
					MeasureSpec.makeMeasureSpec(heightMode - lp.topMargin - lp.bottomMargin, heightSize));

			int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
			int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

			if(lineWidth + childWidth <= getWidth() - getPaddingLeft() - getPaddingRight()) {
				lineWidth += childWidth;
				lineHeight = Math.max(lineHeight, childHeight);

				lineInfo.count ++;
			} else { // 换行
				width = Math.max(width, lineWidth);
				height += lineHeight;

				lineInfo.height = lineHeight;
				lineInfoList.add(lineInfo);
				lineInfo = new LineInfo();
				lineInfo.count ++;

				lineWidth = childWidth;
				lineHeight = childHeight;
			}
		}
		if(childCount > 0) {
			width = Math.max(width, lineWidth);
			height += lineHeight;

			lineInfo.height = lineHeight;
			lineInfoList.add(lineInfo);
		}
		lineInfo = null;

		setMeasuredDimension(
				widthMode == MeasureSpec.EXACTLY ? widthSize : width + getPaddingLeft() + getPaddingRight(),
				heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom());


//		for(LineInfo info : lineInfoList) {
//			L.o(info.toString());
//		}
//		L.o("=================================");
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		L.o("================ changed : " + changed);
		int lc = 0;
		int tc = getPaddingTop();
		int rc = 0;
		int bc = 0;

		int index = 0;;
		for(int i=0; i<lineInfoList.size(); i++) {
			lc = getPaddingLeft();
			LineInfo info = lineInfoList.get(i);
			for(int j=0; j<info.count; j++) {
				View child = getChildAt(index++);
				MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
				lc += lp.leftMargin;
				tc += lp.topMargin;
				rc = lc + child.getMeasuredWidth();
				bc = tc + child.getMeasuredHeight();

				child.layout(lc, tc, rc, bc);

				lc += child.getMeasuredWidth() + lp.rightMargin;
				tc -= lp.topMargin;
			}
			tc += info.height;
		}
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	private static class LineInfo {
		public int count;
		public int height;
		public String toString() {
			return "count:" + count + " height:" + height;
		}
	}
}
