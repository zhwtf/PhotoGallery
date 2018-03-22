package com.bignerdranch.android.photogallery.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bignerdranch.android.photogallery.R;

public class Main2Activity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    // Variables to store the values for the list index of the selected images
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    // Define the behavior for onImageSelected
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was selected
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        //Based on where a user has clicked, store the selected list index for the head, body, and leg BodyPartFragments
        int bodyPartNumber = position / 12;

        int listIndex = position - 12*bodyPartNumber;

        switch (bodyPartNumber) {
            case 0:
                headIndex = listIndex;
                break;
            case 1:
                bodyIndex = listIndex;
                break;
            case 2:
                legIndex = listIndex;
                break;
            default:break;
        }

        //Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
        Bundle bundle = new Bundle();
        bundle.putInt("headIndex", headIndex);
        bundle.putInt("bodyIndex", bodyIndex);
        bundle.putInt("legIndex", legIndex);

        // Attack the Bundle to an intent
        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(bundle);

        // Get a reference to the "Next" button and launch the intent when this button is clicked
        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

}
