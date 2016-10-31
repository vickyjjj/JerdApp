package app.com.example.victoriajuan.jerdapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by victoriajuan on 10/30/16.
 */

public class DisplayFileActivity extends AppCompatActivity {

    private String fileDir;
    private ImageView img;
    private TextView txt;
    private Button playButton;
    private Button pauseButton;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.display_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        img = (ImageView) findViewById(R.id.display_img);
        txt = (TextView) findViewById(R.id.display_notes);
        playButton = (Button) findViewById(R.id.play_recording);
        pauseButton = (Button) findViewById(R.id.pause_recording);

        fileDir = SaveSharedPreference.getFileDir(DisplayFileActivity.this);

        if (fileDir.contains("png")) {
            img.setVisibility(View.VISIBLE);
            txt.setVisibility(View.GONE);
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.GONE);
            displayImage();
        } else if (fileDir.contains("3gp")) {
            txt.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
            playButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            displayAudio();
        } else {
            img.setVisibility(View.GONE);
            txt.setVisibility(View.VISIBLE);
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.GONE);
            displayNote();
        }
    }

    private void displayImage() {

        try {
            File f = new File(fileDir);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void displayAudio() {
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(fileDir);
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                }
                playButton.setEnabled(false);
                pauseButton.setEnabled(true);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.release();
                mPlayer = null;
                playButton.setEnabled(false);
                pauseButton.setEnabled(true);
            }
        });
    }

    private void displayNote() {

        try {
            FileInputStream fin = new FileInputStream(new File(fileDir));
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            txt.setText(temp);
            fin.close();
        } catch (FileNotFoundException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        }

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
