/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter extends SQLiteOpenHelper {

    public static final String ROWID = "ID";
    public static final String TABLE_DATA = "DATA";
    public static final String KEY_PASTBAC = "PASTBAC";
    public static final String KEY_PASTBPM = "PASTBPM";

    private static final String DATABASE_NAME = "breathalyzer.db";
    private static final int DATABASE_VERSION = 3;
    SQLiteDatabase db;

        // table to store past records
    private static final String DATABASE_CREATE = "CREATE TABLE DATA " +
            "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "PASTBAC STRING NULL, PASTBPM STRING NULL)";

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBAdapter.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        onCreate(db);
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        close();
    }

    // add data to user
    public long addData(String BAC, String BPM){
        db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PASTBAC, BAC);
        initialValues.put(KEY_PASTBPM, BPM);
        return db.update(TABLE_DATA, initialValues, null, null);
    }

    //---retrieves past BAC---
    public Cursor getPastBAC() throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT " + KEY_PASTBAC + " FROM " + TABLE_DATA, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---retrieves past BPM---
    public Cursor getPastBPM() throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT " + KEY_PASTBPM + " FROM " + TABLE_DATA, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /*//---insert a test into the database---
    public long insertTest(int ID, String bp, int rr, int bo, int hr, String date)
    {
        db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_BP, bp);
        initialValues.put(KEY_RR, rr);
        initialValues.put(KEY_BO, bo);
        initialValues.put(KEY_HR, hr);
        initialValues.put(KEY_DATE, date);
        return db.update(TABLE_USERS, initialValues, ROWID + " = " + ID, null);
    }

    //---deletes a particular patient---
    public boolean deleteUser(long rowId)
    {
        return db.delete(TABLE_USERS, ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the patients---
    public Cursor getAllUsers() throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---filters department---
    public Cursor filterDept(String dept) throws SQLException
    {
        Cursor mCursor =
                db.rawQuery("SELECT * FROM " + TABLE_PATIENTS + " WHERE DEPARTMENT = " + "'" + dept + "'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---searches for a patient---
    public Cursor searchPatient(long UID) throws SQLException
    {
        Cursor mCursor =
                db.rawQuery("SELECT * FROM " + TABLE_PATIENTS + " WHERE HCID = " + UID, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---retrieves a particular contact---
    public Cursor getPatient(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.rawQuery("SELECT * FROM " + TABLE_PATIENTS + " WHERE ROWID = " + rowId, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }*/
}

