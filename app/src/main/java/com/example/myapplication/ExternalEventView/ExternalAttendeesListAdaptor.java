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
    RecyclerView recyclerView;
    Context context;

    public ExternalAttendeesListAdaptor(LayoutInflater inflater, List<String> externalAttendeeName, RecyclerView recyclerView, Context context) {
        this.inflater = inflater;
        this.externalAttendeeName = externalAttendeeName;
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        //Set response value to be selected name
        //form at the bottom
        //

    }

    @NonNull
    @Override
    public ExternalAttendeesListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.attendee_item, parent, false);
        //view.setOnClickListener(this::onClick);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalAttendeesListAdaptor.ViewHolder holder, int position) {
        holder.externalAttendeeName.setText(externalAttendeeName.get(position));
        holder.externalAttendeeName.setText(String.valueOf(position + 1) + " - ");
        holder.externalSelectAttendeeButton.setText("This is me");
    }

    @Override
    public int getItemCount() {
        return externalAttendeeName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView externalAttendeeName;
        TextView externalNumber;
        Button externalSelectAttendeeButton;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            externalAttendeeName = itemView.findViewById(R.id.externalAttendeeName);
            externalNumber = itemView.findViewById(R.id.externalAttendeeNumber);
            externalSelectAttendeeButton = itemView.findViewById(R.id.externalSelectAttendeeButton);


        }

    }
}
