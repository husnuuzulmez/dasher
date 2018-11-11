package door.com.dash.models;

import java.util.ArrayList;
import java.util.List;

public class Restaurants {
    private List<Restaurant> RestaurantList;

    public Restaurants (){
        RestaurantList = new ArrayList<>();
    }

    public void SetRestaurantList (List<Restaurant> Fl){
        this.RestaurantList = Fl;
    }

    public List<Restaurant>  Addfeed (List<Restaurant> AddFl){
        for (int i=0; i<AddFl.size(); i++){
            RestaurantList.add(AddFl.get(i));
        }
        return RestaurantList;
    }

    public List<Restaurant> getRestaurantList() {
        return this.RestaurantList;
    }
}

