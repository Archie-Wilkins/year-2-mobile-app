package com.example.myapplication.ViewEventInfomation;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.Room.AppDatabase;
import com.example.myapplication.Room.Attendee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class attendeesListFragment extends Fragment {

    AttendeesListAdapter adapter;
    RecyclerView attendeesListView;
    List<Attendee> attendeeInfo = new ArrayList<>();
    List<String> responseList = new ArrayList<>();
    List<String> attendeeNameList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendees_list, container, false);

       Bundle bundle = this.getArguments();
        String eventId = bundle.getString("eventId");

        Context context = getContext();
        AppDatabase db = AppDatabase.getDatabase(context);


        attendeesListView = view.findViewById(R.id.attendeesList);
        adapter = new AttendeesListAdapter(inflater, responseList, attendeeNameList, getActivity().getApplicationContext());


        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {

            @Override
            public void run() {
                attendeeInfo = db.attendeeDAO().getAllAttendeeInfo(Integer.valueOf(eventId));

                for(Attendee attendee : attendeeInfo){
                    responseList.add(attendee.getResponse());
                    attendeeNameList.add(attendee.getAttendeeName());
                }
            };
        });

        awaitTerminationAfterShutdown(executor);

        GridLayoutManager attendeeLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        attendeesListView.setLayoutManager(attendeeLayoutManager);
        attendeesListView.setAdapter(adapter);

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