package com.example.myapplication.ExternalEventView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.Room.AppDatabase;
import com.example.myapplication.Room.Attendee;
import com.example.myapplication.Room.Event;
import com.example.myapplication.Universal.loadingFragment;
import com.example.myapplication.ViewEventInfomation.event_details_eventinfo;
import com.example.myapplication.externalAttendeesAwaitingResponse;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class external_event_view extends AppCompatActivity {


    int eventId;
    ExecutorService executor;
    ExecutorService executor2;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_event_view);


        Uri uri = getIntent().getData();
        if(uri != null) {
            String path = uri.toString();
            eventId = Integer.valueOf(uri.getQueryParameter("event"));
        }else {
            try {
                 eventId = getIntent().getExtras().getInt("eventId");
                System.out.println("Pause");
            }catch (Exception e){

            }
            }
//          Toast.makeText(external_event_view.this, "Path = " + path, Toast.LENGTH_SHORT).show();
        System.out.println("Event id " + eventId);

        //Refactor if time
        this.db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "eventsDatabase"
        ).build();


            getEvent();
        getEventAttendees();
//            Intent currentIntent = this.getIntent();
//            currentIntent.putExtra("eventId", eventId);
//            currentIntent.putExtra("shareable", false);
//
//
//            Fragment fragment = new event_details_eventinfo();
//            getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainer, fragment).commit();




        Fragment eventInfoFragment = new loadingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesEventInfo, eventInfoFragment).commit();

        Fragment yesListFragment = new loadingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesRespondedYes, yesListFragment).commit();

        Fragment noListFragment = new loadingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesRespondedNo, noListFragment).commit();
//
        Fragment maybeListFragment = new loadingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesRespondedMaybe, maybeListFragment).commit();

        Fragment noResponseListFragment = new loadingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesRespondedNoResponse, noResponseListFragment).commit();


            //

//        }else{
//            //display an error
//        }


    }

    private void getEvent() {
        this.executor = Executors.newFixedThreadPool(4);

        System.out.println(eventId);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest getEventsRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://archiewilkins.pythonanywhere.com/api/events/" + eventId, null,
                response -> {
                    System.out.println(response.toString());
                    System.out.println("Step 1");

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

                                        db.eventDao().insertEvent(event);
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


                            getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesEventInfo, fragment).commit();

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
        this.executor2 = Executors.newFixedThreadPool(4);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest getAttendeesRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://archiewilkins.pythonanywhere.com/api/attendeesByEvent/" + eventId, null,
                response -> {
                    System.out.println(response.toString());
                    //need to get response size so I can list through and create event objects
                    System.out.println(response);
                    System.out.println(response.toString());

                    try{
                    ArrayList<Attendee> attendeesList = new ArrayList<>();
                    for (int index = 0; index < response.length(); index++) {
                        String attendeeId = response.getJSONObject(String.valueOf(index)).getString("attendeeId");
                        String attendeeName = response.getJSONObject(String.valueOf(index)).getString("attendeeName");
                        String attendeeEventId = response.getJSONObject(String.valueOf(index)).getString("eventId");
                        String attendeeResponse = response.getJSONObject(String.valueOf(index)).getString("response");

                        Attendee attendee = new Attendee(Integer.valueOf(attendeeId), Integer.valueOf(attendeeEventId), attendeeName, attendeeResponse);
                        attendeesList.add(attendee);
                    }

                    System.out.println(attendeesList);
                        executor2.execute(new Runnable() {
                            @Override
                            public void run() {
                                for(Attendee attendee : attendeesList) {
                                        db.attendeeDAO().insertAttendee(attendee);
                                }
                            };
                        });

                    awaitTerminationAfterShutdown(executor2);


//                    Intent currentIntent2 = this.getIntent();
//                    currentIntent2.putExtra("eventId", eventId);
//

//                Yes List
                  Fragment yesListFragment = new externalAttendeesList();
                  Bundle yesBundle = new Bundle();
                  yesBundle.putString("response", "Yes");
                  yesListFragment.setArguments(yesBundle);
                  getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesRespondedYes, yesListFragment).commit();


//                No List
                Fragment noListFragment = new externalAttendeesList();
                Bundle declinedBundle = new Bundle();
                declinedBundle.putString("response", "No");
                noListFragment.setArguments(declinedBundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesRespondedNo, noListFragment).commit();

//              Maybe List
              Fragment maybeListFragment = new externalAttendeesList();
              Bundle maybeBundle = new Bundle();
              maybeBundle.putString("response", "Maybe");
              maybeListFragment.setArguments(maybeBundle);
              getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesRespondedMaybe, maybeListFragment).commit();

//              No Response List
               Fragment noResponseFragment = new externalAttendeesAwaitingResponse();
               getSupportFragmentManager().beginTransaction().replace(R.id.externalViewFragmentContainerAttendeesRespondedNoResponse, noResponseFragment).commit();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                },
                error -> {
                    System.out.println("Error");
                    Toast.makeText(getApplication().getBaseContext(), "Error Server Not Found", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(getAttendeesRequest);
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