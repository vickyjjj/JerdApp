package app.com.example.victoriajuan.jerdapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victoriajuan on 11/11/16.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<Integer> itemImg;
    private final List<String> itemName;

    public CustomAdapter(Activity context, List<String> itemname) {
        super(context, R.layout.list_item, itemname);

        this.context = context;
        this.itemName = itemname;

        List<Integer> images = new ArrayList<Integer>();

        for (int rep = 0; rep < itemName.size(); rep++) {
            String str = itemName.get(0);
            if (str.contains(".3gp")) {
                images.add(R.drawable.side_nav_bar);
            } else if (str.contains(".txt")) {
                images.add(R.drawable.ic_notes);
            } else if (str.contains(".png")) {
                images.add(R.drawable.ic_camera);
            } else {
                images.add(R.drawable.ic_folder);
            }
        }

        this.itemImg = images;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.list_item_textview);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon_list);

        txtTitle.setText(itemName.get(position));
        imageView.setImageResource(itemImg.get(position));
        return rowView;

    }

    public void clear() {
        itemImg.clear();
        itemName.clear();
    }

    public String getTitle(int position)
    {
        return itemName.get(position);
    }

    public void add(String title) {
        itemName.add(title);
        if (title.contains(".3gp")) {
            itemImg.add(R.drawable.side_nav_bar);
        } else if (title.contains(".txt")) {
            itemImg.add(R.drawable.ic_notes);
        } else if (title.contains(".png")) {
            itemImg.add(R.drawable.ic_camera);
        } else {
            itemImg.add(R.drawable.ic_menu_slideshow);
        }
    }
}
