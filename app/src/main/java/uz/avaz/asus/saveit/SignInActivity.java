package uz.avaz.asus.saveit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

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


public class SignInActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void login(View view) {
        if (isConnected() || true) {
            findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
            findViewById(R.id.go_button).setVisibility(View.GONE);
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Tools.TEMP_ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final WebAPI api = retrofit.create(WebAPI.class);
            Market market = new Market();
            market.setLogin(((EditText) findViewById(R.id.login)).getText().toString());
            market.setPassword(((EditText) findViewById(R.id.password)).getText().toString());
            final Call<Result> resultCall = api.getStatus(market);
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
                    findViewById(R.id.progressbar).setVisibility(View.GONE);
                    findViewById(R.id.go_button).setVisibility(View.VISIBLE);
                }
            });
        } else {
            ((TextView) findViewById(R.id.error)).setText(getResources().getIdentifier("connection_error", "string", getPackageName()));
        }

    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private static class DownloadData extends AsyncTask<Context, Void, Void> {
        @SuppressWarnings("ConstantConditions")
        @Override
        protected Void doInBackground(Context... context) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Tools.TEMP_ADDRESS)
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