package com.example.myapplication.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ViewEventInfomation.event_details;

import java.util.List;

public class eventGridAdapter extends RecyclerView.Adapter<eventGridAdapter.ViewHolder> implements View.OnClickListener  {

    LayoutInflater inflater;
    List<Integer> eventIds;
    List<String> eventDates;
    List<String> titles;
    List<String> eventCardDescriptions;
    List<String> eventEndTimes;
    List<String> eventStartTimes;
    RecyclerView recyclerView;
    Context context;

    public eventGridAdapter(Context context, RecyclerView recyclerView, List<Integer> eventIds, List<String> eventDates, List<String> titles, List<String> eventCardDescriptions, List<String> eventEndTimes, List<String> eventStartTimes) {
        this.inflater = LayoutInflater.from(context);
        this.eventIds = eventIds;
        this.eventDates = eventDates;
        this.titles = titles;
        this.eventCardDescriptions = eventCardDescriptions;
        this.eventEndTimes = eventEndTimes;
        this.eventStartTimes = eventStartTimes;
        this.recyclerView = recyclerView;
        this.context = context;
    }


    @NonNull
    @Override
    public eventGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_grid_card, parent, false);
        view.setOnClickListener(this::onClick);
        return new ViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
        int itemPosition = holder.getAdapterPosition();

        //add bundle here
        Bundle bundle = new Bundle();
        int eventId = eventIds.get(itemPosition);
        //bundle.putInt("eventId",eventId);




        Intent intent = new Intent(v.getContext(), event_details.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("eventId", eventId);
        v.getContext().startActivity(intent);
//        Toast.makeText(context, "Welcome " + itemPosition , Toast.LENGTH_SHORT).show();

    }




    @Override
    public void onBindViewHolder(@NonNull eventGridAdapter.ViewHolder holder, int position) {
        holder.Title.setText(titles.get(position));
        holder.eventCardDescription.setText(eventCardDescriptions.get(position));
        holder.eventDate.setText(eventDates.get(position));
        holder.eventStartTime.setText(eventStartTimes.get(position));
        holder.eventEndTime.setText(eventEndTimes.get(position));

    }



    @Override
    public int getItemCount() {
        return titles.size();
    }



     public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title;
        TextView eventDate;
        TextView eventCardDescription;
        TextView eventEndTime;
        TextView eventStartTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.eventCardTitle);
            eventDate = itemView.findViewById(R.id.eventCardDate);
            eventCardDescription = itemView.findViewById(R.id.eventCardDescription);
            eventStartTime = itemView.findViewById(R.id.eventCardStartTime);
            eventEndTime = itemView.findViewById(R.id.eventCardEndTime);
        }

    }

}
