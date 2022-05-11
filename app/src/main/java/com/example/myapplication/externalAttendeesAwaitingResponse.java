package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Room.AppDatabase;
import com.example.myapplication.Room.Attendee;
import com.example.myapplication.ViewEventInfomation.AttendeesListAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class externalAttendeesAwaitingResponse extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_external_attendees_awaiting_response, container, false);

        arrayList<Attendee> atte =

        RecyclerView recyclerView = view.findViewById(R.id.externalAttendeesListRecyclerView);
        Context context = getContext();
        AppDatabase db = AppDatabase.getDatabase(context);

        int eventId = getActivity().getIntent().getExtras().getInt("eventId");

        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {

            @Override
            public void run() {
                attendeeInfo = db.attendeeDAO().getAllAttendeeInfoByResponseAndEvent(eventId, responseMessage);

                for(Attendee attendee : attendeeInfo){
                    attendeeNameList.add(attendee.getAttendeeName());
                    responseList.add(attendee.getResponse());

                }
            };
        });

        return view;
    }
}