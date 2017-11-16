package org.the3hackers.a3hacks;
import Opertaion.Constants;
import Opertaion.GlobalData;
import Opertaion.SplitJson;
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
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.drive.DriveFile;

public class LoginActivity extends Activity {
    final int CLOSE_DIALOG = 100;
    LinearLayout CreateAccountLinear;
    String New_TAG = "";
    EditText PasswordEditTxt;
    Button SignInBtn;
    String TAG = "";
    RelativeLayout TopbarSetting;
    EditText UserNameEditTxt;
    String checkValidEmail = "wrong";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    LinearLayout forgotPassLinear;
    SharedPreferences sharedPreferences;
    RelativeLayout topBackBtn;
    String userMailID = "";

    class C14281 implements OnClickListener {
        C14281() {
        }

        public void onClick(View v) {
            LoginActivity.this.onBackPressed();
        }
    }

    class C14292 implements TextWatcher {
        C14292() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = LoginActivity.this.UserNameEditTxt.getText().toString().trim();
            if (LoginActivity.this.UserNameEditTxt.getText().toString().length() != 0) {
                LoginActivity.this.UserNameEditTxt.setError(null);
            }
            if (!email.matches(LoginActivity.this.emailPattern) || s.length() <= 0) {
                LoginActivity.this.SignInBtn.setEnabled(false);
                LoginActivity.this.SignInBtn.setBackgroundResource(C1469R.drawable.btn__pressed);
                LoginActivity.this.checkValidEmail = "wrong";
                return;
            }
            LoginActivity.this.checkValidEmail = "correct";
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14303 implements TextWatcher {
        C14303() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (LoginActivity.this.PasswordEditTxt.getText().toString().length() != 0 && LoginActivity.this.checkValidEmail.equalsIgnoreCase("correct")) {
                LoginActivity.this.PasswordEditTxt.setError(null);
                LoginActivity.this.SignInBtn.setEnabled(true);
                LoginActivity.this.SignInBtn.setBackgroundResource(C1469R.drawable.search_btn_bg);
            } else if (LoginActivity.this.checkValidEmail.equalsIgnoreCase("wrong")) {
                LoginActivity.this.SignInBtn.setEnabled(false);
                LoginActivity.this.SignInBtn.setBackgroundResource(C1469R.drawable.btn__pressed);
            } else if (LoginActivity.this.PasswordEditTxt.getText().toString().length() == 0) {
                LoginActivity.this.SignInBtn.setEnabled(false);
                LoginActivity.this.SignInBtn.setBackgroundResource(C1469R.drawable.btn__pressed);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14314 implements OnClickListener {
        C14314() {
        }

        public void onClick(View v) {
            LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), RegisterActivity.class).putExtra("TAG", LoginActivity.this.TAG));
            LoginActivity.this.finish();
        }
    }

    class C14325 implements OnClickListener {
        C14325() {
        }

