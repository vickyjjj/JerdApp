package app.com.example.victoriajuan.jerdapp;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.File;

/**
 * Created by user on 12/31/15.
 */
public class FirstFragment extends Fragment{

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.first_layout, container, false);
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
    }
}
