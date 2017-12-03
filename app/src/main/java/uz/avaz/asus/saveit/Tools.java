package uz.avaz.asus.saveit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class Tools {
    static String markets = "";
    static String categories = "";
    static String products = "";

    static ArrayList<Market> markets_array;
    static ArrayList<Category> categories_array;
    static ArrayList<Product> products_array;

    static void init() {
        markets_array = new ArrayList<>();
        categories_array = new ArrayList<>();
        products_array = new ArrayList<>();

        JSONObject json;
        try {
            json = new JSONObject(markets);
            JSONArray articles = json.getJSONArray("data");
            for (int i = 0; i < articles.length(); i++) {
                Market market = new Market();
                market.id = articles.getJSONObject(i).getInt("id");
                market.name = articles.getJSONObject(i).getString("name");
                market.address = articles.getJSONObject(i).getString("address");
                market.phone = articles.getJSONObject(i).getString("phone");
                market.latitude = articles.getJSONObject(i).getDouble("latitude");
                market.longitude = articles.getJSONObject(i).getDouble("longitude");
                markets_array.add(market);
            }

            json = new JSONObject(categories);
            articles = json.getJSONArray("data");
            for (int i = 0; i < articles.length(); i++) {
                Category category = new Category();
                category.id = articles.getJSONObject(i).getInt("id");
                category.name = articles.getJSONObject(i).getString("name");
                categories_array.add(category);
            }

            json = new JSONObject(products);
            articles = json.getJSONArray("data");
            for (int i = 0; i < articles.length(); i++) {
                Product product = new Product();
                product.id = articles.getJSONObject(i).getInt("id");
                product.name = articles.getJSONObject(i).getString("name");
                product.date = articles.getJSONObject(i).getString("expirydate");
                product.originalprice = articles.getJSONObject(i).getDouble("originalprice");
                product.discont = articles.getJSONObject(i).getDouble("discont");
                product.newprice = articles.getJSONObject(i).getDouble("newprice");
                product.unit = articles.getJSONObject(i).getString("unit");
                product.market = articles.getJSONObject(i).getInt("market");
                product.category = articles.getJSONObject(i).getInt("category");
                products_array.add(product);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("MARKETS SIZE", String.valueOf(markets_array.size()));
        Log.e("CATEGORIES SIZE", String.valueOf(categories_array.size()));
        Log.e("PRODUCTS SIZE", String.valueOf(products_array.size()));
    }

    static Market findMarket(int id) {
        for (Market market : markets_array)
            if (market.id == id)
                return market;
        return null;
    }
}
