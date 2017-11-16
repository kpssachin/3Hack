package org.the3hackers.a3hacks;
import Opertaion.GlobalData;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class DirectionMapActivity extends FragmentActivity implements LocationListener {
    CameraPosition cameraPosition;
    ConnectionDetector cd;
    TextView distanceTimeTxt;
    GoogleMap mGoogleMap;
    double mLatitude = 0.0d;
    double mLongitude = 0.0d;
    ArrayList<LatLng> mMarkerPoints;

    class C13921 implements InfoWindowAdapter {
        C13921() {
        }

        public View getInfoWindow(Marker arg0) {
            return null;
        }

        public View getInfoContents(Marker arg0) {
            View v = DirectionMapActivity.this.getLayoutInflater().inflate(C1469R.layout.info_window_layout, null);
            ((TextView) v.findViewById(C1469R.id.info_doctorName)).setText(GlobalData.Doctorname);
            TextView tvaddress2 = (TextView) v.findViewById(C1469R.id.info_address2);
            ((TextView) v.findViewById(C1469R.id.info_address1)).setText(GlobalData.Address);
            tvaddress2.setText("" + GlobalData.City + "\n" + GlobalData.State);
            return v;
        }
    }

    class C13932 implements OnMapClickListener {
        C13932() {
        }

        public void onMapClick(LatLng point) {
            Log.e("PPPPPP_______------Point ", "" + point);
        }
    }

    class C13943 implements OnClickListener {
        C13943() {
        }

        public void onClick(DialogInterface dialog, int id) {
            DirectionMapActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
            DirectionMapActivity.this.finish();
        }
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        private DownloadTask() {
        }

        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = DirectionMapActivity.this.downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(new String[]{result});
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        private ParserTask() {
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
            String distance = "";
            String duration = "";
            for (int i = 0; i < result.size(); i++) {
                ArrayList<LatLng> points = new ArrayList();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = (List) result.get(i);
                if (result.size() < 1) {
                    Toast.makeText(DirectionMapActivity.this.getBaseContext(), "No Points", 0).show();
                    return;
                }
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = (HashMap) path.get(j);
                    if (j == 0) {
                        distance = (String) point.get("distance");
                    } else if (j == 1) {
                        duration = (String) point.get("duration");
                    } else {
                        points.add(new LatLng(Double.parseDouble((String) point.get("lat")), Double.parseDouble((String) point.get("lng"))));
                    }
                }
                lineOptions.addAll(points);
                lineOptions.width(8.0f);
                lineOptions.color(SupportMenu.CATEGORY_MASK);
            }
            Toast.makeText(DirectionMapActivity.this.getBaseContext(), "Distance:" + distance + ", Duration:" + duration, 0).show();
            DirectionMapActivity.this.distanceTimeTxt.setText("Distance:" + distance + ", Duration:" + duration);
            DirectionMapActivity.this.mGoogleMap.addPolyline(lineOptions);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_direction_map);
        this.cd = new ConnectionDetector(this);
        if (this.cd.isConnectingToInternet()) {
            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
            if (status != 0) {
                GooglePlayServicesUtil.getErrorDialog(status, this, 10).show();
                return;
            }
            this.mMarkerPoints = new ArrayList();
            this.distanceTimeTxt = (TextView) findViewById(C1469R.id.tv_distance_time);
            this.mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(C1469R.id.map_direction)).getMap();
            this.mGoogleMap.setMyLocationEnabled(true);
            this.mGoogleMap.setInfoWindowAdapter(new C13921());
            LocationManager locationManager = (LocationManager) getSystemService("location");
            String provider = locationManager.getBestProvider(new Criteria(), true);
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0.0f, this);
            if (this.mMarkerPoints.size() > 1) {
                FragmentManager fm1 = getSupportFragmentManager();
                this.mMarkerPoints.clear();
                this.mGoogleMap.clear();
                drawMarker(new LatLng(this.mLatitude, this.mLongitude));
            }
            drawMarker(new LatLng(GlobalData.DoctorsLocations_Lat, GlobalData.DoctorsLocations_Lng));
            if (this.mMarkerPoints.size() >= 2) {
                String url = getDirectionsUrl((LatLng) this.mMarkerPoints.get(0), (LatLng) this.mMarkerPoints.get(1));
                DirectionMapActivity directionMapActivity = this;
                new DownloadTask().execute(new String[]{url});
            }
            this.mGoogleMap.setOnMapClickListener(new C13932());
            return;
        }
        Log.e("aSas", "Connection");
        showDialog();
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        return "https://maps.googleapis.com/maps/api/directions/" + "json" + "?" + (str_origin + "&" + ("destination=" + GlobalData.DoctorsLocations_Lat + "," + GlobalData.DoctorsLocations_Lng) + "&" + "sensor=false");
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

    private void showDialog() {
        Builder builder = new Builder(this);
        builder.setTitle("Internet Connection Error").setMessage("Required Internet Connection for use..!!").setCancelable(false).setNegativeButton("GO to Settings", new C13943());
        builder.create().show();
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
        this.mGoogleMap.addMarker(options);
        this.mGoogleMap.addMarker(options).showInfoWindow();
    }

    public void onLocationChanged(Location location) {
        if (this.mMarkerPoints.size() < 2) {
            this.mLatitude = location.getLatitude();
            this.mLongitude = location.getLongitude();
            LatLng point = new LatLng(this.mLatitude, this.mLongitude);
            Log.e("onLocationChanged--------------   ", "yessss");
            this.cameraPosition = new CameraPosition.Builder().target(point).zoom(12.0f).bearing(90.0f).tilt(BitmapDescriptorFactory.HUE_ORANGE).build();
            this.mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(this.cameraPosition));
            drawMarker(point);
        }
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
