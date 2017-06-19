package com.example.max.eindopdrachtprog4app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.max.eindopdrachtprog4app.R;
import com.example.max.eindopdrachtprog4app.domain.Film;
import com.example.max.eindopdrachtprog4app.domain.FilmMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 15-6-2017.
 */

public class FilmRequest {

    private Context context;
    public final String TAG = this.getClass().getSimpleName();

    private FilmRequest.FilmListener listener;
    private int offset;


    public FilmRequest(Context context, FilmRequest.FilmListener listener) {
        this.context = context;
        this.listener = listener;
        this.offset = 0;
    }

    public void volgende(){
        offset = offset + 10;
    }

    public void vorige(){
        if (offset != 0){
            offset = offset - 10;
        }
    }

    //GET REQUEST
    public void handleGetAllFilms() {

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        if (token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonArrayRequest jsObjRequest = new JsonArrayRequest
                    (Request.Method.GET, Config.URL_ALL_FILMS, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            // Succesvol response

                            Log.i(TAG, response.toString());
                            ArrayList<Film> result = FilmMapper.mapFilmList(response);
                            listener.onFilmsAvailable(result);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleErrorResponse(error);
                            Log.e(TAG, error.toString());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(context).addToRequestQueue(jsObjRequest);
        }
    }

    public void handleErrorResponse(VolleyError error) {
        Log.e(TAG, "handleErrorResponse");

        if(error instanceof com.android.volley.AuthFailureError) {

            String json = null;
            NetworkResponse response = error.networkResponse;
            if (response != null && response.data != null) {
                json = new String(response.data);
                json = trimMessage(json, "error");
                if (json != null) {
                    json = "Error " + response.statusCode + ": " + json;
                }
            } else {
                Log.e(TAG, "handleErrorResponse: kon geen networkResponse vinden.");
            }
        } else if(error instanceof com.android.volley.NoConnectionError) {
            Log.e(TAG, "handleErrorResponse: server was niet bereikbaar");
        } else {
            Log.e(TAG, "handleErrorResponse: error = " + error);
        }
    }

    public String trimMessage(String json, String key){
        Log.i(TAG, "trimMessage: json = " + json);
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }

    //
    // Callback interface - implemented by the calling class (MainActivity in our case).
    //
    public interface FilmListener {
        // Callback function to return a fresh list of Films
        void onFilmsAvailable(ArrayList<Film> films);

        // Callback function to handle a single added Film.
        void onFilmAvailable(Film film);

        // Callback to handle serverside API errors
        void onFilmsError(String message);
    }
}
