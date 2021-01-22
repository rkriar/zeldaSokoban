package com.example.bhattysinghkaur_sokoban;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Game extends AppCompatActivity implements GestureDetector.OnGestureListener {
    int row = 15; //how many rows the game grid has
    int col = 9; //how many columns the game grid has
    int lvl = 1; //the lvl the user is on
    int x = 12; //x pos of the user
    int y = 4; //y pos of the user
    int picnum;
    int which_item =5; //var to hold the image number the user is pushing, changes per lvl
    int count_undo = 0; //how many times the user has clicked undo
    int current = 0; //variable to hold the number of turns the user has taken
    int death = 0;
    int song; //the song currently playing

    int undo[][][] = new int[row*col][row][col]; // 3D array to hold user's previous move
    int undox[] = new int [row*col]; //1D array to hold user's x position
    int undoy[] = new int [row*col]; //1D array to hold user's y position
    int facing[] = new int[row*col]; //1D array to hold the direction the user was facing

    String user; //holds user's character choice


    boolean object; //if object was moved for undo
    boolean if_undo; //if undo is equal to true
    boolean music = true; //if there is music playing or not

    //Global variables of the music player
    MediaPlayer happy = new MediaPlayer();

    ImageView pics[] = new ImageView[row * col];

    public static final int SWIPE_THRESHOLD = 100; //how many pixels travelled needed to process swipe (100px)
    public static final int SWIPE_VELEOCITY_THRESHOLD = 100; //how fast user needs to swipe in order to process swipe

    private GestureDetector gestureDetector;

    //Global variables coin images at top left
    private ImageView coin1;
    private ImageView coin2;
    private ImageView coin3;

    //Global variables heart images at top right
    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;


    //house arrays for link
    int house[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {2, 2, 2, 6, 2, 2, 2, 8, 2},  // 3 = blank2 = kill block
            {2, 2, 1, 1, 1, 1, 1, 1, 2},  // 4 = linkforward = link
            {2, 2, 1, 1, 1, 2, 2, 1, 2},  // 5 = pot
            {2, 2, 1, 1, 1, 2, 2, 1, 2},  // 6 = blank3 = win block (move pot here)
            {2, 2, 1, 1, 1, 1, 1, 1, 2},  // 8 = coin to collect in game
            {2, 2, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 5, 1, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 4, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},};

    int house1[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {2, 2, 2, 6, 2, 2, 2, 8, 2},  // 3 = blank2 = kill block
            {2, 2, 1, 1, 1, 1, 1, 1, 2},  // 4 = linkforward = link
            {2, 2, 1, 1, 1, 2, 2, 1, 2},  // 5 = pot
            {2, 2, 1, 1, 1, 2, 2, 1, 2},  // 6 = blank3 = win block (move pot here)
            {2, 2, 1, 1, 1, 1, 1, 1, 2},  // 8 = coin to collect in game
            {2, 2, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 5, 1, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 4, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},};

    //house arrays for zelda
    int house_z[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {2, 2, 2, 6, 2, 2, 2, 8, 2},  // 3 = blank2 = kill block
            {2, 2, 1, 1, 1, 1, 1, 1, 2},  // 7 = zeldaforward = link
            {2, 2, 1, 1, 1, 2, 2, 1, 2},  // 5 = pot
            {2, 2, 1, 1, 1, 2, 2, 1, 2},  // 6 = blank3 = win block (move pot here)
            {2, 2, 1, 1, 1, 1, 1, 1, 2},  // 8 = coin to collect in game
            {2, 2, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 5, 1, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 7, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},};

    int house_z1[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {2, 2, 2, 6, 2, 2, 2, 8, 2},  // 3 = blank2 = kill block
            {2, 2, 1, 1, 1, 1, 1, 1, 2},  // 7 = zeldaforward = link
            {2, 2, 1, 1, 1, 2, 2, 1, 2},  // 5 = pot
            {2, 2, 1, 1, 1, 2, 2, 1, 2},  // 6 = blank3 = win block (move pot here)
            {2, 2, 1, 1, 1, 1, 1, 1, 2},  // 8 = coin to collect in game
            {2, 2, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 5, 1, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 2, 2, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 1, 1, 1, 7, 1, 1, 1, 2},
            {2, 1, 1, 1, 1, 1, 1, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2},};

    //town arrays for link
    int town[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {2, 2, 2, 2, 2, 2, 2, 1, 2},  // 3 = blank2 = kill block
            {1, 1, 1, 1, 6, 1, 1, 1, 1},  // 4 = linkforward = link
            {2, 2, 1, 1, 1, 1, 1, 2, 2},  // 6 = blank3 = win block (move box here)
            {1, 1, 1, 1, 2, 1, 1, 8, 1},  // 8 = coin to collect in game
            {1, 1, 1, 1, 2, 1, 1, 1, 1},  // 9 = box
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {2, 2, 1, 1, 1, 1, 1, 2, 2},
            {3, 3, 3, 3, 1, 3, 3, 3, 3},
            {3, 3, 3, 3, 1, 3, 3, 3, 3},
            {1, 1, 1, 1, 9, 1, 1, 1, 1},
            {1, 1, 1, 1, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},};

    int town1[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {2, 2, 2, 2, 2, 2, 2, 1, 2},  // 3 = blank2 = kill block
            {1, 1, 1, 1, 6, 1, 1, 1, 1},  // 4 = linkforward = link
            {2, 2, 1, 1, 1, 1, 1, 2, 2},  // 8 = coin to collect in game
            {1, 2, 1, 1, 2, 1, 1, 8, 1},  // 9 = groceries_game
            {1, 1, 1, 1, 2, 1, 1, 1, 1},
            {1, 2, 1, 1, 1, 1, 1, 1, 1},
            {2, 2, 1, 1, 1, 1, 1, 2, 2},
            {3, 3, 3, 3, 1, 3, 3, 3, 3},
            {3, 3, 3, 3, 1, 3, 3, 3, 3},
            {1, 1, 1, 1, 9, 1, 1, 1, 1},
            {1, 1, 1, 1, 4, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},};

    //town arrays for zelda
    int town_z[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {2, 2, 2, 2, 2, 2, 2, 1, 2},  // 3 = blank2 = kill block
            {1, 1, 1, 1, 6, 1, 1, 1, 1},  // 7 = zeldaforward = zelda
            {2, 2, 1, 1, 1, 1, 1, 2, 2},  // 6 = blank3 = win block (move box here)
            {1, 1, 1, 1, 2, 1, 1, 8, 1},  // 8 = coin to collect in game
            {1, 1, 1, 1, 2, 1, 1, 1, 1},  // 9 = groceries_game
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {2, 2, 1, 1, 1, 1, 1, 2, 2},
            {3, 3, 3, 3, 1, 3, 3, 3, 3},
            {3, 3, 3, 3, 1, 3, 3, 3, 3},
            {1, 1, 1, 1, 9, 1, 1, 1, 1},
            {1, 1, 1, 1, 7, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},};

    int town_z1[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {2, 2, 2, 2, 2, 2, 2, 1, 2},  // 3 = blank2 = kill block
            {1, 1, 1, 1, 6, 1, 1, 1, 1},  // 7 = zeldaforward = zelda
            {2, 2, 1, 1, 1, 1, 1, 2, 2},  // 6 = blank3 = win block (move box here)
            {1, 1, 1, 1, 2, 1, 1, 8, 1},  // 8 = coin to collect in game
            {1, 1, 1, 1, 2, 1, 1, 1, 1},  // 9 = groceries_game
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {2, 2, 1, 1, 1, 1, 1, 2, 2},
            {3, 3, 3, 3, 1, 3, 3, 3, 3},
            {3, 3, 3, 3, 1, 3, 3, 3, 3},
            {1, 1, 1, 1, 9, 1, 1, 1, 1},
            {1, 1, 1, 1, 7, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},};

    int cave[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {1, 1, 1, 1, 6, 1, 1, 1, 1},  // 3 = blank2 = kill block
            {1, 1, 1, 1, 1, 1, 1, 1, 1},  // 4 = linkforward = link
            {3, 3, 3, 3, 3, 3, 3, 1, 1},  // 6 = blank3 = win block (move groceries_game here)
            {1, 1, 1, 1, 1, 1, 1, 8, 1},  // 8 = coin to collect in game
            {1, 1, 1, 1, 1, 1, 1, 1, 1},  // 9 = groceries_game
            {1, 1, 3, 3, 3, 3, 3, 3, 3},  // 10 = key
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 3, 3, 3, 3, 1, 1},
            {3, 3, 3, 3, 3, 3, 3, 1, 3},
            {1, 1, 1, 1, 10, 1, 1, 1, 1},
            {1, 1, 1, 1, 4, 1, 1, 1, 1},
            {3, 3, 3, 1, 1, 1, 3, 3, 3},
            {3, 3, 3, 1, 1, 1, 3, 3, 3},};

    int cave1[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {1, 1, 1, 1, 6, 1, 1, 1, 1},  // 3 = blank2 = kill block
            {1, 1, 1, 1, 1, 1, 1, 1, 1},  // 4 = linkforward = link
            {3, 3, 3, 3, 3, 3, 3, 1, 1},  // 6 = blank3 = win block (move groceries_game here)
            {1, 1, 1, 1, 1, 1, 1, 8, 1},  // 8 = coin to collect in game
            {1, 1, 1, 1, 1, 1, 1, 1, 1},  // 9 = groceries_game
            {1, 1, 3, 3, 3, 3, 3, 3, 3},  // 10 = key
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 3, 3, 3, 3, 1, 1},
            {3, 3, 3, 3, 3, 3, 3, 1, 3},
            {1, 1, 1, 1, 10, 1, 1, 1, 1},
            {1, 1, 1, 1, 4, 1, 1, 1, 1},
            {3, 3, 3, 1, 1, 1, 3, 3, 3},
            {3, 3, 3, 1, 1, 1, 3, 3, 3},};

    int cave_z[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {1, 1, 1, 1, 6, 1, 1, 1, 1},  // 3 = blank2 = kill block
            {1, 1, 1, 1, 1, 1, 1, 1, 1},  // 7 = zeldaforward = zelda
            {3, 3, 3, 3, 3, 3, 3, 1, 1},  // 6 = blank3 = win block (move groceries_game here)
            {1, 1, 1, 1, 1, 1, 1, 8, 1},  // 8 = coin to collect in game
            {1, 1, 1, 1, 1, 1, 1, 1, 1},  // 9 = groceries_game
            {1, 1, 3, 3, 3, 3, 3, 3, 3},  // 10 = key
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 3, 3, 3, 3, 1, 1},
            {3, 3, 3, 3, 3, 3, 3, 1, 3},
            {1, 1, 1, 1, 10, 1, 1, 1, 1},
            {1, 1, 1, 1, 7, 1, 1, 1, 1},
            {3, 3, 3, 1, 1, 1, 3, 3, 3},
            {3, 3, 3, 1, 1, 1, 3, 3, 3},};

    int cave_z1[][] = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 1 = blank  = walkable block
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  // 2 = blank1 = wall block
            {1, 1, 1, 1, 6, 1, 1, 1, 1},  // 3 = blank2 = kill block
            {1, 1, 1, 1, 1, 1, 1, 1, 1},  // 7 = zeldaforward = zelda
            {3, 3, 3, 3, 3, 3, 3, 1, 1},  // 6 = blank3 = win block (move groceries_game here)
            {1, 1, 1, 1, 1, 1, 1, 8, 1},  // 8 = coin to collect in game
            {1, 1, 1, 1, 1, 1, 1, 1, 1},  // 9 = groceries_game
            {1, 1, 3, 3, 3, 3, 3, 3, 3},  // 10 = key
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 3, 3, 3, 3, 1, 1},
            {3, 3, 3, 3, 3, 3, 3, 1, 3},
            {1, 1, 1, 1, 10, 1, 1, 1, 1},
            {1, 1, 1, 1, 7, 1, 1, 1, 1},
            {3, 3, 3, 1, 1, 1, 3, 3, 3},
            {3, 3, 3, 1, 1, 1, 3, 3, 3},};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //removes notification bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        gestureDetector = new GestureDetector(this);

        //Bundle passed on from CharacterSel class with user char choice
        Bundle bundle = getIntent().getExtras();
        String userchar = bundle.getString("userkey");

        user=userchar;

        happy = MediaPlayer.create(Game.this,R.raw.happy);
        happy.start();

        //finds id of the coins that are collected every lvl, shown up left
        coin1 =  (ImageView)findViewById(R.id.coin1);
        coin2 =  (ImageView)findViewById(R.id.coin2);
        coin3 =  (ImageView)findViewById(R.id.coin3);

        //finds id of the hearts that are lost every time user dies, shown up right
        heart1 = (ImageView)findViewById(R.id.heart1);
        heart2 = (ImageView)findViewById(R.id.heart2);
        heart3 = (ImageView)findViewById(R.id.heart3);

        GridLayout g = findViewById(R.id.grid); //background grid
        int m = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                pics[m] = new ImageView(this);
                setpicStart(pics[m], m, house);
                pics[m].setId(m);
                g.addView(pics[m]);
                m++;
            }
        }
        ImageView i = findViewById(R.id.quest1); //quest1 pop will  show to tell user what to do
        i.setVisibility(View.VISIBLE);

    }

    public void inst (View view){
        //dialog box to inform user that all progress will be lost
        new AlertDialog.Builder(this)
                //The title on the Dialog
                .setTitle("Alert")
                //The message that will appear
                .setMessage("You are exiting the game. All game progress will be lost.")
                //What to do if the button is pressed
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
        Intent i = new Intent (this, instructions.class); //takes user back to character selection screen
        startActivity(i);
    }

    public void quest1 (View view){
        ImageView i = findViewById(R.id.quest1); //when user clicks on pop up, it goes away
        i.setVisibility(View.INVISIBLE);
    }

    public void quest2 (View view){
        ImageView i = findViewById(R.id.quest2); //when user clicks on pop up, it goes away
        i.setVisibility(View.INVISIBLE);
    }
    public void quest3 (View view){
        ImageView i = findViewById(R.id.quest3); //when user clicks on pop up, it goes away
        i.setVisibility(View.INVISIBLE);
    }
    public void quest4 (View view){
        ImageView i = findViewById(R.id.quest4); //when user clicks on pop up, it goes away
        i.setVisibility(View.INVISIBLE);
    }
    public void quest5 (View view){
        ImageView i = findViewById(R.id.quest5); //when user clicks on pop up, it goes away
        i.setVisibility(View.INVISIBLE);
    }

    public void dead (View view){
        ImageView i = findViewById(R.id.dead); //when user clicks on pop up, it goes away
        i.setVisibility(View.INVISIBLE);
    }

    public boolean char_pick(){ //returns true if the user select is link, false if zelda, will help for determining which character to place in the grid
        if (user.equals("Link"))
            return true;
        else
            return false;
    }

    public void direction (int dir, int [][]house) { //sets direction of character according to direction it is facing and what character it is
        if (char_pick()) {
            if (dir == 1)
                pics[x * col + y].setImageResource(R.drawable.linkback);
            else if (dir == 2)
                pics[x * col + y].setImageResource(R.drawable.linkforward);
            else if (dir == 3)
                pics[x * col + y].setImageResource(R.drawable.linkleft);
            else
                pics[x * col + y].setImageResource(R.drawable.linkright);
        } else if (!char_pick()) {
            if (dir == 1)
                pics[x * col + y].setImageResource(R.drawable.zeldaback);
            else if (dir == 2)
                pics[x * col + y].setImageResource(R.drawable.zeldaforward);
            else if (dir == 3)
                pics[x * col + y].setImageResource(R.drawable.zeldaleft);
            else
                pics[x * col + y].setImageResource(R.drawable.zeldaright);
        }
        if (object == true && if_undo == true) { //sets object to its spot from previous move when undo is clicked if moved
            undo_lvlobj(dir, house);
        }
    }

        public void undo_lvlobj(int dir, int[][]house){ //will place the object depending on lvl accordingly to undo method
            if (dir == 1) {
                house[x - 2][y] = 1;
                pics[(x - 2) * col + y].setImageResource(R.drawable.blank);
                house[x - 1][y] = which_item;
                if (lvl == 1)
                pics[(x - 1) * col + y].setImageResource(R.drawable.pot);
                else if (lvl == 2)
                    pics[(x - 1) * col + y].setImageResource(R.drawable.box);
                else if (lvl == 3)
                    pics[(x - 1) * col + y].setImageResource(R.drawable.key);

            }
            else if (dir == 2) {
                house[x + 2][y] = 1;
                pics[(x + 2) * col + y].setImageResource(R.drawable.blank);
                house[x + 1][y] = which_item;
                if (lvl == 1)
                    pics[(x + 1) * col + y].setImageResource(R.drawable.pot);
                else if (lvl == 2)
                    pics[(x + 1) * col + y].setImageResource(R.drawable.box);
                else if (lvl == 3)
                    pics[(x + 1) * col + y].setImageResource(R.drawable.key);
            }
            else if (dir == 3) {
                house[x][y - 2]=1;
                pics[x * col + (y - 2)].setImageResource(R.drawable.blank);
                house[x][y + 1] = which_item;
                if (lvl == 1)
                    pics[x * col + (y - 1)].setImageResource(R.drawable.pot);
                else if (lvl == 2)
                    pics[x * col + (y - 1)].setImageResource(R.drawable.box);
                else if (lvl == 3)
                    pics[x * col + (y - 1)].setImageResource(R.drawable.key);
            }
            else {
                house[x][y + 2] = 1;
                pics[x * col + (y+2)].setImageResource(R.drawable.blank);
                house[x][y + 1] = which_item;
                if (lvl == 1)
                    pics[x * col + (y + 1)].setImageResource(R.drawable.pot);
                else if (lvl == 2)
                    pics[x * col + (y + 1)].setImageResource(R.drawable.box);
                else if (lvl == 3)
                    pics[x * col + (y + 1)].setImageResource(R.drawable.key);
            }
        }

    public void setpicStart(ImageView i, int pos, int[][]house) { //sets the blank grid pictures per level according to the background
        int c = pos / col; //gets first coordinate
        int d = pos % col; //gets second coordinate

        if (char_pick())
        picnum = house[c][d];
        else if (!char_pick())
            picnum = house_z[c][d];

        if (picnum == 1)
            i.setImageResource(R.drawable.blank);
        else if (picnum == 2)
            i.setImageResource(R.drawable.blank1);
        else if (picnum == 3)
            i.setImageResource(R.drawable.blank2);
        else if (picnum == 4)
            i.setImageResource(R.drawable.linkforward);
        else if (picnum == 5)
            i.setImageResource(R.drawable.pot);
        else if (picnum == 6)
            i.setImageResource(R.drawable.blank3);
        else if (picnum == 7)
            i.setImageResource(R.drawable.zeldaforward);
        else if (picnum == 8)
            i.setImageResource(R.drawable.coin_game);
        else if (picnum == 9)
            i.setImageResource(R.drawable.box);
        else if (picnum == 10)
            i.setImageResource(R.drawable.key);
    }

    public void next() { //updates screen to the next lvl
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.rLayout);
        lvl++;
        if (lvl == 2) {
            current = 0;
            x = 12;
            y = 4;
            which_item = 9;
            if (char_pick()) { //if char is link will update to the link version of lvl 2
                copyOver(house, town);
                direction(2, town);
                redraw(town);
            } else { //if char is zelda will update to the zelda version of lvl 2
                copyOver(house_z, town_z);
                direction(2, town_z);
                redraw(town_z);
            }
            Toast.makeText(getApplicationContext(), user + " going to town!", Toast.LENGTH_SHORT).show();
            rLayout.setBackgroundResource(R.drawable.town); //sets background to town
            ImageView i = (ImageView)findViewById(R.id.quest3);
            ImageView j = (ImageView)findViewById(R.id.quest2);
            i.setVisibility(View.VISIBLE);
            j.setVisibility(View.VISIBLE);
        }
        else if (lvl == 3) {
            current = 0;
            x = 12;
            y = 4;
            which_item = 10;
            if (char_pick()) { //if char is link will update to the link version of lvl 3
                copyOver(town, cave);
                direction(2, cave);
                redraw(cave);
                rLayout.setBackgroundResource(R.drawable.cavezelda);
            } else { //if char is zelda will update to the zelda version of lvl 3
                copyOver(town_z, cave_z);
                direction(2, cave_z);
                redraw(cave_z);
                rLayout.setBackgroundResource(R.drawable.cavelink);
            }
            Toast.makeText(getApplicationContext(), user + " is going to the  cave!", Toast.LENGTH_SHORT).show();
            ImageView l = (ImageView)findViewById(R.id.quest5);
            ImageView k = (ImageView)findViewById(R.id.quest4);
            l.setVisibility(View.VISIBLE);
            k.setVisibility(View.VISIBLE);
        }
        else if (lvl == 4){
            Intent i = new Intent (this,win.class); //all levels have been completed here, user goes to win screen
            startActivity(i);
        }
    }

    public void record (int [][]house, int dir) //records user's move in a 3D array
    {
        undox [current] = x;
        undoy [current] = y;
        facing[current] = dir;
        for (int i = 0 ; i < row ; i++)
            for (int j = 0 ; j < col ; j++)
                undo [current] [i] [j] = house [i] [j];
        current++;
    }

    public void undo (View view) //calls set undo method based on the lvl that the  user is on
    {
        if (lvl == 1)
        setUndo(house); //passes along house array for undo
        else if (lvl == 2)
            setUndo(town); //passes along town array for undo
        else if (lvl == 3)
            setUndo(cave);
    }

    public void setUndo (int [][]house){ //set undo method calls upon the previous move stored in undo 3D array
        if (current >0) {
            current--;
            for (int i = 0; i < row; i++)
                for (int j = 0; j < col; j++) {
                    if (object == false) { //object was not moved in previous move
                        house[i][j] = undo[current][i][j];
                        pics[x * col + y].setImageResource(R.drawable.blank);
                        x = undox[current];
                        y = undoy[current];
                        direction(facing[current], house); //gets the previous direction the character was facing
                        count_undo++;
                    }
                    else if (count_undo >0){ //if undo is clicked multiple times
                        if_undo = true;
                        object=true;
                        house[i][j] = undo[current][i][j];
                        pics[x * col + y].setImageResource(R.drawable.blank);
                        x = undox[current];
                        y = undoy[current];
                        direction(facing[current], house);//gets the previous direction the character was facing
                        count_undo++;
                    }
                    else {
                        if_undo = true;
                        house[i][j] = undo[current][i][j];
                        pics[x * col + y].setImageResource(R.drawable.blank);
                        x = undox[current];
                        y = undoy[current];
                        direction(facing[current], house);//gets the previous direction the character was facing
                        count_undo++;
                    }
                }
        }
        else {
            Toast.makeText(getApplicationContext(), "This is where you started, " + user + "!", Toast.LENGTH_SHORT).show();
        }
        object = false;
        if_undo = false;
            }

    public void back (View view){
        //dialog box to inform user that all progress will be lost
        new AlertDialog.Builder(this)
                //The title on the Dialog
                .setTitle("Alert")
                //The message that will appear
                .setMessage("You are exiting the game. All game progress will be lost.")
                //What to do if the button is pressed
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
        Intent i = new Intent (this, CharacterSel.class); //takes user back to character selection screen
        startActivity(i);
    }

    public void musicbutton (View view){ //when the user clicks on  music
        ImageView musicicon = (ImageView)findViewById(R.id.music);
        if (music == true) { //if music is playing
            music = false;
            musicicon.setImageResource(R.drawable.muted); //changes icon to muted
            if(happy.isPlaying()) {
                song = 1;
                Toast.makeText(getApplicationContext(), "The music has stopped", Toast.LENGTH_SHORT).show();
                happy.stop(); //stops playing the music
            }
        }
        else{
            if (music==false)
                happy = MediaPlayer.create(Game.this,R.raw.happy);
                music = true;
            musicicon.setImageResource(R.drawable.unmute); //changes icon to unmuted
            if (song == 1) {
                Toast.makeText(getApplicationContext(), "The music has started yay", Toast.LENGTH_SHORT).show();
                happy.start(); //starts playing the music
            }
        }
    }

    public void lvl_coin(){ //sets the coin according to the lvl the user is on to visible on the screen once it is collected
        if (lvl == 1)
            coin1.setVisibility(View.VISIBLE);
        else if (lvl == 2)
            coin2.setVisibility(View.VISIBLE);
        else if (lvl == 3)
            coin3.setVisibility(View.VISIBLE);
    }

    public void lvl_heart(int death){ //sets the number of lives user has depending on how many times they die
        if (death == 1)
            heart1.setVisibility(View.INVISIBLE);
        else if (death == 2)
            heart2.setVisibility(View.INVISIBLE);
        else if (death == 3){
            heart3.setVisibility(View.INVISIBLE);
            Intent i = new Intent (this, gameover.class);
            startActivity(i);
        }
    }

    public void move_pic(int x, int y, int dir) { //places object in lvl accordingly to where user is facing and has pushed it
        if (lvl == 1) { //will move pot in the 4 directions accordingly
            switch (dir) {
                case 1:
                    pics[(x - 2) * col + y].setImageResource(R.drawable.pot);
                    break;
                case 2:
                    pics[(x + 2) * col + y].setImageResource(R.drawable.pot);
                    break;
                case 3:
                    pics[(x) * col + (y - 2)].setImageResource(R.drawable.pot);
                    break;
                case 4:
                    pics[(x) * col + (y + 2)].setImageResource(R.drawable.pot);
                    break;
            }
        }
        else if (lvl == 2) { //will move box in the 4 directions accordingly
            switch (dir) {
                case 1:
                    pics[(x - 2) * col + y].setImageResource(R.drawable.box);
                    break;
                case 2:
                    pics[(x + 2) * col + y].setImageResource(R.drawable.box);
                    break;
                case 3:
                    pics[(x) * col + (y - 2)].setImageResource(R.drawable.box);
                    break;
                case 4:
                    pics[(x) * col + (y + 2)].setImageResource(R.drawable.box);
                    break;
            }
        }
        else if (lvl == 3){ //will move key in the 4 directions accordingly
            switch (dir) {
                case 1:
                    pics[(x - 2) * col + y].setImageResource(R.drawable.key);
                    break;
                case 2:
                    pics[(x + 2) * col + y].setImageResource(R.drawable.key);
                    break;
                case 3:
                    pics[(x) * col + (y - 2)].setImageResource(R.drawable.key);
                    break;
                case 4:
                    pics[(x) * col + (y + 2)].setImageResource(R.drawable.key);
                    break;
            }
        }
    }

    public void up(View view) { //method for button on screen calls move up according to lvl and passes the array along
        count_undo = 0;
        if (lvl == 1) {
            if (char_pick())
                move_up(house); //passes along link version of house
            else
                move_up(house_z); //passes along zelda version of house
        }
        else if (lvl == 2){
            if (char_pick())
                move_up(town); //passes along link version of town
            else
                move_up(town_z); //passes along zelda version of town
        }
        else if (lvl == 3){
            if (char_pick())
                move_up(cave); //passes along link version of cave
            else
                move_up(cave_z); //passes along link version of house
        }
    }

    public void move_up(int [][]house){ //checks for cases within moveing up
        if ((x - 1)>0 && house[x - 1][y] == 1 || (x - 1)>0 && house[x - 1][y] == 4 || (x - 1)>0 && house[x - 1][y]==8) { //checks for boundary and moves character up if it is a blank ahead
            pics[x * col + y].setImageResource(R.drawable.blank);
            if (house[x - 1][y]==8){ //if space in front is a coin, call coin method to update screen
                lvl_coin();
                house[x - 1][y] = 1;
                Toast.makeText(getApplicationContext(), "You collected a coin!", Toast.LENGTH_SHORT).show();
            }
            object = false;
            record(house,1);
            x--;
            direction(1, house);
        }
        else if ((x - 1)>0 && house[x - 1][y]== 3){ //if space in front is kill block
            pics[x * col + y].setImageResource(R.drawable.blank);
            x--;
            direction(1, house);
            Toast.makeText(getApplicationContext(), "Oh no, you just died!", Toast.LENGTH_SHORT).show();
            death++;
            lvl_heart(death);
            reset_ingame();
        }
        else if ((x - 1)>0 && house[x - 1][y] == which_item ) { //if the space in front is  the  object we are pushing
            if ((x - 2)>0 && house[x - 2][y] == 1 ) {
                house[x - 1][y] = 1;
                house[x - 2][y] = which_item;
                pics[x * col + y].setImageResource(R.drawable.blank);
                object = true;
                move_pic(x,y,1);
                record(house,1);
                x--;
                direction(1, house);
            } else if ((x - 2)>=0 && house[x - 2][y] == 6 ) { //if the space in front is the destination we are trying to reach
                house[x - 1][y] = 1;
                house[x - 2][y] = which_item;
                pics[x * col + y].setImageResource(R.drawable.blank);
                object = true;
                move_pic(x,y,1);
                record(house,1);
                x--;
                direction(1, house);
                next();

            }
        }
    }

    public void down(View view) {//method for button on screen calls move down according to lvl and passes the array along
        count_undo = 0;
        if (lvl == 1) {
            if (char_pick())
                move_down(house); //passes along link version of house
            else
                move_down(house_z); //passes along zelda version of house
        }
        else if (lvl == 2){
            if (char_pick())
                move_down(town); //passes along link version of town
            else
                move_down(town_z); //passes along zelda version of town
        }
        else if (lvl == 3){
            if (char_pick())
                move_down(cave); //passes along link version of cave
            else
                move_down(cave_z); //passes along zelda version of cave
        }
    }

    public void move_down(int [][]house) { //checks for cases within moving down
            if ((x + 1)< row && house[x + 1][y] == 1 || (x + 1)< row && house[x + 1][y] == 4 ||(x + 1)<row &&  house[x + 1][y]==8) { //checks for boundary and will move space if it is blank or orginal character position or coin
                pics[x * col + y].setImageResource(R.drawable.blank);
                if (house[x + 1][y]==8){ //if the space in front is a coin
                    lvl_coin();
                    house[x + 1][y] = 1;
                    Toast.makeText(getApplicationContext(), "You collected a coin!", Toast.LENGTH_SHORT).show();
                }
                object = false;
                record(house,2);
                x++;
                direction(2,house);
            }
            else if ((x + 1)>0 && house[x + 1][y]== 3){ //if space in front is a kill block
                pics[x * col + y].setImageResource(R.drawable.blank);
                x++;
                direction(2, house);
                Toast.makeText(getApplicationContext(), "Oh no, you just died!", Toast.LENGTH_SHORT).show();
                death++;
                lvl_heart(death);
                reset_ingame();
            }
            else if ((x + 1)<row && house[x + 1][y] == which_item) { //if the space in front is the object we are trying to push
                if ( (x + 2)<row && house[x + 2][y] == 1 ) {
                    house[x + 1][y] = 1;
                    house[x + 2][y] = which_item;
                    pics[x * col + y].setImageResource(R.drawable.blank);
                    object = true;
                    move_pic(x,y,2);
                    record(house,2);
                    x++;
                    direction(2, house);
                } else if ((x + 2)<row && house[x + 2][y] == 6) { //if the space in front is the destination we are trying to reach
                    house[x + 1][y] = 1;
                    house[x + 2][y] = 5;
                    pics[x * col + y].setImageResource(R.drawable.blank);
                    object = true;
                    move_pic(x,y,2);
                    record(house,2);
                    x++;
                    direction(2, house);
                    next();
                }

            }
    }

    public void right(View view){ //method for button on screen calls move right according to lvl and passes the array along
        count_undo = 0;
        if (lvl == 1) {
            if (char_pick())
                move_right(house);
            else
                move_right(house_z);
        }
        else if (lvl == 2){
            if (char_pick())
                move_right(town);
            else
                move_right(town_z);
        }
        else if (lvl == 3){
            if (char_pick())
                move_right(cave);
            else
                move_right(cave_z);
        }
    }

    public void move_right(int [][]house) {//checks for cases within moving right
            if ((y + 1)<col && house[x][y + 1] == 1 || (y + 1)<col && house[x][y + 1] == 4 || (y + 1)<col && house[x][y + 1]==8) { //if space in front is blank or coin or character it can move there and also checcks for boundadry
                    pics[x * col + y].setImageResource(R.drawable.blank);
                if (house[x][y + 1]==8){ //if space in front is a coin
                    lvl_coin();
                    house[x][y + 1]=1;
                    Toast.makeText(getApplicationContext(), "You collected a coin!", Toast.LENGTH_SHORT).show();
                }
                object = false;
                record(house,4);
                y++;
                direction(4, house);
            }
            else if ((y + 1)<col && house[x][y + 1]== 3){ //if space in front is a kill  block
                pics[x * col + y].setImageResource(R.drawable.blank);
                y++;
                direction(4, house);
                Toast.makeText(getApplicationContext(), "Oh no, you just died!", Toast.LENGTH_SHORT).show();
                death++;
                lvl_heart(death);
                reset_ingame();
            }
            else if ((y + 1)<col && house[x][y + 1] == which_item) { //if space in  front is the object we are trying  to push
                if ((y + 2)<col && house[x][y + 2] == 1 ) {
                    house[x][y + 1] = 1;
                    house[x][y + 2] = which_item;
                    pics[x * col + y].setImageResource(R.drawable.blank);
                    object = true;
                    move_pic(x,y,4);
                    record(house,4);
                    y++;
                    direction(4, house);
                } else if ((y + 2)<col && house[x][y + 2] == 6) { //if the space  in front is teh destination we are trying  to reach
                    house[x][y + 1] = 1;
                    house[x][y + 2] = 5;
                    pics[x * col + y].setImageResource(R.drawable.blank);
                    object = true;
                    move_pic(x,y,4);
                    record(house,4);
                    y++;
                    direction(4, house);
                    next();
                }

            }
    }

    public void left(View view){ //method for button on screen calls move left according to lvl and passes the array along
        count_undo = 0;
        if (lvl == 1) {
            if (char_pick())
                move_left(house);
            else
                move_left(house_z);
        }
        else if (lvl == 2){
            if (char_pick())
                move_left(town);
            else
                move_left(town_z);
        }
        else if (lvl == 3){
            if (char_pick())
                move_left(cave);
            else
                move_left(cave_z);
        }
    }

    public void move_left (int [][]house){//checks for cases within moving left
            if ((y - 1)>=0 && house[x][y - 1] == 1|| (y -1)>=0 && house[x][y - 1] == 4||(y - 1)>=0 && house[x][y-1]==8) { //checks for boundary or coin or is character space then replacce it  with blank
                pics[x * col + y].setImageResource(R.drawable.blank);
                if (house[x][y - 1]==8){ //if space in front is a coin
                    lvl_coin();
                    house[x][y - 1]=1;
                    Toast.makeText(getApplicationContext(), "You collected a coin!", Toast.LENGTH_SHORT).show();
                }
                object = false;
                record(house,3);
                y--;
                direction(3, house);
            }
            else if ((y - 1)>=0 && house[x][y - 1]== 3){ //if space in front is kill block
                pics[x * col + y].setImageResource(R.drawable.blank);
                y--;
                direction(3, house);
                Toast.makeText(getApplicationContext(), "Oh no, you just died!", Toast.LENGTH_SHORT).show();
                death++;
                lvl_heart(death);
                reset_ingame();
            }
            else if ((y - 1)>=0 && house[x][y - 1] == which_item) { //if  space in front is the object we are trying to push
                if ((y - 2)>=0 && house[x][y - 2] == 1){
                    house[x][y - 1] = 1;
                    house[x][y - 2] = which_item;
                    pics[x * col + y].setImageResource(R.drawable.blank);
                    object = true;
                    move_pic(x,y,3);
                    record(house,3);
                    y--;
                    direction(3, house);
                }
                else if ((y -2)>=0 && house[x][y - 2] == 6) { //if the space in front is destination
                    house[x][y - 1] = 1;
                    house[x][y - 2] = 5;
                    pics[x * col + y].setImageResource(R.drawable.blank);
                    object = true;
                    move_pic(x,y,3);
                    record(house,3);
                    y--;
                    direction(3, house);
                    next();
                }

            }
    }

    public void redraw (int[][]house){ //redraws the grid according to move taken
        int m = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                setpicStart(pics[m], m, house);
                m++;
            }
        }
    }
    public void copyOver(int a[][], int b[][]) { //copies over the old grid to the updated one
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                a[i][j] = b[i][j];
            }
        }
    }

    public void reset (View view){ //resets the grid according to the lvl it is on
        count_undo = 0;
        x = 12;
        y = 4;
        if (lvl == 1){
            if (char_pick()) {
                copyOver(house, house1);
                direction(2, house);
                redraw(house);
            }
            else {
                copyOver(house_z, house_z1);
                direction(2, house_z);
                redraw(house_z);
            }
            coin1.setVisibility(View.INVISIBLE);
        }
        else if (lvl == 2){
            if (char_pick()) {
                copyOver(town, town1);
                direction(2, town);
                redraw(town);
            }
            else {
                copyOver(town_z, town_z1);
                direction(2,town_z);
                redraw(town_z);
            }
            coin2.setVisibility(View.INVISIBLE);
        }
        else if (lvl ==  3){
            if (char_pick()) {
                copyOver(cave, cave1);
                direction(2, cave);
                redraw(cave);
            }
            else {
                copyOver(cave_z, cave_z1);
                direction(2,cave_z);
                redraw(cave_z);
            }
            coin3.setVisibility(View.INVISIBLE);
        }

    }

    public void reset_ingame(){ //the reset in game when the user dies
        count_undo = 0;
        x = 12;
        y = 4;
        if (lvl == 1){
            if (char_pick()) {
                copyOver(house, house1);
                direction(2, house);
                redraw(house);
            }
            else {
                copyOver(house_z, house_z1);
                direction(2, house_z);
                redraw(house_z);
            }
            coin1.setVisibility(View.INVISIBLE);
        }
        else if (lvl == 2){
            if (char_pick()) {
                copyOver(town, town1);
                direction(2, town);
                redraw(town);
            }
            else {
                copyOver(town_z, town_z1);
                direction(2, town_z);
                redraw(town_z);
            }
            coin2.setVisibility(View.INVISIBLE);
        }
        else if (lvl == 3){
            if (char_pick()) {
                copyOver(cave, cave1);
                direction(2, cave);
                redraw(cave);
            }
            else {
                copyOver(cave_z, cave_z1);
                direction(2,cave_z);
                redraw(cave_z);
            }
            coin3.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onDown(MotionEvent e) { //no gesture
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {//no gesture

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {//no gesture
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {//no gesture
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {//no gesture

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velocityX, float velocityY) {//no gesture
        boolean result = false;
        float diffY = moveEvent.getY() - downEvent.getY();
        float diffX = moveEvent.getX() - downEvent.getX();

        //which was greater? movement across Y or X?
        if (Math.abs(diffX) > Math.abs(diffY)){
            //right or left swipe
            if (Math.abs(diffX) > SWIPE_THRESHOLD &&  Math.abs(velocityX) > SWIPE_VELEOCITY_THRESHOLD) {
                if (diffX > 0){
                    onSwipeRight();

                } else {
                    onSwipeLeft();
                }
                result = true;
            }
        }

        else {
            //up or down swipe
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELEOCITY_THRESHOLD){
                if (diffY > 0){
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
        }

        return result;
    }

    private void onSwipeTop() { //swipe output up calls methods according to lvl
        count_undo = 0;
        if (lvl == 1)
            move_up(house);
        else if (lvl == 2)
            move_up(town);
        else if (lvl == 3)
         move_up(cave);
    }

    private void onSwipeBottom() {//swipe output down calls methods according to lvl
        count_undo = 0;
        if (lvl == 1)
            move_down(house);
        else if (lvl == 2)
            move_down(town);
        else if (lvl == 3)
        move_down(cave);
    }

    private void onSwipeLeft() {//swipe output left calls methods according to lvl
        count_undo = 0;
        if (lvl == 1)
            move_left(house);
        else if (lvl == 2)
            move_left(town);
        else if (lvl == 3)
            move_left(cave);
    }

    private void onSwipeRight() {//swipe output right calls methods according to lvl
        count_undo = 0;
        if (lvl == 1)
            move_right(house);
        else if (lvl == 2)
            move_right(town);
        else if (lvl == 3)
        move_right(cave);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}