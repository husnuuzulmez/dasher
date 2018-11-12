package door.com.dash.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import door.com.dash.R;
import door.com.dash.Views.RestaurantDetailActivity;
import door.com.dash.models.Restaurant;
import door.com.dash.models.Restaurants;

import java.util.List;

public class AdapterRestaurant extends RecyclerView.Adapter<AdapterRestaurant.CustomViewHolder> {
    private List<Restaurant> restaurantList;
    public Restaurants restaurants;
    private Context mContext;
    private Restaurant SelectedRestaurant;


    public AdapterRestaurant(Context context, List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        this.restaurants = new Restaurants();
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Restaurant restaurant = restaurantList.get(i);
        //Render image using Picasso library
        if (!TextUtils.isEmpty(restaurant.getImg_url())) {
            Picasso.with(mContext).load(restaurant.getImg_url())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.imageView);
        }

        customViewHolder.textViewName.setText(restaurant.getName());
        customViewHolder.textViewDesc.setText(restaurant.getDesc());
        customViewHolder.textViewStatus.setText(restaurant.getStatus());
    }

    @Override
    public int getItemCount() {
        return (null != restaurantList ? restaurantList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textViewName;
        protected TextView textViewDesc;
        protected TextView textViewStatus;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView =  view.findViewById(R.id.thumbnail);
            this.textViewName = view.findViewById(R.id.name);
            this.textViewDesc =  view.findViewById(R.id.description);
            this.textViewStatus = view.findViewById(R.id.status);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectedRestaurant = restaurantList.get(getAdapterPosition());
                    Intent i = new Intent(mContext, RestaurantDetailActivity.class);
                    i.putExtra("Id",SelectedRestaurant.getId());
                    i.putExtra("Url",SelectedRestaurant.getWebUrl());
                    mContext.startActivity(i);
                }
            });


        }
    }
}


  /*  public void setOnItemClickListener(OnRecyclerViewItemClickListener<ViewModel> listener) {
        this.itemClickListener = listener;
    }
*/


