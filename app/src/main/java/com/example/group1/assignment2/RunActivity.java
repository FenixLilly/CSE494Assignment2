package com.example.group1.assignment2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class RunActivity extends MainActivity {

    Button runBtn;
    Button stopBtn;
    EditText etID;
    EditText etAge;
    EditText etName;
    RadioGroup gender;
    int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_run);

        stopBtn = (Button) findViewById(R.id.button3 );
        Intent intent = getIntent();

        String ID = intent.getStringExtra("ID");
        String age = intent.getStringExtra("age");
        String name = intent.getStringExtra("name");
        int rGroup = intent.getIntExtra("radioGroup1", -1);

        etID = (EditText) findViewById(R.id.editText1);
        etAge = (EditText) findViewById(R.id.editText2);
        etName = (EditText) findViewById(R.id.editText3);
        gender = (RadioGroup) findViewById(R.id.radioGroup1);

        etID.setText(ID);
        etAge.setText(age);
        etName.setText(name);
        gender.check(rGroup);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStopIntent();
            }
        });

    }

    public void callStopIntent(){
        Intent intMain = new Intent(this,MainActivity.class);
        startActivity(intMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.run, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_run, container, false);
            return rootView;
        }
    }
}
