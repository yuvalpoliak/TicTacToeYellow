package com.example.tictactoeyellow;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore, playerTwoScore, whoIsWinning;
    final  Button[] buttons = new Button[9];
    private Button resetGame;

    private int player1Score, player2Score, roundCount;
    boolean activePlayer;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerOneScore = findViewById(R.id.scorePlayer1);
        playerTwoScore = findViewById(R.id.scorePlayer2);
        whoIsWinning = findViewById(R.id.whoIsWinning);
        resetGame = findViewById(R.id.resetGamebtn);

        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "button" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);

            buttons[i].setOnClickListener(this);

        }
        roundCount = 0;
        player1Score = 0;
        player2Score = 0;
        activePlayer = true;

    }



    @Override
    public void onClick(View view) {

        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt
                (buttonID.substring(buttonID.length() - 1, buttonID.length()));

        if (activePlayer) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#FF0000"));
            ((Button) view).setTextSize(80);
            gameState[gameStatePointer] = 0;

        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#0000FF"));
            ((Button) view).setTextSize(80);
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        if(checkWinner()){
            if(activePlayer){
                player1Score ++;
                updatePlayerScore();
                Toast.makeText(this, "player 1 Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
            else{
                player2Score ++;
                updatePlayerScore();
                Toast.makeText(this, "player 2 Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }
        else if(roundCount ==9){
            playAgain();
            Toast.makeText(this, "Its a tie!", Toast.LENGTH_SHORT).show();
        }
        else{
            activePlayer= !activePlayer;

        }
        if(player1Score > player2Score){
            whoIsWinning.setText(("player 1 is winning!"));
        }
        else if(player2Score > player1Score){
            whoIsWinning.setText("player 2 is winning!");
        }
        else{
            whoIsWinning.setText(" ");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                player1Score = 0;
                player2Score = 0;
                whoIsWinning.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWinner() {
        boolean winnerResult = false;


        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                    && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                    && gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;


    }
    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(player1Score));
        playerTwoScore.setText(Integer.toString(player2Score));
    }
    public void playAgain() {
        roundCount = 0;
        activePlayer = true;
        for(int i = 0; i<buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}
