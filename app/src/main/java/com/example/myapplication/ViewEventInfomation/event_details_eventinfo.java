package com.example.myapplication.ViewEventInfomation;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Room.Event;
import com.example.myapplication.R;
import com.example.myapplication.Room.AppDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class event_details_eventinfo extends Fragment {

    List<Event> eventList;
    TextView eventDetailsEventDate;
    TextView eventDetailsEventStartTime;
    TextView eventDetailsEventLocation;
    TextView eventDetailsEventEndTime;
    TextView eventDetailsEventDescription;
    TextView eventDetailsEventType;
    TextView eventDetailsEventAddress;
    TextView eventDetailsEventTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int eventId = getActivity().getIntent().getExtras().getInt("eventId");
        System.out.println(eventId + " FROM THE INTENT");

        Context context = getContext();
        AppDatabase db = AppDatabase.getDatabase(context);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {

            @Override
            public void run() {
                eventList = db.eventDao().getEvent(eventId);

            }
        });

        View view = inflater.inflate(R.layout.fragment_event_details_eventinfo, container, false);

        eventDetailsEventStartTime = view.findViewById(R.id.eventDetailsEventStartTime);
        eventDetailsEventDate = view.findViewById(R.id.eventDetailsEventDate);
        eventDetailsEventLocation = view.findViewById(R.id.eventDetailsEventLocation);
        eventDetailsEventEndTime = view.findViewById(R.id.eventDetailsEventEndTime);
        eventDetailsEventType = view.findViewById(R.id.eventDetailsEventType);
        eventDetailsEventTitle = view.findViewById(R.id.eventDetailsEventTitle);
        eventDetailsEventDescription = view.findViewById(R.id.eventDetailsEventDescription);
        eventDetailsEventAddress = view.findViewById(R.id.eventDetailsEventAddress);


        if (eventList.size() == 1){
            Event event = eventList.get(0);

            eventDetailsEventStartTime.setText(event.getStartTime());
            eventDetailsEventDate.setText(event.getDate());
            eventDetailsEventLocation.setText(event.getLocationName());
            eventDetailsEventEndTime.setText(event.getEndTime());
            eventDetailsEventType.setText(event.getType());
            eventDetailsEventTitle.setText(event.getTitle());
            eventDetailsEventDescription.setText(event.getDescription());
            eventDetailsEventAddress.setText(event.getAddress());
        }else{
            eventDetailsEventStartTime.setText("Error Please Try Again");
            eventDetailsEventDate.setText("Error Please Try Again");
            eventDetailsEventLocation.setText("Error Please Try Again");
            eventDetailsEventEndTime.setText("Error Please Try Again");
            eventDetailsEventType.setText("Error Please Try Again");
            eventDetailsEventTitle.setText("Error Please Try Again");
            eventDetailsEventDescription.setText("Error Please Try Again");
            eventDetailsEventAddress.setText("Error Please Try Again");
        }

        return view;
    }
}