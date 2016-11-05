package com.example.juda.xox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button a1, a2, a3, b1, b2, b3, c1, c2, c3, btnNewGame;

    boolean turn = true;
    int tor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a1 = (Button) findViewById(R.id.a1);
        a2 = (Button) findViewById(R.id.a2);
        a3 = (Button) findViewById(R.id.a3);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        c1 = (Button) findViewById(R.id.c1);
        c2 = (Button) findViewById(R.id.c2);
        c3 = (Button) findViewById(R.id.c3);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);

        tor = 1;

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a1.getText().toString().equals("")) {
                    if (tor == 1) {
                        tor = 2;
                        a1.setText("X");
                    } else if (tor == 2) {
                        tor = 1;
                        a1.setText("O");
                    }
                }
                gameOver();
            }
        });
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a2.getText().toString().equals("")) {
                    if (tor == 1) {
                        tor = 2;
                        a2.setText("X");
                    } else if (tor == 2) {
                        tor = 1;
                        a2.setText("O");
                    }
                }
                gameOver();
            }
        });
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a3.getText().toString().equals("")) {
                    if (tor == 1) {
                        tor = 2;
                        a3.setText("X");
                    } else if (tor == 2) {
                        tor = 1;
                        a3.setText("O");
                    }
                }
                gameOver();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b1.getText().toString().equals("")) {
                    if (tor == 1) {
                        tor = 2;
                        b1.setText("X");
                    } else if (tor == 2) {
                        tor = 1;
                        b1.setText("O");
                    }
                }
                gameOver();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b2.getText().toString().equals("")) {
                    if (tor == 1) {
                        tor = 2;
                        b2.setText("X");
                    } else if (tor == 2) {
                        tor = 1;
                        b2.setText("O");
                    }
                }
                gameOver();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b3.getText().toString().equals("")) {
                    if (tor == 1) {
                        tor = 2;
                        b3.setText("X");
                    } else if (tor == 2) {
                        tor = 1;
                        b3.setText("O");
                    }
                }
                gameOver();
            }
        });
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c1.getText().toString().equals("")) {
                    if (tor == 1) {
                        tor = 2;
                        c1.setText("X");
                    } else if (tor == 2) {
                        tor = 1;
                        c1.setText("O");
                    }
                }
                gameOver();
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c2.getText().toString().equals("")) {
                    if (tor == 1) {
                        tor = 2;
                        c2.setText("X");
                    } else if (tor == 2) {
                        tor = 1;
                        c2.setText("O");
                    }
                }
                gameOver();
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c3.getText().toString().equals("")) {
                    if (tor == 1) {
                        tor = 2;
                        c3.setText("X");
                    } else if (tor == 2) {
                        tor = 1;
                        c3.setText("O");
                    }
                }
                gameOver();
            }
        });

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn = true;
                tor = 0;
               // enableDisableAllButtons(true);
            }
        });
    }

    public void gameOver() {
        String a, b, c, d, e, f, g, h, i;

        boolean end = false;

        a = a1.getText().toString();
        b = a2.getText().toString();
        c = a3.getText().toString();
        d = b1.getText().toString();
        e = b2.getText().toString();
        f = b3.getText().toString();
        g = c1.getText().toString();
        h = c2.getText().toString();
        i = c3.getText().toString();

        //Player X wins
        if (a.equals("X") && b.equals("X") && c.equals("X")) {
            Toast.makeText(this, "Player X wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (a.equals("X") && e.equals("X") && i.equals("X")) {
            Toast.makeText(this, "Player X wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (c.equals("X") && e.equals("X") && g.equals("X")) {
            Toast.makeText(this, "Player X wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (a.equals("X") && d.equals("X") && g.equals("X")) {
            Toast.makeText(this, "Player X wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (b.equals("X") && e.equals("X") && h.equals("X")) {
            Toast.makeText(this, "Player X wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (c.equals("X") && f.equals("X") && i.equals("X")) {
            Toast.makeText(this, "Player X wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (d.equals("X") && e.equals("X") && f.equals("X")) {
            Toast.makeText(this, "Player X wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (g.equals("X") && h.equals("X") && i.equals("X")) {
            Toast.makeText(this, "Player X wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        //Player O wins
        if (a.equals("O") && b.equals("O") && c.equals("O")) {
            Toast.makeText(this, "Player O wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (a.equals("O") && e.equals("O") && i.equals("O")) {
            Toast.makeText(this, "Player O wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (c.equals("O") && e.equals("O") && g.equals("O")) {
            Toast.makeText(this, "Player O wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (a.equals("O") && d.equals("O") && g.equals("O")) {
            Toast.makeText(this, "Player O wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (b.equals("O") && e.equals("O") && h.equals("O")) {
            Toast.makeText(this, "Player O wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (c.equals("O") && f.equals("O") && i.equals("O")) {
            Toast.makeText(this, "Player O wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (d.equals("O") && e.equals("O") && f.equals("O")) {
            Toast.makeText(this, "Player O wins", Toast.LENGTH_SHORT).show();
            end = true;
        }
        if (g.equals("O") && h.equals("O") && i.equals("O")) {
            Toast.makeText(this, "Player O wins", Toast.LENGTH_SHORT).show();
            end = true;
        }

        if (end) {

            a1.setEnabled(false);
            a2.setEnabled(false);
            a3.setEnabled(false);
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
            c1.setEnabled(false);
            c2.setEnabled(false);
            c3.setEnabled(false);
        }
    }

}

   /*private void enableDisableAllButtons (boolean enable){

            a1.setClickable(enable);
            a2.setClickable(enable);
            a3.setClickable(enable);
            b1.setClickable(enable);
            b2.setClickable(enable);
            b3.setClickable(enable);
            c1.setClickable(enable);
            c2.setClickable(enable);
            c3.setClickable(enable);*/



