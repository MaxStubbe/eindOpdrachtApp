package com.example.max.eindopdrachtprog4app.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.max.eindopdrachtprog4app.R;
import com.example.max.eindopdrachtprog4app.domain.Film;
import com.example.max.eindopdrachtprog4app.domain.FilmAdapter;
import com.example.max.eindopdrachtprog4app.service.FilmRequest;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        FilmRequest.FilmListener{

    private ListView listViewFilms;
    private BaseAdapter filmAdapter;
    private ArrayList<Film> films = new ArrayList<>();
    private Button btnLogout;

    private Button btnVolgende;
    private Button btnVorige;

    private FilmRequest request;

    private Button UserFilmbtn;



    public final String TAG = this.getClass().getSimpleName();

    // The name for communicating Intents extras
    public final static String FILM_DATA = "FILMS";

    public static final int MY_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(tokenAvailable()){
            setContentView(R.layout.activity_main);

            listViewFilms = (ListView) findViewById(R.id.Filmlistview);
            listViewFilms.setOnItemClickListener(this);
            filmAdapter = new FilmAdapter(this, getLayoutInflater(), films);
            listViewFilms.setAdapter(filmAdapter);


            request = new FilmRequest(getApplicationContext(), this);

            UserFilmbtn = (Button) findViewById(R.id.UserFilmButton);
            UserFilmbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Navigate to login screen
                    Intent login = new Intent(getApplicationContext(), UserFilmActivity.class);
                    startActivity(login);
                }
            });

            btnLogout = (Button) findViewById(R.id.btnLogout);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.remove(getString(R.string.saved_token));
                    editor.commit();

                    // Empty the homescreen
                    films.clear();
                    filmAdapter.notifyDataSetChanged();

                    // Navigate to login screen
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login);

                }
            });

            btnVolgende = (Button) findViewById(R.id.btnvolgende);
            btnVolgende.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    request.volgende();
                    getFilms();
                }
            });

            btnVorige = (Button) findViewById(R.id.btnvolgende);
            btnVorige.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    request.volgende();
                    getFilms();
                }
            });



            btnVorige = (Button) findViewById(R.id.btnvorige);
            //
            // We hebben een token. Je zou eerst nog kunnen valideren dat het token nog
            // geldig is; dat doen we nu niet.
            // Vul de lijst met Films
            //
            System.out.println("Token gevonden - Films ophalen!");
            getFilms();
            filmAdapter.notifyDataSetChanged();
        } else {
            //
            // Blijkbaar was er geen token - eerst inloggen dus
            //
            System.out.println("Geen token gevonden - inloggen dus");
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(login);
            // Sluit de huidige activity. Dat voorkomt dat de gebuiker via de
            // back-button zonder inloggen terugkeert naar het homescreen.
            finish();
        }



    }

    private boolean tokenAvailable() {
        boolean result = false;

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.saved_token), "dummy default token");
        if (token != null && !token.equals("dummy default token")) {
            result = true;
        }
        return result;
    }

    @Override
    public void onFilmsAvailable(ArrayList<Film> filmsarray) {
        Log.i(TAG, "We hebben " + filmsarray.size() + " items in de lijst");

        films.clear();
        for(int i = 0; i < filmsarray.size(); i++) {
            films.add(filmsarray.get(i));
        }
        filmAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFilmAvailable(Film film) {
        films.add(film);
        filmAdapter.notifyDataSetChanged();

    }

    public void onFilmsError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Start the activity to GET all Films from the server.
     */
    private void getFilms(){
        request.handleGetAllFilms();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position " + position + " is geselecteerd");

        Film film = films.get(position);
        Intent intent = new Intent(getApplicationContext(), Film_Detailed.class);
        intent.putExtra(FILM_DATA, film);
        startActivity(intent);
    }
}
