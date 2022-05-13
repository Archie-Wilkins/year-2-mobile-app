package com.example.myapplication.NewEvent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.SharedPreferences.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class newEventForm extends AppCompatActivity {

    private EditText eventTitleInput;
    private EditText eventLocationNameInput;
    private EditText postCodeInput;
    private CalendarView eventDateInput;
    private EditText startTimeInput;
    private EditText endTimeInput;
    private Button eventSubmitButton;
    private EditText eventTypeInput;
    private EditText eventDescriptionInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_form);

        this.eventTitleInput = findViewById(R.id.eventTitleInput);
        this.eventLocationNameInput = findViewById(R.id.eventLocationInput);
        this.postCodeInput = findViewById(R.id.postCodeInput);
        this.eventTypeInput = findViewById(R.id.eventTypeInput);
        this.startTimeInput = findViewById(R.id.startTimeInput);
        this.endTimeInput = findViewById(R.id.endTimeInput);
        this.eventDateInput = findViewById(R.id.dateInput);
        this.eventDescriptionInput = findViewById(R.id.descriptionInput);

        this.eventSubmitButton = findViewById(R.id.eventSubmit);
        this.eventSubmitButton.setOnClickListener(this::onClick);
    }

    public void onClick(View view){
        submitNewEvent(view);
    }

    private void submitNewEvent(View view) {

        //Getting host id
        String sharedPreferencesFile = getString(R.string.sharedPreferencesFile);
        SharedPreferences sp = getSharedPreferences(sharedPreferencesFile, Context.MODE_PRIVATE);
        int hostId = sp.getInt(UserDetails.KEY_USER_ID, UserDetails.DEFAULT_USER_ID);

        //Getting date from form
        //Calendar milleseconds since epoch to date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

//      Getting event date
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(eventDateInput.getDate());
        String eventDate = formatter.format(calendar.getTime());


        //Getting form values
        String eventTitle = eventTitleInput.getText().toString();
        String eventLocationName = eventLocationNameInput.getText().toString();
        String postCode = postCodeInput.getText().toString();
        String startTime = startTimeInput.getText().toString();
        String endTime = endTimeInput.getText().toString();
        String eventType = eventTypeInput.getText().toString();
        String eventDescription = eventDescriptionInput.getText().toString();

        //Validation
//       Title can't be empty
        if(eventTitle.equals("")){
            Toast.makeText(getApplication().getBaseContext(), "Event Title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
//        Event location name cant be empty
        if(eventLocationName.equals("")){
            Toast.makeText(getApplication().getBaseContext(), "Location can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //Event location name cant be empty
        if(postCode.equals("")){
            Toast.makeText(getApplication().getBaseContext(), "Post code can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }


//        start time cant be empty
        if(startTime.equals("")){
            Toast.makeText(getApplication().getBaseContext(), "Start time can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //end time cant be empty
        if(endTime.equals("")){
            Toast.makeText(getApplication().getBaseContext(), "End time can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }


//        event type cant be empty
        if(eventType.equals("")){
            Toast.makeText(getApplication().getBaseContext(), "Event type can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
//        description cant be empty
        if(eventDescription.equals("")){
            Toast.makeText(getApplication().getBaseContext(), "Event description  can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }


//        Creating Json Object
        HashMap<String, String> JsonMap = new HashMap<>();
        JsonMap.put("hostID", String.valueOf(hostId));
        JsonMap.put("eventTitle",eventTitle);
        JsonMap.put("eventDescription", eventDescription);
        JsonMap.put("eventType", eventType);
        JsonMap.put("eventAddress", postCode);
        JsonMap.put("eventStartTime",startTime);
        JsonMap.put("eventEndTime",endTime);
        JsonMap.put("eventDate",eventDate);
        JsonMap.put("eventLocationName", eventLocationName);
        JSONObject jsonObject = new JSONObject(JsonMap);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.POST,
                "https://archiewilkins.pythonanywhere.com/api/newEvent", jsonObject,
                response -> {
                    try {
                        String responseStatus = response.getString("response");
                        if(responseStatus.equals("success")){
                            Toast.makeText(getApplication().getBaseContext(), "Event Added", Toast.LENGTH_SHORT).show();

                            Intent dashboard = new Intent(getApplicationContext(), com.example.myapplication.Dashboard.dashboard.class);
                            startActivity(dashboard);
                            //Prevents user from going back
                            finish();


                        }else{
                            Toast.makeText(getApplication().getBaseContext(), "Whoops looks like you didn't enter something correctly", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplication().getBaseContext(), "Server Error", Toast.LENGTH_LONG).show();
                    }

                },
                error -> {
                    Toast.makeText(getApplication().getBaseContext(), "Error Server Not Found", Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(getRequest);

    }

}