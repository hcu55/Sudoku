package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    BoardGenerator board = new BoardGenerator();
    TableLayout table;
    TableLayout numberpad;
    CustomButton clickedButton;
    CustomButton[][] buttons = new CustomButton[9][9];
    Dialog dialog;
    Button resetButton;
    boolean[] clickedMemo = new boolean[9];
    boolean[][] saveBoard = new boolean[9][9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = (TableLayout) findViewById(R.id.table_board); //table_board id
        numberpad = (TableLayout) findViewById(R.id.NumberPad); // NumberPda id
        clickedButton = null;
        resetButton = findViewById(R.id.resetButton); //resetButton id

        makeBoard();
        clickNumberPad();
        reset();
    }

    public void makeBoard() {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        tableParams.setMargins(10, 15, 10, 0);

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
                layoutParams.setMargins(5, 0, col == 2 || col == 5 ? 30 : 3, row == 2 || row == 5 || row ==8 ? 20 : 0);

                //버튼 생성
                buttons[row][col] = new CustomButton(this, row, col);
                //보드에서 숫자 가져오기
                int number = board.get(row, col);
                int finalR = row;
                int finalC = col;

                //확률 적용
                if (Math.random() < 0.6) {
                    saveBoard[row][col] = true;
                    buttons[row][col].set(number);
                    buttons[row][col].textView.setTextColor(Color.BLACK);
                }
                else {
                    saveBoard[row][col] = false;
                    buttons[row][col].set(0);
                    buttons[row][col].textView.setTextColor(Color.GRAY);
                    buttons[row][col].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickedButton = buttons[finalR][finalC];
                            numberpad.setVisibility(View.VISIBLE);
                        }
                    });
                    buttons[row][col].setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            clickedButton = buttons[finalR][finalC];
                            memoDialog();
                            return false;
                        }
                    });
                }
                buttons[row][col].setLayoutParams(layoutParams);

                tableRow.addView(buttons[row][col]);
            }
            table.addView(tableRow);
        }
    }

    public void reset() {
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<9; i++) clickedMemo[i] = false;
                for(int j=0; j<9; j++) {
                    for(int k=0; k<9; k++) {
                        if(!saveBoard[j][k]) {
                            buttons[j][k].set(0);
                            buttons[j][k].deletMemo();
                        }
                        buttons[j][k].setBackgroundColor(Color.WHITE);
                    }
                }
            }
        });
    }

    //Conflict
    public void setConflict() {

        int x = clickedButton.row / 3;
        int y = clickedButton.col / 3;

        x = x * 3;
        y = y * 3;

        for (int i = x; i < x + 3; i++) {
            for (int j = y; j < y + 3; j++){
                if(i!=clickedButton.row&&j!= clickedButton.col&&clickedButton.value!=0){
                    if(clickedButton.value== buttons[i][j].value&&clickedButton.value!=0) {
                        buttons[i][j].setBackgroundColor(Color.RED);
                        clickedButton.setBackgroundColor(Color.RED);
                    }
                }
            }
       }
        for(int row=0;row<9;row++) {
            if (clickedButton.value == buttons[row][clickedButton.col].value&&clickedButton.row!=row&&buttons[row][clickedButton.col].value!=0){
                clickedButton.setBackgroundColor(Color.RED);
                buttons[row][clickedButton.col].setBackgroundColor(Color.RED);
            }
        }
        for(int col=0;col<9;col++) {
            if (clickedButton.value == buttons[clickedButton.row][col].value&&clickedButton.col!=col&&buttons[clickedButton.row][col].value!=0){
                clickedButton.setBackgroundColor(Color.RED);
                buttons[clickedButton.row][col].setBackgroundColor(Color.RED);
            }
        }
    }

    // unconflict
    public void unsetConflict() {
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                for(int k=0;k<9;k++){
                    if(buttons[i][j].value==0)
                        break;
                    if(buttons[i][j]==buttons[i][k])
                        break;
                    buttons[i][j].setBackgroundColor(Color.WHITE);
                }
            }
        }

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                for(int k=0;k<9;k++){
                 if(buttons[i][j].value==0)
                     break;
                 if(buttons[j][i]==buttons[k][i])
                     break;
                 buttons[i][j].setBackgroundColor(Color.WHITE);

                }
            }
        }


