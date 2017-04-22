package com.android.rahul.helloworld;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyListFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;

    private static final String IMAGESDATA_KEY = "imagesData";
    private ImagesData mImagesData;

    public MyListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyListFragment newInstance(ImagesData imgs) {
        MyListFragment fragment = new MyListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IMAGESDATA_KEY, imgs);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImagesData = (ImagesData) getArguments().getSerializable(IMAGESDATA_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        // Build city list
        ArrayList<String> cities = new ArrayList();
        for (int i=0; i<mImagesData.count(); i++) {
            cities.add(mImagesData.getAt(i).getValue().getImageLocationData().getCity());
        }

        ListView listView = (ListView) rootView.findViewById(R.id.cityList);
        ArrayAdapter<String> adapter = new ArrayAdapter <>(getContext(), android.R.layout.simple_list_item_1, cities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getContext(), "Clicked on Item #" + position , Toast.LENGTH_SHORT).show();
                mListener.onListItemSelected(position);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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

    public interface OnListFragmentInteractionListener {
        public void onListItemSelected(int position);
    }
}