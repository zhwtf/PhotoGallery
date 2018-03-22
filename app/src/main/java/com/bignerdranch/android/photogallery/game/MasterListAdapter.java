package com.bignerdranch.android.photogallery.game;

import android.content.ContentUris;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by zhenghao on 2018-03-21.
 */

public class MasterListAdapter extends BaseAdapter {

    // Keep track of the context and list of images to display
    private Context mContext;
    private List<Integer> mImageIds;

    public MasterListAdapter(Context context, List<Integer> imageIds) {
        mContext = context;
        mImageIds = imageIds;
    }

    /**
     * Returns the number of items the adapter will display
     */
    @Override
    public int getCount() {
        return mImageIds.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * Creates a new ImageView for each item referenced by the adapter
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // If the view is not recycled, this creates a new ImageView to hold an image
            imageView  = new ImageView(mContext);

            // Define the layout parameters
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8,8,8);

        }else {
            imageView = (ImageView) convertView;
        }
        // Set the image resource and return the newly created ImageView
        imageView.setImageResource(mImageIds.get(position));
        return imageView;
    }


}
