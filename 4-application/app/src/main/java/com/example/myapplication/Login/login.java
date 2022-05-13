package com.example.myapplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Dashboard.dashboard;
import com.example.myapplication.R;
import com.example.myapplication.SharedPreferences.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import android.content.SharedPreferences;



public class login extends AppCompatActivity {

    private Button submitButton;
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String sharedPreferencesFile = getString(R.string.sharedPreferencesFile);
        SharedPreferences sp = getSharedPreferences(sharedPreferencesFile,Context.MODE_PRIVATE);

        boolean userLoggedIn = sp.getBoolean("user_logged_in",false);
        if ( userLoggedIn == true) {
            Intent dashboard = new Intent(getApplicationContext(), com.example.myapplication.Dashboard.dashboard.class);
            startActivity(dashboard);
            //Prevents user from going back
            finish();
        }


        this.submitButton = findViewById(R.id.loginSubmit);
        this.submitButton.setOnClickListener(this::onClick);

        this.usernameInput = findViewById(R.id.usernameInput);
        this.passwordInput = findViewById(R.id.passwordInput);
    }

    public void onClick(View view) {
        onRequestLogin(view);
     }

    public void onRequestLogin(View view) {

        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        HashMap<String, String> JsonMap = new HashMap<>();
        JsonMap.put("username",username);
        JsonMap.put("password",password);
        JSONObject jsonObject = new JSONObject(JsonMap);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.POST,
                "https://archiewilkins.pythonanywhere.com/api/userValid", jsonObject,
                response -> {
                    try {
                        String responseStatus = response.getString("responseStatus");
                        if(responseStatus.equals("success")){
                            String responseUsername = response.getString("username");
                            String responsePassword = response.getString("password");
                            String responseName = response.getString("usersRealName");
                            int responseUserId = response.getInt("userID");
                            Toast.makeText(getApplication().getBaseContext(), "Welcome " + responseName , Toast.LENGTH_SHORT).show();

                            //Setting shared preferences
                            String sharedPreferencesFile = getString(R.string.sharedPreferencesFile);
                            SharedPreferences sp = getSharedPreferences(sharedPreferencesFile,Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean(UserDetails.KEY_USER_LOGGED_IN, true);
                            editor.putInt(UserDetails.KEY_USER_ID, responseUserId);
                            editor.putString(UserDetails.KEY_USERNAME, responseUsername);
                            editor.putString(UserDetails.KEY_USER_REAL_NAME, responseUsername);
                            editor.apply();

                            Intent dashboard = new Intent(getApplicationContext(), dashboard.class);
                            startActivity(dashboard);
                            //Prevents user from going back
                            finish();


                        }else{
                            Toast.makeText(getApplication().getBaseContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplication().getBaseContext(), "Server Error", Toast.LENGTH_LONG).show();
                    }

                },
                error -> {
                    Toast.makeText(getApplication().getBaseContext(), "Error Server Not Found", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(getRequest);
    }

}



