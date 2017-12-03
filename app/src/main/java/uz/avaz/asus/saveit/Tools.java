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

    static void init() {
        markets_array = new ArrayList<>();
        categories_array = new ArrayList<>();
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("MARKETS SIZE", String.valueOf(markets_array.size()));
        Log.e("CATEGORIES SIZE", String.valueOf(categories_array.size()));
    }
}
