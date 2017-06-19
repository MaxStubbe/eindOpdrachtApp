package com.example.max.eindopdrachtprog4app.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.max.eindopdrachtprog4app.R;
import com.example.max.eindopdrachtprog4app.domain.Film;

import static com.example.max.eindopdrachtprog4app.presentation.MainActivity.FILM_DATA;


public class Film_Detailed extends AppCompatActivity {

    private TextView TitleTextview;
    private TextView BeschrijvingTextview;
    private Button leenBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film__detailed);

        TitleTextview = (TextView) findViewById(R.id.textDetailFilmTitle);
        BeschrijvingTextview =(TextView) findViewById(R.id.textDetailFilmDescription);
        leenBtn = (Button) findViewById(R.id.leenButton);

        Bundle extras = getIntent().getExtras();

        Film film = (Film) extras.getSerializable(FILM_DATA);

        TitleTextview.setText(film.getTitle());
        BeschrijvingTextview.setText(film.getDescription());

        if(film.isActive()) {
            leenBtn.setText("Inleveren");
        }else{
            leenBtn.setText("Lenen");
        }


    }
}
