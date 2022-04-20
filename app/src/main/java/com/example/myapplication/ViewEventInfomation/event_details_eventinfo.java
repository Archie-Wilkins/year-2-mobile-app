package com.example.myapplication.ViewEventInfomation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Button shareEventButton;

    int eventId;
    boolean shareable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        eventId = getActivity().getIntent().getExtras().getInt("eventId");
        shareable = getActivity().getIntent().getExtras().getBoolean("shareable");


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

        shareEventButton = view.findViewById(R.id.shareEventButton);
        if(shareable == false){
            //Remove element code taken from https://stackoverflow.com/questions/3995215/add-and-remove-views-in-android-dynamically
            ((ViewGroup) shareEventButton.getParent()).removeView(shareEventButton);
        }else {
            shareEventButton.setOnClickListener(this::onClick);
        }



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

    public void onClick(View view){
        int id = view.getId();

        switch(id){
            case R.id.shareEventButton:

                String eventDetailsEventDateText = eventDetailsEventDate.getText().toString();
                String eventDetailsEventStartTimeText = eventDetailsEventStartTime.getText().toString();
                String eventDetailsEventLocationText = eventDetailsEventLocation.getText().toString();
                String eventDetailsEventEndTimeText = eventDetailsEventEndTime.getText().toString();
                String eventDetailsEventDescriptionText = eventDetailsEventDescription.getText().toString();
                String eventDetailsEventTypeText = eventDetailsEventType.getText().toString();
                String eventDetailsEventAddressText = eventDetailsEventAddress.getText().toString();
                String eventDetailsEventTitleText = eventDetailsEventTitle.getText().toString();

                String message = eventDetailsEventTitleText + "\n"
                        + "\n"
                        + "Date: " + eventDetailsEventDateText
                        + "Time: " + eventDetailsEventStartTimeText + " - " + eventDetailsEventEndTimeText + "\n"
                        + "Location: " + eventDetailsEventLocationText + "\n"
                        + "Address: " + eventDetailsEventAddressText + "\n"
                        + "Event Details: " + eventDetailsEventDescriptionText + "\n"
                        + "\n"
                        + "View Event: www.facebookeventsknockoff.com?event=" + eventId + "\n"
                        + "Shared from An Amazing Events App";


                //Reference code modified from https://stackoverflow.com/questions/12952865/how-to-share-text-to-whatsapp-from-my-app#:~:text=Like%20most%20social%20apps%20on,sendIntent%20%3D%20new%20Intent()%3B%20sendIntent.
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);


        }
    }
}