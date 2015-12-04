package com.kindy.library.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Administrator on 2014/12/20 0020.
 */
public class ImageHelper {

    public static Bitmap handleImageEffect(Bitmap bm, float hue, float saturation, float lum) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0, hue);
        hueMatrix.setRotate(1, hue);
        hueMatrix.setRotate(2, hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(lumMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);

        return bmp;
    }

    /**
     * 底片效果
     * @param bm
     * @return
     */
    public static Bitmap handleImageNegative(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color;
        int r, g, b, a;

        Bitmap bmp = Bitmap.createBitmap(width, height
                , Config.ARGB_8888);

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            r = 255 - r;
            g = 255 - g;
            b = 255 - b;

            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    /**
     * 复古效果
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixelsOldPhoto(Bitmap bm) {
    	long time1 = System.currentTimeMillis();
    	
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0;
        int r, g, b, a, r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        bm.getPixels(oldPx, 0, bm.getWidth(), 0, 0, width, height);
        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            a = Color.alpha(color);
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);

            if (r1 > 255) {
                r1 = 255;
            }
            if (g1 > 255) {
                g1 = 255;
            }
            if (b1 > 255) {
                b1 = 255;
            }

            newPx[i] = Color.argb(a, r1, g1, b1);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        
        long time2 = System.currentTimeMillis();
        long time = time2 - time1;
        System.out.println(" =========== handleImagePixelsOldPhoto time = " + time);
        
        return bmp;
    }

    /**
     * 浮雕效果
     * @param bm
     * @return
     */
    public static Bitmap handleImagePixelsRelief(Bitmap bm) {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
                Config.ARGB_8888);
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color = 0, colorBefore = 0;
        int a, r, g, b;
        int r1, g1, b1;

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];

        bm.getPixels(oldPx, 0, bm.getWidth(), 0, 0, width, height);
        for (int i = 1; i < width * height; i++) {
            colorBefore = oldPx[i - 1];
            a = Color.alpha(colorBefore);
            r = Color.red(colorBefore);
            g = Color.green(colorBefore);
            b = Color.blue(colorBefore);

            color = oldPx[i];
            r1 = Color.red(color);
            g1 = Color.green(color);
            b1 = Color.blue(color);

            r = (r - r1 + 127);
            g = (g - g1 + 127);
            b = (b - b1 + 127);
            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }
    
    
    /**
     * 模糊化
     * @param sentBitmap
     * @param radius
     * @param canReuseInBitmap
     * @return
     */
    public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {  
    	  
        // Stack Blur v1.0 from  
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html  
        //  
        // Java Author: Mario Klingemann <mario at quasimondo.com>  
        // http://incubator.quasimondo.com  
        // created Feburary 29, 2004  
        // Android port : Yahel Bouaziz <yahel at kayenko.com>  
        // http://www.kayenko.com  
        // ported april 5th, 2012  
  
        // This is a compromise between Gaussian Blur and Box blur  
        // It creates much better looking blurs than Box Blur, but is  
        // 7x faster than my Gaussian Blur implementation.  
        //  
        // I called it Stack Blur because this describes best how this  
        // filter works internally: it creates a kind of moving stack  
        // of colors whilst scanning through the image. Thereby it  
        // just has to add one new block of color to the right side  
        // of the stack and remove the leftmost color. The remaining  
        // colors on the topmost layer of the stack are either added on  
        // or reduced by one, depending on if they are on the right or  
        // on the left side of the stack.  
        //  
        // If you are using this algorithm in your code please add  
        // the following line:  
        //  
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>  
  
        Bitmap bitmap;  
        if (canReuseInBitmap) {  
            bitmap = sentBitmap;  
        } else {  
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);  
        }  
  
        if (radius < 1) {  
            return (null);  
        }  
        
        long time1 = System.currentTimeMillis();
  
        int w = bitmap.getWidth();  
        int h = bitmap.getHeight();  
  
        int[] pix = new int[w * h];  
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);  
  
        int wm = w - 1;  
        int hm = h - 1;  
        int wh = w * h;  
        int div = radius + radius + 1;  
  
        int r[] = new int[wh];  
        int g[] = new int[wh];  
        int b[] = new int[wh];  
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;  
        int vmin[] = new int[Math.max(w, h)];  
  
        int divsum = (div + 1) >> 1;  
        divsum *= divsum;  
        int dv[] = new int[256 * divsum];  
        for (i = 0; i < 256 * divsum; i++) {  
            dv[i] = (i / divsum);  
        }  
  
        yw = yi = 0;  
  
        int[][] stack = new int[div][3];  
        int stackpointer;  
        int stackstart;  
        int[] sir;  
        int rbs;  
        int r1 = radius + 1;  
        int routsum, goutsum, boutsum;  
        int rinsum, ginsum, binsum;  
  
        for (y = 0; y < h; y++) {  
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;  
            for (i = -radius; i <= radius; i++) {  
                p = pix[yi + Math.min(wm, Math.max(i, 0))];  
                sir = stack[i + radius];  
                sir[0] = (p & 0xff0000) >> 16;  
                sir[1] = (p & 0x00ff00) >> 8;  
                sir[2] = (p & 0x0000ff);  
                rbs = r1 - Math.abs(i);  
                rsum += sir[0] * rbs;  
                gsum += sir[1] * rbs;  
                bsum += sir[2] * rbs;  
                if (i > 0) {  
                    rinsum += sir[0];  
                    ginsum += sir[1];  
                    binsum += sir[2];  
                } else {  
                    routsum += sir[0];  
                    goutsum += sir[1];  
                    boutsum += sir[2];  
                }  
            }  
            stackpointer = radius;  
  
            for (x = 0; x < w; x++) {  
  
                r[yi] = dv[rsum];  
                g[yi] = dv[gsum];  
                b[yi] = dv[bsum];  
  
                rsum -= routsum;  
                gsum -= goutsum;  
                bsum -= boutsum;  
  
                stackstart = stackpointer - radius + div;  
                sir = stack[stackstart % div];  
  
                routsum -= sir[0];  
                goutsum -= sir[1];  
                boutsum -= sir[2];  
  
                if (y == 0) {  
                    vmin[x] = Math.min(x + radius + 1, wm);  
                }  
                p = pix[yw + vmin[x]];  
  
                sir[0] = (p & 0xff0000) >> 16;  
                sir[1] = (p & 0x00ff00) >> 8;  
                sir[2] = (p & 0x0000ff);  
  
                rinsum += sir[0];  
                ginsum += sir[1];  
                binsum += sir[2];  
  
                rsum += rinsum;  
                gsum += ginsum;  
                bsum += binsum;  
  
                stackpointer = (stackpointer + 1) % div;  
                sir = stack[(stackpointer) % div];  
  
                routsum += sir[0];  
                goutsum += sir[1];  
                boutsum += sir[2];  
  
                rinsum -= sir[0];  
                ginsum -= sir[1];  
                binsum -= sir[2];  
  
                yi++;  
            }  
            yw += w;  
        }  
        for (x = 0; x < w; x++) {  
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;  
            yp = -radius * w;  
            for (i = -radius; i <= radius; i++) {  
                yi = Math.max(0, yp) + x;  
  
                sir = stack[i + radius];  
  
                sir[0] = r[yi];  
                sir[1] = g[yi];  
                sir[2] = b[yi];  
  
                rbs = r1 - Math.abs(i);  
  
                rsum += r[yi] * rbs;  
                gsum += g[yi] * rbs;  
                bsum += b[yi] * rbs;  
  
                if (i > 0) {  
                    rinsum += sir[0];  
                    ginsum += sir[1];  
                    binsum += sir[2];  
                } else {  
                    routsum += sir[0];  
                    goutsum += sir[1];  
                    boutsum += sir[2];  
                }  
  
                if (i < hm) {  
                    yp += w;  
                }  
            }  
            yi = x;  
            stackpointer = radius;  
            for (y = 0; y < h; y++) {  
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )  
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];  
  
                rsum -= routsum;  
                gsum -= goutsum;  
                bsum -= boutsum;  
  
                stackstart = stackpointer - radius + div;  
                sir = stack[stackstart % div];  
  
                routsum -= sir[0];  
                goutsum -= sir[1];  
                boutsum -= sir[2];  
  
                if (x == 0) {  
                    vmin[y] = Math.min(y + r1, hm) * w;  
                }  
                p = x + vmin[y];  
  
                sir[0] = r[p];  
                sir[1] = g[p];  
                sir[2] = b[p];  
  
                rinsum += sir[0];  
                ginsum += sir[1];  
                binsum += sir[2];  
  
                rsum += rinsum;  
                gsum += ginsum;  
                bsum += binsum;  
  
                stackpointer = (stackpointer + 1) % div;  
                sir = stack[stackpointer];  
  
                routsum += sir[0];  
                goutsum += sir[1];  
                boutsum += sir[2];  
  
                rinsum -= sir[0];  
                ginsum -= sir[1];  
                binsum -= sir[2];  
  
                yi += w;  
            }  
        }  
  
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);  
  
        long time2 = System.currentTimeMillis();
        long time = time2 - time1;
