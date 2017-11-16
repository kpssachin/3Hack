package org.the3hackers.a3hacks;
import Adapter.PatientHistoryAdapter;
import Opertaion.Constants;
import Opertaion.GlobalData;
import Opertaion.SplitJson;
import Popupview.ActionItemText;
import Popupview.QuickActionView;
import Services.Connection;
import Services.HttpCall;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.drive.DriveFile;
import java.util.ArrayList;
import java.util.List;

public class PatientHistory extends Activity {
    ImageView BackBtn;
    String Tag = "";
    String UserName;
    RelativeLayout backbutton;
    LinearLayoutManager layoutManager = null;
    String login_Status;
    PatientHistoryAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView patientHistoryView;
    int[] quickActionIcons = new int[]{C1469R.mipmap.ic_profile, C1469R.mipmap.ic_action_about, C1469R.mipmap.ic_action_help, C1469R.mipmap.ic_action_share, C1469R.mipmap.ic_action_feedback, C1469R.mipmap.ic_action_rate_this_app, C1469R.mipmap.power};
    String[] quickActionText = new String[]{"My Profile", "About", "Help", "Share", "Feedback", "Rate us", "Logout"};
    QuickActionView quickActionView;
    SharedPreferences sharedPreferences;
    TextView titleTxt;
    RelativeLayout topBar_doctorsList_settings;

    class C14481 implements OnRefreshListener {

        class C14471 implements Runnable {
            C14471() {
            }

            public void run() {
                PatientHistory.this.getPatientHistory(PatientHistory.this.UserName);
                PatientHistory.this.mSwipeRefreshLayout.setRefreshing(false);
            }
        }

        C14481() {
        }

        public void onRefresh() {
            PatientHistory.this.mSwipeRefreshLayout.setRefreshing(true);
            new Handler().postDelayed(new C14471(), 100);
        }
    }

    class C14492 implements OnClickListener {
        C14492() {
        }

        public void onClick(View v) {
            PatientHistory.this.initializePopUp(v);
        }
    }

    class C14503 implements OnClickListener {
        C14503() {
        }

        public void onClick(View v) {
            PatientHistory.this.onBackPressed();
        }
    }

    class C14536 implements DialogInterface.OnClickListener {
        C14536() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            switch (which) {
                case 0:
                    PatientHistory.this.startActivity(new Intent(PatientHistory.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", "myAppointments"));
                    Log.e("PopUP ABOUT ", " Yes");
                    return;
                case 1:
                    PatientHistory.this.startActivity(new Intent(PatientHistory.this.getBaseContext(), AboutActivity.class));
                    Log.e("PopUP ABOUT ", " Yes");
                    return;
                case 2:
                    PatientHistory.this.startActivity(new Intent(PatientHistory.this.getBaseContext(), HelpActivity.class));
                    Log.e("PopUP HELP ", "  Yes");
                    return;
                case 3:
                    Log.e("PopUP SHARE ", " Yes");
                    GlobalData.generalShare(PatientHistory.this.getApplicationContext());
                    return;
                case 4:
                    Log.e("PopUP FEEDBACK ", " Yes");
                    PatientHistory.this.startActivity(new Intent(PatientHistory.this.getBaseContext(), FeedBackActivity.class).addFlags(32768).addFlags(67108864));
                    return;
                case 5:
                    Log.e("PopUP Rate us ", " Yes");
                    GlobalData.rateThisApp(PatientHistory.this.getApplicationContext());
                    return;
                case 6:
                    Log.e("Logout ", " Yes");
                    PatientHistory.this.showAlertDialog(PatientHistory.this, "Do you want to Logout now ?", 3);
                    return;
                default:
                    return;
            }
        }
    }

    class PopUpAdapter extends BaseAdapter {
        List<ActionItemText> mItems = new ArrayList();
        LayoutInflater mLayoutInflater;

        public PopUpAdapter(Context context) {
            this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            for (int i = 0; i < PatientHistory.this.quickActionText.length; i++) {
                this.mItems.add(new ActionItemText(context, PatientHistory.this.quickActionText[i], PatientHistory.this.quickActionIcons[i]));
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
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.Tag = extras.getString("TAG");
        }
        this.layoutManager = new LinearLayoutManager(getApplicationContext());
        this.layoutManager.setOrientation(1);
        this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        this.login_Status = this.sharedPreferences.getString("login_Status", "");
        this.UserName = this.sharedPreferences.getString("username", "");
        GlobalData.patient_userName = this.UserName;
        if (this.login_Status.equals("")) {
            startActivity(new Intent(getBaseContext(), LoginActivity.class).putExtra("TAG", this.Tag));
            finish();
        } else {
            getPatientHistory(this.UserName);
        }
        setContentView(C1469R.layout.activity_patient_history);
        initializeControl();
    }

