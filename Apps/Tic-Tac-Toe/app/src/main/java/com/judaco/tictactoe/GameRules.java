package com.judaco.tictactoe;

import java.util.Random;

/*
This class will store the elements of the Grid in an array,
and also contain a boolean which indicates if the game is over or not.
 */

public class GameRules {

    private static final Random RANDOM = new Random();
    private char currentPlayer;
    private char[] elements;
    private boolean gameOver;

    public GameRules() {
        elements = new char[9];
        newGame();
    }

    public boolean isGameOver() {
        return gameOver;
    }
    /*
    Let the currentPlayer to set the mark on the Grid
    by two positions (x,y)
     */
    public char play(int x, int y) {
        if (!gameOver && elements[3 * y + x] == ' ') {
            elements[3 * y + x] = currentPlayer;
            changePlayer();
        }

        return checkGameOver();
    }
    /*
    Let to change the currentPlayer for the next play
     */
    public void changePlayer() {
        currentPlayer = (currentPlayer == 'X' ? 'O' : 'X');
    }

    public char getElement(int x, int y) {
        return elements[3 * y + x];
    }

    public void newGame() {
        for (int i = 0; i  < elements.length; i++) {
            elements[i] = ' ';
        }

        currentPlayer = 'X';
        gameOver = false;
    }
    /*
    Check if the game is over by all the options -
    if we'll have a winner or it's gonna be a tie
     */
    public char checkGameOver() {
        //Vertical check
        for (int i = 0; i < 3; i++) {
            if (getElement(i, 0) != ' ' &&
                    getElement(i, 0) == getElement(i, 1)  &&
                    getElement(i, 1) == getElement(i, 2)) {
                gameOver = true;
                return getElement(i, 0);
            }
        //Horizontal check
            if (getElement(0, i) != ' ' &&
                    getElement(0, i) == getElement(1, i)  &&
                    getElement(1, i) == getElement(2, i)) {
                gameOver = true;
                return getElement(0, i);
            }
        }
        //Diagonal check - from left to right
        if (getElement(0, 0) != ' '  &&
                getElement(0, 0) == getElement(1, 1)  &&
                getElement(1, 1) == getElement(2, 2)) {
            gameOver = true;
            return getElement(0, 0);
        }
        ////Diagonal check - from right to left
        if (getElement(2, 0) != ' '  &&
                getElement(2, 0) == getElement(1, 1)  &&
                getElement(1, 1) == getElement(0, 2)) {
            gameOver = true;
            return getElement(2, 0);
        }

        for (int i = 0; i < 9; i++) {
            if (elements[i] == ' ')
                return ' ';
        }
        //A tie
        return 'T';
    }
    /*
    Let the player(user) to set a mark randomaly
     */
    public char computer() {
        if (!gameOver) {
            int position = -1;

            do {
                position = RANDOM.nextInt(9);
            } while (elements[position] != ' ');

            elements[position] = currentPlayer;
            changePlayer();
        }

        return checkGameOver();
    }
}
