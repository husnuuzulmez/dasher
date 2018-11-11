package door.com.dash.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import door.com.dash.DownloadTask;
import door.com.dash.adapters.AdapterRestaurant;
import door.com.dash.models.Restaurants;


public class MyViewModel extends ViewModel {
    private Restaurants restaurants;
    private AdapterRestaurant adapter;
    private DownloadTask myTask;
    private RecyclerView Rview;

    public MyViewModel() {
        restaurants = new Restaurants();
    }

    public void setRview(RecyclerView rview, Context context) {
        Rview = rview;
        adapter =new AdapterRestaurant(context, restaurants.getRestaurantList());
        Rview.setLayoutManager(new GridLayoutManager(context,1));
        Rview.setAdapter(adapter);
    }

    public void collectData() {
        myTask = new DownloadTask(adapter , restaurants);
        myTask.setGetAllData(true);
        myTask.setpagedUrl(40);
        myTask.execute(0);
    }

    public void setRestaurants(Restaurants restaurants) {
        this.restaurants = restaurants;
    }

    public Restaurants getRestaurants() {
        return restaurants;
    }
}

