package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;



public class CustomButton extends FrameLayout {
    int row;
    int col;
    int value;    // 위치 정보(좌표값) &  value
    TextView textView;
//    boolean is_fixed;

    public CustomButton(Context context, int row, int col) {
        super(context);
        this.row = row;
        this.col = col;

        textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.button_selector);
        setClickable(true);
        addView(textView);
    }
    //set 함수로 TextView에서 숫자 수정
    public void set(int a) {
        value = a;
        if(a == 0) {
            textView.setText(""); //erase text attribute
        }
        else {
            textView.setText(a+"");
        }
    }
}
