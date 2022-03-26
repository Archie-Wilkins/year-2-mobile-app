package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class dashboard extends AppCompatActivity {

    private Button newEventButton;
    private GridView eventGrid;

    String[] gridItems = {"Hello","Good Morning","Test","Please Work", "Yeah ok", "lol", "rofl"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        newEventButton = findViewById(R.id.newEventButton);
        newEventButton.setOnClickListener(this::onClick);

        eventGrid = findViewById(R.id.eventGrid);

        for(String item: gridItems){
            eventGrid.addView(item);
        }

        //String sharedPreferencesFile = getString(R.string.sharedPreferencesFile);
        //SharedPreferences sp = getSharedPreferences(sharedPreferencesFile,Context.MODE_PRIVATE);

        //String name = sp.getString(UserDetails.KEY_USER_REAL_NAME, UserDetails.DEFAULT_USER_REAL_NAME);


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