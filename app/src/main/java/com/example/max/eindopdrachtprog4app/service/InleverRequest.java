package com.example.max.eindopdrachtprog4app.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.max.eindopdrachtprog4app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Max on 19-6-2017.
 */

public class InleverRequest {

    private Context context;
    private String customer_id;
    private String return_date;
    private String inventory_id;

    public final String TAG = this.getClass().getSimpleName();

    public InleverRequest(Context context,String inventory_id) {
        this.context = context;
        this.inventory_id = inventory_id;
    }

    //GET REQUEST
    public void handleDeleteLening() {

        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String body = "{\"return_date\":\"" + return_date + "\",\"rental_date\":\"" + date  + "\"}";
        JSONObject jsonBody = null;
        try {
            jsonBody = new JSONObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Haal het token uit de prefs
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String token = sharedPref.getString(context.getString(R.string.saved_token), "dummy default token");
        final String customer_id = sharedPref.getString(context.getString(R.string.customer_id), "dummy default customer");
        if (token != null && !token.equals("dummy default token")) {

            Log.i(TAG, "Token gevonden, we gaan het request uitvoeren");
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.DELETE, Config.URL_LENING(customer_id,inventory_id), jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            Log.i(TAG, response.toString());
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

}
