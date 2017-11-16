package org.the3hackers.a3hacks;
import Services.Connection;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.drive.DriveFile;

public class CancelActivity extends Activity {
    String LocationId = "";
    String PatientAppointment_Id = "";
    String PatientUsername = "";
    TextView TopTitle;
    RelativeLayout appBackBtn;
    Button backBtn;
    EditText cancelReasonEdit;
    Button confirmBtn;
    RelativeLayout topSettingsBtn;

    class C13771 implements OnClickListener {
        C13771() {
        }

        public void onClick(View v) {
            CancelActivity.this.finish();
        }
    }

    class C13782 implements OnClickListener {
        C13782() {
        }

        public void onClick(View v) {
            CancelActivity.this.finish();
        }
    }

    class C13793 implements OnClickListener {
        C13793() {
        }

        public void onClick(View v) {
            if (CancelActivity.this.cancelReasonEdit.getText().toString().trim().length() != 0) {
                CancelActivity.this.cancelAppointmentService(CancelActivity.this.cancelReasonEdit.getText().toString().trim());
                return;
            }
            CancelActivity.this.showAlertDialog(CancelActivity.this, "Please enter the  reason", 1);
        }
    }

    class C13815 implements DialogInterface.OnClickListener {
        C13815() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_cancel);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.PatientAppointment_Id = extras.getString("PatientAppointment_Id");
            this.LocationId = extras.getString("LocationId");
            this.PatientUsername = extras.getString("PatientUsername");
        }
        controls();
    }

    private void controls() {
        this.appBackBtn = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.appBackBtn.setOnClickListener(new C13771());
        this.topSettingsBtn = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.topSettingsBtn.setVisibility(8);
        this.TopTitle = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        this.TopTitle.setText("Cancel Appointment");
        this.cancelReasonEdit = (EditText) findViewById(C1469R.id.cancel_resaonEditTxt);
        this.backBtn = (Button) findViewById(C1469R.id.Cancel_Btn_cancel);
        this.backBtn.setOnClickListener(new C13782());
        this.confirmBtn = (Button) findViewById(C1469R.id.submitBtn_cancel);
        this.confirmBtn.setOnClickListener(new C13793());
    }

    private void cancelAppointmentService(final String reason) {
        new AsyncTask<Void, Void, Integer>() {
            ProgressDialog pd;
            int resultConn = -1;

            protected void onPreExecute() {
                super.onPreExecute();
                this.pd = new ProgressDialog(CancelActivity.this);
                this.pd.setMessage("Loading...");
                this.pd.show();
            }

            protected Integer doInBackground(Void... params) {
                this.resultConn = HttpCall.cancelAppointment(CancelActivity.this.PatientAppointment_Id, CancelActivity.this.LocationId, reason, CancelActivity.this.PatientUsername);
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
                    CancelActivity.this.startActivity(new Intent(CancelActivity.this.getBaseContext(), PatientHistory.class).addFlags(32768).addFlags(67108864).addFlags(DriveFile.MODE_READ_ONLY));
                } else if (this.resultConn == 4) {
                    Log.e(" MainAcitvity   ", "Network error in specialty process");
                    CancelActivity.this.showAlertDialog(CancelActivity.this, CancelActivity.this.getString(C1469R.string.SERVER_PROBLEM_MSG), 1);
                } else if (this.resultConn == 22) {
                    CancelActivity.this.showAlertDialog(CancelActivity.this, CancelActivity.this.getString(C1469R.string.INVALID_DATA_MSG), 1);
                } else {
                    CancelActivity.this.showAlertDialog(CancelActivity.this, CancelActivity.this.getString(C1469R.string.Network_ErrorMsg), 1);
                }
            }
        }.execute(new Void[]{null, null, null});
    }

    public void showAlertDialog(Context context, String message, int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new C13815());
        alertDialog.show();
    }

    public void onBackPressed() {
        finish();
    }
}
