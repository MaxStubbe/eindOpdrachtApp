package com.example.max.eindopdrachtprog4app.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.max.eindopdrachtprog4app.R;

import org.w3c.dom.Text;

public class UserFilm_Detailed extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_film__detailed);

        text = (TextView) findViewById(R.id.textDetailFilmTitle);
    }
}
