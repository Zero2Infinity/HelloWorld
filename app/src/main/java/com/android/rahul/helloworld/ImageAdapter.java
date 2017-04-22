package com.android.rahul.helloworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by rahul on 10/17/16.
 */

public class ImageAdapter extends BaseAdapter {
    Context context;
    long[] imgIds;
    private static LayoutInflater inflater = null;

    public ImageAdapter(Context activity,  long[] images) {
        context = activity;
        imgIds = images;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imgIds.length;
    }

    @Override
    public Object getItem(int position) {
        return imgIds[position];
    }

    @Override
    public long getItemId(int position) {
        return imgIds[position];    // Value itself is ImageID
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View cellView;
        cellView = inflater.inflate(R.layout.cell_view, null);

        if (convertView == null)
        {
            ImageView img = (ImageView) cellView.findViewById(R.id.img);
            Bitmap imgData = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), imgIds[position], MediaStore.Images.Thumbnails.MINI_KIND, null);
            img.setImageBitmap(imgData);

            cellView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if (parent.getContext() instanceof ImageGalleryViewActivity) {
                         ((ImageGalleryViewActivity)parent.getContext()).assetDetailsActivity(imgIds[position]);
                     }
                }
            });

            cellView = (View) cellView;
        }
        else
        {
            cellView = (View) convertView;
        }

        return cellView;
    }
}
