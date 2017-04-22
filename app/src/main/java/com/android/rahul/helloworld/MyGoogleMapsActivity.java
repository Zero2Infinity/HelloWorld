package com.android.rahul.helloworld;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class MyGoogleMapsActivity extends FragmentActivity implements
        MyListFragment.OnListFragmentInteractionListener, MyGoogleMapsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(MyGoogleMapsActivity.class.toString(), "Inside MyGoogleMapsActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (findViewById(R.id.gMapContainer) != null && findViewById(R.id.listFragment) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // Gather all image data and send it respective fragment(s)
            ImagesData imgs = new ImagesData();
            imgs.addData("1", 35.803601, -78.623092, "Raleigh", "00000");
            imgs.addData("2", 40.722740, -73.995178, "New York City", "00000");
            imgs.addData("3", 32.800388, -96.793146, "Dallas", "75275");
            imgs.addData("4", 33.600522, -112.141873, "Phoenix", "00000");

            MyGoogleMapsFragment myMap = MyGoogleMapsFragment.newInstance(imgs);
            MyListFragment myList = MyListFragment.newInstance(imgs);


            // Add the fragment to the 'fragment_container' FrameLayout
            FragmentTransaction fT = getSupportFragmentManager().beginTransaction();
            fT.add(R.id.gMapContainer, myMap);
            fT.add(R.id.listFragment, myList);
            fT.commit();
        }
    }

    public void onListItemSelected(int position) {
        MyGoogleMapsFragment mapFragment =
                (MyGoogleMapsFragment) getSupportFragmentManager().findFragmentById(R.id.gMapContainer);

        if (mapFragment != null) {
            mapFragment.moveCamera(position);
        }
    }

}
