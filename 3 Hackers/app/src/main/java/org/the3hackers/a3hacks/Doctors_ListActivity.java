package org.the3hackers.a3hacks;
import Adapter.MyAdapter;
import Model.DoctorList;
import Opertaion.Constants;
import Opertaion.GlobalData;
import Popupview.ActionItemText;
import Popupview.QuickActionView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.drive.DriveFile;
import java.util.ArrayList;
import java.util.List;

public class Doctors_ListActivity extends Activity {
    Activity act = null;
    RelativeLayout backbutton;
    Dialog dialog;
    ListView doctorList;
    RecyclerView doctorList_recyclerView;
    String login_Status;
    ArrayList<DoctorList> myList;
    int[] quickActionIcons;
    String[] quickActionText;
    QuickActionView quickActionView;
    SharedPreferences sharedPreferences;
    RelativeLayout topBar_doctorsList_settings;
    TextView topTitle;

    class C13991 implements OnClickListener {
        C13991() {
        }

        public void onClick(View v) {
            Doctors_ListActivity.this.initializePopUp(v);
        }
    }

    class C14002 implements OnClickListener {
        C14002() {
        }

        public void onClick(View v) {
            Doctors_ListActivity.this.onBackPressed();
        }
    }

    class C14013 implements DialogInterface.OnClickListener {
        C14013() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if (Doctors_ListActivity.this.login_Status.equals("")) {
                switch (which) {
                    case 0:
                        Log.e("Patient History", " Yes");
                        Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), PatientHistory.class).putExtra("TAG", "DoctorListScreen"));
                        return;
                    case 1:
                        Log.e("PopUP Exit ", " Yes");
                        Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", "Profile_DoctorListScreen"));
                        return;
                    case 2:
                        Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), AboutActivity.class));
                        Log.e("PopUP ABOUT ", " Yes");
                        return;
                    case 3:
                        Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), HelpActivity.class));
                        Log.e("PopUP HELP ", "  Yes");
                        return;
                    case 4:
                        Log.e("PopUP SHARE ", " Yes");
                        GlobalData.generalShare(Doctors_ListActivity.this.getApplicationContext());
                        return;
                    case 5:
                        Log.e("PopUP FEEDBACK ", " Yes");
                        Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), FeedBackActivity.class).addFlags(32768).addFlags(67108864));
                        return;
                    case 6:
                        Log.e("PopUP Rate us ", " Yes");
                        GlobalData.rateThisApp(Doctors_ListActivity.this.getApplicationContext());
                        return;
                    default:
                        return;
                }
            }
            switch (which) {
                case 0:
                    Log.e("Patient History", " Yes");
                    Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), PatientHistory.class).putExtra("TAG", "DoctorListScreen"));
                    return;
                case 1:
                    Log.e("PopUP Exit ", " Yes");
                    Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", "Profile_DoctorListScreen"));
                    return;
                case 2:
                    Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), AboutActivity.class));
                    Log.e("PopUP ABOUT ", " Yes");
                    return;
                case 3:
                    Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), HelpActivity.class));
                    Log.e("PopUP HELP ", "  Yes");
                    return;
                case 4:
                    Log.e("PopUP SHARE ", " Yes");
                    GlobalData.generalShare(Doctors_ListActivity.this.getApplicationContext());
                    return;
                case 5:
                    Log.e("PopUP FEEDBACK ", " Yes");
                    Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), FeedBackActivity.class).addFlags(32768).addFlags(67108864));
                    return;
                case 6:
                    Log.e("PopUP Rate us ", " Yes");
                    GlobalData.rateThisApp(Doctors_ListActivity.this.getApplicationContext());
                    return;
                case 7:
                    Log.e("Logout ", " Yes");
                    Doctors_ListActivity.this.showAlertDialog(Doctors_ListActivity.this, "Do you want to logout now ?", Boolean.valueOf(true));
                    return;
                default:
                    return;
            }
        }
    }

    class C14024 implements DialogInterface.OnClickListener {
        C14024() {
        }

        public void onClick(DialogInterface dialog, int which) {
            GlobalData.logOut(Doctors_ListActivity.this.getApplicationContext());
            Doctors_ListActivity.this.startActivity(new Intent(Doctors_ListActivity.this.getBaseContext(), LoginActivity.class).putExtra("TAG", "doctorListClass"));
        }
    }

    class C14035 implements DialogInterface.OnClickListener {
        C14035() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class PopUpAdapter extends BaseAdapter {
        List<ActionItemText> mItems = new ArrayList();
        LayoutInflater mLayoutInflater;

        public PopUpAdapter(Context context) {
            this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            for (int i = 0; i < Doctors_ListActivity.this.quickActionText.length; i++) {
                this.mItems.add(new ActionItemText(context, Doctors_ListActivity.this.quickActionText[i], Doctors_ListActivity.this.quickActionIcons[i]));
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
        setContentView(C1469R.layout.activity_doctors_list);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        this.login_Status = this.sharedPreferences.getString("login_Status", "");
        if (this.login_Status.equals("")) {
            this.quickActionIcons = new int[]{C1469R.mipmap.ic_my_appt, C1469R.mipmap.ic_profile, C1469R.mipmap.ic_action_about, C1469R.mipmap.ic_action_help, C1469R.mipmap.ic_action_share, C1469R.mipmap.ic_action_feedback, C1469R.mipmap.ic_action_rate_this_app};
            this.quickActionText = new String[]{"My Appts.", "My Profile", "About", "Help", "Share", "Feedback", "Rate Us"};
        } else {
            this.quickActionIcons = new int[]{C1469R.mipmap.ic_my_appt, C1469R.mipmap.ic_profile, C1469R.mipmap.ic_action_about, C1469R.mipmap.ic_action_help, C1469R.mipmap.ic_action_share, C1469R.mipmap.ic_action_feedback, C1469R.mipmap.ic_action_rate_this_app, C1469R.mipmap.power};
            this.quickActionText = new String[]{"My Appts.", "My Profile", "About", "Help", "Share", "Feedback", "Rate Us", "Logout"};
        }
        this.act = this;
        this.myList = new ArrayList();
        controls();
    }

    private void controls() {
        this.topTitle = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        if (GlobalData.patientFullName.equalsIgnoreCase("")) {
            this.topTitle.setText("Welcome Guest");
        } else {
            this.topTitle.setText("Welcome " + GlobalData.patientFullName);
        }
        this.doctorList_recyclerView = (RecyclerView) findViewById(C1469R.id.doctorlist_recyclerView);
        this.doctorList_recyclerView.setAdapter(new MyAdapter(this.act, GlobalData.sMainAry));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(1);
        this.doctorList_recyclerView.setLayoutManager(layoutManager);
        this.doctorList_recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.topBar_doctorsList_settings = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.topBar_doctorsList_settings.setOnClickListener(new C13991());
        this.backbutton = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.backbutton.setOnClickListener(new C14002());
    }

    public void initializePopUp(View view) {
        this.quickActionView = QuickActionView.Builder(view);
        this.quickActionView.setAdapter(new PopUpAdapter(getBaseContext()));
        this.quickActionView.setNumColumns(1);
        this.quickActionView.setAnimStyle(2);
        this.quickActionView.show();
        this.quickActionView.setOnClickListener(new C14013());
    }

    public void showAlertDialog(Context context, String message, Boolean status) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton("Yes", new C14024());
        alertDialog.setButton2("No", new C14035());
        alertDialog.show();
    }

    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
        finish();
    }
}
