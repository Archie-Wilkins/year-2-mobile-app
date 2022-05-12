package com.example.myapplication.ExternalEventView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Room.Attendee;

import java.util.List;

public class ExternalAttendeesListAdaptor extends RecyclerView.Adapter<ExternalAttendeesListAdaptor.ViewHolder>  {

    LayoutInflater inflater;
    List<Attendee> externalAttendees;
    Context context;
    RecyclerView recyclerView;

    public ExternalAttendeesListAdaptor(LayoutInflater inflater, List<Attendee> externalAttendees, Context context, RecyclerView recyclerView) {
        this.inflater = inflater;
        this.externalAttendees = externalAttendees;
        this.context = context;
        this.recyclerView = recyclerView;
    }


    @NonNull
    @Override
    public ExternalAttendeesListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.external_attendee_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalAttendeesListAdaptor.ViewHolder holder, int position) {
        holder.externalAttendeeName.setText(externalAttendees.get(position).getAttendeeName());
        holder.externalAttendeeNumber.setText(String.valueOf(position + 1) + " - ");

        holder.inviteResponseYes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String attendeeName = externalAttendees.get(position).getAttendeeName();
                int attendeeId = externalAttendees.get(position).getAttendeeId();
                String response = "Yes";
            }
        });

        holder.inviteResponseNo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String attendeeName = externalAttendees.get(position).getAttendeeName();
                int attendeeId = externalAttendees.get(position).getAttendeeId();
                String response = "No";
            }
        });

        holder.inviteResponseMaybe.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String attendeeName = externalAttendees.get(position).getAttendeeName();
                int attendeeId = externalAttendees.get(position).getAttendeeId();
                String response = "Maybe";
            }
        });
    }

    @Override
    public int getItemCount() {
        return externalAttendees.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView externalAttendeeName;
        TextView externalAttendeeNumber;
        Button inviteResponseYes;
        Button inviteResponseNo;
        Button inviteResponseMaybe;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            externalAttendeeName = itemView.findViewById(R.id.externalAttendeeName);
            externalAttendeeNumber = itemView.findViewById(R.id.externalAttendeeNumber);
            inviteResponseYes = itemView.findViewById(R.id.inviteResponseYes);
            inviteResponseNo = itemView.findViewById(R.id.inviteResponseNo);
            inviteResponseMaybe = itemView.findViewById(R.id.inviteResponseMaybe);


        }

    }

}
