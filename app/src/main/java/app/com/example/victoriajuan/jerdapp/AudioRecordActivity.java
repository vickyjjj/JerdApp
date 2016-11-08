package app.com.example.victoriajuan.jerdapp;

import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by victoriajuan on 10/22/16.
 */

public class AudioRecordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String selectedProject;
    private MediaRecorder mAudioRecorder;
    private String outputFile;
    private Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.audio_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        timer = (Chronometer) findViewById(R.id.chronometer);

        Spinner spinner = (Spinner) findViewById(R.id.audio_spinner);

        File[] projectNames = AudioRecordActivity.this.getFilesDir().listFiles();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AudioRecordActivity.this, android.R.layout.simple_spinner_item, new ArrayList<String>());

        for (int i = 0; i < projectNames.length; i++) {
            if (!(projectNames[i].getName().equals("imported")))
                dataAdapter.add(projectNames[i].getName());
        }

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(AudioRecordActivity.this);

        final Button startRecording = (Button) findViewById(R.id.start_recording);
        final Button endRecording = (Button) findViewById(R.id.end_recording);

        startRecording.setEnabled(true);
        endRecording.setEnabled(false);

        startRecording.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startRecording();
                startRecording.setEnabled(false);
                endRecording.setEnabled(true);
            }
        });

        endRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endRecording();
                endRecording.setEnabled(false);
                startRecording.setEnabled(true);
                timer.stop();
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedProject = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        selectedProject = "Uncategorized";
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startRecording() {

        final String[] filename = new String[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(AudioRecordActivity.this);
        builder.setTitle("Title your recording:");

        final EditText audInput = new EditText(AudioRecordActivity.this);
        audInput.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(audInput);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());

                filename[0] = audInput.getText().toString() + "_" + formattedDate;

                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();

                dialog.dismiss();

                mAudioRecorder = new MediaRecorder();
                mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                outputFile = getFilesDir().getAbsolutePath() + "/" + selectedProject + "/" + filename[0] + ".3gp";
                mAudioRecorder.setOutputFile(outputFile);

                try {
                    mAudioRecorder.prepare();
                    mAudioRecorder.start();
                }

                catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();

    }

    private void endRecording() {
        mAudioRecorder.stop();
        mAudioRecorder.release();
        mAudioRecorder  = null;

        Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
        finish();
    }


}
