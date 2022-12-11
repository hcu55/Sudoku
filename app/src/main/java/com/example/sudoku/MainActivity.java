package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    BoardGenerator board = new BoardGenerator();
    TableLayout table;
    TableLayout numberpad;
    CustomButton clickedButton;
    CustomButton[][] buttons = new CustomButton[9][9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = (TableLayout) findViewById(R.id.table_board); //table_board id
        numberpad = (TableLayout) findViewById(R.id.NumberPad); //
        clickedButton = null;

        setBoard();
//        clickNumberPad();
    }

    private void setBoard() {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        tableParams.setMargins(15, 15, 15, 15);
//        CustomButton[][] buttons = new CustomButton[9][9];

        for (int row = 0; row < 9; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(tableParams);
            for (int col = 0; col < 9; col++) {
                //마진 적용
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                );
                layoutParams.setMargins(5, 0, col == 2 || col == 5 ? 40 : 3, row == 2 || row == 5 ? 20 : 0);

                //버튼 생성
                buttons[row][col] = new CustomButton(this, row, col);
                //보드에서 행,열 좌표 숫자 가져오기
                int number = board.get(row, col);
                int finalR = row;
                int finalC = col;

                //확률 적용
                if (Math.random() < 0.7) {
                    buttons[row][col].set(number);
                    buttons[row][col].textView.setTextColor(Color.BLACK);
//                    buttons[row][col].is_fixed = true;
                } else {
//                    buttons[row][col].is_fixed = false;
                    buttons[row][col].set(0);     // 랜덤으로 보이게 할것인지 아닌지 setting
                    buttons[row][col].textView.setTextColor(Color.GRAY);
//                    buttons[row][col].setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            clickedButton = buttons[finalR][finalC];
//                            numberpad.setVisibility(View.VISIBLE);
//                        }
//                    });
                    buttons[row][col].setLayoutParams(layoutParams);
//                buttons[row][col].set(0);
                    //행에 버튼 붙이기
                    tableRow.addView(buttons[row][col]);
                }
                //테이블에 행 붙이기
                table.addView(tableRow);
            }

        }
    }

//    public void onClickNum(View v) {
//        clickedCustomButton.set(1);
//        tableLayout.setVisibility(INVISIBLE);
//    }
//    public void onClickNum(View view, int num) {
//        if(board.get(currentButton.row,currentButton.col) == num) unsetConflict();
//        else setConflict();
//        currentButton.set(num);
//        currentButton.set3(num);
//        Toast.makeText(this, "test message", Toast.LENGTH_SHORT).show();
//    }

//        //Conflict
//        public void setConflict () {
//            clickedButton.setBackgroundColor(Color.RED);
//        }
//        // No conflict
//        public void unsetConflict () {
//            clickedButton.setBackgroundColor(Color.WHITE);
//        }

//        public void onClickNum (View view,int num){
//            if (board.get(clickedButton.row, clickedButton.col) == num) unsetConflict();
//            else setConflict();
//            clickedButton.set(num);
//        }
//
//        public void clickNumberPad () {
//            Button button1 = (Button) findViewById(R.id.number1);
//            button1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 1);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button button2 = (Button) findViewById(R.id.number2);
//            button2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 2);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button button3 = (Button) findViewById(R.id.number3);
//            button3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 3);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button button4 = (Button) findViewById(R.id.number4);
//            button4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 4);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button button5 = (Button) findViewById(R.id.number5);
//            button5.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 5);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button button6 = (Button) findViewById(R.id.number6);
//            button6.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 6);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button buttton7 = (Button) findViewById(R.id.number7);
//            buttton7.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 7);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button button8 = (Button) findViewById(R.id.number8);
//            button8.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 8);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button button9 = (Button) findViewById(R.id.number9);
//            button9.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 9);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button buttonCancle = (Button) findViewById(R.id.cancle);
//            buttonCancle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            Button buttonDelete = (Button) findViewById(R.id.delete);
//            buttonDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickNum(view, 0);
//                    clickedButton.setBackgroundColor(Color.WHITE);
//                    numberpad.setVisibility(View.INVISIBLE);
//                }
//            });
//        }


}