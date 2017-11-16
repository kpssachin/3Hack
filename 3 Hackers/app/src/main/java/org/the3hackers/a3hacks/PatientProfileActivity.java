package org.the3hackers.a3hacks;
import Opertaion.Constants;
import Opertaion.GlobalData;
import Opertaion.SplitJson;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.drive.DriveFile;

public class PatientProfileActivity extends Activity {
    String Tag = "";
    TextView TitleTxt;
    TextView UseNameTxt;
    TextView UserAgeTxt;
    TextView UserEmailTxt;
    TextView UserGenderTxt;
    TextView UserMobileTxt;
    String UserName;
    RelativeLayout back_profileRelativeLay;
    LinearLayout editLinear;
    RelativeLayout edit_profileRelativeLay;
    String login_Status;
    ImageView settingsIcon;
    SharedPreferences sharedPreferences;

    class C14571 implements OnClickListener {
        C14571() {
        }

        public void onClick(View v) {
            PatientProfileActivity.this.onBackPressed();
        }
    }

    class C14582 implements OnClickListener {
        C14582() {
        }

        public void onClick(View v) {
            PatientProfileActivity.this.startActivity(new Intent(PatientProfileActivity.this.getBaseContext(), ProfileEditActivity.class).putExtra("UseNameTxt", PatientProfileActivity.this.UseNameTxt.getText().toString().trim()).putExtra("userScreenName", GlobalData.Profile_screenName).putExtra("UserMobileTxt", "" + PatientProfileActivity.this.UserMobileTxt.getText().toString()).putExtra("UserEmailTxt", "" + PatientProfileActivity.this.UserEmailTxt.getText().toString()).putExtra("UserGenderTxt", "" + PatientProfileActivity.this.UserGenderTxt.getText().toString()).putExtra("UserAgeTxt", "" + PatientProfileActivity.this.UserAgeTxt.getText().toString()).putExtra("TAG", PatientProfileActivity.this.Tag));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        requestWindowFeature(5);
        setProgressBarVisibility(true);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        setContentView(C1469R.layout.profile);
        setProgressBarVisibility(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.Tag = extras.getString("TAG");
            Log.e("Tag value in MyProfile  ??? ", "  " + this.Tag);
        }
        this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        this.login_Status = this.sharedPreferences.getString("login_Status", "");
        this.UserName = this.sharedPreferences.getString("username", "");
        GlobalData.patient_userName = this.UserName;
        if (this.login_Status.equals("")) {
            startActivity(new Intent(getBaseContext(), LoginActivity.class).putExtra("TAG", this.Tag));
            finish();
        } else {
            getUserProfile(this.UserName);
        }
        InitializeControls();
    }

    private void InitializeControls() {
        this.back_profileRelativeLay = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.back_profileRelativeLay.setOnClickListener(new C14571());
        this.editLinear = (LinearLayout) findViewById(C1469R.id.profileEdit_LinearLay);
        this.editLinear.setOnClickListener(new C14582());
        this.settingsIcon = (ImageView) findViewById(C1469R.id.topbar_image_settingsIcon);
        this.edit_profileRelativeLay = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.edit_profileRelativeLay.setVisibility(8);
        this.TitleTxt = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        this.TitleTxt.setText("My Profile");
        this.UseNameTxt = (TextView) findViewById(C1469R.id.profile_userFullNameTxt);
        this.UserMobileTxt = (TextView) findViewById(C1469R.id.profile_UserMobileNoTxt);
        this.UserEmailTxt = (TextView) findViewById(C1469R.id.profile_emailTxt);
        this.UserGenderTxt = (TextView) findViewById(C1469R.id.profile_UserGenderTxt);
        this.UserAgeTxt = (TextView) findViewById(C1469R.id.profile_userAgeTxt);
    }

