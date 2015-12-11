package com.kindy.demo.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import android.view.View;

public class CanvasViewTest extends View {

	private Paint paint;
	private ArrayList<PointF> graphics = new ArrayList<PointF>();

	public CanvasViewTest(Context context) {
		super(context);
		paint = new Paint(); // 设置一个笔刷大小是3的黄色的画笔
		paint.setColor(Color.YELLOW);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(3);
	}

	// 在这里我们将测试canvas提供的绘制图形方法
	@Override
	protected void onDraw(Canvas canvas) {
		drawColor(canvas);
		// drawLine(canvas);
		// drawCircle(canvas);
		// drawArc(canvas);
		// drawOval(canvas);
		// drawRect(canvas);
		// drawRoundRect(canvas);
		// drawPosText(canvas);
		// drawTextOnPath(canvas);
		// drawPath(canvas);

		onDraw1(canvas);
		onDraw2(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			graphics.add(new PointF(event.getX(), event.getY()));

			invalidate(); // 重新绘制区域

			return true;
		}

		return super.onTouchEvent(event);
	}

	protected void onDraw2(Canvas canvas) {
		for (PointF point : graphics) {
			canvas.drawPoint(point.x, point.y, paint);
		}
	}

	protected void onDraw1(Canvas canvas) {
		canvas.save();

		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		canvas.translate(canvas.getWidth() / 2, 200); // 将位置移动画纸的坐标点:150,150
		canvas.drawCircle(0, 0, 100, paint); // 画圆圈

		// 使用path绘制路径文字
		canvas.save();
		canvas.translate(-75, -75);
		Path path = new Path();
		path.addArc(new RectF(0, 0, 150, 150), -180, 180);
		Paint citePaint = new Paint(paint);
		citePaint.setTextSize(14);
		citePaint.setStrokeWidth(1);
		canvas.drawTextOnPath("http://www.android777.com", path, 28, 0,
				citePaint);
		canvas.restore();

		Paint tmpPaint = new Paint(paint); // 小刻度画笔对象
		tmpPaint.setStrokeWidth(1);

		float y = 100;
		int count = 60; // 总刻度数

		for (int i = 0; i < count; i++) {
			if (i % 5 == 0) {
				canvas.drawLine(0f, y, 0, y + 12f, paint);
				canvas.drawText(String.valueOf(i / 5 + 1), -4f, y + 25f,
						tmpPaint);

			} else {
				canvas.drawLine(0f, y, 0f, y + 5f, tmpPaint);
			}
			canvas.rotate(360 / count, 0f, 0f); // 旋转画纸
		}

		// 绘制指针
		tmpPaint.setColor(Color.GRAY);
		tmpPaint.setStrokeWidth(4);
		canvas.drawCircle(0, 0, 7, tmpPaint);
		tmpPaint.setStyle(Style.FILL);
		tmpPaint.setColor(Color.YELLOW);
		canvas.drawCircle(0, 0, 5, tmpPaint);
		canvas.drawLine(0, 10, 0, -65, paint);

		canvas.restore();
	}

	protected void drawPath(Canvas canvas) {
		Path path = new Path(); // 定义一条路径
		path.moveTo(10, 10); // 移动到 坐标10,10
		path.lineTo(50, 60);
		path.lineTo(200, 80);
		path.lineTo(10, 10);

		canvas.drawPath(path, paint);
	}

	protected void drawTextOnPath(Canvas canvas) {
		Path path = new Path(); // 定义一条路径
		path.moveTo(10, 10); // 移动到 坐标10,10
		path.lineTo(50, 60);
		path.lineTo(200, 80);
		path.lineTo(10, 10);

		// canvas.drawPath(path, paint);
		canvas.drawTextOnPath("Android777开发者博客", path, 10, 10, paint);
	}

	protected void drawPosText(Canvas canvas) {
		// 按照既定点 绘制文本内容
		canvas.drawPosText("Android777", new float[] { 10, 10, // 第一个字母在坐标10,10
				20, 20, // 第二个字母在坐标20,20
				30, 30, // ....
				40, 40, 50, 50, 60, 60, 70, 70, 80, 80, 90, 90, 100, 100 },
				paint);
	}

	protected void drawRoundRect(Canvas canvas) {
		RectF rect = new RectF(50, 50, 200, 200);
		canvas.drawRoundRect(rect, 30, // x轴的半径
				30, // y轴的半径
				paint);
	}

	protected void drawRect(Canvas canvas) {
		RectF rect = new RectF(50, 50, 200, 200);
		canvas.drawRect(rect, paint);
	}

	protected void drawOval(Canvas canvas) {
		// 定义一个矩形区域
		RectF oval = new RectF(100, 100, 300, 400);
		// 矩形区域内切椭圆
		canvas.drawOval(oval, paint);
	}

	protected void drawLine(Canvas canvas) {
		// 画一条线
		canvas.drawLine(10, 10, 100, 100, paint);
	}

	protected void drawColor(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
	}

	protected void drawCircle(Canvas canvas) {
		canvas.drawCircle(100, 100, 90, paint);
	}

	/**
	 * 绘制弧线区域
	 * 
	 * @param canvas
	 */
	protected void drawArc(Canvas canvas) {
		RectF rect = new RectF(0, 0, 100, 100);

		canvas.drawArc(rect, // 弧线所使用的矩形区域大小
				0, // 开始角度
				90, // 扫过的角度
				true, // 是否使用中心
				paint);
	}
	
}
