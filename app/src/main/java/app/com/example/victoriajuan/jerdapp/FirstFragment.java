package app.com.example.victoriajuan.jerdapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.io.File;
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

        File[] projectNames = getActivity().getFilesDir().listFiles();

        for (int i = 0; i < projectNames.length; i++) {
            mFilesAdapter.add(projectNames[i].getName());
        }

        ListView listView = (ListView) myView.findViewById(R.id.listview_local_projects);
        listView.setAdapter(mFilesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = mFilesAdapter.getItem(i);
                if (str.contains(".")) {
                    SaveSharedPreference.setFileDir(getActivity(), getActivity().getFilesDir().getAbsolutePath() + "/" + selectedProject + "/" + str);
                    Intent nextActivity = new Intent(getActivity(), DisplayFileActivity.class);
                    startActivity(nextActivity);
                } else {
                    selectedProject = str;
                    recreateView(str);
                }
            }
        });

        return myView;

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
