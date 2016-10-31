package app.com.example.victoriajuan.jerdapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by victoriajuan on 10/30/16.
 */

public class DisplayFileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notes_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView img = (ImageView) findViewById(R.id.display_img);
        TextView txt = (TextView) findViewById(R.id.display_notes);
        Button playButton = (Button) findViewById(R.id.play_recording);
        Button pauseButton = (Button) findViewById(R.id.pause_recording);

        String fileType = SaveSharedPreference.getFileType(DisplayFileActivity.this);

        if (fileType.equals("img")) {
            txt.setVisibility(View.GONE);
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.GONE);
        } else if (fileType.equals("aud")) {
            txt.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
        } else {
            img.setVisibility(View.GONE);
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.GONE);
        }
    }
}
