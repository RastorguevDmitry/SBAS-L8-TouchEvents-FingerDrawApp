package com.rdi.fingerdrowact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.rdi.fingerdrowact.MainActivity.currentColor;
import static com.rdi.fingerdrowact.MainActivity.currentTypeFigure;

public class DrawBoxLinePathView extends View {

    private Path mPath;
    private Paint mDotPaint = new Paint();

    private FigureFromTwoPoint mFigureFromTwoPoint;

    private List<ElementForListFigure> mFigureList = new ArrayList<>();

    private GestureDetector gestureDetector;
    private List<PointF> pointList;
    private boolean mScroll = false;

    public void setScroll(boolean scroll) {
        mScroll = scroll;
    }

    public void changeScroll() {
        if (mScroll == true) mScroll = false;
        else mScroll = true;

    }

    public DrawBoxLinePathView(Context context) {
        this(context, null);
    }

    public DrawBoxLinePathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawBoxLinePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mDotPaint.setStrokeWidth(10f);
        mDotPaint.setAntiAlias(true);
        mDotPaint.setStyle(Paint.Style.STROKE);

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
                for (ElementForListFigure drawObject : mFigureList) {
                    switch (drawObject.getCurrentType()) {
                        case BOX:
                        case LINE:
                            ((FigureFromTwoPoint) drawObject.getObject()).scroll(-distanceX, -distanceY);
                            break;
                        case PATH:
                        case POLY:
                            ((Path) drawObject.getObject()).offset(-distanceX, -distanceY);
                            break;
                    }
                }
                invalidate();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mScroll) {
            return gestureDetector.onTouchEvent(event);
        }

        switch (currentTypeFigure) {
            case BOX:
            case LINE: {
                onTouchEventForFigureFromTwoPoint(event);
            }
            break;
            case PATH: {
                onTouchEventForPath(event);
            }
            break;
            case POLY: {
                onTouchEventForPoly(event);
            }
            break;

            default:
                return super.onTouchEvent(event);
        }
        invalidate();
        return true;
    }

    private void onTouchEventForPoly(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
                mFigureList.add(new ElementForListFigure(mPath, currentColor, currentTypeFigure));
                pointList = new ArrayList<>();
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
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        mPath.rewind();
        mPath.moveTo(pointList.get(0).x, pointList.get(0).y);
        for (int i = 1; i < pointList.size(); i++) {
            mPath.lineTo(pointList.get(i).x, pointList.get(i).y);
        }
        mPath.lineTo(pointList.get(0).x, pointList.get(0).y);
    }

    private void onTouchEventForPath(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
                mPath.moveTo(x, y);
                mFigureList.add(new ElementForListFigure(mPath, currentColor, currentTypeFigure));
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mPath = null;
                break;
        }
    }

    private void onTouchEventForFigureFromTwoPoint(MotionEvent event) {
        PointF currentPoint = new PointF(event.getX(), event.getY());
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mFigureFromTwoPoint = new FigureFromTwoPoint(currentPoint);
                mFigureList.add(new ElementForListFigure(mFigureFromTwoPoint, currentColor, currentTypeFigure));
                break;
            case MotionEvent.ACTION_MOVE:
                if (mFigureFromTwoPoint != null) {
                    mFigureFromTwoPoint.setEnd(currentPoint);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mFigureFromTwoPoint = null;
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (ElementForListFigure drawObject : mFigureList) {
            switch (drawObject.getCurrentType()) {
                case BOX:
                    drawBox(canvas, drawObject);
                    break;
                case LINE:
                    drawLine(canvas, drawObject);
                    break;
                case PATH:
                case POLY:
                    drawPath(canvas, drawObject);
                    break;
            }
        }
    }

    private void drawLine(Canvas canvas, ElementForListFigure drawObject) {
        mDotPaint.setColor(drawObject.getColor());
        FigureFromTwoPoint currentLine = (FigureFromTwoPoint) drawObject.getObject();
        canvas.drawLine(
                currentLine.getStart().x,
                currentLine.getStart().y,
                currentLine.getEnd().x,
                currentLine.getEnd().y,
                mDotPaint);
    }

    private void drawBox(Canvas canvas, ElementForListFigure drawObject) {
        mDotPaint.setColor(drawObject.getColor());
        FigureFromTwoPoint currentBox = (FigureFromTwoPoint) drawObject.getObject();
        canvas.drawRect(
                Math.min(currentBox.getEnd().x, currentBox.getStart().x),
                Math.min(currentBox.getEnd().y, currentBox.getStart().y),
                Math.max(currentBox.getEnd().x, currentBox.getStart().x),
                Math.max(currentBox.getEnd().y, currentBox.getStart().y),
                mDotPaint);
    }

    private void drawPath(Canvas canvas, ElementForListFigure drawObject) {
        mDotPaint.setColor(drawObject.getColor());
        switch (drawObject.getCurrentType()) {
            case POLY:
                mDotPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
            case PATH:
                mDotPaint.setStyle(Paint.Style.STROKE);
                break;
        }
        canvas.drawPath((Path) drawObject.getObject(), mDotPaint);
    }

    public void clear() {
        mFigureList.clear();
        invalidate();
    }
}
