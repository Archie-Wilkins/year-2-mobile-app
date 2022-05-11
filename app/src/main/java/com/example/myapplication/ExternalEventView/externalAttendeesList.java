package com.example.myapplication.ExternalEventView;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Dashboard.eventGridAdapter;
import com.example.myapplication.R;
import com.example.myapplication.Room.AppDatabase;
import com.example.myapplication.Room.Attendee;
import com.example.myapplication.Room.Event;
import com.example.myapplication.ViewEventInfomation.AttendeesListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class externalAttendeesList extends Fragment {

    AttendeesListAdapter adapter;
    RecyclerView attendeesListView;
    List<Attendee> attendeeInfo = new ArrayList<>();
    List<String> responseList = new ArrayList<>();
    List<String> attendeeNameList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_external_attendees_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.externalAttendeesListRecyclerView);
        adapter = new AttendeesListAdapter(inflater, responseList, attendeeNameList, getActivity().getApplicationContext());


        Context context = getContext();
        AppDatabase db = AppDatabase.getDatabase(context);

        int eventId = getActivity().getIntent().getExtras().getInt("eventId");
        Bundle bundle = this.getArguments();
        String responseMessage = bundle.getString("response");


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
//
        awaitTerminationAfterShutdown(executor);
//
//
//
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
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