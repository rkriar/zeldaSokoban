package com.example.bhattysinghkaur_sokoban;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

    //When the user clicks play, character selection screen will show
    public void instplay (View view){ //go to instructions screen when clicking "instructions" on splash screen
        Intent i = new Intent (this, CharacterSel.class);
        startActivity(i);
    }
}