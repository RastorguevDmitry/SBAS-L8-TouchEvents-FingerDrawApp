package com.rdi.fingerdrowact;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnClear;
    private ImageButton btnScroll;
    private ImageButton btnDrawRect;
    private ImageButton btnDrawLine;
    private ImageButton btnDrawPath;
    private ImageButton btnDrawPoly;

    private Button btnColorOne;
    private Button btnColorTwo;
    private Button btnColorThree;
    private Button btnColorFour;

    private DrawBoxLinePathView drawView;

    enum TYPE_OF_FIGURE {BOX, LINE, PATH, POLY}
    static TYPE_OF_FIGURE currentTypeFigure;

    static Integer currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawView = findViewById(R.id.draw_view);
        initButtonChooseFigure();
        initButtonChooseColor();
    }

    private void initButtonChooseColor() {
        btnColorOne = findViewById(R.id.color_one);
        btnColorTwo = findViewById(R.id.color_two);
        btnColorThree = findViewById(R.id.color_three);
        btnColorFour = findViewById(R.id.color_four);

        btnColorOne.setBackgroundResource(R.color.colorBtnOne);
        btnColorTwo.setBackgroundResource(R.color.colorBtnTwo);
        btnColorThree.setBackgroundResource(R.color.colorBtnThree);
        btnColorFour.setBackgroundResource(R.color.colorBtnFour);

        setDefaultTextForColor();
        btnColorOne.setTextColor(Color.WHITE);
        btnColorOne.setText("V");
        currentColor = getResources().getColor(R.color.colorBtnOne);

        btnColorOne.setOnClickListener(this);
        btnColorTwo.setOnClickListener(this);
        btnColorThree.setOnClickListener(this);
        btnColorFour.setOnClickListener(this);
    }

    private void initButtonChooseFigure() {
        btnClear = findViewById(R.id.btn_clear);
        btnScroll = findViewById(R.id.btn_scroll);
        btnDrawRect = findViewById(R.id.btn_draw_rect);
        btnDrawLine = findViewById(R.id.btn_draw_line);
        btnDrawPath = findViewById(R.id.btn_draw_path);
        btnDrawPoly = findViewById(R.id.btn_draw_poly_paimter);
        setDefaultBackgroundForBtnChooseFigure();
        btnDrawRect.setAlpha(1f);
        currentTypeFigure = TYPE_OF_FIGURE.BOX;

        btnClear.setOnClickListener(this);
        btnScroll.setOnClickListener(this);
        btnDrawRect.setOnClickListener(this);
        btnDrawLine.setOnClickListener(this);
        btnDrawPath.setOnClickListener(this);
        btnDrawPoly.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                drawView.clear();
                break;
            case R.id.btn_scroll:
                drawView.changeScroll();
                btnScroll.setAlpha(1f);
                break;
            case R.id.btn_draw_rect:
                currentTypeFigure = TYPE_OF_FIGURE.BOX;
                setDefaultBackgroundForBtnChooseFigure();
                btnDrawRect.setAlpha(1f);
                break;
            case R.id.btn_draw_line:
                currentTypeFigure = TYPE_OF_FIGURE.LINE;
                setDefaultBackgroundForBtnChooseFigure();
                btnDrawLine.setAlpha(1f);
                break;
            case R.id.btn_draw_path:
                currentTypeFigure = TYPE_OF_FIGURE.PATH;
                setDefaultBackgroundForBtnChooseFigure();
                btnDrawPath.setAlpha(1f);
                break;
            case R.id.btn_draw_poly_paimter:
                currentTypeFigure = TYPE_OF_FIGURE.POLY;
                setDefaultBackgroundForBtnChooseFigure();
                btnDrawPoly.setAlpha(1f);
                break;

            case R.id.color_one:
                currentColor = getResources().getColor(R.color.colorBtnOne);
                setDefaultTextForColor();
                btnColorOne.setText("V");
                break;
            case R.id.color_two:
                currentColor = getResources().getColor(R.color.colorBtnTwo);
                setDefaultTextForColor();
                btnColorTwo.setText("V");
                break;
            case R.id.color_three:
                currentColor = getResources().getColor(R.color.colorBtnThree);
                setDefaultTextForColor();
                btnColorThree.setText("V");
                break;
            case R.id.color_four:
                currentColor = getResources().getColor(R.color.colorBtnFour);
                setDefaultTextForColor();
                btnColorFour.setText("V");
                break;
        }
    }

    private void setDefaultTextForColor() {
        btnColorOne.setText("");
        btnColorTwo.setText("");
        btnColorThree.setText("");
        btnColorFour.setText("");
        btnScroll.setAlpha(0.5f);
        drawView.setScroll(false);
    }

    private void setDefaultBackgroundForBtnChooseFigure() {
        btnDrawRect.setAlpha(0.5f);
        btnDrawLine.setAlpha(0.5f);
        btnDrawPath.setAlpha(0.5f);
        btnDrawPoly.setAlpha(0.5f);
        btnScroll.setAlpha(0.5f);
        drawView.setScroll(false);
    }
}
