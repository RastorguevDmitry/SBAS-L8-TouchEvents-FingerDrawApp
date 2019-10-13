package com.rdi.fingerdrowact.AnotherViewFromLesson;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BoxDrawView extends View {
    private static final String TAG = "BoxDrawView";
    private Paint mBoxPaint = new Paint();
    private List<Box> mBoxList = new ArrayList<>();
    private Box mCurrentBox;

    public BoxDrawView(Context context) {
        this(context, null);
    }

    public BoxDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentBox = new Box(current);
                mBoxList.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mCurrentBox = null;

                break;
            default:
                return super.onTouchEvent(event);
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       float left = 0;
       float right = 0;
       float top = 0;
       float bottom = 0;
        for (Box box : mBoxList) {
           left = Math.min(box.getCurrent().x, box.getOrigin().x);
           right = Math.max(box.getCurrent().x, box.getOrigin().x);
           top =  Math.min(box.getCurrent().y, box.getOrigin().y);
           bottom =  Math.max(box.getCurrent().y, box.getOrigin().y);
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }

    }

    private void initPaint() {
        mBoxPaint.setColor(Color.BLACK);
    }
}
