package com.kindy.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.view.View;

public class CanvasClipView extends View {
	private Paint mPaint;  
    private Path mPath;
    
//    假设用region1  去组合region2   
//    public enum Op {  
//            DIFFERENCE(0), //最终区域为region1 与 region2不同的区域  
//            INTERSECT(1), // 最终区域为region1 与 region2相交的区域  
//            UNION(2),      //最终区域为region1 与 region2组合一起的区域  
//            XOR(3),        //最终区域为region1 与 region2相交之外的区域  
//            REVERSE_DIFFERENCE(4), //最终区域为region2 与 region1不同的区域  
//            REPLACE(5); //最终区域为为region2的区域  
//     }

	public CanvasClipView(Context context) {
		super(context);
		
		setFocusable(true);  
		  
        mPaint = new Paint();  
        mPaint.setAntiAlias(true);  
        mPaint.setStrokeWidth(6);  
        mPaint.setTextSize(16);  
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setAlpha(100);

        mPath = new Path(); 
	}

	private void drawScene(Canvas canvas) {  
        canvas.clipRect(0, 0, 100, 100);  

        canvas.drawColor(Color.WHITE);  

        mPaint.setColor(Color.RED);
        canvas.drawLine(0, 0, 100, 100, mPaint);  

        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(30, 70, 30, mPaint);  

        mPaint.setColor(Color.BLUE);
        canvas.drawText("Clipping", 100, 30, mPaint);  
    }  

    @Override  
    protected void onDraw(Canvas canvas) {  
        canvas.drawColor(Color.GRAY);  

        canvas.save();  
        canvas.translate(10, 10);  
        drawScene(canvas);  
        canvas.restore();  

        canvas.save();  
        canvas.translate(160, 10);  
        canvas.clipRect(10, 10, 90, 90);  
        canvas.clipRect(30, 30, 70, 70, Region.Op.DIFFERENCE);  
        drawScene(canvas);  
        canvas.restore();  

        canvas.save();  
        canvas.translate(10, 160);  
        mPath.reset();  
//        canvas.clipPath(mPath); // makes the clip empty  
        mPath.addCircle(50, 50, 50, Path.Direction.CCW);  
        canvas.clipPath(mPath, Region.Op.REPLACE);  
        drawScene(canvas);  
        canvas.restore();  

        canvas.save();  
        canvas.translate(160, 160);  
        canvas.clipRect(0, 0, 60, 60);  
        canvas.clipRect(40, 40, 100, 100, Region.Op.UNION);  
        drawScene(canvas);  
        canvas.restore();  

        canvas.save();  
        canvas.translate(10, 310);  
        canvas.clipRect(0, 0, 60, 60);  
        canvas.clipRect(40, 40, 100, 100, Region.Op.XOR);  
        drawScene(canvas);  
        canvas.restore();  

        canvas.save();  
        canvas.translate(160, 310);  
        canvas.clipRect(0, 0, 60, 60);  
        canvas.clipRect(40, 40, 100, 100, Region.Op.REVERSE_DIFFERENCE);  
        drawScene(canvas);  
        canvas.restore();  
    }  
    
}
