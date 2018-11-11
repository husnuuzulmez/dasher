package door.com.dash.Views;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import door.com.dash.ViewModels.MyViewModel;
import door.com.dash.R;


public class MainActivity extends AppCompatActivity {

    public MyViewModel viewModel;
    RecyclerView mRecyclerView;


    public static SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        viewModel.setRview(mRecyclerView,getBaseContext());
        viewModel.collectData();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
               viewModel.collectData();

            }
        });



    }


}