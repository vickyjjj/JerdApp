package app.com.example.victoriajuan.jerdapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by victoriajuan on 10/22/16.
 */

public class NotesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText writtenNote;
    private String selectedProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notes_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        writtenNote = (EditText) findViewById(R.id.input_notes);
        Button saveNotesButton = (Button) findViewById(R.id.save_notes);
        saveNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.notes_spinner);

        File[] projectNames = this.getFilesDir().listFiles();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());

        for (int i = 0; i < projectNames.length; i++) {
            dataAdapter.add(projectNames[i].getName());
        }

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(NotesActivity.this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedProject = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        selectedProject = "Uncategorized";
    }

    public void saveNote() {

        final String[] filename = new String[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title your notes:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());

                filename[0] = input.getText().toString() + "_" + formattedDate;

                try {
                    FileOutputStream outputStream;
                    String string = writtenNote.getText().toString();
                    File newFile = new File(NotesActivity.this.getFilesDir() + "/" + selectedProject + "/", filename[0].replaceAll("\\s+",""));
                    newFile.createNewFile();

                    outputStream = new FileOutputStream(newFile);
                    outputStream.write(string.getBytes());
                    outputStream.close();
                    Toast.makeText(NotesActivity.this, "Notes saved.", Toast.LENGTH_LONG).show();
                    finish();
                } catch (Exception e) {
                    Toast.makeText(NotesActivity.this, "Notes not saved. Check that your title does not contain punctuation.", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();
            }
        });
        builder.show();
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
