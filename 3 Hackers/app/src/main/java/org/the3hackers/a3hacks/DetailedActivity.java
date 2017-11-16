package org.the3hackers.a3hacks;
import Opertaion.Constants;
import Opertaion.GlobalData;
import Popupview.ActionItemText;
import Popupview.QuickActionView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends FragmentActivity {
    public static FragmentManager fragmentManager;
    Animation animation;
    RelativeLayout backBtn;
    int count;
    Fragment fr = null;
    String login_Status;
    Button mapBtn;
    Button profileBtn;
    int[] quickActionIcons;
    String[] quickActionText;
    QuickActionView quickActionView;
    SharedPreferences sharedPreferences;
    RelativeLayout topBar_doctorsDetails_settings;
    TextView topTitle;

    class C13821 implements OnClickListener {
        C13821() {
        }

        public void onClick(View v) {
            DetailedActivity.this.onBackPressed();
        }
    }

    class C13832 implements OnClickListener {
        C13832() {
        }

        public void onClick(View v) {
            try {
                DetailedActivity.this.fr = new DetailsView();
                FragmentTransaction fragmentTransaction = DetailedActivity.this.getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(C1469R.anim.card_flip_right_in, C1469R.anim.card_flip_right_out, C1469R.anim.card_flip_left_in, C1469R.anim.card_flip_left_out);
                fragmentTransaction.replace(C1469R.id.fragment_place, DetailedActivity.this.fr);
                fragmentTransaction.commit();
                DetailedActivity.this.profileBtn.setBackgroundResource(C1469R.drawable.abc_ab_bottom_transparent_dark_holo);
                DetailedActivity.this.mapBtn.setBackgroundColor(DetailedActivity.this.getResources().getColor(17170445));
                DetailedActivity.this.profileBtn.setEnabled(false);
                DetailedActivity.this.mapBtn.setEnabled(true);
            } catch (Exception e) {
                Log.e("Error in select profile button   ", "" + e);
            }
        }
    }

    class C13843 implements OnClickListener {
        C13843() {
        }

        public void onClick(View v) {
            try {
                DetailedActivity.this.mapBtn.setBackgroundResource(C1469R.drawable.abc_ab_bottom_transparent_dark_holo);
                DetailedActivity.this.profileBtn.setBackgroundColor(DetailedActivity.this.getResources().getColor(17170445));
                Log.e("in side the loop DoctorsMapView ", "  yes");
                DetailedActivity.this.fr = new DoctorsMap();
                FragmentTransaction fragmentTransaction = DetailedActivity.this.getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(C1469R.anim.card_flip_right_in, C1469R.anim.card_flip_right_out, C1469R.anim.card_flip_left_in, C1469R.anim.card_flip_left_out);
                fragmentTransaction.replace(C1469R.id.fragment_place, DetailedActivity.this.fr);
                fragmentTransaction.commit();
                DetailedActivity.this.mapBtn.setEnabled(false);
                DetailedActivity.this.profileBtn.setEnabled(true);
            } catch (Exception e) {
                Log.e("Error in select map button   ", "" + e);
            }
        }
    }

    class C13854 implements OnClickListener {
        C13854() {
        }

        public void onClick(View v) {
            DetailedActivity.this.initializePopUp(v);
        }
    }

    class C13865 implements DialogInterface.OnClickListener {
        C13865() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            if (DetailedActivity.this.login_Status.equals("")) {
                switch (which) {
                    case 0:
                        Log.e("Patient History", " Yes");
                        DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), PatientHistory.class).putExtra("TAG", "DoctorListScreen"));
                        return;
                    case 1:
                        Log.e("PopUP Exit ", " Yes");
                        DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", "Profile_DoctorListScreen"));
                        return;
                    case 2:
                        DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), AboutActivity.class));
                        Log.e("PopUP ABOUT ", " Yes");
                        return;
                    case 3:
                        DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), HelpActivity.class));
                        Log.e("PopUP HELP ", "  Yes");
                        return;
                    case 4:
                        Log.e("PopUP SHARE ", " Yes");
                        GlobalData.generalShare(DetailedActivity.this.getApplicationContext());
                        return;
                    case 5:
                        Log.e("PopUP FEEDBACK ", " Yes");
                        DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), FeedBackActivity.class).addFlags(32768).addFlags(67108864));
                        return;
                    case 6:
                        Log.e("PopUP Rate us ", " Yes");
                        GlobalData.rateThisApp(DetailedActivity.this.getApplicationContext());
                        return;
                    default:
                        return;
                }
            }
            switch (which) {
                case 0:
                    Log.e("Patient History", " Yes");
                    DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), PatientHistory.class).putExtra("TAG", "DoctorListScreen"));
                    return;
                case 1:
                    Log.e("PopUP Exit ", " Yes");
                    DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", "Profile_DoctorListScreen"));
                    return;
                case 2:
                    DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), AboutActivity.class));
                    Log.e("PopUP ABOUT ", " Yes");
                    return;
                case 3:
                    DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), HelpActivity.class));
                    Log.e("PopUP HELP ", "  Yes");
                    return;
                case 4:
                    Log.e("PopUP SHARE ", " Yes");
                    GlobalData.generalShare(DetailedActivity.this.getApplicationContext());
                    return;
                case 5:
                    Log.e("PopUP FEEDBACK ", " Yes");
                    DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), FeedBackActivity.class).addFlags(32768).addFlags(67108864));
                    return;
                case 6:
                    Log.e("PopUP Rate us ", " Yes");
                    GlobalData.rateThisApp(DetailedActivity.this.getApplicationContext());
                    return;
                case 7:
                    Log.e("Logout ", " Yes");
                    DetailedActivity.this.showAlertDialog(DetailedActivity.this, "Do you want to Logout now ?", Boolean.valueOf(true));
                    return;
                default:
                    return;
            }
        }
    }

    class C13876 implements DialogInterface.OnClickListener {
        C13876() {
        }

        public void onClick(DialogInterface dialog, int which) {
            GlobalData.logOut(DetailedActivity.this.getApplicationContext());
            DetailedActivity.this.startActivity(new Intent(DetailedActivity.this.getBaseContext(), LoginActivity.class).putExtra("TAG", "doctorDetailsClass"));
        }
    }

    class C13887 implements DialogInterface.OnClickListener {
        C13887() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class PopUpAdapter extends BaseAdapter {
        List<ActionItemText> mItems = new ArrayList();
        LayoutInflater mLayoutInflater;

        public PopUpAdapter(Context context) {
            this.mLayoutInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            for (int i = 0; i < DetailedActivity.this.quickActionText.length; i++) {
                this.mItems.add(new ActionItemText(context, DetailedActivity.this.quickActionText[i], DetailedActivity.this.quickActionIcons[i]));
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
        setContentView(C1469R.layout.details_topbar);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        this.animation = AnimationUtils.loadAnimation(this, C1469R.anim.flipinnext);
        this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        this.login_Status = this.sharedPreferences.getString("login_Status", "");
        if (this.login_Status.equals("")) {
            this.quickActionIcons = new int[]{C1469R.mipmap.ic_my_appt, C1469R.mipmap.ic_profile, C1469R.mipmap.ic_action_about, C1469R.mipmap.ic_action_help, C1469R.mipmap.ic_action_share, C1469R.mipmap.ic_action_feedback, C1469R.mipmap.ic_action_rate_this_app};
            this.quickActionText = new String[]{"My Appts.", "My Profile", "About", "Help", "Share", "Feedback", "Rate Us"};
        } else {
            this.quickActionIcons = new int[]{C1469R.mipmap.ic_my_appt, C1469R.mipmap.ic_profile, C1469R.mipmap.ic_action_about, C1469R.mipmap.ic_action_help, C1469R.mipmap.ic_action_share, C1469R.mipmap.ic_action_feedback, C1469R.mipmap.ic_action_rate_this_app, C1469R.mipmap.power};
            this.quickActionText = new String[]{"My Appts.", "My Profile", "About", "Help", "Share", "Feedback", "Rate Us", "Logout"};
        }
        int count = this.sharedPreferences.getInt("numRun", 0) + 1;
        this.sharedPreferences.edit().putInt("numRun", count).commit();
        Log.e(" App Launcher count ......>>> ", "" + count);
        Controls();
        fragmentManager = getSupportFragmentManager();
        try {
            this.profileBtn.setBackgroundResource(C1469R.drawable.abc_ab_bottom_transparent_dark_holo);
            this.mapBtn.setBackgroundColor(getResources().getColor(17170445));
            this.fr = new DetailsView();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(C1469R.id.fragment_place, this.fr);
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.e("Error in select profile button   ", "" + e);
        }
    }

    private void Controls() {
        this.topTitle = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        if (GlobalData.patientFullName.equalsIgnoreCase("")) {
            this.topTitle.setText("Welcome Guest");
        } else {
            this.topTitle.setText("Welcome " + GlobalData.patientFullName);
        }
        this.backBtn = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.backBtn.setOnClickListener(new C13821());
        this.profileBtn = (Button) findViewById(C1469R.id.btn_profile);
        this.profileBtn.setEnabled(false);
        this.profileBtn.setOnClickListener(new C13832());
        this.mapBtn = (Button) findViewById(C1469R.id.btn_map);
        this.mapBtn.setOnClickListener(new C13843());
        this.topBar_doctorsDetails_settings = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.topBar_doctorsDetails_settings.setOnClickListener(new C13854());
    }

    public void initializePopUp(View view) {
        this.quickActionView = QuickActionView.Builder(view);
        this.quickActionView.setAdapter(new PopUpAdapter(getBaseContext()));
        this.quickActionView.setNumColumns(1);
        this.quickActionView.setAnimStyle(2);
        this.quickActionView.show();
        this.quickActionView.setOnClickListener(new C13865());
    }

    public void showAlertDialog(Context context, String message, Boolean status) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setButton("Yes", new C13876());
        alertDialog.setButton2("No", new C13887());
        alertDialog.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
