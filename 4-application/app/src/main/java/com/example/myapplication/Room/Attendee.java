package com.example.myapplication.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Attendee {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int attendeeId;

    @ColumnInfo(name = "eventId")
    int eventId;

    @ColumnInfo(name = "attendeeName")
    String attendeeName;

    @ColumnInfo(name = "response")
    String response;

    public Attendee(int attendeeId, int eventId, String attendeeName, String response) {
        this.attendeeId = attendeeId;
        this.eventId = eventId;
        this.attendeeName = attendeeName;
        this.response = response;
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
