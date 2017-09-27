package com.example.gowtham.TIC_TAC_TOE;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gowtham.string_rev.R;


public class MainActivity extends AppCompatActivity  {

    static int ct = 0;
    static String Player_Symbol = "X";
    static String Computer_Symbol = "O";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("1","in start");


        Button bt1 = (Button) findViewById(R.id.button);
        Button bt2 = (Button) findViewById(R.id.button2);
        Button bt3 = (Button) findViewById(R.id.button3);
        Button bt4 = (Button) findViewById(R.id.button4);
        Button bt5 = (Button) findViewById(R.id.button5);
        Button bt6 = (Button) findViewById(R.id.button6);
        Button bt7 = (Button) findViewById(R.id.button7);
        Button bt8 = (Button) findViewById(R.id.button8);
        Button bt9 = (Button) findViewById(R.id.button9);
        final Button reset = (Button) findViewById(R.id.button10);


        final Button[][] grids = {{bt1,bt2,bt3},{bt4,bt5,bt6},{bt7,bt8,bt9}};

        setListeners(grids);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_the_game(grids);
                make_reset_disappear();
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setText("Make A Move");
            }
        });


    }

    public void reset_the_game(Button[][] grids)
    {

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
                grids[i][j].setClickable(true);
                grids[i][j].setText("");
            }
    }

    public void setListeners(final Button[][] grid)
    {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
                grid[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Button currButton = (Button) view;
                        currButton.setText(Player_Symbol);
                        currButton.setClickable(false);

                        Board curr_board = get_board(grid);
                        TextView tv = (TextView) findViewById(R.id.textView);


                        if(curr_board.hasWon(Player_Symbol))
                        {
                            tv.setText("you won!!!");
                            disable_buttons(grid);
                            display_reset();
                            return;
                        }

                        if(curr_board.is_draw())
                        {
                            display_reset();
                            tv.setText("ITS A DARW!!");
                            return;
                        }

                        Board newBoard = make_a_move(grid);

                        if(newBoard.hasWon(Computer_Symbol))
                        {
                            tv.setText("you lost!!!");
                            disable_buttons(grid);
                            display_reset();

                            return;
                        }
                        else
                        {
                            tv.setText("Make A Move");
                        }

                    }
                });
            }

    }

    public void disable_buttons(Button[][] grids)
    {
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
                grids[i][j].setClickable(false);
            }
    }

    public void display_reset()
    {
        Button Reset = (Button) findViewById(R.id.button10);
        Reset.setVisibility(View.VISIBLE);
        Reset.setClickable(true);
    }

    public void make_reset_disappear()
    {
        Button Reset = (Button) findViewById(R.id.button10);
        Reset.setVisibility(View.INVISIBLE);
        Reset.setClickable(false);
    }


    public static Board make_a_move(Button[][] grid)
    {
        Board board = get_board(grid);

        Board newBoard = Board.min_max(board,true,Computer_Symbol);

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
                if(!newBoard.state[i][j].equals(board.state[i][j]))
                {
                    grid[i][j].setText(Computer_Symbol);
                    grid[i][j].setClickable(false);
                    return newBoard;
                }
            }

        return newBoard;
    }



    public static Board get_board(Button[][] grid)
    {
        String[][] board = new String[3][3];

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
                board[i][j] = grid[i][j].getText().toString();
            }

        Board obj = new Board(board,0);

        return obj;
    }




}