    private void getUserProfile(final String userName) {
        new AsyncTask<Void, Void, Integer>() {
            ProgressDialog pd;
            int resultConn = -1;

            protected void onPreExecute() {
                super.onPreExecute();
                this.pd = new ProgressDialog(PatientProfileActivity.this);
                this.pd.setMessage("Loading...");
                this.pd.setCanceledOnTouchOutside(false);
                this.pd.show();
            }

            protected Integer doInBackground(Void... params) {
                this.resultConn = HttpCall.createPatientProfileParameter(userName);
                if (this.resultConn == 1) {
                    this.resultConn = SplitJson.splitPatientProfileData(GlobalData.sDownloadedContent);
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
                    try {
                        PatientProfileActivity.this.setData();
                    } catch (Exception e) {
                    }
                } else if (this.resultConn == 4) {
                    PatientProfileActivity.this.showAlertDialog(PatientProfileActivity.this, PatientProfileActivity.this.getString(C1469R.string.SERVER_PROBLEM_MSG), 3);
                } else if (this.resultConn == 66) {
                    PatientProfileActivity.this.showAlertDialog(PatientProfileActivity.this, "Reached Limits ...Please Login..??", 3);
                } else if (this.resultConn == 11) {
                    PatientProfileActivity.this.showAlertDialog(PatientProfileActivity.this, "No Appointment Found", 1);
                } else {
                    PatientProfileActivity.this.showAlertDialog(PatientProfileActivity.this, "Ooops! Looks like you have encountered a connectivity problem. Please make sure you are connected and try again.", 2);
                }
            }
        }.execute(new Void[]{null, null, null});
    }

    private void setData() {
        try {
            this.UseNameTxt.setText(GlobalData.Profile_UserName + " " + GlobalData.profile_LastName);
            this.UserMobileTxt.setText(GlobalData.Profile_UserMobile);
            this.UserEmailTxt.setText(GlobalData.Profile_UserMail);
            this.UserGenderTxt.setText(GlobalData.Profile_UserGender);
            this.UserAgeTxt.setText(GlobalData.Profile_UserAge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlertDialog(Context context, String message, final int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (value == 1) {
                    PatientProfileActivity.this.startActivity(new Intent(PatientProfileActivity.this.getBaseContext(), MainActivity.class).addFlags(32768).addFlags(67108864));
                    PatientProfileActivity.this.finish();
                } else if (value == 2) {
                    PatientProfileActivity.this.getUserProfile(PatientProfileActivity.this.UserName);
                } else if (value == 3) {
                    PatientProfileActivity.this.finish();
                }
            }
        });
        alertDialog.show();
    }

    public void onBackPressed() {
        if (this.Tag.equalsIgnoreCase("DoctorListScreen") || this.Tag.equalsIgnoreCase("Profile_DoctorListScreen")) {
            startActivity(new Intent(getBaseContext(), Doctors_ListActivity.class));
        } else if (this.Tag.equalsIgnoreCase("DetailsScreen") || this.Tag.equalsIgnoreCase("Profile_DetailsScreen")) {
            startActivity(new Intent(getBaseContext(), DetailedActivity.class));
        } else if (this.Tag.equalsIgnoreCase("MainScreen") || this.Tag.equalsIgnoreCase("Profile_MainScreen") || this.Tag.equalsIgnoreCase("Register")) {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
        } else if (this.Tag.equalsIgnoreCase("Profile_MainScreen")) {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
        } else if (this.Tag.equalsIgnoreCase("MainScreen")) {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
        } else if (this.Tag.equalsIgnoreCase("doctorListClass")) {
            startActivity(new Intent(getBaseContext(), Doctors_ListActivity.class));
        } else if (this.Tag.equalsIgnoreCase("doctorDetailsClass")) {
            startActivity(new Intent(getBaseContext(), DetailedActivity.class));
        } else if (this.Tag.equalsIgnoreCase("myAppointments")) {
            startActivity(new Intent(getBaseContext(), PatientHistory.class));
        } else {
            finish();
        }
        finish();
    }
}
