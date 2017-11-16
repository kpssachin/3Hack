package org.the3hackers.a3hacks;
import Opertaion.GlobalData;
import Services.HttpCall;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ForgotActivity extends Activity {
    String TAG = "";
    Button backBtnForgotpwd;
    String checkValidEmail = "wrong";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText emailorMobileNoET;
    RadioGroup radioGroup1;
    String sendOTP_parameterType = "mobile";
    Button submitBtnForgotpwd;
    TextView titleTxt;
    RelativeLayout topBackBtnRelay;
    RelativeLayout topSettingsBtnRelay;

    class C14101 implements TextWatcher {
        C14101() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = ForgotActivity.this.emailorMobileNoET.getText().toString().trim();
            if (ForgotActivity.this.emailorMobileNoET.getText().toString().length() != 0) {
                ForgotActivity.this.emailorMobileNoET.setError(null);
            }
            if (!email.matches(ForgotActivity.this.emailPattern) || s.length() <= 0) {
                ForgotActivity.this.checkValidEmail = "wrong";
                return;
            }
            ForgotActivity.this.checkValidEmail = "correct";
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14112 implements OnClickListener {
        C14112() {
        }

        public void onClick(View v) {
            ForgotActivity.this.onBackPressed();
        }
    }

    class C14123 implements OnClickListener {
        C14123() {
        }

        public void onClick(View v) {
            if (ForgotActivity.this.sendOTP_parameterType.equalsIgnoreCase("email")) {
                if (ForgotActivity.this.emailorMobileNoET.getText().toString().trim().length() == 0) {
                    ForgotActivity.this.emailorMobileNoET.setError("Please enter your email id.");
                } else if (ForgotActivity.this.checkValidEmail.equalsIgnoreCase("wrong")) {
                    ForgotActivity.this.emailorMobileNoET.setError("Please enter a valid email id.");
                    ForgotActivity.this.emailorMobileNoET.requestFocus();
                } else {
                    ForgotActivity.this.getPassword(ForgotActivity.this.emailorMobileNoET.getText().toString().trim(), "Email");
                }
            } else if (ForgotActivity.this.emailorMobileNoET.getText().toString().trim().length() == 0) {
                ForgotActivity.this.emailorMobileNoET.setError("Please enter your mobile number");
            } else if (ForgotActivity.this.emailorMobileNoET.getText().toString().trim().length() < 10) {
                ForgotActivity.this.emailorMobileNoET.setError("Please enter a valid mobile number.");
                ForgotActivity.this.emailorMobileNoET.requestFocus();
            } else {
                ForgotActivity.this.getPassword(ForgotActivity.this.emailorMobileNoET.getText().toString().trim(), "SMS");
            }
        }
    }

    class C14134 implements OnCheckedChangeListener {
        C14134() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case C1469R.id.radioMobile /*2131624145*/:
                    ForgotActivity.this.emailorMobileNoET.setError(null);
                    ForgotActivity.this.emailorMobileNoET.setFilters(new InputFilter[]{new LengthFilter(10)});
                    ForgotActivity.this.emailorMobileNoET.setInputType(3);
                    ForgotActivity.this.emailorMobileNoET.setText("");
                    ForgotActivity.this.emailorMobileNoET.setHint("Please enter your mobile number");
                    ForgotActivity.this.sendOTP_parameterType = "mobile";
                    return;
                case C1469R.id.radioEmail /*2131624146*/:
                    ForgotActivity.this.emailorMobileNoET.setError(null);
                    ForgotActivity.this.sendOTP_parameterType = "email";
                    ForgotActivity.this.emailorMobileNoET.setFilters(new InputFilter[]{new LengthFilter(100)});
                    ForgotActivity.this.emailorMobileNoET.setInputType(33);
                    ForgotActivity.this.emailorMobileNoET.setText("");
                    ForgotActivity.this.emailorMobileNoET.setHint("Please enter a valid email id.");
                    return;
                default:
                    return;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_forgot);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.TAG = extras.getString("TAG");
        }
        this.sendOTP_parameterType = "mobile";
        controls();
    }

    private void controls() {
        this.titleTxt = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        this.titleTxt.setText("Forgot Password ?");
        this.emailorMobileNoET = (EditText) findViewById(C1469R.id.forgotPasswordEditTxt);
        this.emailorMobileNoET.setFilters(new InputFilter[]{new LengthFilter(10)});
        this.emailorMobileNoET.addTextChangedListener(new C14101());
        this.topBackBtnRelay = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.topBackBtnRelay.setOnClickListener(new C14112());
        this.topSettingsBtnRelay = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.topSettingsBtnRelay.setVisibility(8);
        this.submitBtnForgotpwd = (Button) findViewById(C1469R.id.submitBtnForgotPassword);
        this.submitBtnForgotpwd.setOnClickListener(new C14123());
        this.radioGroup1 = (RadioGroup) findViewById(C1469R.id.radioGroup1);
        this.radioGroup1.setOnCheckedChangeListener(new C14134());
    }

    private void getPassword(final String email, final String parameterType) {
        new AsyncTask<Void, Void, Integer>() {
            ProgressDialog pd;
            int resultConn = -1;

            protected void onPreExecute() {
                super.onPreExecute();
                this.pd = new ProgressDialog(ForgotActivity.this);
                this.pd.setMessage("Loading...");
                this.pd.show();
            }

            protected Integer doInBackground(Void... params) {
                this.resultConn = HttpCall.createGetPasswordParameter(email, parameterType);
                return Integer.valueOf(this.resultConn);
            }

            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                this.pd.dismiss();
                if (this.resultConn == 1) {
                    Log.e("Connections ", "success");
                    if (parameterType.equalsIgnoreCase("SMS")) {
                        ForgotActivity.this.showAlertDialog(ForgotActivity.this, "Your password has been sent to your Mobile Number ", 6);
                    } else {
                        ForgotActivity.this.showAlertDialog(ForgotActivity.this, "Your password has been sent to your email address ", 1);
                    }
                    this.pd.dismiss();
                } else if (this.resultConn == 4) {
                    Log.e(" MainAcitvity   ", "Network error in specialty process");
                    ForgotActivity.this.showAlertDialog(ForgotActivity.this, "Ooops! Look like that was an expected error encountered on the server. Please try again later.", 6);
                } else if (this.resultConn == 22) {
                    ForgotActivity.this.showAlertDialog(ForgotActivity.this, "Sorry you are not a registered user", 6);
                } else if (this.resultConn == 11) {
                    GlobalData.toastInflate("No Records Found", 0).show();
                } else if (this.resultConn == 33) {
                    ForgotActivity.this.showAlertDialog(ForgotActivity.this, "This email is already in use. Please use a different email.", 6);
                } else if (this.resultConn == 44) {
                    GlobalData.toastInflate("This username is already in use. Please use a different username.", 0).show();
                } else {
                    ForgotActivity.this.showAlertDialog(ForgotActivity.this, "Ooops! Looks like you have encountered a connectivity problem. Please make sure you are connected and try again.", 6);
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
                    ForgotActivity.this.startActivity(new Intent(ForgotActivity.this.getBaseContext(), LoginActivity.class).putExtra("TAG", "Register").putExtra("UserEmail", "" + ForgotActivity.this.emailorMobileNoET.getText().toString().trim()).putExtra("New_TAG", ForgotActivity.this.TAG).addFlags(32768).addFlags(67108864));
                    ForgotActivity.this.finish();
                } else if (value == 6) {
                    ForgotActivity.this.startActivity(new Intent(ForgotActivity.this.getBaseContext(), LoginActivity.class).putExtra("TAG", ForgotActivity.this.TAG).addFlags(32768).addFlags(67108864));
                    ForgotActivity.this.finish();
                }
            }
        });
        alertDialog.show();
    }

    public void onBackPressed() {
        finish();
    }
}
