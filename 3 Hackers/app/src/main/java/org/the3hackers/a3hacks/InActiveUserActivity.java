package org.the3hackers.a3hacks;
import Opertaion.GlobalData;
import Opertaion.SplitJson;
import Services.HttpCall;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class InActiveUserActivity extends Activity {
    EditText EmailET;
    EditText MobileET;
    LinearLayout OTPlay;
    String TAG = "";
    EditText VerificationET;
    String checkPassword = "no";
    String checkValidEmail = "wrong";
    String email = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button getOTPBtn;
    Button proceedBtn;
    RelativeLayout regTopSettingsInActive;
    RelativeLayout topBackBtnInActive;

    class C14201 implements OnClickListener {
        C14201() {
        }

        public void onClick(View v) {
            InActiveUserActivity.this.onBackPressed();
        }
    }

    class C14212 implements TextWatcher {
        C14212() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = InActiveUserActivity.this.EmailET.getText().toString().trim();
            if (InActiveUserActivity.this.EmailET.getText().toString().trim().length() != 0) {
                InActiveUserActivity.this.EmailET.setError(null);
            }
            if (!email.matches(InActiveUserActivity.this.emailPattern) || s.length() <= 0) {
                InActiveUserActivity.this.checkValidEmail = "wrong";
                return;
            }
            InActiveUserActivity.this.checkValidEmail = "correct";
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14223 implements TextWatcher {
        C14223() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (InActiveUserActivity.this.MobileET.getText().toString().trim().length() != 0) {
                InActiveUserActivity.this.MobileET.setError(null);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C14234 implements OnClickListener {
        C14234() {
        }

        public void onClick(View v) {
            if (InActiveUserActivity.this.EmailET.getText().toString().trim().length() == 0) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter your email id.", 12);
                InActiveUserActivity.this.EmailET.requestFocus();
            } else if (InActiveUserActivity.this.checkValidEmail.equalsIgnoreCase("wrong")) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a valid email id.", 12);
                InActiveUserActivity.this.EmailET.requestFocus();
            } else if (InActiveUserActivity.this.MobileET.getText().toString().trim().length() == 0) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a  mobile number.", 12);
                InActiveUserActivity.this.MobileET.requestFocus();
            } else if (InActiveUserActivity.this.MobileET.getText().toString().trim().length() < 10) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a valid 10-digit mobile number", 12);
                InActiveUserActivity.this.MobileET.requestFocus();
            } else if (InActiveUserActivity.this.MobileET.getText().toString().trim().length() > 10) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a valid 10-digit mobile number", 12);
                InActiveUserActivity.this.MobileET.requestFocus();
            } else {
                InActiveUserActivity.this.getOTPService();
            }
        }
    }

    class C14245 implements OnClickListener {
        C14245() {
        }

        public void onClick(View v) {
            if (InActiveUserActivity.this.EmailET.getText().toString().trim().length() == 0) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter your email id.", 12);
                InActiveUserActivity.this.EmailET.requestFocus();
            } else if (InActiveUserActivity.this.checkValidEmail.equalsIgnoreCase("wrong")) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a valid email id.", 12);
                InActiveUserActivity.this.EmailET.requestFocus();
            } else if (InActiveUserActivity.this.MobileET.getText().toString().trim().length() == 0) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a mobile number.", 12);
                InActiveUserActivity.this.MobileET.requestFocus();
            } else if (InActiveUserActivity.this.MobileET.getText().toString().trim().length() < 10) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a valid 10-digit mobile number", 12);
                InActiveUserActivity.this.MobileET.requestFocus();
            } else if (InActiveUserActivity.this.MobileET.getText().toString().trim().length() > 10) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a valid 10-digit mobile number", 12);
                InActiveUserActivity.this.MobileET.requestFocus();
            } else if (InActiveUserActivity.this.VerificationET.getText().toString().trim().length() == 0) {
                InActiveUserActivity.this.VerificationET.requestFocus();
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter the verification code sent to you via email or SMS", 12);
            } else {
                InActiveUserActivity.this.proceedService();
            }
        }
    }

    class C14256 extends AsyncTask<Void, Void, Integer> {
        ProgressDialog pd;
        int resultConn = -1;

        C14256() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pd = new ProgressDialog(InActiveUserActivity.this);
            this.pd.setMessage("Loading...");
            this.pd.show();
        }

        protected Integer doInBackground(Void... params) {
            this.resultConn = HttpCall.getVerificationServiceMakeInActive("" + InActiveUserActivity.this.MobileET.getText().toString().trim(), InActiveUserActivity.this.EmailET.getText().toString().trim(), InActiveUserActivity.this.VerificationET.getText().toString().trim());
            return Integer.valueOf(this.resultConn);
        }

        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            this.pd.dismiss();
            if (this.resultConn == 1) {
                Log.e("Connections ", "success");
                InActiveUserActivity.this.startActivity(new Intent(InActiveUserActivity.this, LoginActivity.class).putExtra("TAG", "InActiveUser").putExtra("UserEmail", InActiveUserActivity.this.EmailET.getText().toString().trim()).putExtra("New_TAG", InActiveUserActivity.this.TAG).addFlags(32768).addFlags(67108864));
            } else if (this.resultConn == 4) {
                Log.e(" MainAcitvity   ", "Network error in specialty process");
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Ooops! Look like that was an expected error encountered on the server. Please try again later.", 12);
            } else if (this.resultConn == 22) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "No Records Found", 12);
            } else if (this.resultConn == 11) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "No Records Found", 12);
            } else if (this.resultConn == 33) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "This email address is already registered with us. Please login with your email id and password. You may click on the Forgot Password link on the Sign In Screen to request your password.", 8);
            } else if (this.resultConn == 0) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a valid verification code.", 12);
            } else if (this.resultConn == 44) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "This username is already in use. Please use a different name", 12);
            } else {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Ooops! Looks like you have encountered a connectivity problem. Please make sure you are connected and try again.", 12);
            }
        }
    }

    class C14267 extends AsyncTask<Void, Void, Integer> {
        ProgressDialog pd;
        int resultConn = -1;

        C14267() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pd = new ProgressDialog(InActiveUserActivity.this);
            this.pd.setMessage("Loading...");
            this.pd.show();
        }

        protected Integer doInBackground(Void... params) {
            this.resultConn = HttpCall.getVerificationServiceInActive("" + InActiveUserActivity.this.MobileET.getText().toString().trim(), InActiveUserActivity.this.EmailET.getText().toString().trim());
            if (this.resultConn == 1) {
                this.resultConn = SplitJson.splitVerificationCode(GlobalData.sDownloadedContent);
            }
            return Integer.valueOf(this.resultConn);
        }

        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            this.pd.dismiss();
            if (this.resultConn == 1) {
                Log.e("Connections ", "success");
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, GlobalData.getVerificationResponse, 1);
                this.pd.dismiss();
            } else if (this.resultConn == 4) {
                Log.e(" MainAcitvity   ", "Network error in specialty process");
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Ooops! Look like that was an expected error encountered on the server. Please try again later.", 12);
            } else if (this.resultConn == 22) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "No Records Found", 12);
            } else if (this.resultConn == 11) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "No Records Found", 12);
            } else if (this.resultConn == 33) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "This email address is already registered with us. Please login with your email id and password. You may click on the Forgot Password link on the Sign In Screen to request your password.", 8);
            } else if (this.resultConn == 0) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Please enter a valid verification code.", 12);
            } else if (this.resultConn == 44) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "This username is already in use. Please use a different name", 12);
            } else if (this.resultConn == 77) {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "This mobile number is already in use. Please use a different mobile number.", 12);
            } else {
                InActiveUserActivity.this.showAlertDialog(InActiveUserActivity.this, "Ooops! Looks like you have encountered a connectivity problem. Please make sure you are connected and try again.", 12);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_in_active_user);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.TAG = extras.getString("TAG");
            this.email = extras.getString("email");
        }
        initialiseControls();
    }

    private void initialiseControls() {
        this.regTopSettingsInActive = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.regTopSettingsInActive.setVisibility(8);
        this.topBackBtnInActive = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.topBackBtnInActive.setOnClickListener(new C14201());
        this.OTPlay = (LinearLayout) findViewById(C1469R.id.linearOTPlay);
        this.OTPlay.setVisibility(8);
        this.EmailET = (EditText) findViewById(C1469R.id.inactiveEmailEditTxt);
        this.EmailET.addTextChangedListener(new C14212());
        this.EmailET.setText(this.email);
        this.MobileET = (EditText) findViewById(C1469R.id.inactiveMobileEditTxt);
        this.MobileET.requestFocus();
        this.MobileET.addTextChangedListener(new C14223());
        this.VerificationET = (EditText) findViewById(C1469R.id.inactiveOTPEditTxt);
        this.getOTPBtn = (Button) findViewById(C1469R.id.inActiveGetOTPBtn);
        this.getOTPBtn.setOnClickListener(new C14234());
        this.proceedBtn = (Button) findViewById(C1469R.id.inActiveProceedBtn);
        this.proceedBtn.setOnClickListener(new C14245());
    }

    public void onBackPressed() {
        finish();
    }

    private void proceedService() {
        new C14256().execute(new Void[]{null, null, null});
    }

    private void getOTPService() {
        new C14267().execute(new Void[]{null, null, null});
    }

    public void showAlertDialog(Context context, String message, final int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (value == 1) {
                    InActiveUserActivity.this.OTPlay.setVisibility(0);
                    InActiveUserActivity.this.VerificationET.requestFocus();
                } else if (value != 8) {
                }
            }
        });
        alertDialog.show();
    }
}
