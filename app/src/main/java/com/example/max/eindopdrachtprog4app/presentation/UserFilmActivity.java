package com.example.max.eindopdrachtprog4app.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.max.eindopdrachtprog4app.R;
import com.example.max.eindopdrachtprog4app.domain.Film;
import com.example.max.eindopdrachtprog4app.domain.FilmAdapter;
import com.example.max.eindopdrachtprog4app.service.UserFilmRequest;

import java.util.ArrayList;

public class UserFilmActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        UserFilmRequest.FilmListener{

private ListView listViewFilms;
private BaseAdapter filmAdapter;
private ArrayList<Film> films = new ArrayList<>();


public final String TAG = this.getClass().getSimpleName();

// The name for communicating Intents extras
public final static String FILM_DATA = "FILMS";

public static final int MY_REQUEST_CODE = 1234;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_user_film2);

        listViewFilms = (ListView) findViewById(R.id.Filmlistview);
        listViewFilms.setOnItemClickListener(this);
        filmAdapter = new FilmAdapter(this, getLayoutInflater(), films);
        listViewFilms.setAdapter(filmAdapter);


        //
        // We hebben een token. Je zou eerst nog kunnen valideren dat het token nog
        // geldig is; dat doen we nu niet.
        // Vul de lijst met Films
        //
        System.out.println("Token gevonden - Films ophalen!");
        getFilms();
        filmAdapter.notifyDataSetChanged();
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
        UserFilmRequest request = new UserFilmRequest(getApplicationContext(), this);
        request.handleGetAllFilms();
        }

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "Position " + position + " is geselecteerd");

        Film film = films.get(position);
        Intent intent = new Intent(getApplicationContext(), UserFilm_Detailed.class);
        intent.putExtra(FILM_DATA, film);
        startActivity(intent);
        }
        }
