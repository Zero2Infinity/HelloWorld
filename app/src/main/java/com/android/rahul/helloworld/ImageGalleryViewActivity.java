package com.android.rahul.helloworld;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

public class ImageGalleryViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image_gallery);
        long[] ids = searchAllImages();
        setupGridView(ids);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(this.toString(), " Inside onStart() ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(this.toString(), " Inside onStop() ");
    }

    private long[] searchAllImages()
    {
        Cursor cursor = MediaStore.Images.Thumbnails.query(
                getContentResolver(),
                MediaStore.Images.Thumbnails.getContentUri("external"),
                null );

        cursor.moveToFirst();
        int count = cursor.getCount();

        long[] imgIds = new long[count];

        Log.d(ImageGalleryViewActivity.class.toString(), "count = " + cursor.getCount());

        for(int i = 0; i < count; ++i) {
            imgIds[i] = cursor.getLong( cursor.getColumnIndex("image_id") );
            cursor.moveToNext();
        }

        return imgIds;
    }

    private void setupGridView(long[] imgIds) {
        GridView gridView = (GridView) findViewById(R.id.gridView);
        long[] ids = imgIds;
        gridView.setAdapter(new ImageAdapter(getApplicationContext(), ids));
    }

    public void assetDetailsActivity(long imgId) {
        Toast.makeText(getApplicationContext(), "You clicked docId =" + imgId, Toast.LENGTH_SHORT).show();
        Intent assetDetails = new Intent(getApplicationContext(), AssetDetailsActivity.class);
        assetDetails.putExtra("image", imgId);
        startActivity(assetDetails);
    }
}
