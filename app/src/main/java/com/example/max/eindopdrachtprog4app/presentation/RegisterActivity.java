package com.example.max.eindopdrachtprog4app.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.max.eindopdrachtprog4app.R;
import com.example.max.eindopdrachtprog4app.service.Config;
import com.example.max.eindopdrachtprog4app.service.VolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;

    private String mEmail;
    private String mPassword;
    private String mFirstName;
    private String mLastName;

    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = (EditText) findViewById(R.id.edittextEmail);
        editTextPassword = (EditText) findViewById(R.id.edittextregisterPassword);
        editTextFirstName = (EditText) findViewById(R.id.edittextFirstname);
        editTextLastName =  (EditText) findViewById(R.id.edittextLastname);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = editTextEmail.getText().toString();
                mPassword = editTextPassword.getText().toString();
                mFirstName = editTextFirstName.getText().toString();
                mLastName = editTextLastName.getText().toString();

                handleRegister(mEmail, mPassword,mFirstName,mLastName);
            }
        });
    }

    private void handleRegister(String mEmail, String mPassword, String mFirstName, String mLastName) {
        String body = "{\"email\":\"" + mEmail + "\",\"password\":\"" + mPassword + "\",\"first_name\":\"" + mFirstName + "\",\"last_name\":\"" + mLastName + "\"}";
        Log.i(TAG, "handleRegister - body = " + body);

        try {
            JSONObject jsonBody = new JSONObject(body);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.POST, Config.URL_REGISTER, jsonBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Succesvol response
                            displayMessage("Succesvol geregistreerd!");

                                // Start the login activity, and close the register activity
                                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(login);
                                // Close the current activity
                                finish();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            handleErrorResponse(error);
                        }
                    });

            jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1500, // SOCKET_TIMEOUT_MS,
                    2, // DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Access the RequestQueue through your singleton class.
            VolleyRequestQueue.getInstance(this).addToRequestQueue(jsObjRequest);
        } catch (JSONException e) {
        }
        return;
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
                    displayMessage(json);
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

    public void displayMessage(String toastString){
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }
}
