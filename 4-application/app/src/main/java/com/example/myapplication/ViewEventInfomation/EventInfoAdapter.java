package com.example.myapplication.ViewEventInfomation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.ViewEventInfomation.event_details_attendeesinfo;
import com.example.myapplication.ViewEventInfomation.event_details_eventinfo;

public class EventInfoAdapter extends FragmentStateAdapter {

    public EventInfoAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new event_details_attendeesinfo();
        }
        return new event_details_eventinfo();
    }

    @Override
//  2 is the current number of tabs in the view
    public int getItemCount() {
        return 2;
    }
}
