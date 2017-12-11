package uz.avaz.asus.saveit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class StartActivity extends AppCompatActivity {
    private Context context;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        context = this;
        if (isConnected())
            initMarkets();
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

    void initMarkets() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://saveit2.000webhostapp.com/api/").addConverterFactory(GsonConverterFactory.create()).build();
        final Tools.API api = retrofit.create(Tools.API.class);
        Call<Tools.MarketResult> marketResultCall = api.getMarkets();
        marketResultCall.enqueue(new Callback<Tools.MarketResult>() {
            @Override
            public void onResponse(Call<Tools.MarketResult> call, Response<Tools.MarketResult> response) {
                if (response.body().getStatus() == 1)
                    Tools.markets_array = response.body().getData();
                Call<Tools.CategoryResult> categoryResultCall = api.getCategories();
                categoryResultCall.enqueue(new Callback<Tools.CategoryResult>() {
                    @Override
                    public void onResponse(Call<Tools.CategoryResult> call, Response<Tools.CategoryResult> response) {
                        if (response.body().getStatus() == 1)
                            Tools.categories_array = response.body().getData();
                        Call<Tools.ProductResult> productResultCall = api.getProducts();
                        productResultCall.enqueue(new Callback<Tools.ProductResult>() {
                            @Override
                            public void onResponse(Call<Tools.ProductResult> call, Response<Tools.ProductResult> response) {
                                if (response.body().getStatus() == 1)
                                    Tools.products_array = response.body().getData();
                                findViewById(R.id.progressbar).setVisibility(View.GONE);
                                findViewById(R.id.go_button).setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onFailure(Call<Tools.ProductResult> call, Throwable t) {
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<Tools.CategoryResult> call, Throwable t) {
                    }
                });
            }
            @Override
            public void onFailure(Call<Tools.MarketResult> call, Throwable t) {
            }
        });
    }

}
