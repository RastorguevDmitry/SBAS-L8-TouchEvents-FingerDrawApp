package com.rdi.fingerdrowact;

import static com.rdi.fingerdrowact.MainActivity.*;

public class ElementForListFigure {

    private Object mObject;
    private int mColor;
    private TYPE_OF_FIGURE mCurrentType;

    public ElementForListFigure(Object object, int color, TYPE_OF_FIGURE currentType) {
        mObject = object;
        mColor = color;
        mCurrentType = currentType;
    }

    public TYPE_OF_FIGURE getCurrentType() {
        return mCurrentType;
    }

    public void setCurrentType(TYPE_OF_FIGURE mCurrentType) {
        this.mCurrentType = mCurrentType;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object mObject) {
        this.mObject = mObject;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }
}
