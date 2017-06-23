package com.example.formation.lalalacar;



import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.order;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;



    // URL to get contacts JSON
    private static String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-06-19&endtime=2017-06-20";

    List<Earthquake> earthquakeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        earthquakeList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetEarthquakes().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetEarthquakes extends AsyncTask<Void, Void, Void> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray features = jsonObj.getJSONArray("features");

                    // looping through All Contacts
                    for (int i = 0; i < features.length(); i++) {
                        JSONObject eq = features.getJSONObject(i);

                        // Phone node is JSON Object
                       JSONObject properties = eq.getJSONObject("properties");
                        String mag = properties.getString("mag");
                        String distance = properties.getString("place");
                        String direction = properties.getString("place");
                        String date = properties.getString("time");
                        String heure;
                        String lien = properties.getString("url");

                        // Convert timestamp to date and time format
                        Long timeConvert = Long.parseLong(date);
                        date = new SimpleDateFormat("dd MMMM yyyy").format(timeConvert);
                        heure = new SimpleDateFormat("hh:mm:ss a").format(timeConvert);

                        Earthquake quake = new Earthquake(mag,distance,direction,date,heure,lien);
                        earthquakeList.add(quake);
//                        // tmp hash map for single contact
//                        HashMap<String, String> erthquake = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
//                        erthquake.put("mag", mag);
//                        erthquake.put("distance", distance);
//                        erthquake.put("direction", direction);
//                        erthquake.put("date", date);
//                        erthquake.put("heure", heure);
//                        erthquake.put("lien", lien);
//
//                        // adding contact to contact list
//                        earthquakeList.add(erthquake);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
//            ListAdapter adapter = new SimpleAdapter(
//                    MainActivity.this, earthquakeList,
//                    R.layout.earthquake, new String[]{"mag", "distance","direction","date","heure"},
//                    new int[]{R.id.mag, R.id.distance, R.id.direction, R.id.date, R.id.heure});
//            lv.setAdapter(adapter);



            final EarthquakeAdapter adapter = new EarthquakeAdapter(MainActivity.this, earthquakeList);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View view, int position, long id) {

                    Earthquake eq = earthquakeList.get(position);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(eq.getUrl()));
                    startActivity(intent);

                }
            });


        }

    }
}
