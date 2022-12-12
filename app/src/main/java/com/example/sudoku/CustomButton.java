package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;



public class CustomButton extends FrameLayout {
    int row;
    int col;
    int value;
    TextView textView;
    TableLayout memo;
    TextView[] memos = new TextView[9];


    public CustomButton(Context context, int row, int col) {
        super(context);
        this.row = row;
        this.col = col;

        textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(20);
        setBackgroundResource(R.drawable.button_selector);
        setClickable(false);
        addView(textView);

        //Memo Implementation
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        memo = (TableLayout) layoutInflater.inflate(R.layout.layout_memo, null);


        for(int i = 0, k=0; i < 3; i++) {
            TableRow tableRow = (TableRow) memo.getChildAt(i);
            for(int j = 0; j < 3; j++, k++) {
                memos[k] = (TextView) tableRow.getChildAt(j);
            }
        }
        deletMemo();
        addView(memo);
    }

    public void set(int a) {
        value = a;
        if(a == 0) { textView.setText(null); }
        else { textView.setText(String.valueOf(a)); }
    }

    public void setMemo(String[] list) {
        textView.setText("");
        for(int i=0;i<9;i++) {
            memos[i].setText(list[i]);

        }
    }

    public void deletMemo(){
        for(TextView m:memos){
            m.setText("");
        }
    }

}
