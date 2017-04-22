package com.android.rahul.helloworld;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
// import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    private static boolean ALLOWED_READ_EXTERNAL_STORAGE = false;

    private void requestUserPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(MainActivity.class.toString(), "Requesting a permission to READ_EXTERNAL_STORAGE");
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.i(MainActivity.class.toString(), "Show an explanation to the user *asynchronously*");
                Toast.makeText(this, "External Storage Permission required to read images", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_READ_EXTERNAL_STORAGE);
        } else {
            ALLOWED_READ_EXTERNAL_STORAGE = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // displaySelectedImageButtonHandler();
                    ALLOWED_READ_EXTERNAL_STORAGE = true;
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    Toast.makeText(this, "Access read external storage permission denied!", Toast.LENGTH_SHORT).show();
                    ALLOWED_READ_EXTERNAL_STORAGE = false;
                }
               return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make sure if we have all the necessary permissions to start all activities
        requestUserPermission();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button btnImageInfo = (Button) findViewById(R.id.btnImageInfo);
        btnImageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySelectedImageButtonHandler();
            }
        });

        Button btnViewImgGallery = (Button) findViewById(R.id.btnViewImgGallery);
        btnViewImgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayGalleryViewHandler();
            }
        });

        Button btnMaps = (Button) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMapsViewHandler();
            }
        });

        Button btnMyMapActivity = (Button) findViewById(R.id.btnMyGoogleMaps);
        btnMyMapActivity.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {
               displayMyGoogleMapsViewHandler();
           }
        });
    }

    private void displaySelectedImageButtonHandler() {
        Intent displaySelectedImage = new Intent(getApplicationContext(), DisplaySelectedImageActivity.class);
        if (ALLOWED_READ_EXTERNAL_STORAGE)
            startActivity(displaySelectedImage);
        else
            Toast.makeText(this, "Access read external storage permission denied!", Toast.LENGTH_SHORT).show();
    }

    private void displayGalleryViewHandler() {
        Intent displayGalleryView = new Intent(getApplicationContext(), ImageGalleryViewActivity.class);
        if (ALLOWED_READ_EXTERNAL_STORAGE)
            startActivity(displayGalleryView);
        else
            Toast.makeText(this, "Access read external storage permission denied!", Toast.LENGTH_SHORT).show();
    }

    private void displayMapsViewHandler() {
        Intent displayMapsView = new Intent(getApplicationContext(), GoogleMapsFragmentActivity.class);
            startActivity(displayMapsView);
    }

    private void displayMyGoogleMapsViewHandler() {
        Intent displayMyGoogleMapsView = new Intent(getApplicationContext(), MyGoogleMapsActivity.class);
        startActivity(displayMyGoogleMapsView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
