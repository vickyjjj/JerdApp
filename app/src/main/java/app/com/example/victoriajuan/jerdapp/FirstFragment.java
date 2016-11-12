package app.com.example.victoriajuan.jerdapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by user on 12/31/15.
 */

public class FirstFragment extends Fragment{

    View myView;
    private ArrayAdapter<String> mFilesAdapter;
    private String selectedProject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.first_layout, container, false);

        mFilesAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item,
                R.id.list_item,
                new ArrayList<String>()
        );
        //this is a totally rndom comment that does not mean anything

        File[] projectNames = getActivity().getFilesDir().listFiles();

        for (int i = 0; i < projectNames.length; i++) {
            if (!(projectNames[i].getName().equals("imported")))
                mFilesAdapter.add(projectNames[i].getName());
        }

        ListView listView = (ListView) myView.findViewById(R.id.listview_local_projects);
        listView.setAdapter(mFilesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = mFilesAdapter.getItem(i);
                if (str.contains(".")) {
                    smallFileAction(str);
                } else {
                    selectedProject = str;
                    recreateView(str);
                }
            }
        });

        return myView;

    }

    private void smallFileAction(String str) {
        final String[] fileName = new String[1];
        fileName[0] = str;
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("View or send?");
        alertDialog.setMessage("Do you want to send this file to your organization for source checking, or view it yourself?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Send",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://jerd-43491.appspot.com/");
                        StorageReference mountainsRef = storageRef.child(fileName[0]);

                        try {
                            InputStream stream = new FileInputStream(new File(getActivity().getFilesDir().getAbsolutePath() + "/" + selectedProject + "/" + fileName[0]));
                            UploadTask uploadTask = mountainsRef.putStream(stream);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    Toast.makeText(getActivity(), "Files not uploaded.", Toast.LENGTH_LONG).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    Toast.makeText(getActivity(), "Files uploaded.", Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (FileNotFoundException e) {}
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Display",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SaveSharedPreference.setFileDir(getActivity(), getActivity().getFilesDir().getAbsolutePath() + "/" + selectedProject + "/" + fileName[0]);
                        Intent nextActivity = new Intent(getActivity(), DisplayFileActivity.class);
                        startActivity(nextActivity);
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final String[] filename = new String[1];

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Create new project:");

                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filename[0] = input.getText().toString();
                        makeDirectory(filename[0]);
                        mFilesAdapter.add(filename[0]);
                        dialog.dismiss();
                    }
                });
                builder.show();
            }

        });

    }

    private void makeDirectory(String str) {
        File newFile = new File(getActivity().getFilesDir(), str);
        newFile.mkdir();
        Log.d("FirstFragment", getActivity().getFilesDir().getPath());
        Toast.makeText(getActivity(), str + " project created.", Toast.LENGTH_LONG).show();
    }

    private void recreateView(String str) {
        mFilesAdapter.clear();
        File newPath = new File(getActivity().getFilesDir().getAbsolutePath() + "/" + str + "/");
        File[] fileNames = newPath.listFiles();

        for (int i = 0; i < fileNames.length; i++) {
            mFilesAdapter.add(fileNames[i].getName());
        }
    }
}
