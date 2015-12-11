package com.kindy.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;

import com.kindy.demo.R;

public class CanvasSampleView extends View {
	private Bitmap mBitmap;  
    private int limitLength = 0;  
    private int width;  
    private int heigth;  
    private static final int CLIP_HEIGHT = 30;
    
    private boolean status = HIDE;//显示还是隐藏的状态，最开始为HIDE  
    private static final boolean SHOW = true;//显示图片   
    private static final boolean HIDE = false;//隐藏图片
    
    public CanvasSampleView(Context context) {  
        super(context);  
        //3.1以上手机都默认使用了硬件加速，硬件加速的canvas不再支持canvas.clipPath和canvas.clipRegion。
        //所以要想正常跑这个两个程序，需要在constructor里面添加view.setLayerType(VIEW_LAYER_TYPE_SOFTWARE,null)
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//【暂时不加】
        
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tangyan);
        limitLength = width = mBitmap.getWidth();  
        heigth = mBitmap.getHeight();  
    }  

    @Override  
    protected void onDraw(Canvas canvas) {  
        Region region = new Region();  
        int i = 0;  
        while (i * CLIP_HEIGHT <= heigth) {//计算clip的区域  
            if (i % 2 == 0) {
                region.union(new Rect(0, i * CLIP_HEIGHT, limitLength, (i + 1) * CLIP_HEIGHT));
            } else {
                region.union(new Rect(width - limitLength, i * CLIP_HEIGHT, width, (i + 1) * CLIP_HEIGHT));
            }  
            i++;  
        }  

        canvas.clipRegion(region);
        canvas.drawBitmap(mBitmap, 0, 0, new Paint());  
        if (status == HIDE) {//如果此时是隐藏  
            limitLength -= 2;  
            if(limitLength<=0)  
                status=SHOW;  
        } else {//如果此时是显示  
            limitLength += 2;  
            if(limitLength>=width)  
                status=HIDE;  
        }  

        invalidate();  
    }  
    
}
