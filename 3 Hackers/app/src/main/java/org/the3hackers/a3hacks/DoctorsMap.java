package org.the3hackers.a3hacks;
import Opertaion.GlobalData;
import Services.GeocodeJSONParser;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

public class DoctorsMap extends Fragment {
    private static GoogleMap mMap;
    public static View view;
    LatLng Doctors_latLng;
    CameraPosition cameraPosition;
    ConnectionDetector cd;
    String location1;
    ProgressDialog pd;

    class C13961 implements InfoWindowAdapter {
        C13961() {
        }

        public View getInfoWindow(Marker arg0) {
            return null;
        }

        public View getInfoContents(Marker arg0) {
            View v = DoctorsMap.this.getActivity().getLayoutInflater().inflate(C1469R.layout.info_window_layout, null);
            ((TextView) v.findViewById(C1469R.id.info_doctorName)).setText(GlobalData.Doctorname);
            TextView tvaddress2 = (TextView) v.findViewById(C1469R.id.info_address2);
            ((TextView) v.findViewById(C1469R.id.info_address1)).setText(GlobalData.Address);
            tvaddress2.setText("" + GlobalData.City + "\n" + GlobalData.State);
            return v;
        }
    }

    class C13972 implements OnClickListener {
        C13972() {
        }

        public void onClick(DialogInterface dialog, int id) {
            DoctorsMap.this.startActivity(new Intent("android.settings.SETTINGS"));
        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String data;

        private DownloadTask() {
            this.data = null;
        }

        protected String doInBackground(String... url) {
            try {
                this.data = DoctorsMap.this.downloadUrl(url[0]);
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

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void onPostExecute(java.util.List<java.util.HashMap<java.lang.String, java.lang.String>> r15) {
            /*
            r14 = this;
            r11 = medindia4u.net.patientapp.DoctorsMap.mMap;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11.clear();	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = 2130903060; // 0x7f030014 float:1.7412927E38 double:1.0528059966E-314;
            r3 = com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource(r11);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r2 = 0;
        L_0x000f:
            r11 = r15.size();	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            if (r2 >= r11) goto L_0x00ff;
        L_0x0015:
            r9 = new com.google.android.gms.maps.model.MarkerOptions;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r9.<init>();	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r1 = r15.get(r2);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r1 = (java.util.HashMap) r1;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = "lat";
            r11 = r1.get(r11);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = (java.lang.String) r11;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r4 = java.lang.Double.parseDouble(r11);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = "lng";
            r11 = r1.get(r11);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = (java.lang.String) r11;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r6 = java.lang.Double.parseDouble(r11);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = "formatted_address";
            r10 = r1.get(r11);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r10 = (java.lang.String) r10;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = new com.google.android.gms.maps.model.LatLng;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12.<init>(r4, r6);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11.Doctors_latLng = r12;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = r11.Doctors_latLng;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = r11.latitude;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            Opertaion.GlobalData.DoctorsLocations_Lat = r12;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = r11.Doctors_latLng;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = r11.longitude;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            Opertaion.GlobalData.DoctorsLocations_Lng = r12;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = r11.Doctors_latLng;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r9.position(r11);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r9.icon(r3);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = medindia4u.net.patientapp.DoctorsMap.mMap;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11.addMarker(r9);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = medindia4u.net.patientapp.DoctorsMap.mMap;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r8 = r11.addMarker(r9);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r8.showInfoWindow();	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ Exception -> 0x00dd, NullPointerException -> 0x00e6 }
            r11 = r11.getActivity();	 Catch:{ Exception -> 0x00dd, NullPointerException -> 0x00e6 }
            com.google.android.gms.maps.MapsInitializer.initialize(r11);	 Catch:{ Exception -> 0x00dd, NullPointerException -> 0x00e6 }
        L_0x007e:
            if (r2 != 0) goto L_0x009c;
        L_0x0080:
            r11 = "lat and long    ";
            r12 = new java.lang.StringBuilder;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12.<init>();	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r13 = "";
            r12 = r12.append(r13);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r13 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r13 = r13.Doctors_latLng;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = r12.append(r13);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = r12.toString();	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            android.util.Log.e(r11, r12);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
        L_0x009c:
            r11 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = new com.google.android.gms.maps.model.CameraPosition$Builder;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12.<init>();	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r13 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r13 = r13.Doctors_latLng;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = r12.target(r13);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r13 = 1097859072; // 0x41700000 float:15.0 double:5.424144515E-315;
            r12 = r12.zoom(r13);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r13 = 1119092736; // 0x42b40000 float:90.0 double:5.529052754E-315;
            r12 = r12.bearing(r13);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r13 = 1106247680; // 0x41f00000 float:30.0 double:5.465589745E-315;
            r12 = r12.tilt(r13);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = r12.build();	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11.cameraPosition = r12;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = medindia4u.net.patientapp.DoctorsMap.mMap;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = r12.cameraPosition;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r12 = com.google.android.gms.maps.CameraUpdateFactory.newCameraPosition(r12);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11.animateCamera(r12);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = medindia4u.net.patientapp.DoctorsMap.this;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11 = r11.pd;	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r11.dismiss();	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            r2 = r2 + 1;
            goto L_0x000f;
        L_0x00dd:
            r0 = move-exception;
            r11 = "error in MapsInitializer";
            r12 = "   yes";
            android.util.Log.e(r11, r12);	 Catch:{ NullPointerException -> 0x00e6, Exception -> 0x0100 }
            goto L_0x007e;
        L_0x00e6:
            r0 = move-exception;
            r11 = "NullPointerExceptions in clear all markers   ";
            r12 = new java.lang.StringBuilder;
            r12.<init>();
            r13 = "";
            r12 = r12.append(r13);
            r12 = r12.append(r0);
            r12 = r12.toString();
            android.util.Log.e(r11, r12);
        L_0x00ff:
            return;
        L_0x0100:
            r0 = move-exception;
            r11 = "Exception in clear all markers ";
            r12 = new java.lang.StringBuilder;
            r12.<init>();
            r13 = "";
            r12 = r12.append(r13);
            r12 = r12.append(r0);
            r12 = r12.toString();
            android.util.Log.e(r11, r12);
            goto L_0x00ff;
            */
            throw new UnsupportedOperationException("Method not decompiled: medindia4u.net.patientapp.DoctorsMap.ParserTask.onPostExecute(java.util.List):void");
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
        this.cd = new ConnectionDetector(getActivity());
        if (this.cd.isConnectingToInternet()) {
            try {
                view = inflater.inflate(C1469R.layout.activity_map, container, false);
                Log.e("before if condition --------", " yes");
                if (mMap == null) {
                    mMap = ((SupportMapFragment) DetailedActivity.fragmentManager.findFragmentById(C1469R.id.mapview)).getMap();
                    if (mMap != null) {
                        Log.e(" if condition --------", " yes");
                    }
                    mMap.setInfoWindowAdapter(new C13961());
                    this.pd = new ProgressDialog(getActivity());
                    this.pd.setMessage("Loading...");
                    this.pd.setCanceledOnTouchOutside(false);
                    this.pd.show();
                    this.location1 = GlobalData.Address + "," + GlobalData.City + "," + GlobalData.State;
                    Log.e("set Doctors location   ", "" + this.location1);
                    String url = "https://maps.googleapis.com/maps/api/geocode/json?";
                    try {
                        this.location1 = URLEncoder.encode(this.location1, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.e("Map location URL  ", "" + (url + ("address=" + this.location1) + "&" + "sensor=false"));
                    new DownloadTask().execute(new String[]{url});
                }
            } catch (Exception e2) {
                Log.e("Error in load map", "  " + e2);
            }
            return view;
        }
        Log.e("aSas", "Connection");
        showDialog();
        return null;
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

    private void showDialog() {
        Builder builder = new Builder(getActivity());
        builder.setTitle("Internet Connection Error").setMessage("Required Internet Connection for use..!!").setCancelable(false).setNegativeButton("GO to Settings", new C13972());
        builder.create().show();
    }
}
