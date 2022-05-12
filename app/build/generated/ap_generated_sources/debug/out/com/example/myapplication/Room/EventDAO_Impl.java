package com.example.myapplication.Room;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EventDAO_Impl implements EventDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Event> __insertionAdapterOfEvent;

  private final EntityDeletionOrUpdateAdapter<Event> __updateAdapterOfEvent;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllEvents;

  public EventDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEvent = new EntityInsertionAdapter<Event>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Event` (`eventId`,`hostId`,`title`,`description`,`type`,`address`,`locationName`,`startTime`,`endTime`,`date`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Event value) {
        stmt.bindLong(1, value.getEventId());
        stmt.bindLong(2, value.getHostId());
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getType());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAddress());
        }
        if (value.getLocationName() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLocationName());
        }
        if (value.getStartTime() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getStartTime());
        }
        if (value.getEndTime() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getEndTime());
        }
        if (value.getDate() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getDate());
        }
      }
    };
    this.__updateAdapterOfEvent = new EntityDeletionOrUpdateAdapter<Event>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Event` SET `eventId` = ?,`hostId` = ?,`title` = ?,`description` = ?,`type` = ?,`address` = ?,`locationName` = ?,`startTime` = ?,`endTime` = ?,`date` = ? WHERE `eventId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Event value) {
        stmt.bindLong(1, value.getEventId());
        stmt.bindLong(2, value.getHostId());
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getType() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getType());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAddress());
        }
        if (value.getLocationName() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getLocationName());
        }
        if (value.getStartTime() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getStartTime());
        }
        if (value.getEndTime() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getEndTime());
        }
        if (value.getDate() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getDate());
        }
        stmt.bindLong(11, value.getEventId());
      }
    };
    this.__preparedStmtOfDeleteAllEvents = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Event";
        return _query;
      }
    };
  }

  @Override
  public void insertEvent(final Event... events) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEvent.insert(events);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Event event) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfEvent.handle(event);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllEvents() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllEvents.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllEvents.release(_stmt);
    }
  }

  @Override
  public List<Event> getAllUserEvents(final int hostid) {
    final String _sql = "SELECT * FROM Event WHERE hostId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, hostid);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "eventId");
      final int _cursorIndexOfHostId = CursorUtil.getColumnIndexOrThrow(_cursor, "hostId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final List<Event> _result = new ArrayList<Event>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Event _item;
        final int _tmpEventId;
        _tmpEventId = _cursor.getInt(_cursorIndexOfEventId);
        final int _tmpHostId;
        _tmpHostId = _cursor.getInt(_cursorIndexOfHostId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        final String _tmpAddress;
        _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        final String _tmpLocationName;
        _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
        final String _tmpStartTime;
        _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
        final String _tmpEndTime;
        _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
        final String _tmpDate;
        _tmpDate = _cursor.getString(_cursorIndexOfDate);
        _item = new Event(_tmpEventId,_tmpHostId,_tmpTitle,_tmpDescription,_tmpType,_tmpAddress,_tmpLocationName,_tmpStartTime,_tmpEndTime,_tmpDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Event> getEvent(final int eventid) {
    final String _sql = "SELECT * FROM Event WHERE eventId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, eventid);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "eventId");
      final int _cursorIndexOfHostId = CursorUtil.getColumnIndexOrThrow(_cursor, "hostId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "locationName");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final List<Event> _result = new ArrayList<Event>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Event _item;
        final int _tmpEventId;
        _tmpEventId = _cursor.getInt(_cursorIndexOfEventId);
        final int _tmpHostId;
        _tmpHostId = _cursor.getInt(_cursorIndexOfHostId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final String _tmpType;
        _tmpType = _cursor.getString(_cursorIndexOfType);
        final String _tmpAddress;
        _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        final String _tmpLocationName;
        _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
        final String _tmpStartTime;
        _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
        final String _tmpEndTime;
        _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
        final String _tmpDate;
        _tmpDate = _cursor.getString(_cursorIndexOfDate);
        _item = new Event(_tmpEventId,_tmpHostId,_tmpTitle,_tmpDescription,_tmpType,_tmpAddress,_tmpLocationName,_tmpStartTime,_tmpEndTime,_tmpDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean exists(final int eventId) {
    final String _sql = "SELECT EXISTS(SELECT * FROM Event WHERE eventId = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, eventId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
