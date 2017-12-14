package uz.avaz.asus.saveit;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uz.avaz.asus.saveit.Classes.Category;
import uz.avaz.asus.saveit.Classes.Market;
import uz.avaz.asus.saveit.Classes.Product;

import static android.content.ContentValues.TAG;

class Tools {
    //    static String BASE_ADDRESS = "https://saveit2.000webhostapp.com/api/";
    static String IMAGE_ADDRESS = "https://saveit2.000webhostapp.com/app/public/Images/";
    static String BASE_ADDRESS = "https://saveit2.000webhostapp.com/api/";
    //    static String TEMP_ADDRESS = "http://192.168.43.53/api/";

    static List<Market> markets_array;
    static List<Category> categories_array;
    static List<Product> products_array;
    static HashMap<String, Bitmap> images = new HashMap<>();

    static Market market;

    static Market findMarket(int id) {
        for (Market market : markets_array)
            if (market.getId() == id)
                return market;
        return null;
    }

    static List<Product> findProducts() {
        List<Product> products = new ArrayList<>();
        for (Product product : products_array) {
            if (product.getMarket().intValue() == market.getId().intValue())
                products.add(product);
        }
        return products;
    }

    static String findCategoryName(int id) {
        for (int i = 0; i < Tools.categories_array.size(); i++)
            if (Tools.categories_array.get(i).getId() == id)
                return Tools.categories_array.get(i).getName();
        return "";
    }

    static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), 4 * 1024);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 4 * 1024);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (IOException e) {
            Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            closeStream(in);
            closeStream(out);
        }

        return bitmap;
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[4 * 1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                android.util.Log.e(TAG, "Could not close stream", e);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        LinearLayout bmImage;
        ImageView imageView;
        String name;

        DownloadImageTask(LinearLayout bmImage, String name) {
            this.bmImage = bmImage;
            this.name = name;
        }

        DownloadImageTask(ImageView imageView, String name) {
            this.imageView = imageView;
            this.name = name;
        }

        protected Bitmap doInBackground(String... urls) {
            if (images.containsKey(name))
                return images.get(name);
            return loadBitmap(IMAGE_ADDRESS + name);
        }

        protected void onPostExecute(Bitmap result) {
            if (!images.containsKey(name))
                images.put(name, result);
            BitmapDrawable background = new BitmapDrawable(result);
            if (bmImage != null) {
                bmImage.setBackgroundDrawable(background);
            } else if (imageView != null) {
                imageView.setImageBitmap(Bitmap.createScaledBitmap(result, imageView.getLayoutParams().width, imageView.getLayoutParams().height, false));
            }
        }
    }
}