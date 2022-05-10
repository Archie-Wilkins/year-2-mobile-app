package com.example.myapplication.ExternalEventView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Dashboard.eventGridFragment;
import com.example.myapplication.R;
import com.example.myapplication.Room.AppDatabase;
import com.example.myapplication.Room.Event;
import com.example.myapplication.Universal.loadingFragment;
import com.example.myapplication.ViewEventInfomation.event_details_eventinfo;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class external_event_view extends AppCompatActivity {


    int eventId;
    ExecutorService executor;
    private AppDatabase db;


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
            //

          //Refactor if time
          this.db = Room.databaseBuilder(
                  getApplicationContext(),
                  AppDatabase.class,
                  "eventsDatabase"
          ).build();

     }



            getEvent();

//            Intent currentIntent = this.getIntent();
//            currentIntent.putExtra("eventId", eventId);
//            currentIntent.putExtra("shareable", false);
//
//
//            Fragment fragment = new event_details_eventinfo();
//            getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainer, fragment).commit();

            Fragment fragment = new loadingFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainer, fragment).commit();

        }else{
            //display an error
        }


    }

    private void getEvent() {
        this.executor = Executors.newFixedThreadPool(4);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest getEventsRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://archiewilkins.pythonanywhere.com/api/events/" + eventId, null,
                response -> {
                    System.out.println(response.toString());
                    //need to get response size so I can list through and create event objects
                    int responseLength = response.length();
                    System.out.println(response);
                    System.out.println(response.toString());


                        try {
                            String eventIdFromResponse = response.getString("eventID");
                            String hostId = response.getString("hostID");
                            String eventAddress = response.getString("eventAddress");
                            String eventTitle = response.getString("eventTitle");
                            String eventDescription = response.getString("eventDescription");
                            String eventType = response.getString("eventType");
                            String eventLocationName = response.getString("eventLocationName");
                            String eventStartTime = response.getString("eventStartTime");
                            String eventEndTime = response.getString("eventEndTime");
                            String eventDate = response.getString("eventDate");
//                        //eventAttendees is left null

                            Event event = new Event(Integer.valueOf(eventIdFromResponse), Integer.valueOf(hostId), eventTitle, eventDescription, eventType, eventAddress, eventLocationName, eventStartTime, eventEndTime, eventDate);

                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    if(db.eventDao().exists(eventId)){
                                        db.eventDao().update(event);
                                    }else{
                                        db.eventDao().insertEvent(event);
                                    }
                                };
                            });

                            awaitTerminationAfterShutdown(executor);


//                            Fragment fragment = new eventGridFragment();
//
//                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();

                            Intent currentIntent = this.getIntent();
                            currentIntent.putExtra("eventId", eventId);
                            currentIntent.putExtra("shareable", false);


                            Fragment fragment = new event_details_eventinfo();
                            getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainer, fragment).commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                },
                error -> {
                    System.out.println("Error");
                    Toast.makeText(getApplication().getBaseContext(), "Error Server Not Found", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(getEventsRequest);
    }

    private void getEventAttendees() {
        this.executor = Executors.newFixedThreadPool(4);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest getEventsRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://archiewilkins.pythonanywhere.com/api/events/" + eventId, null,
                response -> {
                    System.out.println(response.toString());
                    //need to get response size so I can list through and create event objects
                    int responseLength = response.length();
                    System.out.println(response);
                    System.out.println(response.toString());


                    try {
                        String eventIdFromResponse = response.getString("eventID");
                        String hostId = response.getString("hostID");
                        String eventAddress = response.getString("eventAddress");
                        String eventTitle = response.getString("eventTitle");
                        String eventDescription = response.getString("eventDescription");
                        String eventType = response.getString("eventType");
                        String eventLocationName = response.getString("eventLocationName");
                        String eventStartTime = response.getString("eventStartTime");
                        String eventEndTime = response.getString("eventEndTime");
                        String eventDate = response.getString("eventDate");
//                        //eventAttendees is left null

                        Event event = new Event(Integer.valueOf(eventIdFromResponse), Integer.valueOf(hostId), eventTitle, eventDescription, eventType, eventAddress, eventLocationName, eventStartTime, eventEndTime, eventDate);

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if(db.eventDao().exists(eventId)){
                                    db.eventDao().update(event);
                                }else{
                                    db.eventDao().insertEvent(event);
                                }
                            };
                        });

                        awaitTerminationAfterShutdown(executor);


//                            Fragment fragment = new eventGridFragment();
//
//                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();

                        Intent currentIntent = this.getIntent();
                        currentIntent.putExtra("eventId", eventId);
                        currentIntent.putExtra("shareable", false);


                        Fragment fragment = new event_details_eventinfo();
                        getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainer, fragment).commit();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                },
                error -> {
                    System.out.println("Error");
                    Toast.makeText(getApplication().getBaseContext(), "Error Server Not Found", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(getEventsRequest);
    }






    public  void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


}