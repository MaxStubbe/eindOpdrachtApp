package com.example.max.eindopdrachtprog4app.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.max.eindopdrachtprog4app.R;

public class UserFilm_Detailed extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_film__detailed);

        text = (TextView) findViewById(R.id.textDetailFilmTitle);
    }
}
