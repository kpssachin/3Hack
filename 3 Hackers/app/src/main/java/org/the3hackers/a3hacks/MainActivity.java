package org.the3hackers.a3hacks;
import Database.DBConstants;
import Database.DataCache;
import Database.SpecialityDB;
import Model.Cities;
import Opertaion.Constants;
import Opertaion.GlobalData;
import Opertaion.SplitJsonDoctorsList;
import Popupview.ActionItemText;
import Popupview.QuickActionView;
import Services.Connection;
import Services.HttpCall;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements LocationListener {
    AutoCompleteTextView AutoComplete_DoctorsName;
    AutoCompleteTextView AutoComplete_city;
    AutoCompleteTextView AutoComplete_speciality;
    final int CLOSE_DIALOG = 100;
    String UserName;
    ArrayList<Cities> aryView = null;
    ArrayList<Cities> aryspl = null;
    String cacheCity = "";
    String cacheDoctorName = "";
    String cacheSpecialty = "";
    Dialog dialog;
    DoctorsListFromServer getDoctorList = new DoctorsListFromServer();
    String[] item_city;
    String[] item_specialty;
    String[] item_state;
    Location location;
    LocationManager locationManager;
    String login_Status;
    String provider;
    int[] quickActionIcons;
    String[] quickActionText;
    QuickActionView quickActionView;
    Button reset_Btn;
    int resultConn = -1;
    Button search_Btn;
    SharedPreferences sharedPreferences;
    SpecialityDB spldb;
    RelativeLayout topBar_settings;
    TextView topTitle;
    RelativeLayout topbar_back;

    class C14371 implements TextWatcher {
        C14371() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            Log.e(" AutoComplete_speciality", "please select state");
        }
    }

    class C14382 implements TextWatcher {
        C14382() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            Log.e(" AutoComplete_speciality", "please select city");
        }
    }

    class C14393 implements TextWatcher {
        C14393() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            Log.e(" AutoComplete_city ", "please select Area");
        }
    }

    class C14404 implements OnClickListener {
        C14404() {
        }

        public void onClick(View v) {
            MainActivity.this.AutoComplete_speciality.setText("");
            MainActivity.this.AutoComplete_DoctorsName.setText("");
            MainActivity.this.AutoComplete_city.setText("");
        }
    }

    class C14415 implements OnClickListener {
        C14415() {
        }

        public void onClick(View v) {
            if (MainActivity.this.AutoComplete_city.getText().toString().trim().length() == 0) {
                MainActivity.this.showAlertDialog(MainActivity.this, "Please select a city", 1);
            } else if (MainActivity.this.AutoComplete_DoctorsName.getText().toString().trim().length() == 0 || MainActivity.this.AutoComplete_city.getText().toString().trim().length() == 0) {
                if (MainActivity.this.AutoComplete_speciality.getText().toString().trim().length() == 0 || MainActivity.this.AutoComplete_city.getText().toString().trim().length() == 0) {
                    MainActivity.this.showAlertDialog(MainActivity.this, "Please enter either a doctor's name or a specialty to search for\n", 1);
                } else if (MainActivity.this.AutoComplete_city.getText().toString().trim().length() != 0) {
                    MainActivity.this.getDoctorList = new DoctorsListFromServer();
                    MainActivity.this.getDoctorList.execute(new Void[]{null, null, null});
                } else {
                    MainActivity.this.showAlertDialog(MainActivity.this, "Please select a city", 1);
                }
            } else if (MainActivity.this.AutoComplete_city.getText().toString().trim().length() != 0) {
                MainActivity.this.getDoctorList = new DoctorsListFromServer();
                MainActivity.this.getDoctorList.execute(new Void[]{null, null, null});
            } else {
                MainActivity.this.showAlertDialog(MainActivity.this, "City field is mandatory", 1);
            }
        }
    }

    class C14426 implements OnClickListener {
        C14426() {
        }

        public void onClick(View v) {
            MainActivity.this.onBackPressed();
        }
    }

    class C14437 implements OnClickListener {
        C14437() {
        }

        public void onClick(View v) {
            MainActivity.this.initializePopUp(v);
        }
    }

    class C14448 implements DialogInterface.OnClickListener {
        C14448() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if (MainActivity.this.login_Status.equals("")) {
                switch (which) {
                    case 0:
                        Log.e("Patient History", " Yes");
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), PatientHistory.class).putExtra("TAG", "MainScreen"));
                        return;
                    case 1:
                        Log.e("PopUP Exit ", " Yes");
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", "Profile_MainScreen"));
                        return;
                    case 2:
                        Log.e("PopUP ABOUT ", " Yes");
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), AboutActivity.class));
                        return;
                    case 3:
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), HelpActivity.class));
                        Log.e("PopUP HELP ", "  Yes");
                        return;
                    case 4:
                        Log.e("PopUP SHARE ", " Yes");
                        GlobalData.generalShare(MainActivity.this.getApplicationContext());
                        return;
                    case 5:
                        Log.e("PopUP FEEDBACK ", " Yes");
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), FeedBackActivity.class).addFlags(32768).addFlags(67108864));
                        return;
                    case 6:
                        Log.e("PopUP Rate us ", " Yes");
                        GlobalData.rateThisApp(MainActivity.this.getApplicationContext());
                        return;
                    case 7:
                        Log.e("PopUP Exit ", " Yes");
                        MainActivity.this.showDialog(100);
                        return;
                    default:
                        return;
                }
            }
            switch (which) {
                case 0:
                    Log.e("Patient History", " Yes");
                    MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), PatientHistory.class).putExtra("TAG", "MainScreen"));
                    return;
                case 1:
                    Log.e("PopUP Exit ", " Yes");
                    MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), PatientProfileActivity.class));
                    return;
                case 2:
                    Log.e("PopUP ABOUT ", " Yes");
                    MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), AboutActivity.class));
                    return;
                case 3:
                    MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), HelpActivity.class));
                    Log.e("PopUP HELP ", "  Yes");
                    return;
                case 4:
                    Log.e("PopUP SHARE ", " Yes");
                    GlobalData.generalShare(MainActivity.this.getApplicationContext());
                    return;
                case 5:
                    Log.e("PopUP FEEDBACK ", " Yes");
                    MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), FeedBackActivity.class).addFlags(32768).addFlags(67108864));
                    return;
                case 6:
                    Log.e("PopUP Rate us ", " Yes");
                    GlobalData.rateThisApp(MainActivity.this.getApplicationContext());
                    return;
                case 7:
                    Log.e("PopUP Logout ", " Yes");
                    MainActivity.this.showAlertDialog(MainActivity.this, "Do you want to logout now ?", Boolean.valueOf(true));
                    return;
                case 8:
                    Log.e("PopUP Exit ", " Yes");
                    MainActivity.this.showDialog(100);
                    return;
                default:
                    return;
            }
        }
    }

    class C14459 implements DialogInterface.OnClickListener {
        C14459() {
        }

        public void onClick(DialogInterface dialog, int which) {
            MainActivity.this.logOut();
        }
    }

    class DoctorsListFromServer extends AsyncTask<Void, Void, Integer> {
        ImageView closeBut;
        TextView dialogLoadingTxt;
        ProgressDialog pd;

        DoctorsListFromServer() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pd = new ProgressDialog(MainActivity.this);
            this.pd.setMessage("Loading...");
            this.pd.setCanceledOnTouchOutside(false);
            this.pd.show();
        }

        protected Integer doInBackground(Void... params) {
            MainActivity.this.resultConn = MainActivity.this.createParameter();
            if (MainActivity.this.resultConn == 1) {
                MainActivity.this.resultConn = SplitJsonDoctorsList.splitJsonDoctors(GlobalData.sDownloadedContent);
            }
            return Integer.valueOf(MainActivity.this.resultConn);
        }

        protected void onCancelled() {
            super.onCancelled();
            Log.e("Process", "CANCELLED");
            Connection.closeConnection();
        }

        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            this.pd.dismiss();
            if (MainActivity.this.resultConn == 1) {
                Log.e("Connections ", "success");
                MainActivity.this.sharedPreferences.edit().putString(DBConstants.CITY, "" + MainActivity.this.AutoComplete_city.getText().toString().trim()).commit();
                MainActivity.this.sharedPreferences.edit().putString("Specialty", "" + MainActivity.this.AutoComplete_speciality.getText().toString().trim()).commit();
                MainActivity.this.sharedPreferences.edit().putString("DoctorName", "" + MainActivity.this.AutoComplete_DoctorsName.getText().toString().trim()).commit();
                MainActivity.this.startActivity(new Intent(MainActivity.this.getBaseContext(), Doctors_ListActivity.class));
            } else if (MainActivity.this.resultConn == 4) {
                Log.e(" MainAcitvity   ", "Network error in specialty process");
                MainActivity.this.showAlertDialog(MainActivity.this, MainActivity.this.getString(C1469R.string.SERVER_PROBLEM_MSG), 1);
            } else if (MainActivity.this.resultConn == 11) {
                MainActivity.this.showAlertDialog(MainActivity.this, MainActivity.this.getString(C1469R.string.INVALID_DATA_MSG), 1);
            } else {
                MainActivity.this.showAlertDialog(MainActivity.this, MainActivity.this.getString(C1469R.string.Network_ErrorMsg), 1);
            }
        }
    }

    class PopUpAdapter extends BaseAdapter {
        List<ActionItemText> mItems = new ArrayList();
        LayoutInflater mLayoutInflater;

        public PopUpAdapter(Context context) {
            this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            for (int i = 0; i < MainActivity.this.quickActionText.length; i++) {
                this.mItems.add(new ActionItemText(context, MainActivity.this.quickActionText[i], MainActivity.this.quickActionIcons[i]));
            }
        }

        public int getCount() {
            return this.mItems.size();
        }

        public Object getItem(int arg0) {
            return this.mItems.get(arg0);
        }

        public long getItemId(int arg0) {
            return (long) arg0;
        }

        public View getView(int position, View arg1, ViewGroup arg2) {
            View view = this.mLayoutInflater.inflate(C1469R.layout.action_item_vertical, arg2, false);
            ActionItemText item = (ActionItemText) getItem(position);
            ((ImageView) view.findViewById(C1469R.id.iv_icon)).setImageDrawable(item.getIcon());
            TextView text = (TextView) view.findViewById(C1469R.id.tv_title);
            text.setText(item.getTitle());
            if (GlobalData.screenSize > 6.0d) {
                text.setTextSize(18.0f);
                text.setTypeface(null, 1);
            }
            return view;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_main);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        this.login_Status = this.sharedPreferences.getString("login_Status", "");
        this.UserName = this.sharedPreferences.getString("username", "");
        this.cacheCity = this.sharedPreferences.getString(DBConstants.CITY, "");
        this.cacheSpecialty = this.sharedPreferences.getString("Specialty", "");
        this.cacheDoctorName = this.sharedPreferences.getString("DoctorName", "");
        GlobalData.patientFullName = this.sharedPreferences.getString("userFullname", "");
        if (this.login_Status.equals("")) {
            this.quickActionIcons = new int[]{C1469R.mipmap.ic_my_appt, C1469R.mipmap.ic_profile, C1469R.mipmap.ic_action_about, C1469R.mipmap.ic_action_help, C1469R.mipmap.ic_action_share, C1469R.mipmap.ic_action_feedback, C1469R.mipmap.ic_action_rate_this_app, C1469R.mipmap.power};
            this.quickActionText = new String[]{"My Appts.", "My Profile", "About", "Help", "Share", "Feedback", "Rate us", "Exit"};
        } else {
            this.quickActionIcons = new int[]{C1469R.mipmap.ic_my_appt, C1469R.mipmap.ic_profile, C1469R.mipmap.ic_action_about, C1469R.mipmap.ic_action_help, C1469R.mipmap.ic_action_share, C1469R.mipmap.ic_action_feedback, C1469R.mipmap.ic_action_rate_this_app, C1469R.mipmap.power, C1469R.mipmap.exit};
            this.quickActionText = new String[]{"My Appts.", "My Profile", "About", "Help", "Share", "Feedback", "Rate us", "Logout", "Exit"};
        }
        this.spldb = new SpecialityDB(getApplicationContext());
        try {
            this.locationManager = (LocationManager) getSystemService("location");
            String provider = this.locationManager.getBestProvider(new Criteria(), true);
            this.location = this.locationManager.getLastKnownLocation(provider);
            if (this.location != null) {
                onLocationChanged(this.location);
            }
            this.locationManager.requestLocationUpdates(provider, 20000, 0.0f, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        controls();
        getCity();
        get_Specialty();
    }

    private void setSpecialty() {
        try {
            if (this.aryspl != null) {
                this.item_specialty = new String[this.aryspl.size()];
                for (int i = 0; i < this.aryspl.size(); i++) {
                    this.item_specialty[i] = ((Cities) this.aryspl.get(i)).Specialty;
                }
                Log.v("2.get specialty from local database    " + this.item_specialty, "" + this.item_specialty.length);
                this.AutoComplete_speciality.setAdapter(new ArrayAdapter(this, 17367050, this.item_specialty));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_Specialty() {
        try {
            this.aryspl = this.spldb.getSpecialty();
            Log.e("select speciality array size    ", "" + this.aryspl.size());
            setSpecialty();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCity() {
        try {
            this.aryView = DataCache.getCity(this.AutoComplete_city.getText().toString(), getApplicationContext());
            Log.e("get city array size    ", "" + this.aryView.size());
            setcity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setcity() {
        try {
            if (this.aryView != null) {
                this.item_city = new String[this.aryView.size()];
                for (int i = 0; i < this.aryView.size(); i++) {
                    this.item_city[i] = ((Cities) this.aryView.get(i)).city;
                }
                this.AutoComplete_city.setAdapter(new ArrayAdapter(this, 17367050, this.item_city));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void controls() {
        this.topTitle = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        if (GlobalData.patientFullName.equalsIgnoreCase("")) {
            this.topTitle.setText("Welcome Guest");
        } else {
            this.topTitle.setText("Welcome " + GlobalData.patientFullName);
        }
        this.AutoComplete_speciality = (AutoCompleteTextView) findViewById(C1469R.id.editTxt_speciality);
        this.AutoComplete_speciality.setHintTextColor(getResources().getColor(C1469R.color.light_gray));
        this.AutoComplete_speciality.addTextChangedListener(new C14371());
        this.AutoComplete_DoctorsName = (AutoCompleteTextView) findViewById(C1469R.id.editTxt_DoctorsName);
        this.AutoComplete_DoctorsName.setHintTextColor(getResources().getColor(C1469R.color.light_gray));
        this.AutoComplete_DoctorsName.addTextChangedListener(new C14382());
        this.AutoComplete_city = (AutoCompleteTextView) findViewById(C1469R.id.editTxt_city);
        this.AutoComplete_city.setHintTextColor(getResources().getColor(C1469R.color.light_gray));
        this.AutoComplete_city.setText(GlobalData.CurrentCity);
        this.AutoComplete_city.addTextChangedListener(new C14393());
        this.AutoComplete_speciality.setText(this.cacheSpecialty);
        this.AutoComplete_city.setText(this.cacheCity);
        this.AutoComplete_DoctorsName.setText(this.cacheDoctorName);
        this.reset_Btn = (Button) findViewById(C1469R.id.reset_btn);
        this.reset_Btn.setOnClickListener(new C14404());
        this.search_Btn = (Button) findViewById(C1469R.id.search_btn);
        this.search_Btn.setOnClickListener(new C14415());
        this.topbar_back = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.topbar_back.setOnClickListener(new C14426());
        this.topBar_settings = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.topBar_settings.setOnClickListener(new C14437());
    }

    public void initializePopUp(View view) {
        this.quickActionView = QuickActionView.Builder(view);
        this.quickActionView.setAdapter(new PopUpAdapter(getBaseContext()));
        this.quickActionView.setNumColumns(1);
        this.quickActionView.setAnimStyle(2);
        this.quickActionView.show();
        this.quickActionView.setOnClickListener(new C14448());
    }

    public void showAlertDialog(Context context, String message, Boolean status) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton("Yes", new C14459());
        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void logOut() {
        Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.commit();
        GlobalData.UserName = "";
        GlobalData.patientFullName = "";
        startActivity(new Intent(getBaseContext(), LoginActivity.class).putExtra("TAG", "MainScreen"));
        finish();
    }

    public void showAlertDialog(Context context, String message, int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    protected int createParameter() {
        String vijay;
        if (this.AutoComplete_speciality.getText().toString().length() != 0 && this.AutoComplete_DoctorsName.getText().toString().length() != 0 && this.AutoComplete_city.getText().toString().length() != 0) {
            Log.v("1. create parameter for ", "spl DoctorsName city");
            vijay = GlobalData.vijayData(GlobalData.GetKey);
            this.resultConn = HttpCall.getSplDoctorsNameCityConnection(getBaseContext(), GlobalData.base64Encryption(this.AutoComplete_speciality.getText().toString().trim()), GlobalData.base64Encryption(this.AutoComplete_DoctorsName.getText().toString().trim()), GlobalData.base64Encryption(this.AutoComplete_city.getText().toString().trim()), vijay);
        } else if (this.AutoComplete_DoctorsName.getText().toString().length() != 0 && this.AutoComplete_city.getText().toString().length() != 0) {
            Log.v("2. create parameter for ", " DoctorsName city ");
            vijay = GlobalData.vijayData(GlobalData.GetKey);
            this.resultConn = HttpCall.getDoctorsNameCityConnection(getApplicationContext(), GlobalData.base64Encryption(this.AutoComplete_DoctorsName.getText().toString().trim()), GlobalData.base64Encryption(this.AutoComplete_city.getText().toString().trim()), vijay);
        } else if (this.AutoComplete_speciality.getText().toString().length() != 0 && this.AutoComplete_city.getText().toString().length() != 0) {
            Log.v("3. create parameter for ", " spl city ");
            vijay = GlobalData.vijayData(GlobalData.GetKey);
            this.resultConn = HttpCall.getSplCityConnection(getApplicationContext(), GlobalData.base64Encryption(this.AutoComplete_speciality.getText().toString().trim()), GlobalData.base64Encryption(this.AutoComplete_city.getText().toString().trim()), vijay);
        } else if (this.AutoComplete_city.getText().toString().length() != 0) {
            Log.v("4. create parameter for ", " DoctorsName ");
            this.resultConn = HttpCall.getDoctorsNameConnection(getApplicationContext(), GlobalData.base64Encryption(this.AutoComplete_city.getText().toString().trim()));
        }
        return this.resultConn;
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 100:
                return new Builder(this).setTitle("Find A Doctor").setMessage("Do you want to exit?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                        MainActivity.this.sharedPreferences.edit().putString(DBConstants.CITY, "").commit();
                        MainActivity.this.sharedPreferences.edit().putString("Specialty", "").commit();
                        MainActivity.this.sharedPreferences.edit().putString("DoctorName", "").commit();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
            default:
                return null;
        }
    }

    public void onBackPressed() {
        if (this.getDoctorList.getStatus() != Status.RUNNING) {
            showDialog(100);
        }
    }

    protected void onDestroy() {
        this.locationManager.removeUpdates(this);
        this.locationManager = null;
        super.onDestroy();
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            ((InputMethodManager) getSystemService("input_method")).showSoftInput(view, 1);
        }
    }

    public void onLocationChanged(Location location) {
        try {
            List<Address> addresses = new Geocoder(this, Locale.ENGLISH).getFromLocation(location.getLatitude(), location.getLongitude(), 10);
            if (addresses != null) {
                String city = "";
                Address fetchedAddress = (Address) addresses.get(0);
                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                    Log.e("fetchedAddress.getLocality  ", "   " + fetchedAddress.getLocality());
                    city = fetchedAddress.getLocality();
                    GlobalData.CurrentCity = city;
                }
                try {
                    this.AutoComplete_city.setText(city);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("strAddress  ", " " + strAddress);
                return;
            }
            Log.e("strAddress      ", " No location found..! ");
            GlobalData.CurrentCity = "";
        } catch (IOException e2) {
            GlobalData.CurrentCity = "";
            e2.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onStart() {
        super.onStart();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
}
