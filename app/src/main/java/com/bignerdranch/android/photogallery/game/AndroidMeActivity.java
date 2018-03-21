package com.bignerdranch.android.photogallery.game;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.photogallery.R;
import com.bignerdranch.android.photogallery.data.AndroidImageAssets;

/**
 * Created by zhenghao on 2018-03-21.
 */

public class AndroidMeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {
            // Retrieve list index values that were sent through an intent; use them to display the desired Android-Me body part image
            // Use setListindex(int index) to set the list index for all BodyPartFragments

            // Create a new head BodyPartFragment
            BodyPartFragment headFragment = new BodyPartFragment();

            // Set the list of image id's for the head fragment and set the position to the second image in the list
            headFragment.setImageIds(AndroidImageAssets.getHeads());

            int headIndex = getIntent().getIntExtra("headIndex", 0);
            headFragment.setListIndex(headIndex);

            // Add the fragment to its container using a FragmentManager and a Transaction
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.head_container, headFragment)
                    .commit();

            // Create and display the body and leg BodyPartFragment
            BodyPartFragment bodyFragment = new BodyPartFragment();
            bodyFragment.setImageIds(AndroidImageAssets.getBodies());

            // Retrieve the value from intent
            int bodyIndex = getIntent().getIntExtra("bodyIndex", 0);
            bodyFragment.setListIndex(bodyIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.body_container, bodyFragment)
                    .commit();

            // For leg fragment
            BodyPartFragment legFragment = new BodyPartFragment();
            legFragment.setImageIds(AndroidImageAssets.getLegs());
            // Retrieve the value for legs from the intent
            int legIndex = getIntent().getIntExtra("legIndex", 0);
            legFragment.setListIndex(legIndex);
            fragmentManager.beginTransaction()
                    .add(R.id.leg_container, legFragment)
                    .commit();
        }
    }

}
