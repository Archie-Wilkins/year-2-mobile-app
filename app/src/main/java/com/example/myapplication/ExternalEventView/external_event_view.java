package com.example.myapplication.ExternalEventView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Dashboard.eventGridFragment;
import com.example.myapplication.R;
import com.example.myapplication.ViewEventInfomation.event_details_eventinfo;

public class external_event_view extends AppCompatActivity {


    int eventId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_event_view);


        Uri uri = getIntent().getData();
        if(uri != null){
            String path = uri.toString();
            eventId = Integer.valueOf(uri.getQueryParameter("event"));
//          Toast.makeText(external_event_view.this, "Path = " + path, Toast.LENGTH_SHORT).show();
            System.out.println(eventId);

            Intent currentIntent = this.getIntent();
            currentIntent.putExtra("eventId", eventId);
            currentIntent.putExtra("shareable", false);


            Fragment fragment = new event_details_eventinfo();
            getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainer, fragment).commit();


        }else{
            //display an error
        }





    }
}