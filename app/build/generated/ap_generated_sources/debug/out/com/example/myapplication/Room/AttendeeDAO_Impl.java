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
public final class AttendeeDAO_Impl implements AttendeeDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Attendee> __insertionAdapterOfAttendee;

  private final EntityDeletionOrUpdateAdapter<Attendee> __updateAdapterOfAttendee;

  private final SharedSQLiteStatement __preparedStmtOfResetTable;

  public AttendeeDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAttendee = new EntityInsertionAdapter<Attendee>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Attendee` (`id`,`eventId`,`attendeeName`,`response`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Attendee value) {
        stmt.bindLong(1, value.getAttendeeId());
        stmt.bindLong(2, value.getEventId());
        if (value.getAttendeeName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAttendeeName());
        }
        if (value.getResponse() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getResponse());
        }
      }
    };
    this.__updateAdapterOfAttendee = new EntityDeletionOrUpdateAdapter<Attendee>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Attendee` SET `id` = ?,`eventId` = ?,`attendeeName` = ?,`response` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Attendee value) {
        stmt.bindLong(1, value.getAttendeeId());
        stmt.bindLong(2, value.getEventId());
        if (value.getAttendeeName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAttendeeName());
        }
        if (value.getResponse() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getResponse());
        }
        stmt.bindLong(5, value.getAttendeeId());
      }
    };
    this.__preparedStmtOfResetTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Attendee";
        return _query;
      }
    };
  }

  @Override
  public void insertAttendee(final Attendee... attendees) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAttendee.insert(attendees);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Attendee attendee) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAttendee.handle(attendee);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void resetTable() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfResetTable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfResetTable.release(_stmt);
    }
  }

  @Override
  public List<Attendee> getAllAttendeeInfo(final int eventid) {
    final String _sql = "SELECT * FROM Attendee WHERE eventId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, eventid);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfAttendeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "eventId");
      final int _cursorIndexOfAttendeeName = CursorUtil.getColumnIndexOrThrow(_cursor, "attendeeName");
      final int _cursorIndexOfResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "response");
      final List<Attendee> _result = new ArrayList<Attendee>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Attendee _item;
        final int _tmpAttendeeId;
        _tmpAttendeeId = _cursor.getInt(_cursorIndexOfAttendeeId);
        final int _tmpEventId;
        _tmpEventId = _cursor.getInt(_cursorIndexOfEventId);
        final String _tmpAttendeeName;
        _tmpAttendeeName = _cursor.getString(_cursorIndexOfAttendeeName);
        final String _tmpResponse;
        _tmpResponse = _cursor.getString(_cursorIndexOfResponse);
        _item = new Attendee(_tmpAttendeeId,_tmpEventId,_tmpAttendeeName,_tmpResponse);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean exists(final int attendeeId) {
    final String _sql = "SELECT EXISTS(SELECT * FROM Attendee WHERE id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, attendeeId);
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

  @Override
  public List<Attendee> getAllAttendeeInfoByResponseAndEvent(final int eventid,
      final String response) {
    final String _sql = "SELECT * FROM Attendee WHERE eventId = ? AND response = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, eventid);
    _argIndex = 2;
    if (response == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, response);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfAttendeeId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "eventId");
      final int _cursorIndexOfAttendeeName = CursorUtil.getColumnIndexOrThrow(_cursor, "attendeeName");
      final int _cursorIndexOfResponse = CursorUtil.getColumnIndexOrThrow(_cursor, "response");
      final List<Attendee> _result = new ArrayList<Attendee>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Attendee _item;
        final int _tmpAttendeeId;
        _tmpAttendeeId = _cursor.getInt(_cursorIndexOfAttendeeId);
        final int _tmpEventId;
        _tmpEventId = _cursor.getInt(_cursorIndexOfEventId);
        final String _tmpAttendeeName;
        _tmpAttendeeName = _cursor.getString(_cursorIndexOfAttendeeName);
        final String _tmpResponse;
        _tmpResponse = _cursor.getString(_cursorIndexOfResponse);
        _item = new Attendee(_tmpAttendeeId,_tmpEventId,_tmpAttendeeName,_tmpResponse);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
