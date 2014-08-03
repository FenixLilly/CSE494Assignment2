/* Some code modified from example at http://hmkcode.com/android-simple-sqlite-database-tutorial/ */

package com.example.group1.assignment2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;              // Database Version
    private static final String DATABASE_NAME = "PatientDB";    // Database Name


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create patients table
        String CREATE_PATIENT_TABLE = "CREATE TABLE patients ( " +
                "name TEXT, " +
                "id INTEGER, " +
                "age INTEGER, " +
                "sex TEXT )";

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


    /**
     * CRUD operations (create "add", read "get", update, delete) patient + get all patients
     * + delete all patients
     */

    // Patients table name
    private static final String TABLE_PATIENTS = "patients";

    // Patients Table Column names
    private static final String KEY_NAME = "name";
    private static final String KEY_ID = "id";
    private static final String KEY_AGE = "age";
    private static final String KEY_SEX = "sex";

    private static final String[] COLUMNS = {KEY_NAME,KEY_ID,KEY_AGE,KEY_SEX};


    public void addPatient(Patient patient){
        //for logging
        Log.d("addPatient", patient.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, patient.getName());
        values.put(KEY_ID, patient.getID());
        values.put(KEY_AGE, patient.getAge());
        values.put(KEY_SEX, patient.getSex());

        // 3. insert
        /*db.insert(TABLE_PATIENTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values*/

        db.insert(TABLE_PATIENTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values


        // 4. close
        db.close();
    }

    public Patient getPatient(int id){

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

         Cursor cursor = db.query(TABLE_PATIENTS, // a. table
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
        Patient patient = new Patient();
        patient.setName(cursor.getString(0));
        patient.setID(Integer.parseInt(cursor.getString(1)));
        patient.setAge(Integer.parseInt(cursor.getString(2)));
        patient.setSex(cursor.getString(3));

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
                patient.setName(cursor.getString(0));
                patient.setID(Integer.parseInt(cursor.getString(1)));
                patient.setAge(Integer.parseInt(cursor.getString(2)));
                patient.setSex(cursor.getString(3));

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
        values.put(KEY_NAME, patient.getName());
        values.put(KEY_ID, patient.getID());
        values.put(KEY_AGE, patient.getAge());
        values.put(KEY_SEX, patient.getSex());

        // 3. updating row
        int i = db.update(TABLE_PATIENTS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(patient.getID()) }); //selection args

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
                new String[] { String.valueOf(patient.getID()) });

        // 3. close
        db.close();

        Log.d("deletePatient", patient.toString());

    }

}
