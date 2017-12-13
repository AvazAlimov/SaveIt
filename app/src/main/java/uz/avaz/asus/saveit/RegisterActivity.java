package uz.avaz.asus.saveit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uz.avaz.asus.saveit.Classes.CategoryResult;
import uz.avaz.asus.saveit.Classes.Market;
import uz.avaz.asus.saveit.Classes.MarketResult;
import uz.avaz.asus.saveit.Classes.ProductResult;
import uz.avaz.asus.saveit.Classes.Result;
import uz.avaz.asus.saveit.Services.WebAPI;

public class RegisterActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    AnimationDrawable animation;
    private Context context;
    private double latitude;
    private double longitude;
    private String mediaPath;

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
        if (isValid()) {
            if (isConnected()) {
                findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
                findViewById(R.id.go_button).setVisibility(View.GONE);

                Retrofit retrofit = new Retrofit.Builder().baseUrl(Tools.BASE_ADDRESS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final WebAPI api = retrofit.create(WebAPI.class);

                Market market = new Market();
                market.setLogin(((EditText) findViewById(R.id.login)).getText().toString());
                market.setPassword(((EditText) findViewById(R.id.password)).getText().toString());
                market.setName(((EditText) findViewById(R.id.name)).getText().toString());
                market.setPhone(((EditText) findViewById(R.id.phone)).getText().toString());
                market.setAddress(((TextView) findViewById(R.id.address)).getText().toString());
                market.setLatitude(latitude);
                market.setLongitude(longitude);
                MultipartBody.Part image = null;
                if (mediaPath != null) {
                    File file = new File(mediaPath);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    image = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                }
                RequestBody login = RequestBody.create(MediaType.parse("text/plain"), market.getLogin());
                RequestBody password = RequestBody.create(MediaType.parse("text/plain"), market.getPassword());
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), market.getName());
                RequestBody address = RequestBody.create(MediaType.parse("text/plain"), market.getAddress());
                RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), market.getPhone());

                final Call<Result> resultCall = api.createMarket(login, password, name, address, phone, market.getLatitude(), market.getLongitude(), image);
                resultCall.enqueue(new Callback<Result>() {
                    @SuppressWarnings("ConstantConditions")
                    @Override
                    public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                        if (response.body().getStatus() == 1) {
                            Tools.market = response.body().getMarket();
                            new DownloadData().execute(getBaseContext());
                        } else {
                            findViewById(R.id.progressbar).setVisibility(View.GONE);
                            findViewById(R.id.go_button).setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {
                        Log.e("TAG", t.getMessage());
                        findViewById(R.id.progressbar).setVisibility(View.GONE);
                        findViewById(R.id.go_button).setVisibility(View.VISIBLE);
                    }
                });
            } else {
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean isValid() {
        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.password);
        EditText name = findViewById(R.id.name);
        EditText phone = findViewById(R.id.phone);
        TextView address = findViewById(R.id.address);

        login.setBackgroundColor(0x33888888);
        password.setBackgroundColor(0x33888888);
        name.setBackgroundColor(0x33888888);
        phone.setBackgroundColor(0x33888888);
        address.setBackgroundColor(0x33888888);

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

    public void showGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            findViewById(R.id.image_check).setVisibility(View.VISIBLE);
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            assert selectedImage != null;
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            cursor.close();
        } else {
            findViewById(R.id.image_check).setVisibility(View.INVISIBLE);
            mediaPath = null;
        }
    }

    public void closeMap(View view) {
        onBackPressed();
    }

    private static class DownloadData extends AsyncTask<Context, Void, Void> {
        @SuppressWarnings("ConstantConditions")
        @Override
        protected Void doInBackground(Context... context) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Tools.BASE_ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final WebAPI api = retrofit.create(WebAPI.class);
            final Call<MarketResult> marketResultCall = api.getMarkets();
            final Call<CategoryResult> categoryResultCall = api.getCategories();
            final Call<ProductResult> productResultCall = api.getProducts();
            try {
                Tools.markets_array = marketResultCall.execute().body().getData();
                Tools.categories_array = categoryResultCall.execute().body().getData();
                Tools.products_array = productResultCall.execute().body().getData();
                Intent intent = new Intent(context[0], MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context[0].startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
