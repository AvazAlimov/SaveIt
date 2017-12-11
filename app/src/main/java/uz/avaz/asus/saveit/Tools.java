package uz.avaz.asus.saveit;

import java.util.List;

import retrofit2.Call;
import uz.avaz.asus.saveit.Classes.Category;
import uz.avaz.asus.saveit.Classes.Market;
import uz.avaz.asus.saveit.Classes.Product;
import retrofit2.http.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Tools {
    static List<Market> markets_array;
    static List<Category> categories_array;
    static List<Product> products_array;

    static Market findMarket(int id) {
        for (Market market : markets_array)
            if (market.getId() == id)
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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
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
