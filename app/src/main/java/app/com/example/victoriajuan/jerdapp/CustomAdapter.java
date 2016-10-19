package app.com.example.victoriajuan.jerdapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.com.example.victoriajuan.jerdapp.R;

public class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> itemTitle;
    private final List<String> itemDescr;
    private final List<Integer> imgid;

    public CustomAdapter(Activity context, List<String> itemname, List<String> itemDescr, List<Integer> imgid) {
        super(context, R.layout.list_item, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemTitle = itemname;
        this.imgid = imgid;
        this.itemDescr = itemDescr;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.list_item_forecast_textview);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.list_item_forecast_textview2);

        txtTitle.setText(itemTitle.get(position));
        imageView.setImageResource(imgid.get(position));
        extratxt.setText(itemDescr.get(position));
        return rowView;

    }


    public void clear() {
        itemTitle.clear();
        itemDescr.clear();
    }

    public void add(String title, String descr) {
        itemTitle.add(title);
        itemDescr.add(descr);
    }



}