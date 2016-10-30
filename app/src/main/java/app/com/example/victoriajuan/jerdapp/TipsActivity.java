package app.com.example.victoriajuan.jerdapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victoriajuan on 10/22/16.
 */

public class TipsActivity extends AppCompatActivity {

    private ArrayAdapter<String> mTipsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tips_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        List<String> tips = new ArrayList<String>();
        tips.add("1. Start off with introductions. Mention your name, offer a smile, then ask what their name/age/position is. Make sure to double check spelling.");
        tips.add("2. Begin with the lighter questions first. Who was involved? What was done? When and where?");
        tips.add("3. Vary your question beginnings: Tell me about.... What were you feeling when.... What was it like....");
        tips.add("4. Ask the more difficult questions. How did that happen? Why did you do that? What is the meaning?");
        tips.add("5. Always look sincerely at your subject. Ask follow up questions for more information or clarification.");
        tips.add("6. End with thoughts on the future. What do you look forward to? How will this change in the next months?");
        tips.add("7. Give your interview subject a chance for 'any last thoughts.' Get contact info and say thanks.");

        mTipsAdapter = new ArrayAdapter<String>(
                TipsActivity.this,
                R.layout.list_item,
                R.id.list_item,
                tips
        );

        ListView listView = (ListView) findViewById(R.id.listview_tips);
        listView.setAdapter(mTipsAdapter);

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
