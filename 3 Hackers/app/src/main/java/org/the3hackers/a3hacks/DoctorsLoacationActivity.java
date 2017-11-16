package org.the3hackers.a3hacks;
import Opertaion.GlobalData;
import Services.GeocodeJSONParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class DoctorsLoacationActivity extends FragmentActivity {
    CameraPosition cameraPosition;
    String location = "";
    GoogleMap mMap;

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String data;

        private DownloadTask() {
            this.data = null;
        }

        protected String doInBackground(String... url) {
            try {
                this.data = DoctorsLoacationActivity.this.downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return this.data;
        }

        protected void onPostExecute(String result) {
            new ParserTask().execute(new String[]{result});
        }
    }

    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        JSONObject jObject;

        ParserTask() {
        }

        protected List<HashMap<String, String>> doInBackground(String... jsonData) {
            List<HashMap<String, String>> places = null;
            GeocodeJSONParser parser = new GeocodeJSONParser();
            try {
                this.jObject = new JSONObject(jsonData[0]);
                places = parser.parse(this.jObject);
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        protected void onPostExecute(List<HashMap<String, String>> list) {
            DoctorsLoacationActivity.this.mMap.clear();
            for (int i = 0; i < list.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> hmPlace = (HashMap) list.get(i);
                String name = (String) hmPlace.get("formatted_address");
                LatLng latLng = new LatLng(Double.parseDouble((String) hmPlace.get("lat")), Double.parseDouble((String) hmPlace.get("lng")));
                markerOptions.position(latLng);
                markerOptions.title(name);
                DoctorsLoacationActivity.this.mMap.addMarker(markerOptions);
                if (i == 0) {
                    DoctorsLoacationActivity.this.cameraPosition = new Builder().target(latLng).zoom(15.0f).bearing(90.0f).tilt(BitmapDescriptorFactory.HUE_ORANGE).build();
                }
                DoctorsLoacationActivity.this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(DoctorsLoacationActivity.this.cameraPosition));
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_doctors_location);
        this.mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(C1469R.id.map)).getMap();
        String url = "https://maps.googleapis.com/maps/api/geocode/json?";
        try {
            this.location = GlobalData.Address + "," + GlobalData.City + "," + GlobalData.State;
            Log.e("doctors current location ???      ", this.location);
            this.location = URLEncoder.encode(this.location, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url = url + ("address=" + this.location) + "&" + "sensor=false";
        new DownloadTask().execute(new String[]{url});
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(strUrl).openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String str = "";
            while (true) {
                str = br.readLine();
                if (str == null) {
                    break;
                }
                sb.append(str);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
