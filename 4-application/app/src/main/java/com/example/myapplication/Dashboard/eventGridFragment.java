package com.example.myapplication.Dashboard;

import static java.lang.Thread.sleep;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Room.Event;
import com.example.myapplication.R;
import com.example.myapplication.Room.AppDatabase;
import com.example.myapplication.SharedPreferences.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class eventGridFragment extends Fragment {

    eventGridAdapter adapter;

    private RecyclerView eventRecyclerView;


    List<Event> usersEvents = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_grid, container, false);


//                    Bundle bundle = new Bundle();
//                    bundle.getInt("userId", userId);

        //Adding recycler view container
        // E/RecyclerView: No adapter attached; skipping layout
        Context context = getContext();
        AppDatabase db = AppDatabase.getDatabase(context);

        ArrayList<Integer> eventIds = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> eventCardDescriptions = new ArrayList<>();
        ArrayList<String> eventDates = new ArrayList<>();
        ArrayList<String> eventStartTimes = new ArrayList<>();
        ArrayList<String> eventEndTimes = new ArrayList<>();

        String sharedPreferencesFile = getString(R.string.sharedPreferencesFile);
        SharedPreferences sp = this.getActivity().getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE);

        int userId = sp.getInt(UserDetails.KEY_USER_ID, UserDetails.DEFAULT_USER_ID);

        eventRecyclerView =  (RecyclerView) view.findViewById(R.id.eventRecyclerView);


        adapter = new eventGridAdapter(getActivity().getApplicationContext(), eventRecyclerView, eventIds, eventDates, titles, eventCardDescriptions ,eventEndTimes,eventStartTimes);
        adapter.notifyDataSetChanged();


        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {

            @Override
            public void run() {
                usersEvents = db.eventDao().getAllUserEvents(userId);

                for(Event event : usersEvents){
                    eventIds.add(event.getEventId());
                    titles.add(event.getTitle());
                    eventCardDescriptions.add(event.getDescription());
                    eventDates.add(event.getDate());
                    eventStartTimes.add(event.getStartTime());
                    eventEndTimes.add(event.getEndTime());

                }
            };
        });

        //  Ensures room query finishes executing before proceeding otherwise it operates asynchronously
        awaitTerminationAfterShutdown(executor);



        GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        eventRecyclerView.setLayoutManager(layoutManager);
        eventRecyclerView.setAdapter(adapter);

        return view;
    }



//    https://www.baeldung.com/java-executor-wait-for-threads
    //  Ensures room query finishes executing before proceeding otherwise it operates asynchronously
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