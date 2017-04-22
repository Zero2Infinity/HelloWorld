package com.android.rahul.helloworld;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by prahul on 4/16/2017.
 */

public class ImagesData implements Serializable {
    private Map<String, ImageLocationData> mImagesData = new LinkedHashMap<>();

    public boolean removeAll() {
        mImagesData.clear();
        if (mImagesData.isEmpty())
            return true;
        else
            return false;
    }

    public void addData(String imgId, double lat, double lng) {
        ImageLocationData loc = new ImageLocationData(new LatLng(lat, lng));
        mImagesData.put(imgId, loc);
    }

    public void addData(String imgId, double lat, double lng, String city, String zipCode) {
        ImageLocationData loc = new ImageLocationData(new LatLng(lat, lng), city, zipCode);
        mImagesData.put(imgId, loc);
    }

    public Map.Entry<String, ImageLocationData> getAt(int index) {
        Set<Map.Entry<String, ImageLocationData>> mapSet = mImagesData.entrySet();
        Map.Entry<String, ImageLocationData> element = (Map.Entry<String, ImageLocationData>)mapSet.toArray()[index];
        return element;
    }

    public void removeDataByImgId(String imgId) {
        ImageLocationData obj = mImagesData.get(imgId);
        if (obj != null)
            mImagesData.remove(obj);
    }

    public void removeData(ImageLocationData location) {
        if (location != null)
            mImagesData.remove(location);
    }

    public int count() {
        return mImagesData.size();
    }

} // Class
