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
import android.widget.EditText;
import android.widget.TextView;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.parceler.Parcels;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class LocationSearchActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener{
    private static final String TAG = "LocationSearchActivity";
    int ORIG_PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int DEST_PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    @BindView(R.id.origLocation) TextView origLocation;
    @BindView(R.id.destLocation) TextView destLocation;
    @BindView(R.id.date) TextView dateText;

    private DateTime date;
    private DatePickerDialog dpDialog;
    private double origlat;
    private double origlong;
    private double destlat;
    private double destlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        DateTime today = DateTime.now();
        dpDialog = DatePickerDialog.newInstance(this,
                today.getYear(), today.getMonthOfYear() - 1, today.getDayOfMonth());

        dateText.setText(formatted(today));
    }

    @OnClick(R.id.origLocation)
    public void origLocationPressed() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, ORIG_PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.destLocation)
    public void destLocationPressed() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, DEST_PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.date})
    public void datePressed() {
        dpDialog.show(getFragmentManager(), "daterpickerdialog");
    }

    @OnClick(R.id.fab)
    public void FABPressed() {
        Intent result = new Intent();
        LocationLookup aLoc = new LocationLookup();
        aLoc.origLat = origlat;
        aLoc.origLng = origlong;
        aLoc.endLat = destlat;
        aLoc.endLng = destlong;
        // add more code to initialize the rest of the fields
        Parcelable parcel = Parcels.wrap(aLoc);
        result.putExtra("LOCATIONLOOKUP", parcel);
        setResult(RESULT_OK, result);
        finish();
    }

    private String formatted(DateTime d) {
        return d.monthOfYear().getAsShortText(Locale.getDefault()) + " " +
                d.getDayOfMonth() + ", " + d.getYear();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ORIG_PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place pl = PlaceAutocomplete.getPlace(this, data);
                origLocation.setText(pl.getAddress());
                origlat = pl.getLatLng().latitude;
                origlong = pl.getLatLng().longitude;
                Log.i(TAG, "onActivityResult: " + pl.getName() + "/" + pl.getAddress());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status stat = PlaceAutocomplete.getStatus(this, data);
                Log.d(TAG, "onActivityResult: ");
            }
            else if (requestCode == RESULT_CANCELED){
                System.out.println("Cancelled by the user");
            }
        }
        if (requestCode == DEST_PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place pl = PlaceAutocomplete.getPlace(this, data);
                destLocation.setText(pl.getAddress());
                destlat = pl.getLatLng().latitude;
                destlong = pl.getLatLng().longitude;
                Log.i(TAG, "onActivityResult: " + pl.getName() + "/" + pl.getAddress());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status stat = PlaceAutocomplete.getStatus(this, data);
                Log.d(TAG, "onActivityResult: ");
            }
            else if (requestCode == RESULT_CANCELED){
                System.out.println("Cancelled by the user");
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        date = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
        dateText.setText(formatted(date));
    }
}
