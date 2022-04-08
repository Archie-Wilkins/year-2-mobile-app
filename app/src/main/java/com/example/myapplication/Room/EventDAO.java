package com.example.myapplication.Room;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Dao;


import java.util.List;

@Dao
public interface EventDAO {

    @Query("SELECT * FROM Event WHERE hostId = :hostid")
    List<Event> getAllUserEvents(int hostid);

    @Query("SELECT * FROM Event WHERE eventId = :eventid")
    List<Event> getEvent(int eventid);

    @Query("DELETE FROM Event")
    void deleteAllEvents();

    @Insert
    void insertEvent(Event... events);
}
