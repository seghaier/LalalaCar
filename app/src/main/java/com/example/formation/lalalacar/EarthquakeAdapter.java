package com.example.formation.lalalacar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Formation on 22/06/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    //orders est la liste des models à afficher
    public EarthquakeAdapter(Context context, List<Earthquake> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake,parent, false);
        }

        ListViewHolder viewHolder = (ListViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ListViewHolder();
            viewHolder.mag = (TextView) convertView.findViewById(R.id.mag);
            viewHolder.distance = (TextView) convertView.findViewById(R.id.distance);
            viewHolder.direction = (TextView) convertView.findViewById(R.id.direction);
            viewHolder.place = (TextView) convertView.findViewById(R.id.date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.heure);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Earthquake earthquake = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.mag.setText(earthquake.getMag());
        viewHolder.distance.setText(earthquake.getDistance());
        viewHolder.direction.setText(earthquake.getDirection());
        viewHolder.place.setText(earthquake.getDate());
        viewHolder.time.setText(earthquake.getTime());

        return convertView;
    }

    private class ListViewHolder {
        public TextView mag, distance, direction, place, time, url;

    }

}
