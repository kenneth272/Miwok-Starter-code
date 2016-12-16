package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<Word>{

    private int mColorResourceId;

    public WordAdapter (Activity context, ArrayList<Word> words, int colorResourceId) {
        //Here we initilaise the arrayadapter internal storage for the context and the list.
        // the 2nd argument is used when the array adapter is populating a textview
        // coz this is a custom adapter for 2 textviews and imageview
        // going to use this 2nd argument, so it can be any value, so we use 0
        super (context, 0, words);
        this.mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //Get the(word) object located at this position in the list
        Word currentWord = getItem(position);

        //Find the TextView in the list_item.xml layout with the ID versione_name
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
            miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
            defaultTextView.setText(currentWord.getDefaultTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

            if (currentWord.hasImage()) {
                imageView.setImageResource(currentWord.getImageResourceId());
            }
            else {
                imageView.setVisibility(View.GONE);
            }

        //Set theme colour for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        //Find the color the resource id maps to
            int color = ContextCompat.getColor(getContext(), mColorResourceId);
        //Set the background color of the text ContainerView
            textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
