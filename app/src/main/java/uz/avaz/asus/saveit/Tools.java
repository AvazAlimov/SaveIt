package uz.avaz.asus.saveit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


class Tools {
    static List<Market> markets_array;
    static List<Category> categories_array;
    static List<Product> products_array;



    static Market findMarket(int id) {
        for (Market market : markets_array)
            if (market.id == id)
                return market;
        return null;
    }

    interface API {
        @GET("/api/markets")
        Call<MarketResult> getMarkets();

        @GET("/api/categories")
        Call<CategoryResult> getCategories();

        @GET("/api/products")
        Call<ProductResult> getProducts();
    }

    class MarketResult {
        @SerializedName("data")
        @Expose
        private List<Market> data;
        @SerializedName("status")
        @Expose
        private Integer status;

        MarketResult() {

        }

        public void setData(List<Market> data) {
            this.data = data;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        int getStatus() {
            return status;
        }

        public List<Market> getData() {
            return data;
        }
    }

    class CategoryResult {
        @SerializedName("data")
        @Expose
        private List<Category> data;
        @SerializedName("status")
        @Expose
        private Integer status;

        CategoryResult() {

        }

        public void setData(List<Category> data) {
            this.data = data;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        int getStatus() {
            return status;
        }

        public List<Category> getData() {
            return data;
        }
    }

    class ProductResult {
        @SerializedName("data")
        @Expose
        private List<Product> data;
        @SerializedName("status")
        @Expose
        private Integer status;

        ProductResult() {

        }

        public void setData(List<Product> data) {
            this.data = data;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        int getStatus() {
            return status;
        }

        public List<Product> getData() {
            return data;
        }
    }
}
