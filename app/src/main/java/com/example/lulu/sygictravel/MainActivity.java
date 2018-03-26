package com.example.lulu.sygictravel;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


import com.sygic.travel.sdk.model.geo.Location;
import com.sygic.travel.sdk.model.place.Place;



public class MainActivity extends AppCompatActivity {

    final String API_KEY = "wdf2THo7el503MNwpVxH2o6sQkc9Yu8a6xtI0mB9";
    final String API_URL = "https://api.sygictravelapi.com/1.0/{lang}";
    String test;

    protected TextView text1;
    protected ListView list1;
    protected float lat;
    protected float lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.text1);

        //list1=(ListView) findViewById(R.id.list1);

        String name;

        Place place= new Place();
        Location location = new Location();
        //lat = (float) 51.5007972;
        //lng = (float) -0.1425249;
        //location.setLat(lat);
        //location.setLng(lng);
        place.setId("poi:440");
        place.setLevel("POI");
        place.setUrl("https://travel.sygic.com/go/poi:440");
        name =place.getId();
        //location = place.getLocation();

        //lat = location.getLat();
        //name = lat;
        text1.setText(name);

    }


}
