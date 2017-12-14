package uz.avaz.asus.saveit;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import uz.avaz.asus.saveit.Classes.Market;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Market market;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //noinspection ConstantConditions
        int number = getIntent().getExtras().getInt("market");
        market = Tools.findMarket(number);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (market != null) {
                    ((TextView) findViewById(R.id.title)).setText(market.getName());
                    ((TextView) findViewById(R.id.address)).setText(market.getAddress());
                    ((TextView) findViewById(R.id.phone)).setText(market.getPhone());
                    ((TextView) findViewById(R.id.products)).setText(String.format("%s : %d", getString(R.string.products), Tools.productCount(market)));
                    if (market.getImage() != null)
                        if (!market.getImage().isEmpty())
                            new Tools.DownloadImageTask((ImageView) findViewById(R.id.image), market.getImage()).execute(Tools.IMAGE_ADDRESS + market.getImage());
                    findViewById(R.id.card).setVisibility(View.VISIBLE);
                }
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        assert market != null;
        String title = market.getName() + " " + market.getPhone();
        LatLng coordinates = new LatLng(market.getLatitude(), market.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(coordinates).title(title).visible(true));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15.0f));
    }

    public void goBack(View view) {
        onBackPressed();
    }
}
