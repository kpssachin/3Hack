package org.the3hackers.a3hacks;
import Opertaion.Constants;
import Opertaion.GlobalData;
import Services.HttpCall;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FeedBackActivity extends Activity {
    String Email;
    String UserName;
    RelativeLayout back_Btn;
    String donorEmail_is = "wrong";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditText feedBack_Name;
    EditText feedback_comment;
    EditText feedback_email;
    Button feedback_reset;
    Button feedback_submit;
    String login_Status;
    RelativeLayout settings_btn;
    SharedPreferences sharedPreferences;

    class C14041 implements OnClickListener {
        C14041() {
        }

        public void onClick(View v) {
            FeedBackActivity.this.onBackPressed();
        }
    }

    class C14052 implements TextWatcher {
        C14052() {
        }

        public void afterTextChanged(Editable s) {
            if (!FeedBackActivity.this.feedback_email.getText().toString().trim().matches(FeedBackActivity.this.emailPattern) || s.length() <= 0) {
                FeedBackActivity.this.donorEmail_is = "wrong";
                return;
            }
            FeedBackActivity.this.donorEmail_is = "correct";
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    class C14063 implements OnClickListener {
        C14063() {
        }

        public void onClick(View v) {
            FeedBackActivity.this.feedback_comment.setText("");
        }
    }

    class C14074 implements OnClickListener {
        C14074() {
        }

        public void onClick(View v) {
            if (FeedBackActivity.this.feedBack_Name.getText().toString().trim().length() == 0) {
                FeedBackActivity.this.showAlertDialog(FeedBackActivity.this, "Please enter your full name.", 1);
                FeedBackActivity.this.feedBack_Name.requestFocus();
            } else if (FeedBackActivity.this.feedback_email.getText().toString().trim().length() == 0) {
                FeedBackActivity.this.showAlertDialog(FeedBackActivity.this, "Please enter your email id.", 1);
                FeedBackActivity.this.feedback_email.requestFocus();
            } else if (FeedBackActivity.this.donorEmail_is.equalsIgnoreCase("wrong")) {
                FeedBackActivity.this.showAlertDialog(FeedBackActivity.this, "Please enter a valid email id.", 1);
                FeedBackActivity.this.feedback_email.requestFocus();
            } else if (FeedBackActivity.this.feedback_comment.getText().toString().trim().length() == 0) {
                FeedBackActivity.this.showAlertDialog(FeedBackActivity.this, "Please enter your feedback", 1);
                FeedBackActivity.this.feedback_comment.requestFocus();
            } else {
                FeedBackActivity.this.sendFeedback();
            }
        }
    }

    class C14085 extends AsyncTask<Void, Void, Integer> {
        ImageView closeBut;
        Dialog dialog;
        TextView dialogLoadingTxt;
        ProgressDialog pd;
        int resultConn = -1;

        C14085() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pd = new ProgressDialog(FeedBackActivity.this);
            this.pd.setMessage("Loading...");
            this.pd.setCanceledOnTouchOutside(false);
            this.pd.show();
        }

        protected Integer doInBackground(Void... params) {
            this.resultConn = HttpCall.sendFeedback(FeedBackActivity.this.getBaseContext(), FeedBackActivity.this.feedBack_Name.getText().toString(), FeedBackActivity.this.feedback_email.getText().toString(), FeedBackActivity.this.feedback_comment.getText().toString());
            return Integer.valueOf(this.resultConn);
        }

        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            this.pd.dismiss();
            if (this.resultConn == 1) {
                FeedBackActivity.this.feedback_comment.setText("");
                GlobalData.toastInflate("Your feedback has been successfully submitted ", 0).show();
                FeedBackActivity.this.finish();
            } else if (this.resultConn == 4) {
                Log.e(" MainAcitvity   ", "Network error in specialty process");
                FeedBackActivity.this.showAlertDialog(FeedBackActivity.this, FeedBackActivity.this.getString(C1469R.string.SERVER_PROBLEM_MSG), 1);
            } else if (this.resultConn == 22) {
                FeedBackActivity.this.showAlertDialog(FeedBackActivity.this, FeedBackActivity.this.getString(C1469R.string.INVALID_DATA_MSG), 1);
            } else {
                FeedBackActivity.this.showAlertDialog(FeedBackActivity.this, FeedBackActivity.this.getString(C1469R.string.Network_ErrorMsg), 1);
            }
        }
    }

    class C14096 implements DialogInterface.OnClickListener {
        C14096() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_feedback);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        this.login_Status = this.sharedPreferences.getString("login_Status", "");
        this.UserName = this.sharedPreferences.getString("userFullname", "");
        this.Email = this.sharedPreferences.getString("userEmail", "");
        controls();
    }

    private void controls() {
        this.back_Btn = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.back_Btn.setOnClickListener(new C14041());
        this.settings_btn = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.settings_btn.setVisibility(8);
        this.feedBack_Name = (EditText) findViewById(C1469R.id.feedback_EditTxt_name);
        this.feedback_email = (EditText) findViewById(C1469R.id.feedback_EditTxt__email);
        this.feedback_email.addTextChangedListener(new C14052());
        if (this.login_Status.equals("")) {
            this.feedBack_Name.setText("");
            this.feedback_email.setText("");
        } else {
            this.feedBack_Name.setText(this.UserName);
            this.feedback_email.setText(this.Email);
        }
        this.feedback_comment = (EditText) findViewById(C1469R.id.feedback_EditTxt__comment);
        this.feedback_comment.requestFocus();
        this.feedback_reset = (Button) findViewById(C1469R.id.feedback_reset_btn);
        this.feedback_reset.setOnClickListener(new C14063());
        this.feedback_submit = (Button) findViewById(C1469R.id.feedback_submit_btn);
        this.feedback_submit.setOnClickListener(new C14074());
    }

    protected void sendFeedback() {
        new C14085().execute(new Void[]{null, null, null});
    }

    public void showAlertDialog(Context context, String message, int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new C14096());
        alertDialog.show();
    }

    public void onBackPressed() {
        finish();
    }
}
