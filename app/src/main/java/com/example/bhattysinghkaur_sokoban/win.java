package com.example.bhattysinghkaur_sokoban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//Names:   Ramneek Kaur, Amrit Singh, Harnoor Bhatty
//Date:    May 6 2019
//Purpose: To create a moderately complex game for
//Ms. Mafteecher's grade 6 students who finish work early
public class win extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void playagain(View view){
        Intent i  = new Intent (this, CharacterSel.class); //moves to character screen
        startActivity(i);
    }
}