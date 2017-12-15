package uz.avaz.asus.saveit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uz.avaz.asus.saveit.Classes.Category;
import uz.avaz.asus.saveit.Classes.Product;
import uz.avaz.asus.saveit.Classes.Result;
import uz.avaz.asus.saveit.Classes.ResultProduct;
import uz.avaz.asus.saveit.Services.WebAPI;

public class AddProductActivity extends AppCompatActivity {
    private String mediaPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        List<String> list = new ArrayList<>();
        for (Category category : Tools.categories_array)
            list.add(category.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.category)).setAdapter(adapter);
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void showGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            findViewById(R.id.image_check).setVisibility(View.VISIBLE);
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            assert selectedImage != null;
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);
            cursor.close();
        } else {
            findViewById(R.id.image_check).setVisibility(View.INVISIBLE);
            mediaPath = null;
        }
    }

    public void addProduct(View view) {
        if (isValid()) {
            if (isConnected()) {
                findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
                findViewById(R.id.go_button).setVisibility(View.GONE);
                Retrofit retrofit = new Retrofit.Builder().baseUrl(Tools.BASE_ADDRESS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final WebAPI api = retrofit.create(WebAPI.class);
                final Product product = new Product();
                product.setName(((EditText) findViewById(R.id.name)).getText().toString());
                product.setDate(((EditText) findViewById(R.id.date)).getText().toString());
                product.setUnit(((EditText) findViewById(R.id.unit)).getText().toString());
                product.setPrice(Double.parseDouble(((EditText) findViewById(R.id.price)).getText().toString()));
                product.setDiscount(Double.parseDouble(((EditText) findViewById(R.id.discount)).getText().toString()));
                product.setCategory(Tools.categories_array.get(((Spinner) findViewById(R.id.category)).getSelectedItemPosition()).getId());
                product.setMarket(Tools.market.getId());
                MultipartBody.Part image = null;
                if (mediaPath != null) {
                    File file = new File(mediaPath);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    image = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                }
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), product.getName());
                RequestBody date = RequestBody.create(MediaType.parse("text/plain"), product.getDate());
                RequestBody unit = RequestBody.create(MediaType.parse("text/plain"), product.getUnit());
                final Call<ResultProduct> resultCall = api.createProduct(name, date, unit, product.getPrice(), product.getDiscount(), product.getCategory(), product.getMarket(), image);
                resultCall.enqueue(new Callback<ResultProduct>() {
                    @SuppressWarnings("ConstantConditions")
                    @Override
                    public void onResponse(@NonNull Call<ResultProduct> call, @NonNull Response<ResultProduct> response) {
                        if (response.body().getStatus() == 1) {
                            Tools.products_array.add(response.body().getProduct());
                            onBackPressed();
                        } else {
                            findViewById(R.id.progressbar).setVisibility(View.GONE);
                            findViewById(R.id.go_button).setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResultProduct> call, @NonNull Throwable t) {
                        Log.e("TAG", t.getMessage());
                        findViewById(R.id.progressbar).setVisibility(View.GONE);
                        findViewById(R.id.go_button).setVisibility(View.VISIBLE);
                    }
                });
            }
        }
    }

    private boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean isValid() {
        EditText name = findViewById(R.id.name);
        EditText date = findViewById(R.id.date);
        EditText unit = findViewById(R.id.unit);
        EditText price = findViewById(R.id.price);
        EditText discount = findViewById(R.id.discount);
        Spinner category = findViewById(R.id.category);

        name.setBackgroundColor(0x33888888);
        date.setBackgroundColor(0x33888888);
        unit.setBackgroundColor(0x33888888);
        price.setBackgroundColor(0x33888888);
        discount.setBackgroundColor(0x33888888);
        category.setBackgroundColor(0x33888888);

        if (name.getText().toString().isEmpty()) {
            name.setBackgroundColor(0x44F44336);
            return false;
        }
        if (date.getText().toString().isEmpty()) {
            date.setBackgroundColor(0x44F44336);
            return false;
        }
        if (unit.getText().toString().isEmpty()) {
            unit.setBackgroundColor(0x44F44336);
            return false;
        }
        if (price.getText().toString().isEmpty()) {
            price.setBackgroundColor(0x44F44336);
            return false;
        }
        if (discount.getText().toString().isEmpty()) {
            discount.setBackgroundColor(0x44F44336);
            return false;
        }
        if (category.getSelectedItem().toString().isEmpty()) {
            category.setBackgroundColor(0x44F44336);
            return false;
        }
        return true;
    }
}
