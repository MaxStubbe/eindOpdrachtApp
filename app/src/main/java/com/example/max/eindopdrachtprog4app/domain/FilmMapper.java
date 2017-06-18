package com.example.max.eindopdrachtprog4app.domain;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Max on 15-6-2017.
 */

public class FilmMapper {

    public static final String FILM_RESULT = "result";
    public static final String FILM_TITLE = "title";
    public static final String FILM_DESCRIPTION = "description";
    /**
     * Map het JSON response op een arraylist en retourneer deze.
     */
    public static ArrayList<Film> mapFilmList(JSONArray response){

        ArrayList<Film> result = new ArrayList<>();


        try{

            for(int i = 0; i < response.length(); i++){
                JSONObject jsonProduct = response.getJSONObject(i);

                Film Film = new Film(
                        jsonProduct.getString(FILM_TITLE),
                        jsonProduct.getString(FILM_DESCRIPTION)
                );

                result.add(Film);
            }
        } catch( JSONException ex) {
            Log.e("FilmMapper", "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
        return result;
    }
}
