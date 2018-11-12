package door.com.dash;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import door.com.dash.Views.MainActivity;
import door.com.dash.adapters.AdapterRestaurant;
import door.com.dash.models.Restaurants;


@RunWith(AndroidJUnit4.class)
public class DownloadTaskTest {

    DownloadTask myTask;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class,true,false);

    @Test
    public void TestDownloadDatapaged() throws Throwable {
        final MainActivity act = rule.launchActivity(new Intent());
        final CountDownLatch signal = new CountDownLatch(1);
        final Restaurants[] restaurants=new Restaurants[1];
        act.viewModel.setRestaurants(new Restaurants());

        act.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                restaurants[0] = act.viewModel.getRestaurants();
                AdapterRestaurant adapter = new AdapterRestaurant(act.getBaseContext(),restaurants[0].getRestaurantList());
                myTask = new DownloadTask(adapter,restaurants[0]);
                myTask.setGetAllData(false);
                myTask.setpagedUrl(40);
                myTask.execute(0);
            }
        });

        signal.await(10, TimeUnit.SECONDS);

        int restCount = restaurants[0].getRestaurantList().size();
        Assert.assertTrue(restCount>0);
        Assert.assertTrue(restCount<41);


    }


    @Test
    public void TestDownloadDataAll() throws Throwable {
        final MainActivity act = rule.launchActivity(new Intent());
        final CountDownLatch signal = new CountDownLatch(1);
        final Restaurants[] restaurants=new Restaurants[1];
        act.viewModel.setRestaurants(new Restaurants());

        act.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                restaurants[0] = act.viewModel.getRestaurants();
                AdapterRestaurant adapter = new AdapterRestaurant(act.getBaseContext(),restaurants[0].getRestaurantList());
                myTask = new DownloadTask(adapter,restaurants[0]);
                myTask.setGetAllData(true);
                myTask.setpagedUrl(40);
                myTask.execute(0);
            }
        });

        signal.await(20, TimeUnit.SECONDS);

        int restCount = restaurants[0].getRestaurantList().size();
        Assert.assertTrue(restCount>200);
    }




}