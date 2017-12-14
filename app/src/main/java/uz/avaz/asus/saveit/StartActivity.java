package uz.avaz.asus.saveit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uz.avaz.asus.saveit.Classes.*;
import uz.avaz.asus.saveit.Services.WebAPI;

public class StartActivity extends AppCompatActivity {
    private Context context;
    private AnimationDrawable animation;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        context = this;

        animation = (AnimationDrawable) findViewById(R.id.layout).getBackground();
        animation.setExitFadeDuration(2000);
        animation.start();

        if (isConnected())
            new DownloadData().execute(findViewById(R.id.progressbar), findViewById(R.id.go_button));
        else
            Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
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

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void goToMainWindow(View view) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void goToSignInWindow(View view) {
        Intent intent = new Intent(context, SignInActivity.class);
        startActivity(intent);
    }

    public void goToSignUpWindow(View view) {
        Intent intent = new Intent(context, RegisterActivity.class);
        startActivity(intent);
    }

    private static class DownloadData extends AsyncTask<View, Void, View[]> {
        @SuppressWarnings("ConstantConditions")
        @Override
        protected View[] doInBackground(View... views) {
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
            } catch (IOException e) {
                return null;
            }
            return views;
        }

        protected void onPostExecute(View... views) {
            if (views != null) {
                views[0].setVisibility(View.INVISIBLE);
                views[1].setVisibility(View.VISIBLE);
            }
        }
    }
}
