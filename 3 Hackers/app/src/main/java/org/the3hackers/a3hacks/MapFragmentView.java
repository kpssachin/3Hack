package org.the3hackers.a3hacks;
import Opertaion.GlobalData;
import Services.GeocodeJSONParser;
import android.app.Fragment;
import android.app.FragmentManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class MapFragmentView extends Fragment implements LocationListener {
    private static GoogleMap mMap;
    public static View view;
    LatLng DeslatLng;
    CameraPosition cameraPosition;
    String location1;
    double mLatitude = 0.0d;
    double mLongitude = 0.0d;
    ArrayList<LatLng> mMarkerPoints;

    class C14461 implements InfoWindowAdapter {
        C14461() {
        }

        public View getInfoWindow(Marker arg0) {
            return null;
        }

        public View getInfoContents(Marker arg0) {
            View v = MapFragmentView.this.getActivity().getLayoutInflater().inflate(C1469R.layout.info_window_layout, null);
            ((TextView) v.findViewById(C1469R.id.info_doctorName)).setText(GlobalData.Doctorname);
            TextView tvaddress2 = (TextView) v.findViewById(C1469R.id.info_address2);
            ((TextView) v.findViewById(C1469R.id.info_address1)).setText(GlobalData.Address);
            tvaddress2.setText("" + GlobalData.City + "\n" + GlobalData.State);
            return v;
        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String data;

        private DownloadTask() {
            this.data = null;
        }

        protected String doInBackground(String... url) {
            try {
                this.data = MapFragmentView.this.downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return this.data;
        }

        protected void onPostExecute(String result) {
            new ParserTask().execute(new String[]{result});
        }
    }

    private class DownloadTaskGeocoder extends AsyncTask<String, Void, String> {
        private DownloadTaskGeocoder() {
        }

        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = MapFragmentView.this.downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTaskGeocoder().execute(new String[]{result});
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
            MapFragmentView.mMap.clear();
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(C1469R.mipmap.ic_action_location);
            for (int i = 0; i < list.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> hmPlace = (HashMap) list.get(i);
                double lat = Double.parseDouble((String) hmPlace.get("lat"));
                double lng = Double.parseDouble((String) hmPlace.get("lng"));
                String name = (String) hmPlace.get("formatted_address");
                MapFragmentView.this.DeslatLng = new LatLng(lat, lng);
                markerOptions.position(MapFragmentView.this.DeslatLng);
                markerOptions.icon(icon);
                MapFragmentView.mMap.addMarker(markerOptions);
                Marker marker = MapFragmentView.mMap.addMarker(markerOptions);
                try {
                    MapsInitializer.initialize(MapFragmentView.this.getActivity());
                } catch (Exception e) {
                    Log.e("error in MapsInitializer", "   yes");
                }
            }
        }
    }

    private class ParserTaskGeocoder extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        private ParserTaskGeocoder() {
        }

        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            List<List<HashMap<String, String>>> routes = null;
            try {
                routes = new DirectionsJSONParser().parse(new JSONObject(jsonData[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            PolylineOptions lineOptions = null;
            for (int i = 0; i < result.size(); i++) {
                ArrayList<LatLng> points = new ArrayList();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = (List) result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = (HashMap) path.get(j);
                    points.add(new LatLng(Double.parseDouble((String) point.get("lat")), Double.parseDouble((String) point.get("lng"))));
                }
                lineOptions.addAll(points);
                lineOptions.width(5.0f);
                lineOptions.color(SupportMenu.CATEGORY_MASK);
            }
            MapFragmentView.mMap.addPolyline(lineOptions);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        try {
            view = inflater.inflate(C1469R.layout.activity_map, container, false);
            Log.e("before if condition --------", " yes");
            if (mMap == null) {
                mMap = ((SupportMapFragment) DetailedActivity.fragmentManager.findFragmentById(C1469R.id.mapview)).getMap();
                if (mMap != null) {
                    Log.e(" if condition --------", " yes");
                }
                this.location1 = GlobalData.Address + "," + GlobalData.City + "," + GlobalData.State;
                Log.e("set Doctors location   ", "" + this.location1);
                String url = "https://maps.googleapis.com/maps/api/geocode/json?";
                try {
                    this.location1 = URLEncoder.encode(this.location1, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url = url + ("address=" + this.location1) + "&" + "sensor=false";
                MapFragmentView mapFragmentView = this;
                new DownloadTask().execute(new String[]{url});
                mMap.setMyLocationEnabled(true);
                LocationManager locationManager = (LocationManager) getActivity().getSystemService("location");
                String provider = locationManager.getBestProvider(new Criteria(), true);
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    onLocationChanged(location);
                }
                locationManager.requestLocationUpdates(provider, 20000, 0.0f, this);
                if (this.mMarkerPoints.size() > 1) {
                    FragmentManager fm1 = getFragmentManager();
                    this.mMarkerPoints.clear();
                    mMap.clear();
                    drawMarker(new LatLng(this.mLatitude, this.mLongitude));
                }
                drawMarker(this.DeslatLng);
                if (this.mMarkerPoints.size() >= 2) {
                    String url_geo_coder = getDirectionsUrl((LatLng) this.mMarkerPoints.get(0), (LatLng) this.mMarkerPoints.get(1));
                    mapFragmentView = this;
                    new DownloadTaskGeocoder().execute(new String[]{url_geo_coder});
                }
                mMap.setInfoWindowAdapter(new C14461());
            }
        } catch (Exception e2) {
            Log.e("Error in load map", "  " + e2);
        }
        return view;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        return "https://maps.googleapis.com/maps/api/directions/" + "json" + "?" + (str_origin + "&" + ("destination=" + dest.latitude + "," + dest.longitude) + "&" + "sensor=false");
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

    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (mMap != null) {
            this.location1 = GlobalData.Address + "," + GlobalData.City + "," + GlobalData.State;
            Log.e("set Doctors location   ", "" + this.location1);
            String url = "https://maps.googleapis.com/maps/api/geocode/json?";
            try {
                this.location1 = URLEncoder.encode(this.location1, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url = url + ("address=" + this.location1) + "&" + "sensor=false";
            new DownloadTask().execute(new String[]{url});
        }
        if (mMap == null) {
            mMap = ((SupportMapFragment) DetailedActivity.fragmentManager.findFragmentById(C1469R.id.mapview)).getMap();
            if (mMap != null) {
                this.location1 = GlobalData.Address + "," + GlobalData.City + "," + GlobalData.State;
                Log.e("set Doctors location   ", "" + this.location1);
                url = "https://maps.googleapis.com/maps/api/geocode/json?";
                try {
                    this.location1 = URLEncoder.encode(this.location1, "utf-8");
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }
                url = url + ("address=" + this.location1) + "&" + "sensor=false";
                new DownloadTask().execute(new String[]{url});
            }
        }
    }

    public void onDestroyView() {
        try {
            if (mMap != null) {
                DetailedActivity.fragmentManager.beginTransaction().remove(DetailedActivity.fragmentManager.findFragmentById(C1469R.id.mapview)).commitAllowingStateLoss();
                mMap = null;
            }
        } catch (Exception e) {
            Log.e("Error in onDestroyview in doctors map view ", " " + e);
        }
        super.onDestroyView();
    }

    public void onLocationChanged(Location location) {
        if (this.mMarkerPoints.size() < 2) {
            this.mLatitude = location.getLatitude();
            this.mLongitude = location.getLongitude();
            LatLng point = new LatLng(this.mLatitude, this.mLongitude);
            Log.e("onLocationChanged--------------   ", "yessss");
        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }

    private void drawMarker(LatLng point) {
        this.mMarkerPoints.add(point);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(C1469R.mipmap.ic_action_location);
        MarkerOptions options = new MarkerOptions();
        options.position(point);
        if (this.mMarkerPoints.size() == 1) {
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        } else if (this.mMarkerPoints.size() == 2) {
            options.icon(BitmapDescriptorFactory.fromResource(C1469R.mipmap.ic_action_location));
        }
        mMap.addMarker(options);
        mMap.addMarker(options).showInfoWindow();
    }
}
