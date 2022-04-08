package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class event_details_attendeesinfo extends Fragment {


    ExecutorService executor;
    private Button addAttendeeButton;
    private EditText attendeeNameInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_details_attendeesinfo, container, false);

        addAttendeeButton = view.findViewById(R.id.submitNewAttendeeButton);
        addAttendeeButton.setOnClickListener(this::onClick);

        attendeeNameInput = view.findViewById(R.id.editTextTextPersonName);


        System.out.println("Creating attendees object");
        int eventId = getActivity().getIntent().getExtras().getInt("eventId");

        //Run loading screen until attendees load
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.attendeesfragmentContainerView, new loadingFragment());
        fragmentTransaction.commit();


        //Running loading view
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.fragmentContainerView, new loadingFragment());
//        fragmentTransaction.commit();

        getAttendeesList(eventId);

        //Bug here that view is being returned before the call is made
        return view;
    }

    private void getAttendeesList(int eventId) {

        ArrayList<Event> eventList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest getEventRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://archiewilkins.pythonanywhere.com/api/eventsWithAttendees/" + eventId, null,
                response -> {
                    System.out.println(response.toString());
                    //need to get response size so I can list through and create event objects


                    try {

                        //JSONObject responseObject = response.getJSONObject(String.valueOf(1));
                        String eventIdResponse = response.getString("eventID");
                        String hostIdResponse = response.getString("hostID");
                        String eventAddressResponse = response.getString("eventAddress");
                        String eventTitleResponse = response.getString("eventTitle");
                        String eventDescriptionResponse = response.getString("eventDescription");
                        String eventTypeResponse = response.getString("eventType");
                        String eventLocationNameResponse = response.getString("eventLocationName");
                        String eventStartTimeResponse = response.getString("eventStartTime");
                        String eventEndTimeResponse = response.getString("eventEndTime");
                        String eventDateResponse = response.getString("eventDate");
                        JSONObject eventAttendeesResponse = response.getJSONObject("eventAttendees");
                        System.out.println(eventDescriptionResponse);
                        System.out.println(eventAttendeesResponse.toString());

                        ArrayList<Attendee> attendeesList = new ArrayList<>();
                        int numOfAttendees = eventAttendeesResponse.length();
                        for (int index = 0; index < numOfAttendees; index++) {
                            String attendeeId = eventAttendeesResponse.getJSONObject(String.valueOf(index)).getString("attendeeId");
                            String attendeeName = eventAttendeesResponse.getJSONObject(String.valueOf(index)).getString("attendeeName");
                            String attendeeEventId = eventAttendeesResponse.getJSONObject(String.valueOf(index)).getString("eventId");
                            String attendeeResponse = eventAttendeesResponse.getJSONObject(String.valueOf(index)).getString("response");

                            Attendee attendee = new Attendee(Integer.valueOf(attendeeId), Integer.valueOf(attendeeEventId), attendeeName, attendeeResponse);
                            attendeesList.add(attendee);
                        }

                        System.out.println(attendeesList);

                        //Need to set up list view adapter
                        //


                        Context context = getContext();
                        AppDatabase db = AppDatabase.getDatabase(context);






                        this.executor = Executors.newFixedThreadPool(4);

                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                db.attendeeDAO().resetTable();

                                for(Attendee attendee: attendeesList){
                                    db.attendeeDAO().insertAttendee(attendee);
                                }
                            };
                        });

                        awaitTerminationAfterShutdown(executor);

                        Bundle bundle = new Bundle();
                        bundle.putString("eventId", String.valueOf(eventId));
                        Fragment fragment = new attendeesListFragment();
                        fragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.attendeesfragmentContainerView, fragment).commit();



                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                },
                error -> {
                    System.out.println("Error");
//                        Toast.makeText(getApplication().getBaseContext(), "Error Server Not Found", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(getEventRequest);

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

    public void onClick(View view){
        int id = view.getId();

        System.out.println("On click");
        Intent intent = null;
        switch(id){
            case R.id.submitNewAttendeeButton:
                //Api call
                System.out.println("Goooooooooo");
                String name = attendeeNameInput.getText().toString();
                int eventId = getActivity().getIntent().getExtras().getInt("eventId");
                addAttendee(name, eventId);

        }
    }


    private void addAttendee(String attendeeName, int eventId) {

        HashMap<String, String> JsonMap = new HashMap<>();
        JsonMap.put("attendeeName",attendeeName);
        JsonMap.put("eventID",String.valueOf(eventId));
        JsonMap.put("response","Yet to respond");
        JSONObject jsonObject = new JSONObject(JsonMap);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest addAttendeeRequest = new JsonObjectRequest(
                Request.Method.POST,
                "https://archiewilkins.pythonanywhere.com/api/addEventAttendees" , jsonObject,
                response -> {
                    System.out.println(response.toString());
                    //need to get response size so I can list through and create event objects
                    String responseStatus = null;
                    try {
                        responseStatus = response.getString("response");
                        if(responseStatus.equals("success")) {

                            Toast.makeText(getActivity().getApplication().getBaseContext(), "Attendee Added", Toast.LENGTH_SHORT).show();


                            //TO DO
                            //add event attendee to list via adapter and notify data set changed


                        }

                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                        //give error toast
                        Toast.makeText(getActivity().getApplication().getBaseContext(), "Whoops something went wrong", Toast.LENGTH_SHORT).show();

                    }
                },
                error -> {
                    System.out.println("Error");
                        Toast.makeText(getActivity().getApplication().getBaseContext(), "Error Can't reach server", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(addAttendeeRequest);

    }
}
