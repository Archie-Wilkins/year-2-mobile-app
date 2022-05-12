package com.example.myapplication.ExternalEventView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.myapplication.Room.Attendee;
import com.example.myapplication.SharedPreferences.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
                String attendeeResponse = "Yes";
                updateUserResponseRequest( attendeeId, attendeeResponse);
            }
        });

        holder.inviteResponseNo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String attendeeName = externalAttendees.get(position).getAttendeeName();
                int attendeeId = externalAttendees.get(position).getAttendeeId();
                String attendeeResponse = "No";
                updateUserResponseRequest( attendeeId, attendeeResponse);
            }
        });

        holder.inviteResponseMaybe.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String attendeeName = externalAttendees.get(position).getAttendeeName();
                int attendeeId = externalAttendees.get(position).getAttendeeId();
                String attendeeResponse = "Maybe";
                updateUserResponseRequest( attendeeId, attendeeResponse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return externalAttendees.size();
    }

    public void updateUserResponseRequest(int attendeeId, String attendeeResponse){

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
                            //need to store username and password
//
                            //update database
                            Toast.makeText(context, "User Updated", Toast.LENGTH_LONG).show();

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
