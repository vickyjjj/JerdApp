package app.com.example.victoriajuan.jerdapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by victoriajuan on 10/12/16.
 */

public class ThirdFragment extends Fragment{

    View myView;
    private CustomAdapter mFilesAdapter;
    private String selectedProject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        List<String> itemNames = new ArrayList<String>();

        myView = inflater.inflate(R.layout.third_layout, container, false);

        File newPath = new File(getActivity().getFilesDir().getAbsolutePath() + "/imported/");
        if (!newPath.exists()) {
            newPath.mkdir();
        }

        File[] projectNames = newPath.listFiles();

        for (int i = 0; i < projectNames.length; i++) {
            itemNames.add(projectNames[i].getName());
        }

        ListView listView = (ListView) myView.findViewById(R.id.listview_shared_projects);
        mFilesAdapter = new CustomAdapter(getActivity(), itemNames);
        listView.setAdapter(mFilesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = mFilesAdapter.getTitle(i);
                SaveSharedPreference.setFileDir(getActivity(), getActivity().getFilesDir().getAbsolutePath() + "/imported/" + str);
                Intent nextActivity = new Intent(getActivity(), DisplayFileActivity.class);
                startActivity(nextActivity);
            }
        });

        return myView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        final String[] filename = new String[2];

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_third);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Name of file to download:");

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filename[0] = input.getText().toString();
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://jerd-43491.appspot.com/");
                        StorageReference pathReference = storageRef.child(filename[0]);

                        if (filename[0].contains("png")) {
                            filename[1] = ".png";
                        } else if (filename[0].contains("3gp")) {
                            filename[1] = ".3gp";
                        } else {
                            filename[1] = ".txt";
                        }

                        try {
                            File newDirectory = new File(getActivity().getFilesDir().getAbsolutePath() + "/imported/");
                            File localFile = File.createTempFile(filename[0].substring(0, filename[0].length() - 4), filename[1], newDirectory);
                            mFilesAdapter.add(localFile.getName());
                            pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getActivity(), "Files downloaded.", Toast.LENGTH_LONG).show();
                                    mFilesAdapter.notifyDataSetChanged();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(getActivity(), "Files not downloaded. Check that you have entered the name of the file correctly.", Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch(IOException e) { }


                        dialog.dismiss();
                    }
                });
                builder.show();
            }

        });

    }
}
