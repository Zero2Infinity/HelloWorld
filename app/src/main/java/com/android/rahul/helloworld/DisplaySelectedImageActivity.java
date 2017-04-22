package com.android.rahul.helloworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DisplaySelectedImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_selected_image);

        // Create a Intent to select image from phone...
        Button btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == Activity.RESULT_OK && data != null) {
            readImageDataFromDB(getApplicationContext(), data.getData(), null, null);
            displayThumbnail(getApplicationContext(),data.getData());
        }
    }

    private void readImageDataFromDB(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int colCount = cursor.getColumnCount();
                String[] colNames = cursor.getColumnNames();

                for (int col=0; col < colCount; col++) {
                    Log.d( DisplaySelectedImageActivity.class.toString(), colNames[col] + "=" + cursor.getString(col));
                }
                Log.d( DisplaySelectedImageActivity.class.toString(), "UriPath =" + uri);

            }
        } catch (Exception ex) {
            Log.d(DisplaySelectedImageActivity.class.toString(), "Exception: " + ex.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    private void displayThumbnail(Context context, Uri uri) {
        Cursor cursor = null;
        long imgId;
        try {
            cursor = MediaStore.Images.Thumbnails.queryMiniThumbnails(context.getContentResolver(), uri, MediaStore.Images.Thumbnails.MINI_KIND, null);
            if (cursor != null && cursor.moveToFirst()) {
                String docId = cursor.getString(cursor.getColumnIndex("document_id"));
                String[] split = docId.split(":");
                imgId = Long.parseLong(split[1]);

                Bitmap img = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), imgId, MediaStore.Images.Thumbnails.MINI_KIND, null);
                ImageView imgViewer = (ImageView) findViewById(R.id.imgViewer);
                imgViewer.setImageBitmap(img);
                Log.d(DisplaySelectedImageActivity.class.toString(), "Height:" + img.getHeight() + " x Width:" + img.getWidth());
            }
        } catch (Exception ex) {
            Log.d(DisplaySelectedImageActivity.class.toString(), "Exception: " + ex);
        } finally {
            if (cursor != null)
                cursor.close();
        }


    }
}
