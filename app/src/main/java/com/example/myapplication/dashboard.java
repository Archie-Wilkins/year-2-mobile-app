package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class dashboard extends AppCompatActivity {

    private Button newEventButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        newEventButton = findViewById(R.id.newEventButton);
        newEventButton.setOnClickListener(this::onClick);
        this.sharedPreferences = this.getPreferences(MODE_PRIVATE);



        Integer name = this.sharedPreferences.getInt(UserDetails.KEY_USER_ID, UserDetails.DEFAULT_USER_ID);
        Toast.makeText(getApplication().getBaseContext(), "Hi " + name , Toast.LENGTH_SHORT).show();

    }

    public void onClick(View view){
        int id = view.getId();

        Intent intent = null;
        switch(id){
            case R.id.newEventButton:
                Intent newEventForm = new Intent(getApplicationContext(), newEventForm.class);
                startActivity(newEventForm);

        }
    }
}