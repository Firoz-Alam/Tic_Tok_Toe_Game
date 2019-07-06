package com.example.lenovo.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonReset;
    private boolean gameOver = false;
    private GridLayout gridLayout;

    enum Player {

        ONE, TWO, NO
    }

    Player currentPlayer = Player.ONE;

    Player[] playerChoice = new Player[9];
    int[][] winnerArrays = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {2, 4, 6}, {0, 4, 8}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridlayout);
        buttonReset = findViewById(R.id.buttonReset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        for (int index = 0; index < playerChoice.length; index++) {
            playerChoice[index] = Player.NO;
        }

    }


    public void imageViewTapped(View view) {

        ImageView tappedImageView = (ImageView) view;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if (playerChoice[tiTag] == Player.NO && gameOver == false) {

            tappedImageView.setTranslationX(-2000f);


            playerChoice[tiTag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.right);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImageView.setImageResource(R.drawable.cross);
                currentPlayer = Player.ONE;
            }
            tappedImageView.animate().translationXBy(2000f).alpha(1f).rotation(3600).setDuration(1000);
            Toast.makeText(this, tappedImageView.getTag().toString(), Toast.LENGTH_SHORT).show();


            for (int[] winnerColmns : winnerArrays) {
                if (playerChoice[winnerColmns[0]] == playerChoice[winnerColmns[1]]
                        && playerChoice[winnerColmns[1]] == playerChoice[winnerColmns[2]]
                        && playerChoice[winnerColmns[0]] != Player.NO) {

                    buttonReset.setVisibility(View.VISIBLE);
                    gameOver = true;

                    String gameWinner = "";

                    if (currentPlayer == Player.ONE) {
                        gameWinner = "Player Two";
                    } else if (currentPlayer == Player.TWO) {
                        gameWinner = "Player tow";
                    }

                    Toast.makeText(this, gameWinner + " is Win the game", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    public void resetGame(){

        for (int index = 0; index < gridLayout.getChildCount(); index++){

            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }

        for (int index = 0; index < playerChoice.length; index++){
            playerChoice[index] = Player.NO;
        }

        gameOver = false;
        buttonReset.setVisibility(View.INVISIBLE);
    }
}