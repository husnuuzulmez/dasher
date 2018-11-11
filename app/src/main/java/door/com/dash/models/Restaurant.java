package door.com.dash.models;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;

public class Restaurant implements Serializable {
    private int Id;
    private String Name;
    private String Desc;
    private String img_url;
    private String Status;
    private Address address;
    private JSONObject Jobject;
    private String WebUrl;

    public void setJobject(JSONObject jobject) {
        Jobject = jobject;
    }

    public JSONObject getJobject() {
        return Jobject;
    }

    public String getWebUrl() {
        return WebUrl;
    }

    public void setWebUrl() {
        try {
            WebUrl ="https://www.doordash.com"+getJobject().getString("url") ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Context context) {
        try {
            JSONObject JAddress = getJobject().getJSONObject("address");
            double Lat = JAddress.getDouble("lat");
            double Lng = JAddress.getDouble("lng");

            Geocoder geocoder = new Geocoder(context);
            Address   addr = geocoder.getFromLocation(Lat, Lng, 1).get(0);
            addr.setPhone(getJobject().getString("phone_number")  );
            this.address =addr;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setShortProp(JSONObject JObject) {
       setJobject(JObject);
       setWebUrl();
       setId(JObject.optInt("id"));
       setName(JObject.optString("name"));
       setDesc(JObject.optString("description"));
       setImg_url (JObject.optString("cover_img_url"));
       setStatus(JObject.optString("status"));
    }

    public void setLongProp(JSONObject JObject, Context context) {
        setShortProp(JObject);
        setAddress(context);
        setWebUrl();

    }

}
