package uz.avaz.asus.saveit.Services;

import android.support.annotation.Nullable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import uz.avaz.asus.saveit.Classes.*;

public interface WebAPI {
    @GET("/api/markets")
    Call<MarketResult> getMarkets();

    @GET("/api/categories")
    Call<CategoryResult> getCategories();

    @GET("/api/products")
    Call<ProductResult> getProducts();

    @POST("/api/market/login")
    Call<Result> getStatus(@Body Market market);

    @Multipart
    @POST("/api/market/create")
    Call<Result> createMarket(
            @Part("login") RequestBody login,
            @Part("password") RequestBody  password,
            @Part("name") RequestBody name,
            @Part("address") RequestBody address,
            @Part("phone") RequestBody phone,
            @Part("latitude") Double latitude,
            @Part("longitude") Double longitude,
            @Nullable @Part MultipartBody.Part image
    );
}
