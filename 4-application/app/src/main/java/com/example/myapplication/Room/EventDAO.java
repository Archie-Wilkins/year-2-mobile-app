package com.example.myapplication.Room;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Dao;
import androidx.room.Update;


import java.util.List;

@Dao
public interface EventDAO {

    @Query("SELECT * FROM Event WHERE hostId = :hostid")
    List<Event> getAllUserEvents(int hostid);

    @Query("SELECT * FROM Event WHERE eventId = :eventid")
    List<Event> getEvent(int eventid);

    @Query("DELETE FROM Event")
    void deleteAllEvents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEvent(Event... events);

    @Query("SELECT EXISTS(SELECT * FROM Event WHERE eventId = :eventId)")
    boolean exists(int eventId);

    @Update
    void update(Event event);
}
