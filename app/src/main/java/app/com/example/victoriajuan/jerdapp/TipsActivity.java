package app.com.example.victoriajuan.jerdapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by victoriajuan on 10/22/16.
 */

public class TipsActivity extends AppCompatActivity {

    private ArrayAdapter<String> mTipsAdapter;
    final String[] filename = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tips_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTipsAdapter = new ArrayAdapter<String>(
                TipsActivity.this,
                R.layout.list_item,
                R.id.list_item,
                new ArrayList<String>()
        );

        ListView listView = (ListView) findViewById(R.id.listview_tips);
        listView.setAdapter(mTipsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String[] str = new String[1];
                str[0] = mTipsAdapter.getItem(i);
                AlertDialog alertDialog = new AlertDialog.Builder(TipsActivity.this).create();
                alertDialog.setTitle("Delete this question?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mTipsAdapter.remove(str[0]);
                                mTipsAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_first);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TipsActivity.this);
                builder.setTitle("Create new interview question:");

                final EditText input = new EditText(TipsActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTipsAdapter.add(input.getText().toString());
                        mTipsAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.show();
            }

        });

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
