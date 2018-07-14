package com.judaco.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private GameRules gameRules;
    private BoardView boardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardView = findViewById(R.id.board);
        gameRules = new GameRules();
        boardView.setGameRules(gameRules);
        boardView.setMainActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_game) {
            newGame();
        }
        return super.onOptionsItemSelected(item);
    }

    public void gameIsOver(char c) {
        String message = (c == 'T') ? "Game is over. It's a TIE" : "Game is over. " + c + " wins";

        new AlertDialog.Builder(this).setTitle("Tic Tac Toe").setMessage(message)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        newGame();
                    }
                }).show();
    }

    private void newGame() {
        gameRules.newGame();
        boardView.invalidate();
    }
}
