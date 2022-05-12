package com.example.myapplication.ExternalEventView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Dashboard.dashboard;
import com.example.myapplication.R;
import com.example.myapplication.Room.AppDatabase;
import com.example.myapplication.Room.Attendee;
import com.example.myapplication.Room.Event;
import com.example.myapplication.SharedPreferences.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExternalAttendeesListAdaptor extends RecyclerView.Adapter<ExternalAttendeesListAdaptor.ViewHolder>  {

    private final ExternalAttendeesListAdaptor ExternalAttendeesListAdaptor;
    LayoutInflater inflater;
    List<Attendee> externalAttendees;
    Context context;
    RecyclerView recyclerView;

    public ExternalAttendeesListAdaptor(LayoutInflater inflater, List<Attendee> externalAttendees, Context context, RecyclerView recyclerView) {
        this.inflater = inflater;
        this.externalAttendees = externalAttendees;
        this.context = context;
        this.recyclerView = recyclerView;
        this.ExternalAttendeesListAdaptor = this;
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
            public void onClick(View view){
                Attendee attendee = externalAttendees.get(position);
                String attendeeResponse = "Yes";
                updateUserResponseRequest(attendee, attendeeResponse, view);
            }
        });

        holder.inviteResponseNo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Attendee attendee = externalAttendees.get(position);
                String attendeeResponse = "No";
                updateUserResponseRequest(attendee, attendeeResponse, view);
            }
        });

        holder.inviteResponseMaybe.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Attendee attendee = externalAttendees.get(position);
                String attendeeResponse = "Maybe";
                updateUserResponseRequest(attendee, attendeeResponse, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return externalAttendees.size();
    }

    public void updateUserResponseRequest(Attendee attendee, String attendeeResponse, View view){
        int attendeeId = attendee.getAttendeeId();
        HashMap<String, String> JsonMap = new HashMap<>();
        JsonMap.put("response",attendeeResponse);
        JSONObject jsonObject = new JSONObject(JsonMap);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest patchUserStatusRequest = new JsonObjectRequest(
                Request.Method.PATCH,
                "https://archiewilkins.pythonanywhere.com/api/userStatus/" + attendeeId, jsonObject,
                response -> {
                    System.out.println(response.toString());
                    try {
                        String responseStatus = response.getString("response");
                        if(responseStatus.equals("success")){
                            //need to update database values
                            //need to update relevant fragment
                            //

                            AppDatabase db = AppDatabase.getDatabase(context);

                            ExecutorService executor = Executors.newFixedThreadPool(4);
                            executor.execute(new Runnable() {

                                @Override
                                public void run() {
                                    Attendee attendeeUpdated = new Attendee(
                                            attendee.getAttendeeId(),
                                            attendee.getEventId(),
                                            attendee.getAttendeeName(),
                                            attendeeResponse);


                                    db.attendeeDAO().update(attendeeUpdated);
                                    externalAttendees.remove(attendee); //Actually change your list of items here
                                }
                            });

                            awaitTerminationAfterShutdown(executor);
                            ExternalAttendeesListAdaptor.notifyDataSetChanged();


//
                            //update database
                            Toast.makeText(context, attendee.getAttendeeName() + " updated", Toast.LENGTH_LONG).show();



                            int eventId = attendee.getEventId();
                            boolean shareable = false;

                            Intent intent = new Intent(context, external_event_view.class);

                            intent.putExtra("eventId", eventId);
                            intent.putExtra("shareable", shareable);

                            view.getContext().startActivity(intent);

//                            overridePendingTransition(0, 0);
//                            startActivity(getIntent());
//                            overridePendingTransition(0, 0);

                        }else{
                            Toast.makeText(context, "Oh no something went wrong, try again", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Server Error", Toast.LENGTH_LONG).show();
                    }

                },
                error -> {
                    System.out.println("Error");
                    Toast.makeText(context, "Error Server Not Found", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(patchUserStatusRequest);
    }




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
