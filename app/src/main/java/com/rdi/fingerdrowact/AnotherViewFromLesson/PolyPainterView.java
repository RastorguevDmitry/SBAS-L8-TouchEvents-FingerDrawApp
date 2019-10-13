package com.rdi.fingerdrowact.AnotherViewFromLesson;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.rdi.fingerdrowact.R;

import java.util.ArrayList;
import java.util.List;

public class PolyPainterView extends View {
    private static final String TAG = "PolyPainterView";
    private GestureDetector gestureDetector;

    private Paint mPaint;
    private List<PointF> pointList = new ArrayList<>();

    private boolean mScroll;

    public void setmScroll(boolean mScroll) {
        this.mScroll = mScroll;
    }

    public PolyPainterView(Context context) {
        this(context, null);
    }

    public PolyPainterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PolyPainterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (pointList.size() == 1) {
            canvas.drawPoint(pointList.get(0).x, pointList.get(0).y, mPaint);
        } else {
            for (int i = 1; i < pointList.size(); i++) {
                PointF one = pointList.get(i - 1);
                PointF two = pointList.get(i);

                canvas.drawLine(one.x, one.y, two.x, two.y, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mScroll) {
            return gestureDetector.onTouchEvent(event);
        }


        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                pointList.clear();
                pointList.add(new PointF(event.getX(), event.getY()));
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int pointerId = event.getPointerId(event.getActionIndex());
                if (pointList.size() == pointerId) {
                    pointList.add(new PointF(
                            event.getX(event.getActionIndex()),
                            event.getY(event.getActionIndex())));
                } else {
                    PointF point = pointList.get(pointerId);
                    point.x = event.getX(event.getActionIndex());
                    point.y = event.getY(event.getActionIndex());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int id = event.getPointerId(i);
                    PointF point = pointList.get(id);
                    point.x = event.getX(i);
                    point.y = event.getY(i);
                }


                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                return super.onTouchEvent(event);
        }
        invalidate();

        return true;

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(getContext().getResources().getDimension(R.dimen.stroke));
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                for (PointF value : pointList) {
                    value.x -= distanceX;
                    value.y -= distanceY;
                }
                invalidate();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                mPaint.setColor(Color.YELLOW);
                invalidate();
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

}
