package uz.avaz.asus.saveit;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
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

        addMarkets();
    }

    private void addMarkets() {
        int size;
        LinearLayout container = (LinearLayout) findViewById(R.id.container);
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

            item.addView(image);
            container.addView(item);
        }
    }

//    private void addItems() {
//        LinearLayout container = (LinearLayout) findViewById(R.id.container);
//        for (int i = 1; i <= 21; i++) {
//            LinearLayout item = new LinearLayout(this);
//            item.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            item.setTag(i);
//            item.setBackgroundResource(R.drawable.item_click);
//            item.setClickable(true);
//            item.setOrientation(LinearLayout.HORIZONTAL);
//
//            item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openCalculator(v);
//                }
//            });
//
//            View view = new View(this);
//            view.setLayoutParams(new ViewGroup.LayoutParams(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics())), ViewGroup.LayoutParams.MATCH_PARENT));
//            view.setBackgroundResource(R.color.mainColor);
//
//            TextView title = new TextView(this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
//            params.weight = 1f;
//            title.setGravity(Gravity.CENTER_VERTICAL);
//            title.setMaxLines(2);
//            title.setLayoutParams(params);
//            title.setText(getResources().getIdentifier("calc_title_" + i, "string", getPackageName()));
//            title.setTextColor(Color.BLACK);
//            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
//            int size = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
//            title.setPadding(size, size, size, size);
//
//            ImageButton button = new ImageButton(this);
//            button.setPadding(size, size, size, size);
//            size = getResources().getDimensionPixelSize(R.dimen.size_64);
//            button.setLayoutParams(new ViewGroup.LayoutParams(size, size));
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showFullTitle(v);
//                }
//            });
//            button.setBackgroundResource(android.R.color.transparent);
//            button.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            button.setImageResource(R.drawable.show);
//            button.setTag(i);
//
//            item.addView(view);
//            item.addView(title);
//            item.addView(button);
//
//            View line = new View(this);
//            size = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
//            lp.setMargins(size, 0, size, 0);
//            line.setBackgroundResource(android.R.color.darker_gray);
//            line.setLayoutParams(lp);
//
//            container.addView(item);
//            if (i < 21)
//                container.addView(line);
//        }
//    }
}
