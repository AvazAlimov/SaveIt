package uz.avaz.asus.saveit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uz.avaz.asus.saveit.Classes.Market;
import uz.avaz.asus.saveit.Classes.Product;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addMarkets();
                addProducts();
            }
        });
    }

    private void addMarkets() {
        int size;
        LinearLayout container = findViewById(R.id.container);
        for (int i = 0; i < Tools.markets_array.size(); i++) {
            Market market = Tools.markets_array.get(i);
            size = getResources().getDimensionPixelSize(R.dimen.size_8dp);
            LinearLayout item = new LinearLayout(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            lp.setMargins(0, size, 0, 0);
            item.setLayoutParams(lp);
            item.setTag(market.getId());
            item.setBackgroundResource(R.drawable.item);
            item.setClickable(true);
            item.setOrientation(LinearLayout.HORIZONTAL);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMarket(v);
                }
            });
            size = getResources().getDimensionPixelSize(R.dimen.size_72dp);
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.ic_online_store);
            image.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setBackgroundResource(android.R.color.transparent);
            if (market.getImage() != null)
                if (!market.getImage().isEmpty())
                    new Tools.DownloadImageTask(image, market.getImage()).execute(Tools.IMAGE_ADDRESS + market.getImage());
            size = getResources().getDimensionPixelSize(R.dimen.size_16dp);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            TextView name = new TextView(this);
            LinearLayout.LayoutParams name_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            name_lp.setMargins(size, size, size, 0);
            name.setLayoutParams(name_lp);
            name.setText(market.getName());
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            name.setTextColor(Color.BLACK);
            name.setTypeface(name.getTypeface(), Typeface.BOLD);
            TextView address = new TextView(this);
            LinearLayout.LayoutParams address_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            address_lp.setMargins(size, 0, size, size);
            address.setLayoutParams(address_lp);
            address.setText(market.getAddress());
            address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            address.setTextColor(Color.BLACK);
            layout.addView(name);
            layout.addView(address);
            item.addView(image);
            item.addView(layout);
            container.addView(item);
        }
    }

    private void openMarket(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("market", (int) v.getTag());
        startActivity(intent);
    }

    private void addProducts() {
        int size;
        LinearLayout container = findViewById(R.id.product_container);
        for (int i = 0; i < Tools.products_array.size(); i++) {
            Product product = Tools.products_array.get(i);
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(product.getDate());
                Date currentDate = new Date(System.currentTimeMillis());
                if (currentDate.before(date)) {
                    size = getResources().getDimensionPixelSize(R.dimen.size_8dp);
                    LinearLayout item = new LinearLayout(this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.gravity = Gravity.CENTER;
                    lp.setMargins(0, size, 0, 0);
                    item.setLayoutParams(lp);
                    item.setTag(product.getId());
                    item.setBackgroundResource(R.drawable.item);
                    item.setClickable(true);
                    item.setTag(product.getMarket());
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openMarket(view);
                        }
                    });
                    item.setOrientation(LinearLayout.HORIZONTAL);
                    size = getResources().getDimensionPixelSize(R.dimen.size_72dp);
                    ImageView image = new ImageView(this);
                    image.setImageResource(R.drawable.ic_groceries);
                    image.setLayoutParams(new ViewGroup.LayoutParams(size, size));
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    image.setBackgroundResource(android.R.color.transparent);
                    if (product.getImage() != null)
                        if (!product.getImage().isEmpty())
                            new Tools.DownloadImageTask(image, product.getImage()).execute(Tools.IMAGE_ADDRESS + product.getImage());
                    size = getResources().getDimensionPixelSize(R.dimen.size_16dp);

                    LinearLayout layout = new LinearLayout(this);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    TextView name = new TextView(this);
                    LinearLayout.LayoutParams name_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    name_lp.setMargins(size, size, size, 0);
                    name.setLayoutParams(name_lp);
                    name.setText(product.getName());
                    name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    name.setTextColor(Color.BLACK);
                    name.setTypeface(name.getTypeface(), Typeface.BOLD);

                    TextView address = new TextView(this);
                    LinearLayout.LayoutParams address_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    address_lp.setMargins(size, 0, size, size);
                    address.setLayoutParams(address_lp);
                    address.setText(
                            String.format("%s: %s\n%s: %s\n%s: %s%s",
                                    getString(R.string.category),
                                    Tools.findCategoryName(product.getCategory()),
                                    getString(R.string.expirydate),
                                    product.getDate(),
                                    getString(R.string.price),
                                    product.getNew_price(),
                                    getString(R.string.sum))
                    );
                    address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    address.setTextColor(Color.BLACK);

                    layout.addView(name);
                    layout.addView(address);

                    item.addView(image);
                    item.addView(layout);
                    container.addView(item);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void switchScrolls(View view) {
        if (findViewById(R.id.market_scroll).getVisibility() == View.VISIBLE) {
            findViewById(R.id.market_scroll).setVisibility(View.GONE);
            findViewById(R.id.product_scroll).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.title)).setText(R.string.products);
            ((ImageView) findViewById(R.id.title_image)).setImageResource(R.drawable.ic_box);
        } else {
            findViewById(R.id.market_scroll).setVisibility(View.VISIBLE);
            findViewById(R.id.product_scroll).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.title)).setText(R.string.markets);
            ((ImageView) findViewById(R.id.title_image)).setImageResource(R.drawable.ic_market);
        }
    }
}
