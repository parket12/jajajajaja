package com.example.a0110;

import java.util.Random;

public class Game {
    private char[][] board;
    private char currentPlayer;
    private int xWins, oWins, draws;

    public Game() {
        board = new char[3][3];
        currentPlayer = 'X';
        xWins = 0;
        oWins = 0;
        draws = 0;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int row, int col) {
        if (board[row][col] == 0) {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    public boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                    (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    public boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        return true;
    }

    public void reset() {
        board = new char[3][3];
        currentPlayer = 'X';
    }

    public void updateStats(boolean xWon, boolean oWon) {
        if (xWon) {
            xWins++;
        } else if (oWon) {
            oWins++;
        } else {
            draws++;
        }
    }

    public int getXWins() {
        return xWins;
    }

    public int getOWins() {
        return oWins;
    }

    public int getDraws() {
        return draws;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public void updateStats(int xWins, int oWins, int draws) {
    }
}
