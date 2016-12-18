package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        //Find the views that shows the number category
        TextView numbers = (TextView) findViewById(R.id.numbers);

        //Set on clickListener on that view
        numbers.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

            //Find the views that shows the family category
            TextView family = (TextView) findViewById(R.id.family);

            //Set on clickListener on that view
            family.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                    startActivity(familyIntent);
                }
            });

        //Find the views that shows the colors category
        TextView colors = (TextView) findViewById(R.id.colors);

        //Set on clickListener on that view
        colors.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent colorsIntent = new Intent(MainActivity.this, ColorsActivity.class);
                startActivity(colorsIntent);
            }
        });

        //Find the views that shows the phrases category
        TextView phrases = (TextView) findViewById(R.id.phrases);

        //Set on clickListener on that view
        phrases.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });
    }


}
