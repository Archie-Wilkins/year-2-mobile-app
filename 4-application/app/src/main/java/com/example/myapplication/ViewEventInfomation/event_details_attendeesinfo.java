package com.example.myapplication.ViewEventInfomation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.myapplication.Room.Event;
import com.example.myapplication.R;
import com.example.myapplication.Room.AppDatabase;
import com.example.myapplication.Room.Attendee;
import com.example.myapplication.Universal.loadingFragment;

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

        int eventId = getActivity().getIntent().getExtras().getInt("eventId");

        //Run loading screen until attendees load
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.attendeesfragmentContainerView, new loadingFragment());
        fragmentTransaction.commit();

        getAttendeesList(eventId);

        return view;
    }

    private void getAttendeesList(int eventId) {

        ArrayList<Event> eventList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest getEventRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://archiewilkins.pythonanywhere.com/api/eventsWithAttendees/" + eventId, null,
                response -> {
                    try {

                        JSONObject eventAttendeesResponse = response.getJSONObject("eventAttendees");
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
                        Toast.makeText(getActivity().getBaseContext(), "Error Server Not Found", Toast.LENGTH_LONG).show();
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

    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.submitNewAttendeeButton:
                String name = attendeeNameInput.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(getActivity().getApplication().getBaseContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    int eventId = getActivity().getIntent().getExtras().getInt("eventId");
                    //Emptying event input
                    attendeeNameInput.setText("");
                    addAttendee(name, eventId);
                }
        }
    }


    private void addAttendee(String attendeeName, int eventId) {

        HashMap<String, String> JsonMap = new HashMap<>();
        JsonMap.put("attendeeName",attendeeName);
        JsonMap.put("eventID",String.valueOf(eventId));
        JsonMap.put("response","Yet to respond");
        JSONObject jsonObject = new JSONObject(JsonMap);

        //AttendeeId set to 0, room will automatically set proper attendeeId - effectively just a null value

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest addAttendeeRequest = new JsonObjectRequest(
                Request.Method.POST,
                "https://archiewilkins.pythonanywhere.com/api/addEventAttendees" , jsonObject,
                response -> {
                    String responseStatus = null;
                    try {
                        responseStatus = response.getString("response");
                        if(responseStatus.equals("success")) {

                            Attendee attendee = new Attendee(0, eventId, attendeeName, "Yet to respond");

                            Context context = getContext();
                            AppDatabase db = AppDatabase.getDatabase(context);

                            ExecutorService executor = Executors.newFixedThreadPool(4);
                            executor.execute(new Runnable() {

                                @Override
                                public void run() {
                                    db.attendeeDAO().insertAttendee(attendee);
                                }

                            });

                        }

                        Toast.makeText(getActivity().getApplication().getBaseContext(), "Attendee Added", Toast.LENGTH_SHORT).show();

                        awaitTerminationAfterShutdown(executor);
                        //Rebuilds fragment (Sub optimal in terms of code performance)
                        Bundle bundle = new Bundle();
                        bundle.putString("eventId", String.valueOf(eventId));
                        Fragment fragment = new attendeesListFragment();
                        fragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.attendeesfragmentContainerView, fragment).commit();


                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                        Toast.makeText(getActivity().getApplication().getBaseContext(), "Whoops something went wrong", Toast.LENGTH_SHORT).show();

                    }
                },
                error -> {
                        Toast.makeText(getActivity().getApplication().getBaseContext(), "Error Can't reach server", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(addAttendeeRequest);
    }
}