//        System.out.println(" =========== doBlur time = " + time);
        
        return (bitmap);  
    }  
    
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
    
    
// ========================= 图片缩放 =========================================
    
    /**
	 * 把bitmap转换成byte[]
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] bitmapToBytes(String filePath) {
		Integer inSampleSize = 1;
		Bitmap bm = getSmallBitmap(filePath, inSampleSize);
		int quality = 100;
		if(inSampleSize == 1) {
			quality = 100;
		} else if(inSampleSize == 2) {
			quality = 80;
		} else if(inSampleSize == 3) {
			quality = 60;
		} else {
			quality = 40;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, quality, baos);
		byte[] b = baos.toByteArray();
		
//		Debug.o("bitmapToBytes : " + b.length);
		return b;
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		
		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
		}


//		Debug.o("width : " + width + " height : " + height + " inSampleSize : " + inSampleSize);
		
		return inSampleSize;
	}
	
	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, Integer inSampleSize) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 720, 1280);
		inSampleSize = options.inSampleSize;

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}
    
	
	
// ========================= 图片剪切 =========================================	
	
	public static Bitmap getCircleBitmap(Bitmap bitmap) {
        return getCircleBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight());
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap, int width, int height) {
        Bitmap croppedBitmap = scaleCenterCrop(bitmap, width, height);
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, width, height);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        int radius = (width > height) ? height : width;
        radius /= 2;

        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(croppedBitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        Config config = source.getConfig();
        if(config == null) {// why ?
        	config = Config.ARGB_8888;
        }
        
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, config);
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }
}
