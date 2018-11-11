package door.com.dash;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import door.com.dash.Views.MainActivity;
import door.com.dash.adapters.AdapterRestaurant;
import door.com.dash.models.Restaurant;
import door.com.dash.models.Restaurants;

public class DownloadTask extends AsyncTask<Integer, Void, Integer> {
    private static final String TAG = "Asynctask";
    private List<Restaurant> restaurantList;
    private Restaurants restaurants;
    private AdapterRestaurant Myadaptor;
    private int start=0;
    private boolean getAllData=false;

    private String Surl="https://api.doordash.com/v2/restaurant/?lat=37.422740&lng=-122.139956";

    public DownloadTask(  AdapterRestaurant Myadaptor , Restaurants restaurants) {
        this.Myadaptor = Myadaptor;
        this.restaurants = restaurants;
        this.restaurantList = restaurants.getRestaurantList();

    }

    public void setGetAllData(boolean getAllData) {
        this.getAllData = getAllData;
    }

    public void setpagedUrl(int page){
       String url="https://api.doordash.com/v2/restaurant/?lat=37.422740&lng=-122.139956";
   //     if (getAllData==false)
            url=url+"&offset=0&limit="+String.valueOf(page);

        setSurl(url);
    }

    public void setSurl(String surl) {
      Surl = surl;
    }

    @Override
    protected Integer doInBackground(Integer... params) {
      //  start = params[0];
        Integer result = 0;
        HttpURLConnection urlConnection;
        try {
      //      if (start==0)
      //         Surl = "https://api.doordash.com/v2/restaurant/?lat=37.422740&lng=-122.139956&offset=0&limit=40"; //+ params[0];
      //      else  Surl = "https://api.doordash.com/v2/restaurant/?lat=37.422740&lng=-122.139956&offset=0&limit=366";
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
            Log.d(TAG, e.getLocalizedMessage());
        }
        return result; //"Failed to fetch data!";
    }

    @Override
    protected void onPostExecute(Integer result) {

 //   dlg.hide();
        if (result == 1) {
            Myadaptor.restaurants.SetRestaurantList(restaurantList );
            Myadaptor.notifyDataSetChanged();
            MainActivity.swipeRefreshLayout.setRefreshing(false);


        } else {

        }
    }


    private void parseResult(String result) {
        Log.d(TAG, result);

        JSONArray RestObjects = null;
        try {
            RestObjects = new JSONArray(result);
            for (int i = start; i < RestObjects.length(); i++) {
                JSONObject restObject = RestObjects.optJSONObject(i);
                Restaurant restaurant = new Restaurant();
                restaurant.setShortProp(restObject);
                restaurantList.add(restaurant);
            }

            restaurants.SetRestaurantList(restaurantList);
            Log.d("sayi",String.valueOf(restaurantList.size()));

          //  if (restaurantList.size()<50) {
            if (getAllData){
                DownloadTask myTask = new DownloadTask(Myadaptor, restaurants);
                myTask.execute(start);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
