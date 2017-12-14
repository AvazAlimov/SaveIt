package uz.avaz.asus.saveit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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

    static Market market;

    static Market findMarket(int id) {
        for (Market market : markets_array)
            if (market.getId() == id)
                return market;
        return null;
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
}