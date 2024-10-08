package com.example.a0110;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Game game;
    private SharedPreferences prefs;
    private TextView statsTextView;
    private boolean isDarkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        isDarkTheme = prefs.getBoolean("isDark", false);
        setTheme(isDarkTheme ? R.style.AppTheme_Dark : R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        statsTextView = findViewById(R.id.statsTextView);
        updateStatsDisplay();

        setupButtons();
    }

    private void setupButtons() {
        Button[][] buttons = new Button[3][3];
        buttons[0][0] = findViewById(R.id.button00);
        buttons[0][1] = findViewById(R.id.button01);
        buttons[0][2] = findViewById(R.id.button02);
        buttons[1][0] = findViewById(R.id.button10);
        buttons[1][1] = findViewById(R.id.button11);
        buttons[1][2] = findViewById(R.id.button12);
        buttons[2][0] = findViewById(R.id.button20);
        buttons[2][1] = findViewById(R.id.button21);
        buttons[2][2] = findViewById(R.id.button22);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j].setOnClickListener(v -> {
                    if (game.makeMove(row, col)) {
                        buttons[row][col].setText(String.valueOf(game.getCurrentPlayer()));
                        if (game.checkWin()) {
                            if (game.getCurrentPlayer() == 'X') {
                                game.updateStats(true, false);
                            } else {
                                game.updateStats(false, true);
                            }
                            saveStats();
                            Toast.makeText(this, "Победитель: " + game.getCurrentPlayer(), Toast.LENGTH_SHORT).show();
                            resetGame(buttons);
                        } else if (game.checkDraw()) {
                            game.updateStats(false, false);
                            saveStats();
                            Toast.makeText(this, "Ничья!", Toast.LENGTH_SHORT).show();
                            resetGame(buttons);
                        } else {
                            game.switchPlayer(); // Переключение на следующего игрока
                        }
                    }
                });
            }
        }

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> resetGame(buttons));

        Button themeButton = findViewById(R.id.themeButton);
        themeButton.setOnClickListener(v -> switchTheme());
    }

    private void resetGame(Button[][] buttons) {
        game.reset();
        for (Button[] buttonRow : buttons) {
            for (Button button : buttonRow) {
                button.setText("");
            }
        }
        updateStatsDisplay();
    }

    private void saveStats() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("xWins", game.getXWins());
        editor.putInt("oWins", game.getOWins());
        editor.putInt("draws", game.getDraws());
        editor.apply();
    }

    private void updateStatsDisplay() {
        statsTextView.setText("Иксы: " + game.getXWins() +
                "\nНули нули: " + game.getOWins() +
                "\n50 на 50: " + game.getDraws());
    }


    private void switchTheme() {
        isDarkTheme = !isDarkTheme;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isDark", isDarkTheme);
        editor.apply();
        recreate();
    }
}
