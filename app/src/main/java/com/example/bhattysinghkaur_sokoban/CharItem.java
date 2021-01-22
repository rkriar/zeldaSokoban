package com.example.bhattysinghkaur_sokoban;

public class CharItem {
    //member  variables
    private String mCharName;
    private int mCharImage;

    //Constructor to pass values when objects are created of this class
    public CharItem(String charName, int charImage) {
        mCharName = charName;
        mCharImage = charImage;
    }

        //Getter Method to return name
        public String getCharName() {return mCharName;}

        //Getter method to return image
        public int getCharImage(){return mCharImage;}
    }