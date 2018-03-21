package com.bignerdranch.android.photogallery.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.android.photogallery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BodyPartFragment extends Fragment {

    // Final Strings to store state information about the list of images and list index
    public static final String IMAGE_ID_LIST = "image_ids";
    public static final String LIST_INDEX = "list_index";

    // Tag for logging
    private static final String TAG = "BodyPartFragment";

    // Variables to store a list of image resources and the index of the image that this fragment displays
    private List<Integer> mImageIds;
    private int mListIndex;



    public BodyPartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Load the saved state (the list of images and list index) if there is one
        if (savedInstanceState != null) {
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_body_part, container, false);

        // Get the reference to the ImageView in the fragment layout
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        // If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        if (mImageIds != null) {
            // Set the image resource to the list item at the stored index
            imageView.setImageResource(mImageIds.get(mListIndex));

            // Set a click listener on the image view
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Increment position as long as the index remains <= the size of the image ids list
                    if (mListIndex < mImageIds.size()-1) {
                        mListIndex++;
                    } else {
                        mListIndex = 0;
                    }
                    // Set the image resource to the new list item
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });
        } else {
            Log.v(TAG, "This fragment has a null list of image id's");
        }

        // return the root view
        return rootView;

    }

    // Setter methods for keeping track of the list images this fragment can display and which image
    // in the list is currently being displayed
    public void setImageIds(List<Integer> imageIds) {
        mImageIds = imageIds;
    }

    public void setListIndex(int index) {
        mListIndex = index;
    }

    // save the current state of this fragment
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
        currentState.putInt(LIST_INDEX, mListIndex);
    }

}
