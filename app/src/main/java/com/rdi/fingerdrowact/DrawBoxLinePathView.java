package com.rdi.fingerdrowact;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.rdi.fingerdrowact.MainActivity.currentColor;
import static com.rdi.fingerdrowact.MainActivity.currentTYPEOFFIGURE;

public class DrawBoxLinePathView extends View {

    private Path mPath;
    private Paint mDotPaint = new Paint();
    private Paint mBoxPaint = new Paint();

    private FigureFromTwoPoint mBox;
    private FigureFromTwoPoint mLine;

    private List<ElementForListFigure> mFigureList = new ArrayList<>();

    public DrawBoxLinePathView(Context context) {
        this(context, null);
    }

    public DrawBoxLinePathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DrawBoxLinePathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        mDotPaint.setStrokeWidth(10f);
        mDotPaint.setAntiAlias(true);
        mDotPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        PointF currentPoint = new PointF(x, y);
        int action = event.getAction();

        switch (currentTYPEOFFIGURE) {
            case BOX: {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mBox = new FigureFromTwoPoint(currentPoint);
                        mFigureList.add(new ElementForListFigure(mBox, currentColor, MainActivity.TYPE_OF_FIGURE.BOX));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mBox != null) {
                            mBox.setEnd(currentPoint);
                            invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mBox = null;
                        break;
                    default:
                        return super.onTouchEvent(event);
                }
            }
            break;
            case PATH: {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mPath = new Path();
                        mPath.moveTo(x, y);
                        mFigureList.add(new ElementForListFigure(mPath, currentColor, MainActivity.TYPE_OF_FIGURE.PATH));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mPath.lineTo(x, y);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mPath = null;
                        break;
                    default:
                        return super.onTouchEvent(event);
                }
            }
            break;
            case LINE: {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mLine = new FigureFromTwoPoint(currentPoint);
                        mFigureList.add(new ElementForListFigure(mLine, currentColor, MainActivity.TYPE_OF_FIGURE.LINE));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mLine != null) {
                            mLine.setEnd(currentPoint);
                            invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    default:
                        return super.onTouchEvent(event);
                }
            }
            break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ElementForListFigure drawObject : mFigureList) {
            switch (drawObject.getCurrentType()) {
                case BOX:
                    drawBox(canvas, drawObject);
                    break;
                case PATH:
                    drawPath(canvas, drawObject);
                    break;
                case LINE:
                    drawLine(canvas, drawObject);
                    break;
            }
        }
    }

    private void drawPath(Canvas canvas, ElementForListFigure drawObject) {
        mDotPaint.setColor(drawObject.getColor());
        canvas.drawPath((Path) drawObject.getObject(), mDotPaint);
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
        FigureFromTwoPoint currentBox = (FigureFromTwoPoint) drawObject.getObject();
        mBoxPaint.setColor(drawObject.getColor());
        canvas.drawRect(
                Math.min(currentBox.getEnd().x, currentBox.getStart().x),
                Math.min(currentBox.getEnd().y, currentBox.getStart().y),
                Math.max(currentBox.getEnd().x, currentBox.getStart().x),
                Math.max(currentBox.getEnd().y, currentBox.getStart().y),
                mBoxPaint);
    }

    public void clear() {
        mFigureList.clear();
        invalidate();
    }


}
