package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.ExternalEventView.ExternalAttendeesListAdaptor;
import com.example.myapplication.Room.AppDatabase;
import com.example.myapplication.Room.Attendee;
import com.example.myapplication.ViewEventInfomation.AttendeesListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class externalAttendeesAwaitingResponse extends Fragment {

    ExternalAttendeesListAdaptor adapter;
    List<Attendee> attendees = new ArrayList<>();
    ArrayList<String> attendeeNames = new ArrayList<>();
    RecyclerView attendeesListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_external_attendees_awaiting_response, container, false);



        attendeesListView = view.findViewById(R.id.externalAttendeesAwaitingResponseList);
        Context context = getContext();
        AppDatabase db = AppDatabase.getDatabase(context);
//
        int eventId = getActivity().getIntent().getExtras().getInt("eventId");
//
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {

            @Override
            public void run() {
                attendees = db.attendeeDAO().getAllAttendeeInfoByResponseAndEvent(eventId, "Yet to respond");

                for(Attendee attendee : attendees){
                    attendeeNames.add(attendee.getAttendeeName());
                }
            };
        });

        adapter = new ExternalAttendeesListAdaptor(inflater, attendeeNames,context, attendeesListView);

//     adapter here
     GridLayoutManager attendeeLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
     attendeesListView.setLayoutManager(attendeeLayoutManager);
     attendeesListView.setAdapter(adapter);

        return view;
    }
}