    public void onBackPressed() {
        if (this.Tag.equalsIgnoreCase("MainScreen")) {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
            finish();
        } else if (this.Tag.equalsIgnoreCase("DoctorListScreen")) {
            startActivity(new Intent(getBaseContext(), Doctors_ListActivity.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
            finish();
        } else if (this.Tag.equalsIgnoreCase("DetailsScreen")) {
            startActivity(new Intent(getBaseContext(), DetailedActivity.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
            finish();
        } else {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
            finish();
        }
    }

    private void initializeControl() {
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(C1469R.id.myAppointment_swipe_refresh_layout);
        this.mSwipeRefreshLayout.setOnRefreshListener(new C14481());
        this.titleTxt = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        this.titleTxt.setText("My Appointments");
        this.topBar_doctorsList_settings = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.topBar_doctorsList_settings.setOnClickListener(new C14492());
        this.backbutton = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.backbutton.setOnClickListener(new C14503());
    }

    private void getPatientHistory(final String userName) {
        new AsyncTask<Void, Void, Integer>() {
            ProgressDialog pd;
            int resultConn = -1;

            protected void onPreExecute() {
                super.onPreExecute();
                this.pd = new ProgressDialog(PatientHistory.this);
                this.pd.setMessage("Loading...");
                this.pd.setCanceledOnTouchOutside(false);
                this.pd.show();
            }

            protected Integer doInBackground(Void... params) {
                this.resultConn = HttpCall.createPatientHistoryParameter(userName);
                if (this.resultConn == 1) {
                    this.resultConn = SplitJson.splitPatientHistoryData(GlobalData.sDownloadedContent);
                }
                return Integer.valueOf(this.resultConn);
            }

            protected void onCancelled() {
                super.onCancelled();
                Log.e("Process", "CANCELLED");
                Connection.closeConnection();
            }

            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                this.pd.dismiss();
                if (this.resultConn == 1) {
                    Log.e("Patient Array Size ", "" + GlobalData.mainPatientHistoryArray.size());
                    try {
                        if (GlobalData.mainPatientHistoryArray != null) {
                            PatientHistory.this.setData();
                        }
                    } catch (Exception e) {
                    }
                } else if (this.resultConn == 4) {
                    PatientHistory.this.showAlertDialog(PatientHistory.this, PatientHistory.this.getString(C1469R.string.SERVER_PROBLEM_MSG), 3);
                } else if (this.resultConn == 66) {
                    PatientHistory.this.showAlertDialog(PatientHistory.this, "Reached Limits ...Please Login..??", 3);
                } else if (this.resultConn == 11) {
                    PatientHistory.this.showAlertDialog(PatientHistory.this, "No Appointments Found", 1);
                } else {
                    PatientHistory.this.showAlertDialog(PatientHistory.this, "Ooops! Looks like you have encountered a connectivity problem. Please make sure you are connected and try again.", 2);
                }
            }
        }.execute(new Void[]{null, null, null});
    }

    public void showAlertDialog(Context context, String message, final int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (value == 1) {
                    PatientHistory.this.onBackPressed();
                } else if (value == 2) {
                    PatientHistory.this.getPatientHistory(PatientHistory.this.UserName);
                } else if (value == 3) {
                    GlobalData.logOut(PatientHistory.this.getApplicationContext());
                    PatientHistory.this.startActivity(new Intent(PatientHistory.this.getBaseContext(), LoginActivity.class).putExtra("TAG", "myAppointments"));
                }
            }
        });
        alertDialog.show();
    }

    private void setData() {
        Log.e("Patient Array Size ", "" + GlobalData.mainPatientHistoryArray.size());
        this.patientHistoryView = (RecyclerView) findViewById(C1469R.id.patentHistory_recyclerView);
        this.mAdapter = new PatientHistoryAdapter(GlobalData.mainPatientHistoryArray, this);
        this.patientHistoryView.setLayoutManager(this.layoutManager);
        this.patientHistoryView.setItemAnimator(new DefaultItemAnimator());
        this.patientHistoryView.setHasFixedSize(true);
        this.patientHistoryView.setAdapter(this.mAdapter);
    }

    public void initializePopUp(View view) {
        this.quickActionView = QuickActionView.Builder(view);
        this.quickActionView.setAdapter(new PopUpAdapter(getBaseContext()));
        this.quickActionView.setNumColumns(1);
        this.quickActionView.setAnimStyle(2);
        this.quickActionView.show();
        this.quickActionView.setOnClickListener(new C14536());
    }
}
