package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class eventGridAdapter extends RecyclerView.Adapter<eventGridAdapter.ViewHolder>  {

    LayoutInflater inflater;
    List<String> eventDates;
    List<String> titles;
    List<String> eventCardDescriptions;
    List<String> eventEndTimes;
    List<String> eventStartTimes;


    public eventGridAdapter(Context context, List<String> eventDates, List<String> titles, List<String> eventCardDescriptions, List<String> eventEndTimes, List<String> eventStartTimes) {
        this.inflater = LayoutInflater.from(context);
        this.eventDates = eventDates;
        this.titles = titles;
        this.eventCardDescriptions = eventCardDescriptions;
        this.eventEndTimes = eventEndTimes;
        this.eventStartTimes = eventStartTimes;
    }


    @NonNull
    @Override
    public eventGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_grid_card, parent, false);
        return new ViewHolder(view);
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
