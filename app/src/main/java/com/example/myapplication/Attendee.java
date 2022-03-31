package com.example.myapplication;

public class Attendee {

    int attendeeId;
    int eventId;
    String response;
    String attendeeName;


    public Attendee(int attendeeId, int eventId, String response, String attendeeName) {
        this.attendeeId = attendeeId;
        this.eventId = eventId;
        this.response = response;
        this.attendeeName = attendeeName;
    }

    public int getAttendeeId() {
        return attendeeId;
    }

    public int getEventId() {
        return eventId;
    }

    public String getResponse() {
        return response;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

}
