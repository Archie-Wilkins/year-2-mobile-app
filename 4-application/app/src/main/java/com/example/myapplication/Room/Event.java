package com.example.myapplication.Room;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.CalendarContract;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class Event{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "eventId")
    int eventId;

    @ColumnInfo(name = "hostId")
    int hostId;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "type")
    String type;

    @ColumnInfo(name = "address")
    String address;

    @ColumnInfo(name = "locationName")
    String locationName;

    @ColumnInfo(name = "startTime")
    String startTime;

    @ColumnInfo(name = "endTime")
    String endTime;

    @ColumnInfo(name = "date")
    String date;


    public Event(int eventId, int hostId, String title, String description, String type, String address, String locationName, String startTime, String endTime, String date) {
        this.eventId = eventId;
        this.hostId = hostId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.address = address;
        this.locationName = locationName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;

    }

    public int getEventId() {
        return eventId;
    }

    public int getHostId() {
        return hostId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "Event{" +
                "EventId=" + eventId +
                ", HostId=" + hostId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", locationName='" + locationName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

}
