package com.example.bhattysinghkaur_sokoban;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
public class MainActivity<uiOptions> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //removes notification bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //hides navigation bar (we can choose to enable this or not)
        //hideNavigationBar();

    }


    private void hideNavigationBar() {  //method to remove navigation bar (not called)

        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );


    }

    public void play (View view) {
        Intent i = new Intent(this, CharacterSel.class);
        startActivity(i);
        finish();
        overridePendingTransition(0,android.R.anim.fade_out);
    }

    public void inst (View view){
        Intent i = new Intent(this, instructions.class);
        startActivity(i);
        finish();
        overridePendingTransition(0,android.R.anim.fade_out);
    }
}