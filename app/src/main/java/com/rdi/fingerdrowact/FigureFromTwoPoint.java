package com.rdi.fingerdrowact;

import android.graphics.PointF;

public class FigureFromTwoPoint {
    private PointF mStart;
    private PointF mEnd;

    public FigureFromTwoPoint(PointF start, PointF end) {
        mStart = start;
        mEnd = end;
    }

    public FigureFromTwoPoint(PointF start) {
        mStart = start;
        mEnd = start;
    }

    public PointF getStart() {
        return mStart;
    }

    public void setStart(PointF start) {
        mStart = start;
    }

    public PointF getEnd() {
        return mEnd;
    }

    public void setEnd(PointF end) {
        mEnd = end;
    }
}
