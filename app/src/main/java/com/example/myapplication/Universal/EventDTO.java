package com.example.myapplication.Universal;

import java.util.Arrays;


public class EventDTO {
    int EventId;
    int HostId;
    String title;
    String description;
    String type;
    String address;
    String locationName;
    String startTime;
    String endTime;
    String date;

    public EventDTO(int eventId, int hostId, String title, String description, String type, String address, String locationName, String startTime, String endTime, String date) {
        this.EventId = eventId;
        this.HostId = hostId;
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
        return EventId;
    }

    public int getHostId() {
        return HostId;
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
                "EventId=" + EventId +
                ", HostId=" + HostId +
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
