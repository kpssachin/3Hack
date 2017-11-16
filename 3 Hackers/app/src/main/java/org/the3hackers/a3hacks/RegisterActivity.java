package org.the3hackers.a3hacks;
import Opertaion.GlobalData;
import Opertaion.SplitJson;
import Services.HttpCall;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class RegisterActivity extends Activity {
    static RadioGroup Register_radioGroup = null;
    private static RadioButton Register_radioSexButton = null;
    public static final String THE_PASSWORD_AND_CONFIRM_PASSWORD_MUST_MATCH = "Password and confirm password do not match.";
    static EditText UsernameEditTxt;
    static String verificationCode = "";
    static EditText verificationCodeET;
    RadioButton Register_femaleRadioBtn;
    RadioButton Register_maleRadioBtn;
    String TAG = "";
    EditText ageEt;
    int categoryNo = 0;
    String checkPassword = "no";
    String checkValidEmail = "wrong";
    EditText conformpasswordEditTxt;
    EditText emailEditTxt;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText fullNameET;
    Button getVerificationBTN;
    EditText mobileNOET;
    EditText passwordEditTxt;
    RelativeLayout regTopSettings;
    Button submitBtn;
    RelativeLayout topTitle;

    class C14701 implements OnClickListener {
        C14701() {
        }

        public void onClick(View v) {
            RegisterActivity.this.onBackPressed();
        }
    }

    class C14712 implements OnClickListener {
        C14712() {
        }

        public void onClick(View v) {
            Log.e("START SERVICE", " yes");
            if (RegisterActivity.this.fullNameET.getText().toString().trim().length() == 0) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter your full name.", 12);
                RegisterActivity.this.fullNameET.requestFocus();
            } else if (RegisterActivity.this.mobileNOET.getText().toString().trim().length() == 0) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid mobile number.", 12);
                RegisterActivity.this.mobileNOET.requestFocus();
            } else if (RegisterActivity.this.mobileNOET.getText().toString().trim().length() < 10) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid 10-digit mobile number", 12);
                RegisterActivity.this.mobileNOET.requestFocus();
            } else if (RegisterActivity.this.mobileNOET.getText().toString().trim().length() > 10) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid 10-digit mobile number", 12);
                RegisterActivity.this.mobileNOET.requestFocus();
            } else if (RegisterActivity.this.emailEditTxt.getText().toString().trim().length() == 0) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter your email id.", 12);
                RegisterActivity.this.emailEditTxt.requestFocus();
            } else if (RegisterActivity.this.checkValidEmail.equalsIgnoreCase("wrong")) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid email id.", 12);
                RegisterActivity.this.emailEditTxt.requestFocus();
            } else if (RegisterActivity.this.passwordEditTxt.getText().toString().trim().length() == 0) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter your password.", 12);
                RegisterActivity.this.passwordEditTxt.requestFocus();
            } else if (RegisterActivity.this.conformpasswordEditTxt.getText().toString().trim().length() == 0) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please confirm your password.", 12);
                RegisterActivity.this.conformpasswordEditTxt.requestFocus();
            } else if (RegisterActivity.this.checkPassword.equalsIgnoreCase("no")) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, RegisterActivity.THE_PASSWORD_AND_CONFIRM_PASSWORD_MUST_MATCH, 12);
                RegisterActivity.this.conformpasswordEditTxt.requestFocus();
            } else if (RegisterActivity.UsernameEditTxt.getText().toString().trim().length() == 0) {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a username (this is your screen name, e.g.  Andy12) ", 12);
                RegisterActivity.UsernameEditTxt.requestFocus();
            } else if (RegisterActivity.this.Register_maleRadioBtn.isChecked() || RegisterActivity.this.Register_femaleRadioBtn.isChecked()) {
                RegisterActivity.this.getVerificationCode();
            } else {
                RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please select Gender", 12);
            }
        }
    }

    class C14723 implements TextWatcher {
        C14723() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (RegisterActivity.verificationCodeET.getText().toString().trim().length() != 0) {
                RegisterActivity.verificationCodeET.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14734 implements TextWatcher {
        C14734() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (RegisterActivity.this.mobileNOET.getText().toString().trim().length() != 0) {
                RegisterActivity.this.mobileNOET.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14745 implements TextWatcher {
        C14745() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (RegisterActivity.this.ageEt.getText().toString().trim().length() != 0) {
                RegisterActivity.this.ageEt.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14756 implements TextWatcher {
        C14756() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (RegisterActivity.this.fullNameET.getText().toString().trim().length() != 0) {
                RegisterActivity.this.fullNameET.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14767 implements TextWatcher {
        C14767() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (RegisterActivity.UsernameEditTxt.getText().toString().trim().length() != 0) {
                RegisterActivity.UsernameEditTxt.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14778 implements TextWatcher {
        C14778() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (RegisterActivity.this.passwordEditTxt.getText().toString().trim().length() != 0) {
                RegisterActivity.this.passwordEditTxt.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14789 implements TextWatcher {
        C14789() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pwd = RegisterActivity.this.passwordEditTxt.getText().toString().trim();
            String con_pwd = RegisterActivity.this.conformpasswordEditTxt.getText().toString().trim();
            if (RegisterActivity.this.conformpasswordEditTxt.getText().toString().trim().length() != 0) {
                RegisterActivity.this.conformpasswordEditTxt.setError(null);
            }
            if (RegisterActivity.this.passwordEditTxt.getText().toString().trim().length() == 0) {
                return;
            }
            if (pwd.equalsIgnoreCase(con_pwd)) {
                RegisterActivity.this.checkPassword = "yes";
                return;
            }
            RegisterActivity.this.checkPassword = "no";
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public static class IncomingSms extends BroadcastReceiver {
        final SmsManager sms = SmsManager.getDefault();

        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                try {
                    Object[] pdusObj = (Object[]) bundle.get("pdus");
                    for (Object obj : pdusObj) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) obj);
                        String senderNum = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();
                        Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                        if (currentMessage.getOriginatingAddress().equalsIgnoreCase("IX-MEDSMS") || currentMessage.getOriginatingAddress().equalsIgnoreCase("DM-MEDSMS")) {
                            try {
                                RegisterActivity.verificationCodeET.setText("");
                                String[] parts = message.toString().split("Your Medindia Verification Code is");
                                String part1 = parts[0];
                                RegisterActivity.verificationCode = parts[1].toString();
                                Log.e("verificationCode    ", "" + RegisterActivity.verificationCode);
                                RegisterActivity.verificationCodeET.setText(RegisterActivity.verificationCode.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e2) {
                    Log.e("SmsReceiver", "Exception smsReceiver" + e2);
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_register);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.TAG = extras.getString("TAG");
        }
        controls();
    }

    private void controls() {
        Register_radioGroup = (RadioGroup) findViewById(C1469R.id.RegisterRadioBtnGender);
        this.Register_maleRadioBtn = (RadioButton) findViewById(C1469R.id.registerMale);
        this.Register_femaleRadioBtn = (RadioButton) findViewById(C1469R.id.registerFemale);
        this.regTopSettings = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.regTopSettings.setVisibility(8);
        this.topTitle = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.topTitle.setOnClickListener(new C14701());
        this.getVerificationBTN = (Button) findViewById(C1469R.id.verificationTxt);
        this.getVerificationBTN.setOnClickListener(new C14712());
        verificationCodeET = (EditText) findViewById(C1469R.id.Et_verificationCode_signUp);
        verificationCodeET.setEnabled(false);
        verificationCodeET.addTextChangedListener(new C14723());
        this.mobileNOET = (EditText) findViewById(C1469R.id.Et_mobileno_signUp);
        this.mobileNOET.addTextChangedListener(new C14734());
        this.ageEt = (EditText) findViewById(C1469R.id.Et_age_signUp);
        this.ageEt.addTextChangedListener(new C14745());
        this.fullNameET = (EditText) findViewById(C1469R.id.Et_fullname_signUp);
        this.fullNameET.addTextChangedListener(new C14756());
        UsernameEditTxt = (EditText) findViewById(C1469R.id.Et_username_signUp);
        UsernameEditTxt.addTextChangedListener(new C14767());
        this.passwordEditTxt = (EditText) findViewById(C1469R.id.Et_password_signUp);
        this.passwordEditTxt.addTextChangedListener(new C14778());
        this.conformpasswordEditTxt = (EditText) findViewById(C1469R.id.Et_conform_password_signUp);
        this.conformpasswordEditTxt.addTextChangedListener(new C14789());
        this.emailEditTxt = (EditText) findViewById(C1469R.id.Et_email_signUp);
        this.emailEditTxt.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = RegisterActivity.this.emailEditTxt.getText().toString().trim();
                if (RegisterActivity.this.emailEditTxt.getText().toString().trim().length() != 0) {
                    RegisterActivity.this.emailEditTxt.setError(null);
                }
                if (!email.matches(RegisterActivity.this.emailPattern) || s.length() <= 0) {
                    RegisterActivity.this.checkValidEmail = "wrong";
                    return;
                }
                RegisterActivity.this.checkValidEmail = "correct";
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.submitBtn = (Button) findViewById(C1469R.id.BTN_signUp_Login);
        this.submitBtn.setEnabled(false);
        this.submitBtn.setBackgroundResource(C1469R.drawable.btn__pressed);
        this.submitBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RegisterActivity.Register_radioSexButton = (RadioButton) RegisterActivity.this.findViewById(RegisterActivity.Register_radioGroup.getCheckedRadioButtonId());
                String selectSpinnerData = RegisterActivity.Register_radioSexButton.getText().toString();
                Log.e("selectSpinnerData   " + selectSpinnerData, " int value    " + RegisterActivity.this.categoryNo);
                if (RegisterActivity.this.fullNameET.getText().toString().trim().length() == 0) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter your full name.", 12);
                    RegisterActivity.this.fullNameET.requestFocus();
                } else if (RegisterActivity.this.mobileNOET.getText().toString().trim().length() == 0) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid mobile number.", 12);
                    RegisterActivity.this.mobileNOET.requestFocus();
                } else if (RegisterActivity.this.mobileNOET.getText().toString().trim().length() < 10) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid 10-digit mobile number", 12);
                    RegisterActivity.this.mobileNOET.requestFocus();
                } else if (RegisterActivity.this.mobileNOET.getText().toString().trim().length() > 10) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid 10-digit mobile number", 12);
                    RegisterActivity.this.mobileNOET.requestFocus();
                } else if (RegisterActivity.this.emailEditTxt.getText().toString().trim().length() == 0) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter your email id.", 12);
                    RegisterActivity.this.emailEditTxt.requestFocus();
                } else if (RegisterActivity.this.checkValidEmail.equalsIgnoreCase("wrong")) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid email id.", 12);
                    RegisterActivity.this.emailEditTxt.requestFocus();
                } else if (RegisterActivity.this.passwordEditTxt.getText().toString().trim().length() == 0) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter your password.", 12);
                    RegisterActivity.this.passwordEditTxt.requestFocus();
                } else if (RegisterActivity.this.conformpasswordEditTxt.getText().toString().trim().length() == 0) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please confirm your password.", 12);
                    RegisterActivity.this.conformpasswordEditTxt.requestFocus();
                } else if (RegisterActivity.this.checkPassword.equalsIgnoreCase("no")) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, RegisterActivity.THE_PASSWORD_AND_CONFIRM_PASSWORD_MUST_MATCH, 12);
                    RegisterActivity.this.conformpasswordEditTxt.requestFocus();
                } else if (RegisterActivity.UsernameEditTxt.getText().toString().trim().length() == 0) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter your username.", 12);
                    RegisterActivity.UsernameEditTxt.requestFocus();
                } else if (!RegisterActivity.this.Register_maleRadioBtn.isChecked() && !RegisterActivity.this.Register_femaleRadioBtn.isChecked()) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please select Gender", 12);
                } else if (RegisterActivity.this.ageEt.getText().toString().trim().length() == 0) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter your age.", 12);
                    RegisterActivity.this.ageEt.requestFocus();
                } else if (RegisterActivity.this.ageEt.getText().toString().trim().equalsIgnoreCase("0")) {
                    RegisterActivity.this.ageEt.requestFocus();
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid age.", 12);
                } else if (RegisterActivity.verificationCodeET.getText().toString().trim().length() == 0) {
                    RegisterActivity.verificationCodeET.requestFocus();
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter the verification code sent to you via email or SMS", 12);
                } else {
                    RegisterActivity.this.userSignUp(RegisterActivity.UsernameEditTxt.getText().toString().trim(), RegisterActivity.this.passwordEditTxt.getText().toString().trim(), RegisterActivity.this.conformpasswordEditTxt.getText().toString().trim(), RegisterActivity.this.emailEditTxt.getText().toString().trim(), selectSpinnerData, RegisterActivity.this.fullNameET.getText().toString().trim(), RegisterActivity.this.ageEt.getText().toString().trim(), RegisterActivity.this.mobileNOET.getText().toString().trim(), RegisterActivity.verificationCodeET.getText().toString().trim());
                }
            }
        });
    }

    private void getVerificationCode() {
        new AsyncTask<Void, Void, Integer>() {
            ProgressDialog pd;
            int resultConn = -1;

            protected void onPreExecute() {
                super.onPreExecute();
                this.pd = new ProgressDialog(RegisterActivity.this);
                this.pd.setMessage("loading...");
                this.pd.setCanceledOnTouchOutside(false);
                this.pd.show();
            }

            protected Integer doInBackground(Void... params) {
                this.resultConn = HttpCall.getVerificationService("" + RegisterActivity.this.mobileNOET.getText().toString().trim(), RegisterActivity.this.emailEditTxt.getText().toString().trim(), RegisterActivity.UsernameEditTxt.getText().toString().trim(), RegisterActivity.this.fullNameET.getText().toString().trim());
                if (this.resultConn == 1) {
                    this.resultConn = SplitJson.splitVerificationCode(GlobalData.sDownloadedContent);
                }
                return Integer.valueOf(this.resultConn);
            }

            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                this.pd.dismiss();
                if (this.resultConn == 1) {
                    Log.e("Connections ", "success");
                    RegisterActivity.this.submitBtn.setEnabled(true);
                    RegisterActivity.verificationCodeET.setEnabled(true);
                    RegisterActivity.verificationCodeET.requestFocus();
                    RegisterActivity.this.submitBtn.setBackgroundResource(C1469R.drawable.search_btn_bg);
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, GlobalData.getVerificationResponse, 1);
                    this.pd.dismiss();
                } else if (this.resultConn == 4) {
                    Log.e(" MainAcitvity   ", "Network error in specialty process");
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Ooops! Look like that was an expected error encountered on the server. Please try again later.", 12);
                } else if (this.resultConn == 22) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "No Records Found", 12);
                } else if (this.resultConn == 11) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "No Records Found", 12);
                } else if (this.resultConn == 33) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "This email address is already registered with us. Please login with your email id and password. You may click on the Forgot Password link on the Sign In Screen to request your password.", 8);
                } else if (this.resultConn == 0) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid verification code.", 12);
                } else if (this.resultConn == 44) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "This username is already in use. Please use a different name", 12);
                } else if (this.resultConn == 77) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "This Mobile Number is already in use. Please use a different Mobile Number.", 12);
                } else {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Ooops! Looks like you have encountered a connectivity problem. Please make sure you are connected and try again.", 12);
                }
            }
        }.execute(new Void[]{null, null, null});
    }

    public void userSignUp(String username, String password, String conformPassword, String email, String selectedCategory, String fullName, String age, String mobileNo, String VerificationCode) {
        final String str = username;
        final String str2 = password;
        final String str3 = conformPassword;
        final String str4 = email;
        final String str5 = selectedCategory;
        final String str6 = fullName;
        final String str7 = age;
        final String str8 = mobileNo;
        final String str9 = VerificationCode;
        new AsyncTask<Void, Void, Integer>() {
            ProgressDialog pd;
            int resultConn = -1;

            protected void onPreExecute() {
                super.onPreExecute();
                this.pd = new ProgressDialog(RegisterActivity.this);
                this.pd.setMessage("loading...");
                this.pd.setCanceledOnTouchOutside(false);
                this.pd.show();
            }

            protected Integer doInBackground(Void... params) {
                this.resultConn = HttpCall.usersignUPConnection(RegisterActivity.this.getApplicationContext(), str, str2, str3, str4, str5, str6, str7, str8, str9);
                return Integer.valueOf(this.resultConn);
            }

            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                this.pd.dismiss();
                Log.v("Response Code ", "   ???? " + this.resultConn);
                if (this.resultConn == 1) {
                    Log.e("Connections ", "success");
                    RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class).putExtra("TAG", "Register").putExtra("UserEmail", "" + RegisterActivity.this.emailEditTxt.getText().toString().trim()).putExtra("New_TAG", RegisterActivity.this.TAG));
                    RegisterActivity.this.finish();
                } else if (this.resultConn == 4) {
                    Log.e(" MainAcitvity   ", "Network error in specialty process");
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Ooops! Look like that was an expected error encountered on the server. Please try again later.", 12);
                } else if (this.resultConn == 22) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid username/password.", 12);
                } else if (this.resultConn == 11) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "No Records Found", 12);
                } else if (this.resultConn == 33) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "This email address is already registered with us. Please login with your email id and password. In case you have you encounter a problem, click Forgot Password on the Sign In screen.", 8);
                } else if (this.resultConn == 0) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Please enter a valid verification code.", 12);
                } else if (this.resultConn == 44) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "This username is already in use. Please use a different name.", 12);
                } else if (this.resultConn == 77) {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "This Mobile Number is already in use. Please use a different Mobile Number.", 12);
                } else {
                    RegisterActivity.this.showAlertDialog(RegisterActivity.this, "Ooops! Looks like you have encountered a connectivity problem. Please make sure you are connected and try again.", 12);
                }
            }
        }.execute(new Void[]{null, null, null});
    }

    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class).putExtra("TAG", this.TAG));
        finish();
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
