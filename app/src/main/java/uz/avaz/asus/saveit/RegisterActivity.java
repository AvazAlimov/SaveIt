package uz.avaz.asus.saveit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    AnimationDrawable animation;
    private Context context;
    private double latitude;
    private double longitude;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = this;
        animation = (AnimationDrawable) findViewById(R.id.layout).getBackground();
        animation.setExitFadeDuration(2000);
        animation.start();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animation != null && !animation.isRunning())
            animation.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animation != null && animation.isRunning())
            animation.stop();
    }

    @Override
    public void onBackPressed() {
        if (findViewById(R.id.map_layout).getVisibility() == RelativeLayout.VISIBLE)
            findViewById(R.id.map_layout).setVisibility(RelativeLayout.INVISIBLE);
        else
            super.onBackPressed();
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void showMap(View view) {
        findViewById(R.id.map_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng coordinates = new LatLng(41.2995f, 69.2401f);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 12.0f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                mMap.addMarker(new MarkerOptions().position(latLng));
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    ((TextView) findViewById(R.id.address)).setText(addresses.get(0).getAddressLine(0));
                } catch (IOException e) {
                    ((TextView) findViewById(R.id.address)).setText("");
                }
            }
        });
    }

    public void register(View view) {

    }

    private boolean isValid() {
        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.password);
        EditText name = findViewById(R.id.name);
        EditText phone = findViewById(R.id.phone);
        TextView address = findViewById(R.id.address);

        if (login.getText().toString().isEmpty()) {
            login.setBackgroundColor(0x44F44336);
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            password.setBackgroundColor(0x44F44336);
            return false;
        }
        if (name.getText().toString().isEmpty()) {
            name.setBackgroundColor(0x44F44336);
            return false;
        }
        if (phone.getText().toString().isEmpty()) {
            phone.setBackgroundColor(0x44F44336);
            return false;
        }
        if (address.getText().toString().isEmpty()) {
            address.setBackgroundColor(0x44F44336);
            return false;
        }

        return true;
    }
}
