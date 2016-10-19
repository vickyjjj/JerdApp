package app.com.example.victoriajuan.jerdapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static app.com.example.victoriajuan.jerdapp.R.id.gridview;

/**
 * Created by victoriajuan on 10/18/16.
 */

public class MainFragment extends Fragment {

    private CustomAdapter adapter;

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_main, container, false);

        adapter = new CustomAdapter(getActivity(), classTitles, weekHomework, hwIcons);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_classes);
        listView.setAdapter(adapter);

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(HelloGridView.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
