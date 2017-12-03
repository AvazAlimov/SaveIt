package uz.avaz.asus.saveit;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addMarkets();
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
            lp.setMargins(0, size, 0, 0);
            item.setLayoutParams(lp);
            item.setTag(market.id);
            item.setBackgroundResource(R.drawable.item);
            item.setClickable(true);
            item.setOrientation(LinearLayout.HORIZONTAL);
            size = getResources().getDimensionPixelSize(R.dimen.size_16dp);
            item.setPadding(size, size, size, size);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //openMarket(v);
                }
            });

            size = getResources().getDimensionPixelSize(R.dimen.size_72dp);
            ImageView image = new ImageButton(this);
            image.setImageResource(R.drawable.ic_store);
            image.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setBackgroundResource(android.R.color.transparent);

            size = getResources().getDimensionPixelSize(R.dimen.size_16dp);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView name = new TextView(this);
            LinearLayout.LayoutParams name_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            name_lp.setMargins(size, 0, size, 0);
            name.setLayoutParams(name_lp);
            name.setText(market.name);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            name.setTextColor(Color.BLACK);
            name.setTypeface(name.getTypeface(), Typeface.BOLD);

            TextView address = new TextView(this);
            LinearLayout.LayoutParams address_lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            address_lp.setMargins(size, 0, size, 0);
            address.setLayoutParams(address_lp);
            address.setText(market.address);
            address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            address.setTextColor(Color.BLACK);

            layout.addView(name);
            layout.addView(address);

            item.addView(image);
            item.addView(layout);
            container.addView(item);
        }
    }

    public void switchScrolls(View view) {
        if(findViewById(R.id.market_scroll).getVisibility() == View.VISIBLE){
            findViewById(R.id.market_scroll).setVisibility(View.GONE);
            findViewById(R.id.product_scroll).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.market_scroll).setVisibility(View.VISIBLE);
            findViewById(R.id.product_scroll).setVisibility(View.GONE);
        }
    }
}
