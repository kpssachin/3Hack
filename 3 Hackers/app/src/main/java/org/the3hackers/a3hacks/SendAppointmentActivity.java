package org.the3hackers.a3hacks;
import Opertaion.GlobalData;
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
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SendAppointmentActivity extends Activity {
    TextView AppointmentDateTxt;
    String AppointmentDate_ = "";
    String AppointmentTime = "";
    TextView AppointmentTimeTxt;
    Button BookAppointmentBtn;
    String DoctorName = "";
    TextView DoctorNameTxt;
    String Doctor_Appt_day = "";
    String Doctor_HospitalName = "";
    String Doctor_Qualification = "";
    TextView HospitalNameTxt;
    String LocationId = "";
    String PatientUsername = "";
    TextView QualificationTxt;
    EditText ReasonEditTxt;
    RelativeLayout backTopbarRelativeLay;
    RelativeLayout settingsTopbarRelativeLay;
    String specialty = "";
    TextView specialtyTxt;
    TextView titleTopbarTxt;

    class C14791 implements OnClickListener {
        C14791() {
        }

        public void onClick(View v) {
            SendAppointmentActivity.this.onBackPressed();
        }
    }

    class C14802 implements OnClickListener {
        C14802() {
        }

        public void onClick(View v) {
            String SuccessMSg = "Thank you for booking your appointment with " + SendAppointmentActivity.this.DoctorName + ". Your appointment will be confirmed after verification by the doctor's office at " + SendAppointmentActivity.this.HospitalNameTxt.getText().toString() + ".  We will send you an email / SMS with the appointment details.";
            if (SendAppointmentActivity.this.ReasonEditTxt.getText().toString().trim().length() != 0) {
                SendAppointmentActivity.this.sendAppointmentDetails();
            } else {
                SendAppointmentActivity.this.showAlertDialog(SendAppointmentActivity.this, "Please enter your reason.", 1);
            }
        }
    }

    class C14813 extends AsyncTask<Void, Void, Integer> {
        ProgressDialog pd;
        int resultConn = -1;

        C14813() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pd = new ProgressDialog(SendAppointmentActivity.this);
            this.pd.setMessage("Loading...");
            this.pd.setCanceledOnTouchOutside(false);
            this.pd.show();
        }

        protected Integer doInBackground(Void... params) {
            this.resultConn = HttpCall.sendAppointmentPatameter(SendAppointmentActivity.this.LocationId, SendAppointmentActivity.this.AppointmentDate_, SendAppointmentActivity.this.AppointmentTimeTxt.getText().toString(), SendAppointmentActivity.this.ReasonEditTxt.getText().toString().trim(), SendAppointmentActivity.this.PatientUsername);
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
                SendAppointmentActivity.this.showAlertDialog(SendAppointmentActivity.this, "Thank you for booking your appointment with " + SendAppointmentActivity.this.DoctorName + ". Your appointment will be confirmed after verification by the doctor's office at " + SendAppointmentActivity.this.HospitalNameTxt.getText().toString() + ".  We will send you an email / SMS with the appointment details.", 2);
            } else if (this.resultConn == 4) {
                SendAppointmentActivity.this.showAlertDialog(SendAppointmentActivity.this, SendAppointmentActivity.this.getString(C1469R.string.SERVER_PROBLEM_MSG), 1);
            } else if (this.resultConn == 66) {
                SendAppointmentActivity.this.showAlertDialog(SendAppointmentActivity.this, "Reached Limits ...Please Login..??", 1);
            } else if (this.resultConn == 11) {
                SendAppointmentActivity.this.showAlertDialog(SendAppointmentActivity.this, SendAppointmentActivity.this.getString(C1469R.string.INVALID_DATA_MSG), 1);
            } else {
                SendAppointmentActivity.this.showAlertDialog(SendAppointmentActivity.this, SendAppointmentActivity.this.getString(C1469R.string.Network_ErrorMsg), 1);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_send_appointment);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        this.AppointmentTime = getIntent().getExtras().getString("AppointmentTime");
        this.DoctorName = getIntent().getExtras().getString("DoctorName");
        this.AppointmentDate_ = getIntent().getExtras().getString("AppointmentDate");
        this.LocationId = getIntent().getExtras().getString("locationId");
        this.PatientUsername = getIntent().getExtras().getString("Patient_Username");
        this.Doctor_Qualification = getIntent().getExtras().getString("doctor_qualification");
        this.Doctor_HospitalName = getIntent().getExtras().getString("doctor_hospitalName");
        this.Doctor_Appt_day = getIntent().getExtras().getString("doctor_Appt_day");
        this.specialty = getIntent().getExtras().getString("doctorSpecialty");
        controls();
    }

    private void controls() {
        this.titleTopbarTxt = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        if (GlobalData.patientFullName.equalsIgnoreCase("")) {
            this.titleTopbarTxt.setText("Welcome Guest");
        } else {
            this.titleTopbarTxt.setText("Welcome " + GlobalData.patientFullName);
        }
        this.specialtyTxt = (TextView) findViewById(C1469R.id.sendAppointmentSpecialtyTxt);
        this.specialtyTxt.setText(this.specialty);
        this.backTopbarRelativeLay = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.backTopbarRelativeLay.setOnClickListener(new C14791());
        this.settingsTopbarRelativeLay = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.settingsTopbarRelativeLay.setVisibility(8);
        this.AppointmentTimeTxt = (TextView) findViewById(C1469R.id.sendAppointmentTimeTxt);
        this.AppointmentTimeTxt.setText(this.AppointmentTime);
        this.DoctorNameTxt = (TextView) findViewById(C1469R.id.sendAppointmentDoctorNameTxt);
        this.DoctorNameTxt.setText(this.DoctorName);
        this.AppointmentDateTxt = (TextView) findViewById(C1469R.id.sendAppointmentDateTxt);
        this.AppointmentDateTxt.setText(this.AppointmentDate_ + "  " + this.Doctor_Appt_day);
        this.HospitalNameTxt = (TextView) findViewById(C1469R.id.sendAppointmentHospitalNameTxt);
        this.HospitalNameTxt.setText(this.Doctor_HospitalName);
        this.QualificationTxt = (TextView) findViewById(C1469R.id.sendAppointmentQualificationTxt);
        this.QualificationTxt.setText(this.Doctor_Qualification.trim());
        this.ReasonEditTxt = (EditText) findViewById(C1469R.id.sendAppointmentEditTxt);
        this.BookAppointmentBtn = (Button) findViewById(C1469R.id.sendAppointmentBtn);
        this.BookAppointmentBtn.setOnClickListener(new C14802());
    }

    public void onBackPressed() {
        finish();
    }

    private void sendAppointmentDetails() {
        new C14813().execute(new Void[]{null, null, null});
    }

    public void showAlertDialog(Context context, String message, final int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (value == 2) {
                    SendAppointmentActivity.this.startActivity(new Intent(SendAppointmentActivity.this.getBaseContext(), PatientHistory.class));
                }
            }
        });
        alertDialog.show();
    }
}