        public void onClick(View v) {
            LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), ForgotActivity.class).putExtra("TAG", LoginActivity.this.TAG));
        }
    }

    class C14336 implements OnClickListener {
        C14336() {
        }

        public void onClick(View v) {
            if (LoginActivity.this.UserNameEditTxt.getText().toString().length() == 0) {
                LoginActivity.this.showAlertDialog(LoginActivity.this, "Please enter email id", 7);
            } else if (LoginActivity.this.PasswordEditTxt.getText().toString().length() == 0) {
                LoginActivity.this.showAlertDialog(LoginActivity.this, "Please enter your password.", 7);
            } else if (LoginActivity.this.checkValidEmail.equalsIgnoreCase("wrong")) {
                LoginActivity.this.showAlertDialog(LoginActivity.this, "Please enter a  valid email address", 7);
                LoginActivity.this.UserNameEditTxt.requestFocus();
            } else {
                LoginActivity.this.userLogin(LoginActivity.this.UserNameEditTxt.getText().toString(), LoginActivity.this.PasswordEditTxt.getText().toString());
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_login);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.TAG = extras.getString("TAG");
            if (this.TAG.equalsIgnoreCase("Register")) {
                this.userMailID = extras.getString("UserEmail");
                this.TAG = extras.getString("New_TAG");
            } else if (this.TAG.equalsIgnoreCase("InActiveUser")) {
                this.userMailID = extras.getString("UserEmail");
                this.TAG = extras.getString("New_TAG");
            }
        }
        this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        controls();
    }

    private void controls() {
        this.TopbarSetting = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.TopbarSetting.setVisibility(8);
        this.topBackBtn = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.topBackBtn.setOnClickListener(new C14281());
        this.UserNameEditTxt = (EditText) findViewById(C1469R.id.Et_username_login);
        this.UserNameEditTxt.addTextChangedListener(new C14292());
        this.PasswordEditTxt = (EditText) findViewById(C1469R.id.Et_password_login);
        if (!this.userMailID.equalsIgnoreCase("")) {
            this.UserNameEditTxt.setText(this.userMailID);
            this.PasswordEditTxt.requestFocus();
        }
        this.PasswordEditTxt.addTextChangedListener(new C14303());
        this.CreateAccountLinear = (LinearLayout) findViewById(C1469R.id.createAccount_login);
        this.CreateAccountLinear.setOnClickListener(new C14314());
        this.forgotPassLinear = (LinearLayout) findViewById(C1469R.id.forgetPasswordLinearlay);
        this.forgotPassLinear.setOnClickListener(new C14325());
        this.SignInBtn = (Button) findViewById(C1469R.id.BTN_signIn_Login);
        this.SignInBtn.setEnabled(false);
        this.SignInBtn.setBackgroundResource(C1469R.drawable.btn__pressed);
        this.SignInBtn.setOnClickListener(new C14336());
    }

    protected void userLogin(final String userName, final String Password) {
        new AsyncTask<Void, Void, Integer>() {
            ProgressDialog pd;
            int resultConn = -1;

            protected void onPreExecute() {
                super.onPreExecute();
                this.pd = new ProgressDialog(LoginActivity.this);
                this.pd.setMessage("Loading...");
                this.pd.setCanceledOnTouchOutside(false);
                this.pd.show();
            }

            protected Integer doInBackground(Void... params) {
                this.resultConn = HttpCall.userLoginConnection(LoginActivity.this.getApplicationContext(), userName, Password);
                if (this.resultConn == 1) {
                    this.resultConn = SplitJson.splitLoginDetails(GlobalData.sDownloadedContent);
                }
                return Integer.valueOf(this.resultConn);
            }

            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                this.pd.dismiss();
                if (this.resultConn == 1) {
                    Log.e("Connections ", "success");
                    LoginActivity.this.sharedPreferences.edit().putString("login_Status", "success").commit();
                    LoginActivity.this.sharedPreferences.edit().putString("username", GlobalData.patient_userName).commit();
                    LoginActivity.this.sharedPreferences.edit().putString("userFullname", GlobalData.patientFullName).commit();
                    LoginActivity.this.sharedPreferences.edit().putString("userEmail", LoginActivity.this.UserNameEditTxt.getText().toString()).commit();
                    GlobalData.UserName = GlobalData.getUserName(LoginActivity.this.getApplicationContext());
                    if (LoginActivity.this.TAG.equalsIgnoreCase("DoctorListScreen")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), PatientHistory.class).putExtra("TAG", LoginActivity.this.TAG));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("DetailsScreen")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), PatientHistory.class).putExtra("TAG", LoginActivity.this.TAG));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("MainScreen") || LoginActivity.this.TAG.equalsIgnoreCase("Register")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), PatientHistory.class).putExtra("TAG", LoginActivity.this.TAG));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("Profile_DoctorListScreen")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", "DoctorListScreen"));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("Profile_DetailsScreen")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", "DetailsScreen"));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("Profile_MainScreen") || LoginActivity.this.TAG.equalsIgnoreCase("Register")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), PatientProfileActivity.class).putExtra("TAG", "MainScreen"));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("MyAppt")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), MainActivity.class));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("doctorListClass")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), Doctors_ListActivity.class));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("doctorDetailsClass")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), DetailedActivity.class));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("myAppointments")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), PatientHistory.class));
                    } else if (LoginActivity.this.TAG.equalsIgnoreCase("MainScreen") || LoginActivity.this.TAG.equalsIgnoreCase("Register")) {
                        LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), MainActivity.class));
                    }
                    LoginActivity.this.finish();
                } else if (this.resultConn == 4) {
                    Log.e(" MainActivity   ", "Network error in specialty process");
                    LoginActivity.this.showAlertDialog(LoginActivity.this, LoginActivity.this.getString(C1469R.string.SERVER_PROBLEM_MSG), 3);
                } else if (this.resultConn == 22) {
                    LoginActivity.this.showAlertDialog(LoginActivity.this, "Incorrect username or password", 6);
                } else if (this.resultConn == 11) {
                    LoginActivity.this.showAlertDialog(LoginActivity.this, "No Records Found", 7);
                } else if (this.resultConn == 55) {
                    LoginActivity.this.showAlertDialogOldUser(LoginActivity.this, "Thank You! You are an existing member of Medindia. However, your membership is not yet activated. To activate your membership click on the link in the activation email sent earlier. ", 10);
                } else {
                    LoginActivity.this.showAlertDialog(LoginActivity.this, LoginActivity.this.getString(C1469R.string.Network_ErrorMsg), 7);
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
                if (value == 10) {
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), InActiveUserActivity.class));
                }
            }
        });
        alertDialog.show();
    }

    public void showAlertDialogOldUser(Context context, String message, final int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (value == 10) {
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this.getBaseContext(), InActiveUserActivity.class).putExtra("TAG", LoginActivity.this.TAG).putExtra("email", LoginActivity.this.UserNameEditTxt.getText().toString().trim()));
                }
            }
        });
        alertDialog.show();
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 100:
                return new Builder(this).setTitle("Find A Doctor").setMessage("Do you want to exit?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.this.finish();
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
        if (this.TAG.equalsIgnoreCase("MyAppt")) {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
        } else if (this.TAG.equalsIgnoreCase("doctorListClass")) {
            startActivity(new Intent(getBaseContext(), Doctors_ListActivity.class));
        } else if (this.TAG.equalsIgnoreCase("doctorDetailsClass")) {
            startActivity(new Intent(getBaseContext(), DetailedActivity.class));
        } else if (this.TAG.equalsIgnoreCase("myAppointments")) {
            startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
            finish();
        } else if (this.TAG.equalsIgnoreCase("MainScreen") || this.TAG.equalsIgnoreCase("Register")) {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
        } else {
            finish();
        }
        finish();
    }
}
