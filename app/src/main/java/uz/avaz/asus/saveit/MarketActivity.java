package uz.avaz.asus.saveit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import uz.avaz.asus.saveit.Classes.Market;
import uz.avaz.asus.saveit.Classes.Product;

public class MarketActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Tools.market.getName());
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.market, menu);
        ((TextView) findViewById(R.id.market_name)).setText(Tools.market.getName());
        ((TextView) findViewById(R.id.market_address)).setText(Tools.market.getAddress());
        if (Tools.market.getImage() != null)
            if (!Tools.market.getImage().isEmpty())
                new Tools.DownloadImageTask((LinearLayout) findViewById(R.id.background_image), Tools.market.getImage()).execute(Tools.IMAGE_ADDRESS + Tools.market.getImage());
        loadProducts();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_products) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadProducts();
                }
            });
        } else if (id == R.id.nav_trash) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadTrashedProducts();
                }
            });
        } else if (id == R.id.nav_user) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadItems();
                }
            });
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            Tools.market = null;
            Intent intent = new Intent(this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    private void loadItems() {
        int size;
        LinearLayout main_layout = findViewById(R.id.container);
        main_layout.removeAllViews();
        getLayoutInflater().inflate(R.layout.container_layout, main_layout);
        LinearLayout markets_container = findViewById(R.id.markets_container);
        LinearLayout products_container = findViewById(R.id.products_container);
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
            markets_container.addView(item);
        }
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
                    products_container.addView(item);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProducts() {
        List<Product> products = Tools.findProducts();
        int size;
        LinearLayout main_layout = findViewById(R.id.container);
        main_layout.removeAllViews();
        getLayoutInflater().inflate(R.layout.product_add_layout, main_layout);
        LinearLayout container = findViewById(R.id.products_container);

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
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

    private void loadTrashedProducts() {
        List<Product> products = Tools.findProducts();
        int size;
        LinearLayout main_layout = findViewById(R.id.container);
        main_layout.removeAllViews();
        getLayoutInflater().inflate(R.layout.product_add_layout, main_layout);
        LinearLayout container = findViewById(R.id.products_container);
        findViewById(R.id.add_button).setVisibility(View.GONE);

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(product.getDate());
                Date currentDate = new Date(System.currentTimeMillis());
                Log.e("DATE", date.toString() + " * " + currentDate.toString());
                if (currentDate.after(date)) {
                    size = getResources().getDimensionPixelSize(R.dimen.size_8dp);
                    LinearLayout item = new LinearLayout(this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.gravity = Gravity.CENTER;
                    lp.setMargins(0, size, 0, 0);
                    item.setLayoutParams(lp);
                    item.setTag(product.getId());
                    item.setBackgroundResource(R.drawable.item);
                    item.setClickable(true);
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

    private void openMarket(View v) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("market", (int) v.getTag());
        startActivity(intent);
    }

    public void goToAddProductWindow(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }

    public void switchContainers(View view) {
        if (findViewById(R.id.markets_container).getVisibility() == View.VISIBLE) {
            findViewById(R.id.markets_container).setVisibility(View.GONE);
            findViewById(R.id.products_container).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.change_button)).setText(getString(R.string.markets));
        } else {
            findViewById(R.id.markets_container).setVisibility(View.VISIBLE);
            findViewById(R.id.products_container).setVisibility(View.GONE);
            ((Button) findViewById(R.id.change_button)).setText(getString(R.string.products));
        }
    }
}