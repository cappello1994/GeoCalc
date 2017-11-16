package cis.gvsu.edu.geocalculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.parceler.Parcels;

public class HistoryActivity extends AppCompatActivity
        implements HistoryFragment.OnListFragmentInteractionListener{

    private static final String TAG = "beepin";

    @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
        @Override
        public void onListFragmentInteraction (LocationLookup item){
            Log.i(TAG, "onListFragmentInteraction:");
            Intent intent = new Intent();
            LocationLookup aLoc = new LocationLookup();
            aLoc.origLat = item.origLat;
            aLoc.origLng = item.origLng;
            aLoc.endLat = item.endLat;
            aLoc.endLng = item.endLng;
            // add more code to initialize the rest of the fields
            Parcelable parcel = Parcels.wrap(aLoc);
            intent.putExtra("LOCATIONLOOKUP", parcel);
            setResult(MainActivity.HISTORY_RESULT, intent);
            finish();

    }

}
