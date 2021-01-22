package com.example.bhattysinghkaur_sokoban;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

public class CharacterSel extends AppCompatActivity {
    private ArrayList<CharItem> mCharList;
    private CharAdapter mAdapter;
    private static String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sel);

        initList();

        //Finds Spinner
        Spinner spinnerChar = findViewById(R.id.char_sel);

        mAdapter = new CharAdapter(this, mCharList);
        spinnerChar.setAdapter(mAdapter);

        spinnerChar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CharItem clickedItem = (CharItem) parent.getItemAtPosition(position); //stores position of item in arraylist
                String clickedCharName = clickedItem.getCharName();//gets the name of the item at position from clickedItem
                user = clickedCharName; //stores character name in a variable called user
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void initList() {
        mCharList = new ArrayList<>(); //creates new arraylist and fills it with items
        //Adds characters to Spinner ArrayList, defined to be filled with a charname
        mCharList.add(new CharItem("Link", R.drawable.link_charsel));
        mCharList.add(new CharItem("Zelda", R.drawable.zelda_charsel));
    }

    public void to_GS(View view) {
        Intent i = new Intent(this, Game.class);
        //Creates a bundle to hold String of user character
        Bundle bundle = new Bundle();
        bundle.putString("userkey", user);
        i.putExtras(bundle); //puts the information inside the intent
        startActivity(i);

    }
}