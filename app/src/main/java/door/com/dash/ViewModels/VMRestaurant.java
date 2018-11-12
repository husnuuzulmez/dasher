package door.com.dash.ViewModels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.location.Address;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import door.com.dash.BR;
import door.com.dash.R;
import door.com.dash.models.Restaurant;

public class VMRestaurant extends BaseObservable{
    Restaurant restaurant;
    Context mContext;
    private ImageView imgicon;
    private ScrollView scrollView;

    public VMRestaurant(Context context, int restaurantId){
        this.mContext=context;
        restaurant = new Restaurant();
        DownloadRestDetail myTask = new DownloadRestDetail();
        myTask.execute(restaurantId);
    }

    public void setImgicon(ImageView imgicon) {
        this.imgicon = imgicon;
    }

    public Address getAddress(){
        return restaurant.getAddress();
    }

    public String getName(){
        return restaurant.getName();
    }

    public String getStatus(){
        return restaurant.getStatus();
    }

    public String getDesc(){
        return restaurant.getDesc();
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public int getavgRating(){
        return (int) (restaurant.getAvgrating()*20.0);
    }

    public String  getavgRatetxt(){
        return String.valueOf ((restaurant.getAvgrating()));
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
                scrollView.scrollTo(0, 0);
                notifyPropertyChanged(BR._all);
            } else {
                //
            }
        }

        private void parseResult(String result) {
            try {
                JSONObject restObject =  new JSONObject(result);
                restaurant.setLongProp(restObject, mContext);
                //   binding.setData(restaurant);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void Uploadimg(String img_url) {
        if (!TextUtils.isEmpty(img_url)) {
            Glide.with(mContext)
                    .load(img_url)
                    .into(imgicon);
        }
        else imgicon.setImageResource(R.drawable.placeholder);
    }

}
