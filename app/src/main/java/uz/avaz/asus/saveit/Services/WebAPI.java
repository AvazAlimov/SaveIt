package uz.avaz.asus.saveit.Services;

import retrofit2.Call;
import retrofit2.http.GET;
import uz.avaz.asus.saveit.Classes.*;

public interface WebAPI {
    @GET("/api/markets")
    Call<MarketResult> getMarkets();

    @GET("/api/categories")
    Call<CategoryResult> getCategories();

    @GET("/api/products")
    Call<ProductResult> getProducts();
}
