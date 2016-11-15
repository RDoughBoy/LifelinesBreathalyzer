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

    public static final String TABLE_USERS = "USERS";
    public static final String ROWID = "ID";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_EMAIL = "EMAIL";
    public static final String KEY_PASSWORD = "PASSWORD";
    public static final String KEY_CITY = "CITY";
    public static final String KEY_PROVINCE = "PROVINCE";
    public static final String KEY_LICENCE = "LICENCE";
    public static final String KEY_ADDRESS = "ADDRESS";
    public static final String KEY_PHONE = "PHONE";

    public static final String TABLE_DATA = "DATA";
    public static final String KEY_PASTBAC = "PASTBAC";
    public static final String KEY_PASTBPM = "PASTBPM";

    private static final String DATABASE_NAME = "breathalyzer.db";
    private static final int DATABASE_VERSION = 3;
    SQLiteDatabase db;

    // Database creation sql statement
    private static final String DATABASE_CREATE_USERS = "CREATE TABLE USERS " +
            "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL, " +
            "EMAIL TEXT NOT NULL UNIQUE, PASSWORD TEXT NOT NULL, CITY TEXT NOT NULL, " +
            "PROVINCE TEXT NOT NULL, LICENCE TEXT NOT NULL, ADDRESS TEXT NOT NULL, PHONE INTEGER NOT NULL)";

    // table to store past records
    private static final String DATABASE_CREATE_DATA = "CREATE TABLE DATA " +
            "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, EMAIL TEXT NOT NULL UNIQUE, " +
            "PASTBAC STRING NULL, PASTBPM STRING NULL)";

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_USERS);
        db.execSQL(DATABASE_CREATE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBAdapter.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
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

    //---insert a patient into the database---
    public long insertUser(String name, String email, String password, String city, String province, String licence, String address, int phone_number)
    {
        db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_PASSWORD, password);
        initialValues.put(KEY_CITY, city);
        initialValues.put(KEY_PROVINCE, province);
        initialValues.put(KEY_LICENCE, licence);
        initialValues.put(KEY_ADDRESS, address);
        initialValues.put(KEY_PHONE, phone_number);
        insertData(email);
        return db.insert(TABLE_USERS, null, initialValues);
    }

    // insert user to data table
    public long insertData(String email){
        db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_EMAIL, email);
        return db.insert(TABLE_DATA, null, initialValues);
    }

    // add data to user
    public long addData(String email, String BAC, String BPM){
        db = this.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PASTBAC, BAC);
        initialValues.put(KEY_PASTBPM, BPM);
        return db.update(TABLE_DATA, initialValues, KEY_EMAIL + " = " + email, null);
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

    //---searches for a users login---
    public Cursor searchLogin(String email) throws SQLException
    {
        Cursor mCursor =
                db.rawQuery("SELECT " + KEY_EMAIL + ", " +  KEY_PASSWORD + " FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = ?", new String[] { email });
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

