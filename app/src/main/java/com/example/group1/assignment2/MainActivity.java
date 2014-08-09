package com.example.group1.assignment2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


public class MainActivity extends Activity implements SensorEventListener {

    Button runBtn;
    EditText etID;
    EditText etAge;
    EditText etName;
    RadioGroup gender;

    Intent intRun;

    String Name, ID, Age, Sex, tableName;

    private SensorManager mySensorManager;
    private Sensor myAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        // invoke and register accelerometer sensor
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        MySQLiteHelper db = new MySQLiteHelper(this);

        runBtn  = (Button) findViewById(R.id.button1);
        etID = (EditText) findViewById(R.id.editText1);
        etAge = (EditText) findViewById(R.id.editText2);
        etName = (EditText) findViewById(R.id.editText3);
        gender = (RadioGroup) findViewById(R.id.radioGroup1);

        runBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callRunIntent();
            }
        });

        /**
         * CRUD Operations
         * */
        // add Patients
//        db.addData(objectEventData);
        //db.addPatient(new Patient("Katie Smith", 2, 27, "Female"));

        // get all patients
//        List<Patient> list = db.getAllPatients();


        // delete one patient
//      db.deletePatient(list.get(0));

        // get all patients
//        db.getAllPatients();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long currentTime = System.currentTimeMillis();

            if ((currentTime - lastUpdate) > 1000) {
                long diffTime = currentTime - lastUpdate;
                lastUpdate = currentTime;

                float speed = Math.abs( x + y + z - last_x - last_y - last_z )/ diffTime * 10000;

                if ( speed > SHAKE_THRESHOLD) {

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener(this, myAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void callRunIntent(){
        intRun = new Intent(this, RunActivity.class);
        intRun.putExtra("ID", etID.getText().toString());
        intRun.putExtra("age", etAge.getText().toString());
        intRun.putExtra("name", etName.getText().toString());
        onCheckedChanged(gender, gender.getCheckedRadioButtonId());

//        callCreatePatientTable(db);

        startActivity(intRun);
    }

    /*Called when the RunActivity is invoked. When the selection is cleared,
    checkedId is -1.*/
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton1:
                //intRun.putExtra("radioGroup1", "male");
                intRun.putExtra("radioGroup1", group.getCheckedRadioButtonId());
                break;
            case R.id.radioButton2:
                //intRun.putExtra("radioGroup1", "female");
                intRun.putExtra("radioGroup1", group.getCheckedRadioButtonId());
                break;
        }
    }

    public void callCreatePatientTable(MySQLiteHelper db){
        Name = etName.getText().toString();
        ID = etID.getText().toString();
        Age = etAge.getText().toString();
        Sex = "";
        tableName = Name + "_" + ID + "_" + Age + "_" + Sex;
        db.setTableName(tableName);

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
