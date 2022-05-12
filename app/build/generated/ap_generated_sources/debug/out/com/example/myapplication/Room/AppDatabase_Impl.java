package com.example.myapplication.Room;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile EventDAO _eventDAO;

  private volatile AttendeeDAO _attendeeDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Event` (`eventId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `hostId` INTEGER NOT NULL, `title` TEXT, `description` TEXT, `type` TEXT, `address` TEXT, `locationName` TEXT, `startTime` TEXT, `endTime` TEXT, `date` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Attendee` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `eventId` INTEGER NOT NULL, `attendeeName` TEXT, `response` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '30e5b1304453dac2fbedd04ee6958258')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Event`");
        _db.execSQL("DROP TABLE IF EXISTS `Attendee`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsEvent = new HashMap<String, TableInfo.Column>(10);
        _columnsEvent.put("eventId", new TableInfo.Column("eventId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvent.put("hostId", new TableInfo.Column("hostId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvent.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvent.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvent.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvent.put("address", new TableInfo.Column("address", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvent.put("locationName", new TableInfo.Column("locationName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvent.put("startTime", new TableInfo.Column("startTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvent.put("endTime", new TableInfo.Column("endTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEvent.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEvent = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEvent = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEvent = new TableInfo("Event", _columnsEvent, _foreignKeysEvent, _indicesEvent);
        final TableInfo _existingEvent = TableInfo.read(_db, "Event");
        if (! _infoEvent.equals(_existingEvent)) {
          return new RoomOpenHelper.ValidationResult(false, "Event(com.example.myapplication.Room.Event).\n"
                  + " Expected:\n" + _infoEvent + "\n"
                  + " Found:\n" + _existingEvent);
        }
        final HashMap<String, TableInfo.Column> _columnsAttendee = new HashMap<String, TableInfo.Column>(4);
        _columnsAttendee.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendee.put("eventId", new TableInfo.Column("eventId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendee.put("attendeeName", new TableInfo.Column("attendeeName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendee.put("response", new TableInfo.Column("response", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAttendee = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAttendee = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAttendee = new TableInfo("Attendee", _columnsAttendee, _foreignKeysAttendee, _indicesAttendee);
        final TableInfo _existingAttendee = TableInfo.read(_db, "Attendee");
        if (! _infoAttendee.equals(_existingAttendee)) {
          return new RoomOpenHelper.ValidationResult(false, "Attendee(com.example.myapplication.Room.Attendee).\n"
                  + " Expected:\n" + _infoAttendee + "\n"
                  + " Found:\n" + _existingAttendee);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "30e5b1304453dac2fbedd04ee6958258", "e07823cbdca78033850323187c364ae3");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "Event","Attendee");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `Event`");
      _db.execSQL("DELETE FROM `Attendee`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public EventDAO eventDao() {
    if (_eventDAO != null) {
      return _eventDAO;
    } else {
      synchronized(this) {
        if(_eventDAO == null) {
          _eventDAO = new EventDAO_Impl(this);
        }
        return _eventDAO;
      }
    }
  }

  @Override
  public AttendeeDAO attendeeDAO() {
    if (_attendeeDAO != null) {
      return _attendeeDAO;
    } else {
      synchronized(this) {
        if(_attendeeDAO == null) {
          _attendeeDAO = new AttendeeDAO_Impl(this);
        }
        return _attendeeDAO;
      }
    }
  }
}
