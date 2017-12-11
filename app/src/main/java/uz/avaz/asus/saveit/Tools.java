package uz.avaz.asus.saveit;

import java.util.List;

import uz.avaz.asus.saveit.Classes.Category;
import uz.avaz.asus.saveit.Classes.Market;
import uz.avaz.asus.saveit.Classes.Product;

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
}
