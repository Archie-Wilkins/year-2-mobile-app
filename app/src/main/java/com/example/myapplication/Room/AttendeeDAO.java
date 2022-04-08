package com.example.myapplication.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AttendeeDAO {

    @Query("SELECT * FROM Attendee WHERE eventId = :eventid")
    List<Attendee> getAllAttendeeInfo(int eventid);

//    @Query("SELECT attendeeName,response FROM Attendee WHERE eventId = :eventid")
//    List<Attendee> getAttendeeNamesAndResponse(int eventid);

    @Query("DELETE FROM Attendee")
    void resetTable();

    @Insert
    void insertAttendee(Attendee... attendees);

}