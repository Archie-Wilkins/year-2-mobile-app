package com.example.myapplication.ExternalEventView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ViewEventInfomation.AttendeesListAdapter;

import java.util.List;

public class ExternalAttendeesListAdaptor extends RecyclerView.Adapter<ExternalAttendeesListAdaptor.ViewHolder> implements View.OnClickListener   {

    LayoutInflater inflater;
    List<String> externalAttendeeName;
    Context context;
    RecyclerView recyclerView;

    public ExternalAttendeesListAdaptor(LayoutInflater inflater, List<String> externalAttendeeName, Context context, RecyclerView recyclerView) {
        this.inflater = inflater;
        this.externalAttendeeName = externalAttendeeName;
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
        holder.externalAttendeeName.setText(externalAttendeeName.get(position));
        holder.externalAttendeeNumber.setText(String.valueOf(position + 1) + " - ");
        holder.inviteResponseYes.setOnClickListener(this::onClick);
        holder.inviteResponseNo.setOnClickListener(this::onClick);
        holder.inviteResponseMaybe.setOnClickListener(this::onClick);

    }

    @Override
    public int getItemCount() {
        return externalAttendeeName.size();
    }

    @Override
    public void onClick(View view) {
//        RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
//        int itemPosition = holder.getAdapterPosition();

        //add bundle here
//        String position = externalAttendeeName.get(itemPosition);
//        Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, String.valueOf(id) , Toast.LENGTH_SHORT).show();
////
//        switch(id){
//            case R.id.inviteResponseYes:
//                Toast.makeText(context, "Yes ", Toast.LENGTH_SHORT).show();
//            case R.id.inviteResponseNo:
//                Toast.makeText(context, "No ", Toast.LENGTH_SHORT).show();
//            case R.id.inviteResponseMaybe:
//                Toast.makeText(context, "Maybe ", Toast.LENGTH_SHORT).show();
//    }
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
