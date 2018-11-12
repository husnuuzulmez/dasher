package door.com.dash.Views;


import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;

import door.com.dash.ViewModels.VMRestaurant;
import door.com.dash.R;
import door.com.dash.databinding.ActivityRestaurantDetailBinding;

public class RestaurantDetailActivity extends AppCompatActivity {
    ActivityRestaurantDetailBinding  binding;
    ScrollView scrollView;
    WebView  webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_detail);


        webView = binding.webview1;
        int restaurantId = getIntent().getIntExtra("Id",0);
        String webUrl = getIntent().getStringExtra("Url");

        VMRestaurant vmRestaurant=new VMRestaurant(getBaseContext(),restaurantId);
        vmRestaurant.setImgicon((ImageView) binding.imgIcon );
        vmRestaurant.setScrollView((ScrollView) binding.ScrollView);

        binding.setData(vmRestaurant);
        binding.executePendingBindings();

        WebView  webView = findViewById(R.id.webview1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);

    }



}
