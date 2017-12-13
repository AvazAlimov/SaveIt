package uz.avaz.asus.saveit;

import java.util.List;

import uz.avaz.asus.saveit.Classes.Category;
import uz.avaz.asus.saveit.Classes.Market;
import uz.avaz.asus.saveit.Classes.Product;

class Tools {
//    static String BASE_ADDRESS = "https://saveit2.000webhostapp.com/api/";
    static String BASE_ADDRESS = "https://saveit2.000webhostapp.com/api/";
//    static String TEMP_ADDRESS = "http://192.168.43.53/api/";

    static List<Market> markets_array;
    static List<Category> categories_array;
    static List<Product> products_array;

    static Market market;

    static Market findMarket(int id) {
        for (Market market : markets_array)
            if (market.getId() == id)
                return market;
        return null;
    }
}