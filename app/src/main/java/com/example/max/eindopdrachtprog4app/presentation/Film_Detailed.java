package com.example.max.eindopdrachtprog4app.presentation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.max.eindopdrachtprog4app.R;
import com.example.max.eindopdrachtprog4app.domain.Film;
import com.example.max.eindopdrachtprog4app.service.InleverRequest;
import com.example.max.eindopdrachtprog4app.service.LeenRequest;

import static com.example.max.eindopdrachtprog4app.presentation.MainActivity.FILM_DATA;


public class Film_Detailed extends AppCompatActivity {

    private TextView TitleTextview;
    private TextView BeschrijvingTextview;
    private EditText ReturndateEditText;
    private Button leenBtn;


    private String return_date;
    private String inventory_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film__detailed);

        TitleTextview = (TextView) findViewById(R.id.textDetailFilmTitle);
        BeschrijvingTextview =(TextView) findViewById(R.id.textDetailFilmDescription);
        ReturndateEditText = (EditText) findViewById(R.id.edittextReturndate);
        leenBtn = (Button) findViewById(R.id.leenButton);

        Bundle extras = getIntent().getExtras();

        Film film = (Film) extras.getSerializable(FILM_DATA);
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.inventory_id = film.getInventory_id();

        TitleTextview.setText(film.getTitle());
        BeschrijvingTextview.setText(film.getDescription());

        if(film.isActive()) {
            leenBtn.setText("Inleveren");
            leenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        deleteLening();
                    }
            });
        }else{
            leenBtn.setText("Lenen");
            leenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ReturndateEditText.getText().toString() != "") {
                        return_date = ReturndateEditText.getText().toString();
                        postLening();
                    }else{

                    }
                }
            });
        }
    }

    private void postLening(){

        LeenRequest request = new LeenRequest(getApplicationContext(),return_date,inventory_id);
        request.handlePostLening();
    }

    private void deleteLening(){
        InleverRequest request = new InleverRequest(getApplicationContext(),inventory_id);
        request.handleDeleteLening();
    }
}
