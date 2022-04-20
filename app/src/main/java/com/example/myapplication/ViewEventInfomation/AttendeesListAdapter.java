package com.example.myapplication.ViewEventInfomation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class AttendeesListAdapter extends RecyclerView.Adapter<AttendeesListAdapter.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    List<String> response;
    List<String> attendeeName;
    RecyclerView recyclerView;
    Context context;

    public AttendeesListAdapter(LayoutInflater inflater, List<String> response, List<String> attendeeName, RecyclerView recyclerView, Context context) {
        this.inflater = inflater;
        this.response = response;
        this.attendeeName = attendeeName;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        System.out.println("Nice");
    }

    @NonNull
    @Override
    public AttendeesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.attendee_item, parent, false);
        view.setOnClickListener(this::onClick);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendeesListAdapter.ViewHolder holder, int position) {
        holder.attendeeName.setText(attendeeName.get(position));
        holder.attendeeNumber.setText(String.valueOf(position + 1) + " - ");
        holder.attendeeResponse.setText(response.get(position));
    }


    @Override
    public int getItemCount() {
        return response.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView attendeeName;
        TextView attendeeResponse;
        TextView attendeeNumber;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            attendeeNumber = itemView.findViewById(R.id.externalAttendeeNumber);
            attendeeResponse = itemView.findViewById(R.id.attendeeResponse);
            attendeeName = itemView.findViewById(R.id.externalAttendeeName);
        }
    }
}
