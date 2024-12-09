package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    private final Context context;
    private final List<Item> items;

    public GridAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        Item item = items.get(position);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView text1 = convertView.findViewById(R.id.item_text1);
        TextView text2 = convertView.findViewById(R.id.item_text2);
        TextView text3 = convertView.findViewById(R.id.item_text3);

        imageView.setImageResource(item.getImage());
        text1.setText(item.getText1());
        text2.setText(item.getText2());
        text3.setText(item.getText3());

        return convertView;
    }
}

