package org.the3hackers.a3hacks;
import Database.DBConstants;
import Database.SpecialityDB;
import Opertaion.CheckDataBase;
import Opertaion.Constants;
import Opertaion.GlobalData;
import Opertaion.SplitJson;
import Services.HttpCall;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.drive.DriveFile;

public class SplashActivity extends Activity {
    public static final String TAG = "Splash Activity";
    String AndroidVersion;
    String CountryISO;
    String Current_countryName;
    String ModelNumber;
    String OperatorName;
    String UDid;
    ConnectionDetector cd;
    int count;
    private BroadcastReceiver mHandleMessageReceiver = new C14918();
    AsyncTask<Void, Void, Void> mRegisterTask;
    double screenSize;
    SharedPreferences sharedPreferences;
    SpecialityDB spl_DB;
    TelephonyManager tel;
    int value;

    class C14852 extends AsyncTask<Void, Void, Integer> {
        int resultConn = -1;

        C14852() {
        }

        protected Integer doInBackground(Void... params) {
            this.resultConn = HttpCall.getKeyRequst();
            if (this.resultConn == 1) {
                this.resultConn = SplitJson.splitKey(GlobalData.sDownloadedContent);
            }
            return Integer.valueOf(this.resultConn);
        }

        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (this.resultConn == 1) {
                Log.e(" SplashActivity   ", "  CONN_SUCCESS");
            } else if (this.resultConn == 4) {
                Log.e(" SplashActivity   ", SplashActivity.this.getString(C1469R.string.SERVER_PROBLEM_MSG));
                SplashActivity.this.showAlertDialog(SplashActivity.this, SplashActivity.this.getString(C1469R.string.SERVER_PROBLEM_MSG, new Object[]{Integer.valueOf(1)}));
            } else if (this.resultConn == 11) {
                SplashActivity.this.showAlertDialog(SplashActivity.this, SplashActivity.this.getString(C1469R.string.INVALID_DATA_MSG, new Object[]{Integer.valueOf(1)}));
                Log.e(" SplashActivity   ", SplashActivity.this.getString(C1469R.string.INVALID_DATA_MSG));
            } else {
                SplashActivity.this.showAlertDialog(SplashActivity.this, SplashActivity.this.getString(C1469R.string.Network_ErrorMsg, new Object[]{Integer.valueOf(1)}));
                Log.e(" SplashActivity   ", SplashActivity.this.getString(C1469R.string.Network_ErrorMsg));
            }
        }
    }

    class C14863 extends AsyncTask<Void, Void, Integer> {
        int resultConn = -1;

        C14863() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Integer doInBackground(Void... params) {
            Log.v("doInBackground ", " YES");
            try {
                this.resultConn = CheckDataBase.checkDataBase(SplashActivity.this, SplashActivity.this.spl_DB);
                SplashActivity.this.spl_DB.closeDb();
            } catch (Exception e) {
                this.resultConn = 0;
            }
            return null;
        }

        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            Log.v("onPostExecute ", " YES");
            if (this.resultConn == 1) {
                Log.e(SplashActivity.TAG, "Data Base Created..!");
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else if (this.resultConn == 3) {
                Toast.makeText(SplashActivity.this, "Error in creating database", 0).show();
            } else if (this.resultConn == 2) {
                SplashActivity.this.monthlyUpdate();
            }
            SplashActivity.this.finish();
        }
    }

    class C14874 extends AsyncTask<Void, Void, Integer> {
        int resultConn = -1;

        C14874() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Integer doInBackground(Void... params) {
            this.resultConn = HttpCall.getSpeciality(SplashActivity.this.getApplicationContext(), GlobalData.vijayData(GlobalData.GetKey), GlobalData.base64Encryption("speciality-list"), GlobalData.base64Encryption("Android"));
            if (this.resultConn == 1) {
                this.resultConn = SplitJson.getLastModifiedDate(GlobalData.sDownloadedContent, SplashActivity.this.getApplicationContext());
            }
            return Integer.valueOf(this.resultConn);
        }

        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            Log.e("onPostExecute   ", "     Yes");
            if (this.resultConn == 1) {
                String modifiedDate = SplashActivity.this.spl_DB.getLastModifiedDate();
                Log.e("modifiedDate  from local db ", "" + modifiedDate);
                Log.e("GlobalData.lastUpdate   ", "" + GlobalData.lastUpdate);
                if (GlobalData.lastUpdate.equalsIgnoreCase(modifiedDate)) {
                    Log.v("pass the next activity  MainActivity ", "yessssss");
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                    return;
                }
                Log.e("update Specialty from server ", "yessssss");
                SplashActivity.this.spl_DB.delete(DBConstants.TABLE_SPECIALITY, null);
                SplashActivity.this.spl_DB.delete(DBConstants.TABLE_LAST_MODIFIED, null);
                SplashActivity.this.spl_DB.deleteDatabase(SplashActivity.this);
                Log.e("table are successfully deleted ", "yessssss");
                SplashActivity.this.specialtyService();
                return;
            }
            SplashActivity.this.showAlertDialog(SplashActivity.this, SplashActivity.this.getString(C1469R.string.Network_ErrorMsg, new Object[]{Integer.valueOf(1)}));
            SplashActivity.this.finish();
        }
    }

    class C14885 extends AsyncTask<Void, Void, Integer> {
        int resultConn = -1;

        C14885() {
        }

        protected Integer doInBackground(Void... params) {
            this.resultConn = HttpCall.getSpeciality(SplashActivity.this.getApplicationContext(), GlobalData.vijayData(GlobalData.GetKey), GlobalData.base64Encryption("speciality-list"), GlobalData.base64Encryption("Android"));
            if (this.resultConn == 1) {
                this.resultConn = SplitJson.splitJsonData(GlobalData.sDownloadedContent, SplashActivity.this.getApplicationContext());
            }
            return Integer.valueOf(this.resultConn);
        }

        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (this.resultConn == 1) {
                Log.e("CONN_SUCCESS     -----------", " specialty data successfully fetch from server");
                Intent in = new Intent(SplashActivity.this, MainActivity.class);
                in.addFlags(67108864);
                in.addFlags(DriveFile.MODE_READ_ONLY);
                SplashActivity.this.startActivity(in);
            } else if (this.resultConn == 4) {
                Log.e(" specialty data    -----------", "Network error in specialty process");
                SplashActivity.this.showAlertDialog(SplashActivity.this, SplashActivity.this.getString(C1469R.string.Network_ErrorMsg, new Object[]{Integer.valueOf(1)}));
            } else if (this.resultConn == 22) {
                SplashActivity.this.showAlertDialog(SplashActivity.this, SplashActivity.this.getString(C1469R.string.Network_ErrorMsg, new Object[]{Integer.valueOf(1)}));
            } else {
                SplashActivity.this.showAlertDialog(SplashActivity.this, SplashActivity.this.getString(C1469R.string.Network_ErrorMsg, new Object[]{Integer.valueOf(1)}));
            }
        }
    }

    class C14896 implements OnClickListener {
        C14896() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class C14907 implements OnClickListener {
        C14907() {
        }

        public void onClick(DialogInterface dialog, int id) {
            SplashActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
            SplashActivity.this.finish();
        }
    }

    class C14918 extends BroadcastReceiver {
        C14918() {
        }

        public void onReceive(Context context, Intent intent) {
            try {
                GlobalData.notificationTxt = intent.getExtras().getString("message").toString();
                Log.e("onReceive newMessage in splash Activity ", GlobalData.notificationTxt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_splash);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        GlobalData.screenSize = GlobalData.getMobileScreenSize(getApplicationContext());
        GlobalData.UserName = GlobalData.getUserName(getApplicationContext());
        GlobalData.initializeDatas(this);
        this.spl_DB = new SpecialityDB(getApplicationContext());
        checkNotNull("http://apps.medindia.net/notification/Register.aspx?", "SERVER_URL");
        checkNotNull("807689892411", "SENDER_ID");
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        this.tel = (TelephonyManager) getSystemService("phone");
        GlobalData.UDid = System.getString(super.getContentResolver(), "android_id");
        GlobalData.ModelNumber = Build.MODEL;
        GlobalData.AndroidVersion = VERSION.RELEASE;
        GlobalData.OperatorName = this.tel.getNetworkOperatorName().toString();
        GlobalData.CountryISO = this.tel.getSimCountryIso().toString();
        GlobalData.Current_countryName = getApplicationContext().getResources().getConfiguration().locale.getDisplayCountry();
        Log.v("Current countryName  ", GlobalData.Current_countryName);
        Log.v("country Name    ", getApplicationContext().getResources().getConfiguration().locale.getDisplayCountry());
        this.cd = new ConnectionDetector(getApplicationContext());
        if (this.cd.isConnectingToInternet()) {
            getKey();
            registerReceiver(this.mHandleMessageReceiver, new IntentFilter("com.medindia.app.main.DISPLAY_MESSAGE"));
            startService(new Intent(this, SmsService.class));
            final String regId = GCMRegistrar.getRegistrationId(this);
            Log.e("GCM reg ID    ", regId);
            if (regId.equals("")) {
                GCMRegistrar.register(this, "807689892411");
            } else if (GCMRegistrar.isRegisteredOnServer(this)) {
                Log.e(" GCMRegistrar.isRegisteredOnServer   ", "    Device is already registered.");
            } else {
                final Context context = this;
                this.mRegisterTask = new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        if (!HttpCall.sendData("http://apps.medindia.net/notification/Register.aspx?", regId)) {
                            GCMRegistrar.unregister(context);
                        }
                        return null;
                    }

                    protected void onPostExecute(Void result) {
                        SplashActivity.this.mRegisterTask = null;
                    }
                };
                this.mRegisterTask.execute(new Void[]{null, null, null});
            }
            this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
            this.value = this.sharedPreferences.getInt("numRun", 0);
            Log.e(" App Launcher count ......>>> ", "" + this.value);
            checkDatabase();
            return;
        }
        Log.e("aSas", "Connection");
        showDialog();
    }

    private void getKey() {
        new C14852().execute(new Void[]{null, null, null});
    }

    private void checkDatabase() {
        new C14863().execute(new Void[]{null, null, null});
    }

    protected void monthlyUpdate() {
        String str = this.spl_DB.getLastModifiedMonth();
        String getMonth1 = String.valueOf(GlobalData.getMonth());
        Log.e("monthlyUpdate condition   global data", "    " + GlobalData.getMonth());
        Log.e("monthlyUpdate condition form database   ", "" + str);
        Log.e("monthlyUpdate condition ??????????????  ", "" + getMonth1.equalsIgnoreCase(str));
        if (!getMonth1.equalsIgnoreCase(str)) {
            new C14874().execute(new Void[]{null, null, null});
        } else if (this.value > 500000000) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void specialtyService() {
        new C14885().execute(new Void[]{null, null, null});
    }

    public void showAlertDialog(Context context, String message) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new C14896());
        alertDialog.show();
    }

    private void showDialog() {
        Builder builder = new Builder(this);
        builder.setTitle("Internet Connection Error").setMessage("Required Internet Connection for use..!!").setCancelable(false).setNegativeButton("GO to Settings", new C14907());
        builder.create().show();
    }

    protected void onDestroy() {
        if (this.mRegisterTask != null) {
            this.mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(this.mHandleMessageReceiver);
            GCMRegistrar.onDestroy(getApplicationContext());
        } catch (IllegalArgumentException e) {
            Log.e("IllegalArgumentException in OnDestroy Method Splash Screen  ", "   " + e);
        } catch (Exception e2) {
            Log.e("Exception in OnDestroy Method Splash Screen ", "  " + e2);
        }
        super.onDestroy();
    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(getString(C1469R.string.error_config, new Object[]{name}));
        }
    }
}
