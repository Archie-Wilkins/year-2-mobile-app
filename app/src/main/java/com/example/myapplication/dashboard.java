package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class dashboard extends AppCompatActivity {

    ExecutorService executor;

    private AppDatabase db;

    private Button newEventButton;

    public static final String DATABASE_NAME = "eventsDatabase";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        newEventButton = findViewById(R.id.newEventButton);
        newEventButton.setOnClickListener(this::onClick);

        String sharedPreferencesFile = getString(R.string.sharedPreferencesFile);
        SharedPreferences sp = getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE);

        int userId = sp.getInt(UserDetails.KEY_USER_ID, UserDetails.DEFAULT_USER_ID);


        //Need to do get request for all users events
        getEvents(userId);


        //Adding fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerView, new loadingFragment());
        fragmentTransaction.commit();

    }



    private void getEvents(int userId){
        this.executor = Executors.newFixedThreadPool(4);

        this.db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "eventsDatabase"
        ).build();



        ArrayList<Event> eventList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest getEventsRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://archiewilkins.pythonanywhere.com/api/usersEvents/" + userId, null,
                response -> {
                    System.out.println(response.toString());
                    //need to get response size so I can list through and create event objects
                    int responseLength = response.length();


                    for(int index = 0; index < responseLength; index++){
                        try {
                            String eventId = response.getJSONObject(String.valueOf(index)).getString("eventID");
                        String hostId = response.getJSONObject(String.valueOf(index)).getString("hostID");
                        String eventAddress = response.getJSONObject(String.valueOf(index)).getString("eventAddress");
                        String eventTitle = response.getJSONObject(String.valueOf(index)).getString("eventTitle");
                        String eventDescription = response.getJSONObject(String.valueOf(index)).getString("eventDescription");
                        String eventType = response.getJSONObject(String.valueOf(index)).getString("eventType");
                        String eventLocationName = response.getJSONObject(String.valueOf(index)).getString("eventLocationName");
                        String eventStartTime = response.getJSONObject(String.valueOf(index)).getString("eventStartTime");
                        String eventEndTime = response.getJSONObject(String.valueOf(index)).getString("eventEndTime");
                        String eventDate = response.getJSONObject(String.valueOf(index)).getString("eventDate");
//                        //eventAttendees is left null

                        Event event = new Event(Integer.valueOf(eventId), Integer.valueOf(hostId), eventTitle, eventDescription, eventType, eventAddress, eventLocationName, eventStartTime, eventEndTime, eventDate);
                        eventList.add(event);


                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }

                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            db.clearAllTables();
                            for(Event event : eventList) {
                                db.eventDao().insertEvent(event);
                            }
                        };
                    });

                    awaitTerminationAfterShutdown(executor);


                    Fragment fragment = new eventGridFragment();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
////                    fragmentTransaction.add(R.id.fragmentContainerView, fragment);
//                    fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
//                    fragmentTransaction.commit();

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();


                },
                error -> {
                    System.out.println("Error");
                    Toast.makeText(getApplication().getBaseContext(), "Error Server Not Found", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(getEventsRequest);


    };


    public void onClick(View view){
        int id = view.getId();

        Intent intent = null;
        switch(id){
            case R.id.newEventButton:
                Intent newEventForm = new Intent(getApplicationContext(), newEventForm.class);
                startActivity(newEventForm);


        }
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

    public void startGridEventDetailsActivity(){

    }
}