package door.com.dash.Views;


import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import door.com.dash.databinding.ActivityRetaurantDetailBinding;
import door.com.dash.R;
import door.com.dash.models.Restaurant;

public class RetaurantDetailActivity extends AppCompatActivity {
    ActivityRetaurantDetailBinding  binding;
    Restaurant restaurant = new Restaurant();
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retaurant_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retaurant_detail);

        scrollView=binding.ScrollView;
        int restaurantId = getIntent().getIntExtra("Id",0);
        String webUrl = getIntent().getStringExtra("Url");
        WebView  webView = findViewById(R.id.webview1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);
        DownloadRestDetail myTask = new DownloadRestDetail();
        myTask.execute(restaurantId);
    }

    private class DownloadRestDetail extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            int result = 0;
            String restId = String.valueOf(params[0]);
            HttpURLConnection urlConnection;
            try {
            String  Surl = "https://api.doordash.com/v2/restaurant/"+restId+"/";
                URL url = new URL(Surl);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d("Retaurant Detail", e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
            Uploadimg(restaurant.getImg_url());

            } else {
                //
            }

            scrollView.scrollTo(0, 0);
        }

        private void parseResult(String result) {
            try {
                    JSONObject restObject =  new JSONObject(result);
                    restaurant.setLongProp(restObject, getBaseContext());
                    binding.setData(restaurant);
            } catch (JSONException e) {
                    e.printStackTrace();
            }
        }
    }

    private void Uploadimg(String img_url) {
        ImageView imgicon = binding.imgIcon;
        if (!TextUtils.isEmpty(img_url)) {
            Glide.with(getBaseContext())
                    .load(img_url)
                    .into(imgicon);
        }
        else imgicon.setImageResource(R.drawable.placeholder);
    }

}
