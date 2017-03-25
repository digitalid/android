package com.thinkitlimited.digitalid;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkitlimited.digitalid.Util.Preferences;

import static android.R.transition.explode;

/**
 * Created by JOSHUA BUJO on 2/6/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    Button btnViewCards,btnCreateCard;
    Preferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        mPreferences= new Preferences(this);

        Explode explode = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            explode = new Explode();
            explode.setDuration(500);

            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
        }

        final TextView etPhoneNumber = (TextView) findViewById(R.id.et_Phonenumber);
        btnViewCards = (Button) findViewById(R.id.btnViewCards);
        btnCreateCard = (Button) findViewById(R.id.btnCreateCard);

        Intent intent = getIntent();
        String phonenumber = intent.getStringExtra("phone_number");

        etPhoneNumber.setText(phonenumber + "");
        btnViewCards.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){

                Intent i = new Intent(getApplicationContext(), AllCardsActivity.class);
                startActivity(i);

            }
        });

        btnCreateCard.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View view){
                Intent i = new Intent(getApplicationContext(), NewCardsActivity.class);
                startActivity(i);
            }
        });
    }
}
