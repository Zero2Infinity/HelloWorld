package com.android.rahul.helloworld;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


public class MyGoogleMapsFragment extends Fragment implements
        OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String IMAGESDATA_KEY = "imagesData";
    private ImagesData mImagesData;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 2;
    private Location mLastLocation;

    private OnFragmentInteractionListener mListener;


    public MyGoogleMapsFragment() {
        // Required empty public constructor
    }

    public static MyGoogleMapsFragment newInstance(ImagesData imgs) {
        MyGoogleMapsFragment fragment = new MyGoogleMapsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IMAGESDATA_KEY, imgs);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Needs to create an instance of the Google Play Services API Client
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_google_maps, container, false);

        // Wait for inflater to inflate fragment then only I can find gMap id.
        SupportMapFragment mapFragment =
                (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.gMap);
        mapFragment.getMapAsync(this);

        if (getArguments() != null)
            mImagesData = (ImagesData) getArguments().getSerializable(IMAGESDATA_KEY);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
        } else {
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setMyLocationEnabled(true);
            // [TODO] Do something from this point onwards ....
            showAllMarkers();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);

                // [TODO] Do something from this point onwards ....
                showAllMarkers();

                return;
            }
        } else {
            Toast.makeText(this.getContext(), "Access current location permission denied!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(this.getClass().toString(), "onConnected called");
        if (ContextCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (mLastLocation != null) {
            Log.i(this.getClass().toString(), "MyLocation - [" + String.valueOf(mLastLocation.getLatitude()) + "] [" + String.valueOf(mLastLocation.getLongitude()) + "]");
        }
    }

    @Override
    public void onConnectionSuspended(int x) {
        // Nothing yet!
    }

    @Override
    public void onConnectionFailed(ConnectionResult cr) {
        // Nothing yet!
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        requestLocationPermission();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    // This method receive all GPS related info and able to render it on Google Maps
    private void showAllMarkers() {
        Log.i(GoogleMapsFragmentActivity.class.toString(), "Map - isMyLocationEnabled() = " + mMap.isMyLocationEnabled());

        for (int i=0; i<mImagesData.count(); i++) {
            ImageLocationData location = mImagesData.getAt(i).getValue().getImageLocationData();
            mMap.addMarker( new MarkerOptions().position(location.getLatLng()).icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN) ));
        }
    }

    public interface OnFragmentInteractionListener {
        // void onFragmentInteraction(Uri uri);
    }

    public void moveCamera(int position) {
        // Toast.makeText(getContext(), "Position=" + position, Toast.LENGTH_SHORT).show();
        ImageLocationData loc = mImagesData.getAt(position).getValue().getImageLocationData();
        LatLngBounds.Builder pointsBuilder = new LatLngBounds.Builder();
        pointsBuilder.include(loc.getLatLng());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc.getLatLng(), 10));
    }
}
