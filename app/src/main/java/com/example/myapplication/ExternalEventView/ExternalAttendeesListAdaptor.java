package com.example.myapplication.ExternalEventView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ViewEventInfomation.AttendeesListAdapter;

import java.util.List;

public class ExternalAttendeesListAdaptor extends RecyclerView.Adapter<ExternalAttendeesListAdaptor.ViewHolder> implements View.OnClickListener   {

    LayoutInflater inflater;
    Button externalAttendeeItemButton;
    List<String> externalAttendeeName;
    RecyclerView recyclerView;
    Context context;


    @Override
    public void onClick(View view) {
        //Set response value to be selected name
    }

    @NonNull
    @Override
    public ExternalAttendeesListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalAttendeesListAdaptor.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    }
}
