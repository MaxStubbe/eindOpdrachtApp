package com.example.max.eindopdrachtprog4app.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.max.eindopdrachtprog4app.R;

import java.util.ArrayList;


public class FilmAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflator;
    private ArrayList FilmArrayList;

    public FilmAdapter(Context context, LayoutInflater layoutInflater, ArrayList<Film> filmArrayList) {
        mContext = context;
        mInflator = layoutInflater;
        FilmArrayList = filmArrayList;
    }

    @Override
    public int getCount() {
        int size = FilmArrayList.size();
        // Log.i("getCount()", "=" + size);
        return size;
    }

    @Override
    public Object getItem(int position) {
        return FilmArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            // Als convertView nog niet bestaat maken we een nieuwe aan.
            convertView = mInflator.inflate(R.layout.film_listview_row, null);

            viewHolder = new ViewHolder();
            viewHolder.FilmNaam = (TextView) convertView.findViewById(R.id.FilmtitelTextview);
            viewHolder.FilmBeschrijving = (TextView) convertView.findViewById(R.id.FilmBeschrijvingtextview);

            // Koppel de view aan de viewHolder
            convertView.setTag(viewHolder);
        } else {
            // Als de convertView wel bestaat gebruiken we die.
            viewHolder = (ViewHolder) convertView.getTag();
        }


        Film film = (Film) FilmArrayList.get(position);

        viewHolder.FilmNaam.setText(film.getTitle());
        viewHolder.FilmBeschrijving.setText(film.getDescription());


        return convertView;
    }

    // Holds all data to the view. Wordt evt. gerecycled door Android
    private static class ViewHolder {
        public TextView FilmNaam;
        public TextView FilmBeschrijving;
    }
}


