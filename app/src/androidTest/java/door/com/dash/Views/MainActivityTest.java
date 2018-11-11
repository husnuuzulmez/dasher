package door.com.dash.Views;


import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import door.com.dash.models.Restaurants;




@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule(MainActivity.class, true, false);

    @Test
    public void TestCollectData() throws Throwable {
        final MainActivity act = rule.launchActivity(new Intent());
        final CountDownLatch signal = new CountDownLatch(1);
        final Restaurants[] restaurants = new Restaurants[1];


        act.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                restaurants[0] = act.viewModel.getRestaurants();
                act.viewModel.collectData();
            }
        });

        signal.await(10, TimeUnit.SECONDS);

        int restCount = restaurants[0].getRestaurantList().size();
        Assert.assertTrue(restCount > 0);
    }

}

