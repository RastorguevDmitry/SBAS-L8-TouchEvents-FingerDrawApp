package com.rdi.fingerdrowact.AnotherViewFromLesson;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {

    private static final String TAG = "DrawView";
    private Path mPath = new Path();
    private Paint mDotPaint = new Paint();
    private Paint mBackgroundPaint = new Paint();

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mDotPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        Log.d(TAG, "X: " + x + " Y: " + y);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
                mPath.lineTo(x, y);

                break;
            default:
                return super.onTouchEvent(event);
        }

        invalidate();
        return true;
    }

    private void setUpPaint() {
        mBackgroundPaint.setColor(Color.WHITE);

        mDotPaint.setColor(Color.RED);
        mDotPaint.setStrokeWidth(10f);
        mDotPaint.setAntiAlias(true);
        mDotPaint.setStyle(Paint.Style.STROKE);

    }

    public void clear() {
        mPath.reset();
        invalidate();
    }
}
