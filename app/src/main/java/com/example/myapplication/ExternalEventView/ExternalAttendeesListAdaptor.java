package com.example.myapplication.ExternalEventView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ViewEventInfomation.AttendeesListAdapter;

import java.util.List;

public class ExternalAttendeesListAdaptor extends RecyclerView.Adapter<ExternalAttendeesListAdaptor.ViewHolder> implements View.OnClickListener   {


    LayoutInflater inflater;
    List<String> externalAttendeeName;
    Context context;

    public ExternalAttendeesListAdaptor(LayoutInflater inflater, List<String> externalAttendeeName, Context context) {
        this.inflater = inflater;
        this.externalAttendeeName = externalAttendeeName;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        System.out.println("Nice");

    }

    @NonNull
    @Override
    public ExternalAttendeesListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.external_attendee_item, parent, false);
        view.setOnClickListener(this::onClick);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalAttendeesListAdaptor.ViewHolder holder, int position) {
        holder.externalAttendeeName.setText(externalAttendeeName.get(position));
        holder.externalAttendeeNumber.setText(String.valueOf(position + 1) + " - ");
    }

    @Override
    public int getItemCount() {
        return externalAttendeeName.size();
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
//            inviteResponseMaybe = itemView.findViewById(R.id.inviteResponseMaybe);
            inviteResponseMaybe = itemView.findViewById(R.id.eventCardEndTime);

        }

    }
}
