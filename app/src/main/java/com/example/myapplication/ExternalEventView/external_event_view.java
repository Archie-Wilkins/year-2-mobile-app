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

            //Create room table for creating external event
            //With event Id get users who have responded
            //With event Id get users who haven't responded
            //With event Id get event information
            //3 API calls

            //To Do
            //1. Create Python API calls for getting users who have accepted
            //2. Create Python API calls for getting users who are yet to respond
            //3. Create API call for getting event information
            //4. Create API call for getting attendees who have responded
            //5. Create API call for getting attendees who have yet to respond

            //7. Create adapter for viewing users who have responded
            //8. Create adapter for viewing users who need to respond



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