//        int x = clickedButton.row / 3;
//        int y = clickedButton.col / 3;
//
//        x = x * 3;
//        y = y * 3;
//
//        for (int i = x; i < x + 3; i++) {
//            for (int j = y; j < y + 3; j++){
//                if(i!=clickedButton.row&&j!= clickedButton.col){
//                    if(clickedButton.value== buttons[i][j].value) {
//                        buttons[i][j].setBackgroundColor(Color.WHITE);
//                        clickedButton.setBackgroundColor(Color.WHITE);
//                    }
//                }
//            }
//        }
    }

    public void onClickNum (View view,int num) {
        clickedButton.set(num);
        clickedButton.deletMemo();
        numberpad.setVisibility(View.INVISIBLE);

        unsetConflict();
        setConflict();
        clickedButton.set(num);
    }

    public void clickNumberPad() {
        Button button1 = (Button) findViewById(R.id.number1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 1);
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button button2 = (Button) findViewById(R.id.number2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 2);
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button button3 = (Button) findViewById(R.id.number3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 3);
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button button4 = (Button) findViewById(R.id.number4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 4);
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button button5 = (Button) findViewById(R.id.number5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 5);
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button button6 = (Button) findViewById(R.id.number6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 6);
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button buttton7 = (Button) findViewById(R.id.number7);
        buttton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 7);
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button button8 = (Button) findViewById(R.id.number8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 8);
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button button9 = (Button) findViewById(R.id.number9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 9);
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button buttonCancle = (Button) findViewById(R.id.cancle);
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberpad.setVisibility(View.INVISIBLE);
            }
        });

        Button buttonDelete = (Button) findViewById(R.id.delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNum(view, 0);
                clickedButton.setBackgroundColor(Color.WHITE);
                numberpad.setVisibility(View.INVISIBLE);

            }
        });
    }

    public void memoDialog() {
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_memo);
        dialog.show();
        String[] memoList = {"","","","","","","","",""};
        WindowManager.LayoutParams layoutparams = new WindowManager.LayoutParams();
        layoutparams.copyFrom(dialog.getWindow().getAttributes());
        layoutparams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutparams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Window window = dialog.getWindow();
        window.setAttributes(layoutparams);

        ToggleButton memo1 = dialog.findViewById(R.id.memoNum1);
        ToggleButton memo2 = dialog.findViewById(R.id.memoNum2);
        ToggleButton memo3 = dialog.findViewById(R.id.memoNum3);
        ToggleButton memo4 = dialog.findViewById(R.id.memoNum4);
        ToggleButton memo5 = dialog.findViewById(R.id.memoNum5);
        ToggleButton memo6 = dialog.findViewById(R.id.memoNum6);
        ToggleButton memo7 = dialog.findViewById(R.id.memoNum7);
        ToggleButton memo8 = dialog.findViewById(R.id.memoNum8);
        ToggleButton memo9 = dialog.findViewById(R.id.memoNum9);
        Button delete = dialog.findViewById(R.id.memoDel);
        Button cancle = dialog.findViewById(R.id.memoCan);
        Button ok = dialog.findViewById(R.id.memoOk);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedButton.deletMemo();
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedButton.setBackgroundColor(Color.WHITE);
                clickedButton.setMemo(memoList);
                dialog.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        memo1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                    memoList[0]= "1";
                else
                    memoList[0]= "";
            }
        });
        memo2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                    memoList[1]= "2";
                else
                    memoList[1]= "";
            }
        });
        memo3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                    memoList[2]= "3";
                else
                    memoList[2]= "";
            }
        });
        memo4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                    memoList[3]= "4";
                else
                    memoList[3]= "";
            }
        });
        memo5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                    memoList[4]= "5";
                else
                    memoList[4]= "";;
            }
        });
        memo6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                    memoList[5]= "6";
                else
                    memoList[5]= "";
            }
        });
        memo7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                    memoList[6]= "7";
                else
                    memoList[6]= "";
            }
        });
        memo8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                    memoList[7]= "8";
                else
                    memoList[7]= "";
            }
        });
        memo9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if(check)
                    memoList[8]= "9";
                else
                    memoList[8]= "";
            }
        });
    }
}