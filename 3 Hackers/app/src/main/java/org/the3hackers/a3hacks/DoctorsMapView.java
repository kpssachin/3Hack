package org.the3hackers.a3hacks;
import Services.GeocodeJSONParser;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class DoctorsMapView extends Fragment {
    private static final LatLng GOLDEN_GATE_BRIDGE = new LatLng(13.082285d, 80.218057d);
    CameraPosition cameraPosition;
    double latitude = 13.083397d;
    String location1;
    double longitude = 80.215998d;
    SupportMapFragment mMapFragment;
    GoogleMap map;
    MapView mapView;

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String data;

        private DownloadTask() {
            this.data = null;
        }

        protected String doInBackground(String... url) {
            try {
                this.data = DoctorsMapView.this.downloadUrl(url[0]);
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
            DoctorsMapView.this.map.clear();
            for (int i = 0; i < list.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> hmPlace = (HashMap) list.get(i);
                String name = (String) hmPlace.get("formatted_address");
                LatLng latLng = new LatLng(Double.parseDouble((String) hmPlace.get("lat")), Double.parseDouble((String) hmPlace.get("lng")));
                markerOptions.position(latLng);
                markerOptions.title(name);
                DoctorsMapView.this.map.addMarker(markerOptions);
                MapsInitializer.initialize(DoctorsMapView.this.getActivity());
                if (i == 0) {
                    Log.e("lat and long    ", "" + latLng);
                }
                Marker museum = DoctorsMapView.this.map.addMarker(new MarkerOptions().position(latLng).title("Medindia").snippet("Medindia-Medical/Health Website-Networking for Health"));
                DoctorsMapView.this.cameraPosition = new Builder().target(latLng).zoom(17.0f).bearing(90.0f).tilt(BitmapDescriptorFactory.HUE_ORANGE).build();
                DoctorsMapView.this.map.animateCamera(CameraUpdateFactory.newCameraPosition(DoctorsMapView.this.cameraPosition));
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(C1469R.layout.activity_map, container, false);
        this.mapView = (MapView) rootView.findViewById(C1469R.id.mapview);
        this.mapView.onCreate(savedInstanceState);
        this.map = this.mapView.getMap();
        this.location1 = "A-113,3rd Avenue,Anna Nagar,Chennai-600 102 ,India";
        String url = "https://maps.googleapis.com/maps/api/geocode/json?";
        try {
            this.location1 = URLEncoder.encode(this.location1, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        url = url + ("address=" + this.location1) + "&" + "sensor=false";
        new DownloadTask().execute(new String[]{url});
        return rootView;
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
