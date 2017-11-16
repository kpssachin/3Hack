package org.the3hackers.a3hacks;
import Opertaion.Constants;
import Opertaion.GlobalData;
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
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileEditActivity extends Activity {
    String Age = "";
    String EmailId = "";
    String FullName = "";
    String Gender = "";
    String LastName = "";
    String MobileNo = "";
    String ScreenName = "";
    String Tag = "";
    TextView TitleTxt;
    EditText ageEt;
    String checkValidEmail = "wrong";
    EditText emailEditTxt;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioButton femaleRadioBtn;
    EditText fullNameET;
    RadioButton maleRadioBtn;
    EditText mobileNOET;
    LinearLayout profileDoneLinear;
    RadioGroup radioGroup;
    private RadioButton radioSexButton;
    RelativeLayout regTopSettings;
    SharedPreferences sharedPreferences;
    String[] spin_gender;
    Spinner spinner_gender;
    RelativeLayout topTitle;

    class C14611 implements OnClickListener {
        C14611() {
        }

        public void onClick(View v) {
            ProfileEditActivity.this.finish();
        }
    }

    class C14622 implements TextWatcher {
        C14622() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (ProfileEditActivity.this.mobileNOET.getText().toString().trim().length() != 0) {
                ProfileEditActivity.this.mobileNOET.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14633 implements TextWatcher {
        C14633() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (ProfileEditActivity.this.ageEt.getText().toString().trim().length() != 0) {
                ProfileEditActivity.this.ageEt.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14644 implements TextWatcher {
        C14644() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (ProfileEditActivity.this.fullNameET.getText().toString().trim().length() != 0) {
                ProfileEditActivity.this.fullNameET.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14655 implements TextWatcher {
        C14655() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = ProfileEditActivity.this.emailEditTxt.getText().toString().trim();
            if (ProfileEditActivity.this.emailEditTxt.getText().toString().trim().length() != 0) {
                ProfileEditActivity.this.emailEditTxt.setError(null);
            }
            if (!email.matches(ProfileEditActivity.this.emailPattern) || s.length() <= 0) {
                ProfileEditActivity.this.checkValidEmail = "wrong";
                return;
            }
            ProfileEditActivity.this.checkValidEmail = "correct";
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14666 implements OnClickListener {
        C14666() {
        }

        public void onClick(View v) {
            if (ProfileEditActivity.this.fullNameET.getText().toString().trim().length() == 0) {
                ProfileEditActivity.this.fullNameET.setError("FirstName is Required");
                ProfileEditActivity.this.fullNameET.requestFocus();
            } else if (ProfileEditActivity.this.mobileNOET.getText().toString().trim().length() == 0) {
                ProfileEditActivity.this.mobileNOET.setError("Please Enter mobile No");
                ProfileEditActivity.this.mobileNOET.requestFocus();
            } else if (ProfileEditActivity.this.mobileNOET.getText().toString().trim().length() < 10) {
                ProfileEditActivity.this.mobileNOET.setError("please enter a valid 10-digit phone number");
                ProfileEditActivity.this.mobileNOET.requestFocus();
            } else if (ProfileEditActivity.this.mobileNOET.getText().toString().trim().length() > 10) {
                ProfileEditActivity.this.mobileNOET.setError("please enter a valid 10-digit phone number");
                ProfileEditActivity.this.mobileNOET.requestFocus();
            } else if (ProfileEditActivity.this.emailEditTxt.getText().toString().trim().length() == 0) {
                ProfileEditActivity.this.emailEditTxt.setError("Email is Required");
                ProfileEditActivity.this.emailEditTxt.requestFocus();
            } else if (ProfileEditActivity.this.checkValidEmail.equalsIgnoreCase("wrong")) {
                ProfileEditActivity.this.emailEditTxt.setError("Please Enter Valid EmailId");
                ProfileEditActivity.this.emailEditTxt.requestFocus();
            } else if (ProfileEditActivity.this.ageEt.getText().toString().trim().length() == 0) {
                ProfileEditActivity.this.ageEt.setError("Age is Required");
                ProfileEditActivity.this.ageEt.requestFocus();
            } else {
                ProfileEditActivity.this.updateProfile();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setSoftInputMode(3);
        setContentView(C1469R.layout.profile_edit);
        this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.FullName = extras.getString("UseNameTxt");
            this.ScreenName = extras.getString("userScreenName");
            this.MobileNo = extras.getString("UserMobileTxt");
            this.EmailId = extras.getString("UserEmailTxt");
            this.Gender = extras.getString("UserGenderTxt");
            this.Age = extras.getString("UserAgeTxt");
            this.Tag = extras.getString("TAG");
        }
        controls();
    }

    private void controls() {
        this.TitleTxt = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        this.TitleTxt.setText("Edit Profile");
        this.radioGroup = (RadioGroup) findViewById(C1469R.id.RadioBtnGender);
        this.maleRadioBtn = (RadioButton) findViewById(C1469R.id.profileEditMale);
        this.femaleRadioBtn = (RadioButton) findViewById(C1469R.id.profileEditFemale);
        this.regTopSettings = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.regTopSettings.setVisibility(8);
        this.topTitle = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.topTitle.setOnClickListener(new C14611());
        this.mobileNOET = (EditText) findViewById(C1469R.id.Et_mobileno_profileEdit);
        this.mobileNOET.addTextChangedListener(new C14622());
        this.ageEt = (EditText) findViewById(C1469R.id.Et_age_profileEdit);
        this.ageEt.addTextChangedListener(new C14633());
        this.fullNameET = (EditText) findViewById(C1469R.id.Et_fullname_profileEdit);
        this.fullNameET.addTextChangedListener(new C14644());
        this.emailEditTxt = (EditText) findViewById(C1469R.id.Et_email_profileEdit);
        this.emailEditTxt.addTextChangedListener(new C14655());
        this.emailEditTxt.setText(this.EmailId);
        this.ageEt.setText(this.Age);
        this.fullNameET.setText(this.FullName);
        this.mobileNOET.setText(this.MobileNo);
        if (this.Gender.equalsIgnoreCase("Male")) {
            this.maleRadioBtn.setChecked(true);
        } else {
            this.femaleRadioBtn.setChecked(true);
        }
        this.profileDoneLinear = (LinearLayout) findViewById(C1469R.id.profileDone_linearLay);
        this.profileDoneLinear.setOnClickListener(new C14666());
    }

    private void updateProfile() {
        this.radioSexButton = (RadioButton) findViewById(this.radioGroup.getCheckedRadioButtonId());
        final String sGender = this.radioSexButton.getText().toString();
        final String email = this.emailEditTxt.getText().toString();
        final String Age = this.ageEt.getText().toString();
        final String fName = this.fullNameET.getText().toString();
        final String Mobile = this.mobileNOET.getText().toString();
        new AsyncTask<Void, Void, Integer>() {
            ProgressDialog pd;
            int resultConn = -1;

            protected void onPreExecute() {
                super.onPreExecute();
                this.pd = new ProgressDialog(ProfileEditActivity.this);
                this.pd.setMessage("loading...");
                this.pd.setCanceledOnTouchOutside(false);
                this.pd.show();
            }

            protected Integer doInBackground(Void... params) {
                this.resultConn = HttpCall.updateProfileService(email, Age, fName, Mobile, sGender, ProfileEditActivity.this.ScreenName);
                return Integer.valueOf(this.resultConn);
            }

            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                this.pd.dismiss();
                if (this.resultConn == 1) {
                    Log.e("Connections ", "success");
                    GlobalData.patientFullName = ProfileEditActivity.this.fullNameET.getText().toString().trim();
                    ProfileEditActivity.this.sharedPreferences.edit().putString("userFullname", GlobalData.patientFullName).commit();
                    ProfileEditActivity.this.startActivity(new Intent(ProfileEditActivity.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", ProfileEditActivity.this.Tag));
                    ProfileEditActivity.this.finish();
                } else if (this.resultConn == 4) {
                    Log.e(" MainAcitvity   ", "Network error in specialty process");
                    ProfileEditActivity.this.showAlertDialog(ProfileEditActivity.this, "Ooops! Look like that was an expected error encountered on the server. Please try again later.", 12);
                } else if (this.resultConn == 22) {
                    GlobalData.toastInflate("Incorrrect username or password. Please try again.", 0).show();
                    ProfileEditActivity.this.showAlertDialog(ProfileEditActivity.this, "No Records Found", 12);
                } else if (this.resultConn == 11) {
                    ProfileEditActivity.this.showAlertDialog(ProfileEditActivity.this, "No Records Found", 12);
                } else if (this.resultConn == 33) {
                    ProfileEditActivity.this.showAlertDialog(ProfileEditActivity.this, "This email address is already registered. Please use it to Login or click Forgot Password on the Sign In screen.", 8);
                } else if (this.resultConn == 0) {
                    ProfileEditActivity.this.showAlertDialog(ProfileEditActivity.this, "Please Enter Valid Verification Code", 12);
                } else if (this.resultConn == 44) {
                    ProfileEditActivity.this.showAlertDialog(ProfileEditActivity.this, "This screen name is already in use. Please use a different name.", 12);
                } else if (this.resultConn == 77) {
                    ProfileEditActivity.this.showAlertDialog(ProfileEditActivity.this, "This mobile number is already in use. Please use a different mobile number.", 12);
                } else {
                    ProfileEditActivity.this.showAlertDialog(ProfileEditActivity.this, "Ooops! Looks like you have encountered a connectivity problem. Please make sure you are connected and try again.", 12);
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
                if (value == 1 || value != 8) {
                }
            }
        });
        alertDialog.show();
    }
}
