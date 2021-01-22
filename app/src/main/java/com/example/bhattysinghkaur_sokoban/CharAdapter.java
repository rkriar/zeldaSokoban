package com.example.bhattysinghkaur_sokoban;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CharAdapter extends ArrayAdapter<CharItem> {

    public CharAdapter(Context context, ArrayList<CharItem> charlist) {//passes on arraylist of character items
        super(context, 0, charlist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {//if the convertView does not exist
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.char_sel_row, parent, false);
        }

        //Finds ImageView and TextView of Spinner
        ImageView imageViewChar = convertView.findViewById(R.id.image_view_char);
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        CharItem currentItem = getItem(position);//gets current char in position and adds it to arraylist

        if (currentItem != null) {
            //sets the Image and Text according to current Item
            imageViewChar.setImageResource(currentItem.getCharImage());
            textViewName.setText(currentItem.getCharName());
        }

        return convertView;
    }

}