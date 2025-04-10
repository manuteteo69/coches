package com.example.coches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    private Context context;
    private List<CountryItem> list;

    public CountryAdapter(Context context, List<CountryItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }
    @Override
    public Object getItem(int position) { return list.get(position); }
    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }
        ImageView imgFlag = convertView.findViewById(R.id.img_flag);
        TextView txtCountry = convertView.findViewById(R.id.txt_country);

        CountryItem item = list.get(position);
        imgFlag.setImageResource(item.flagRes);
        txtCountry.setText(item.countryName);

        return convertView;
    }

    // Para el dropdown, si quieres el mismo layout:
    // Vista del dropdown usando spinner_item.xml
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
        }
        ImageView imgFlag = convertView.findViewById(R.id.img_flag);
        TextView txtCountry = convertView.findViewById(R.id.txt_country);
        CountryItem item = list.get(position);
        imgFlag.setImageResource(item.flagRes);
        txtCountry.setText(item.countryName);
        return convertView;
    }
}
