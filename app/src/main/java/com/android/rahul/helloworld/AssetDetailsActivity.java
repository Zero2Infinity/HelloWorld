package com.android.rahul.helloworld;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;

public class AssetDetailsActivity extends AppCompatActivity {

    static long ImageId;
    static String RealPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);
        ImageId = getIntent().getLongExtra("image", 0);
        RealPath = getOriginalFile();
        try {
            loadAssetDetails();
            getExifData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getOriginalFile() {
        String imagePath = null;
        String where = "_id="+ImageId;
        Cursor cursor  = MediaStore.Images.Media.query(getContentResolver(),
                 MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                 null, where, null);

        logImageDetails(cursor);
        if (cursor != null && cursor.getCount() == 1 && cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            if (index > 0) {
                    imagePath = cursor.getString(index);
            }
        }
        return imagePath;
    }

    private void loadAssetDetails() throws IOException {
        ImageView img = (ImageView) findViewById(R.id.fileViewer);
        Log.d(AssetDetailsActivity.class.toString(), RealPath);
        Bitmap bMap = BitmapFactory.decodeFile(RealPath);
        img.setImageBitmap(bMap);

    }

    private void getExifData() throws IOException {
        // ExifInterface
        ExifInterface exif = new ExifInterface(RealPath);
        double latitude = exif.getAttributeDouble(ExifInterface.TAG_GPS_LATITUDE, 1.0);
        Log.d(AssetDetailsActivity.class.toString(), "Latitude = " + latitude);
    }

    private void logImageDetails(Cursor cursor) {
        int i = 0;
        if (cursor != null && cursor.moveToFirst()) {
            for(String col : cursor.getColumnNames()) {
                Log.d(AssetDetailsActivity.class.toString(), i +"="+ col + " : " + cursor.getType(i));
                if (cursor.getType(i) == 1) //INT
                {
                    Log.d(AssetDetailsActivity.class.toString(), String.valueOf(cursor.getInt(i)));
                }
                if (cursor.getType(i) == 2) // FLOAT
                {
                    Log.d(AssetDetailsActivity.class.toString(), String.valueOf(cursor.getFloat(i)));
                }
                else if (cursor.getType(i) == 3) //STRING
                {
                    Log.d(AssetDetailsActivity.class.toString(), cursor.getString(i));
                }
                ++i;
            }
        }
    }
}

/* Test Snippets
// MediaMetadataRetriever using RingToneManager
//        Uri defaultRing = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
//        MediaMetadataRetriever mdRetriever = new MediaMetadataRetriever();
//        mdRetriever.setDataSource(getApplicationContext(), defaultRing);
//        String title = mdRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
//        Log.d(AssetDetailsActivity.class.toString(), "Title = " + title);
*/