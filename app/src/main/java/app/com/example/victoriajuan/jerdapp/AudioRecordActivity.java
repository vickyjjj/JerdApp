package app.com.example.victoriajuan.jerdapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by victoriajuan on 10/22/16.
 */

public class AudioRecordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String selectedProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.audio_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button startRecording = (Button) findViewById(R.id.start_recording);
        Button pauseRecording = (Button) findViewById(R.id.pause_recording);
        Button endRecording = (Button) findViewById(R.id.end_recording);

        endRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecording();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.audio_spinner);

        File[] projectNames = this.getFilesDir().listFiles();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());

        for (int i = 0; i < projectNames.length; i++) {
            dataAdapter.add(projectNames[i].getName());
        }

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(AudioRecordActivity.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedProject = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        selectedProject = "Uncategorized";
    }

    public void saveRecording() {

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
