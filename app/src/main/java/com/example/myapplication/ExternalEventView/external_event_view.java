package com.example.myapplication.ExternalEventView;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class external_event_view extends AppCompatActivity {

    TextView externalViewNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_event_view);



        externalViewNumber = findViewById(R.id.externalViewNumber);

        Uri uri = getIntent().getData();
        if(uri != null){
            String path = uri.toString();
//            Toast.makeText(external_event_view.this, "Path = " + path, Toast.LENGTH_SHORT).show();
        }

    }
}