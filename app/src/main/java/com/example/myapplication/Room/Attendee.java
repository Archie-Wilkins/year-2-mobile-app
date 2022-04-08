package com.example.myapplication.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Attendee {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "attendeeId")
    int attendeeId;

    @ColumnInfo(name = "eventId")
    int eventId;

    @ColumnInfo(name = "response")
    String response;

    @ColumnInfo(name = "attendeeName")
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

    @Override
    public String toString() {
        return "Attendee{" +
                "attendeeId=" + attendeeId +
                ", eventId=" + eventId +
                ", response='" + response + '\'' +
                ", attendeeName='" + attendeeName + '\'' +
                '}';
    }

}
