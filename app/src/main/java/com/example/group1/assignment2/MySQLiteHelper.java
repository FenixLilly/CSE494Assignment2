package com.example.group1.assignment2;

/* Some code modified from example at http://hmkcode.com/android-simple-sqlite-database-tutorial/ */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // database version
    private static final int DATABASE_VERSION = 1;
    // database name
    private static final String DATABASE_NAME = "EventDB";

    // Table Name
    private static String TABLE_NAME = "";

    // Patients Table Column names
    private static final String KEY_ID = "id";
    private static final String COLUMN_TIME = "timestamp";
    private static final String COLUMN_X = "x";
    private static final String COLUMN_Y = "y";
    private static final String COLUMN_Z = "z";

    private static final String[] COLUMNS = {KEY_ID, COLUMN_X, COLUMN_Y, COLUMN_Z};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create patients table
        String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY, "
        + COLUMN_TIME + " TEXT, " + COLUMN_X + " INTEGER, " + COLUMN_Y + " INTEGER, " + COLUMN_Z + " INTEGER );";

        // create patients table
        db.execSQL(CREATE_PATIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older patients table if existed
        db.execSQL("DROP TABLE IF EXISTS patients");

        // create fresh patients table
        this.onCreate(db);
    }

    public void setTableName (String tableName){
        TABLE_NAME = tableName;
    }

    public void createPatientTable(SQLiteDatabase db, String tableName) {

        setTableName(tableName);

        String CREATE_PATIENT_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_TIME + " TEXT, " + COLUMN_X + " INTEGER, " + COLUMN_Y + " INTEGER, " + COLUMN_Z + " INTEGER );";

        db.execSQL(CREATE_PATIENT_TABLE);
    }



    /**
     * CRUD operations (create "add", read "get", update, delete) patient + get all patients
     * + delete all patients
     */


    public void addData(ObjectEventData objectEventData){
        //for logging
        Log.d("adding Data", objectEventData.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, objectEventData.getId());
        values.put(COLUMN_TIME, objectEventData.getTimeStamp());
        values.put(COLUMN_X, objectEventData.getX());
        values.put(COLUMN_Y, objectEventData.getY());
        values.put(COLUMN_Z, objectEventData.getZ());


        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ObjectEventData getEventData(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        /*Cursor cursor =
                db.query(TABLE_PATIENTS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit*/

         Cursor cursor = db.query(TABLE_NAME, // a. table
                 COLUMNS, // b. column names
                 " id = ?", // c. selections
                 new String[] { String.valueOf(id) }, // d. selections args
                 null, // e. group by
                 null, // f. having
                 null, // g. order by
                 null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build patient object
        ObjectEventData objectEventData = new ObjectEventData();
        objectEventData.setId(Integer.parseInt(cursor.getString(0)));
        objectEventData.setTimeStamp(String.toString(cursor.getString(1)));
        objectEventData.setX(Integer.parseInt(cursor.getString(1)));
        objectEventData.setY(Integer.parseInt(cursor.getString(2)));
        objectEventData.setZ(Integer.parseInt(cursor.getString(3)));

        //log
        Log.d("getPatient(" + id + ")", patient.toString());

        // 5. return patient
        return patient;
    }

    // Get All Patients
    public List<Patient> getAllPatients() {
        List<Patient> patients = new LinkedList<Patient>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PATIENTS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build patient and add it to list
        Patient patient = null;
        if (cursor.moveToFirst()) {
            do {
                patient = new Patient();
                patient.setId(Integer.parseInt(cursor.getString(0)));
                patient.setX(Integer.parseInt(cursor.getString(1)));
                patient.setY(Integer.parseInt(cursor.getString(2)));
                patient.setZ(Integer.parseInt(cursor.getString(3)));

                // Add patient to patients
                patients.add(patient);
            } while (cursor.moveToNext());
        }

        Log.d("getAllPatients()", patients.toString());

        // return patients
        return patients;
    }

    // Updating single patient
    public int updatePatient(Patient patient) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ID, patient.getId());
        values.put(KEY_X, patient.getX());
        values.put(KEY_X, patient.getY());
        values.put(KEY_X, patient.getZ());

        // 3. updating row
        int i = db.update(TABLE_PATIENTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(patient.getId()) }); //selection args

        // 4. close
        db.close();
        return i;
    }

    // Deleting single patient
    public void deletePatient(Patient patient) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_PATIENTS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(patient.getId()) });

        // 3. close
        db.close();

        Log.d("deletePatient", patient.toString());

    }

}
