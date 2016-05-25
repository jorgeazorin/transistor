/**
 * PlayerActivity.java
 * Implements the app's player activity
 * The player activity sets up the now playing view for phone  mode and inflates a menubar menu
 *
 * This file is part of
 * TRANSISTOR - Radio App for Android
 *
 * Copyright (c) 2015-16 - Y20K.org
 * Licensed under the MIT-License
 * http://opensource.org/licenses/MIT
 */


package org.y20k.transistor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import org.y20k.transistor.core.Collection;
import org.y20k.transistor.helpers.TransistorKeys;


/**
 * PlayerActivity class
 */
public final class PlayerActivity extends AppCompatActivity {

    /* Define log tag */
    private static final String LOG_TAG = PlayerActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set content view
        setContentView(R.layout.activity_player);

        // get intent
        Intent intent = getIntent();

        // CASE: show player in phone mode
        if (intent != null && TransistorKeys.ACTION_SHOW_PLAYER.equals(intent.getAction())) {

            // get id of station from intent
            int stationID;
            if (intent.hasExtra(TransistorKeys.EXTRA_STATION_ID)) {
                stationID = intent.getIntExtra(TransistorKeys.EXTRA_STATION_ID, 0);
            } else {
                stationID = 0;
            }

            // get collection object from intent
            Collection collection;
            if (intent.hasExtra(TransistorKeys.EXTRA_COLLECTION)) {
                collection = intent.getParcelableExtra(TransistorKeys.EXTRA_COLLECTION);
            } else {
                collection = null;
            }

            // get playback action from intent
            boolean startPlayback;
            if (intent.hasExtra(TransistorKeys.EXTRA_PLAYBACK_STATE)) {
                startPlayback = intent.getBooleanExtra(TransistorKeys.EXTRA_PLAYBACK_STATE, false);

                // Get a support ActionBar corresponding to this toolbar
                ActionBar ab = getSupportActionBar();

                // Enable the Up button
                ab.setDisplayHomeAsUpEnabled(true);

//                // artificial back stack.
//                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//                stackBuilder.addParentStack(MainActivity.class);
//                stackBuilder.addNextIntent(new Intent(this, MainActivity.class));


            } else {
                startPlayback = false;
            }





            // create bundle for player activity fragment
            Bundle args = new Bundle();
            args.putParcelable(TransistorKeys.ARG_COLLECTION, collection);
            args.putInt(TransistorKeys.ARG_STATION_ID, stationID);
            args.putBoolean(TransistorKeys.ARG_PLAYBACK, startPlayback);

            PlayerActivityFragment playerActivityFragment = new PlayerActivityFragment();
            playerActivityFragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .add(R.id.player_container, playerActivityFragment)
                    .commit();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
