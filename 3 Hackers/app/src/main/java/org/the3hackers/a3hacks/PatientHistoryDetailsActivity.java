package org.the3hackers.a3hacks;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PatientHistoryDetailsActivity extends Activity {
    String AppointmentCancelReason = "";
    String AppointmentCancelledByWhom = "";
    TextView ApptCancelReasonTxt;
    TextView ApptCancel_WhomTxt;
    LinearLayout CancelReasonLinearLayout;
    String ContactNo = "";
    String Date = "";
    String DoctorName = "";
    TextView DoctorNameTxt;
    String Doctor_Reason = "";
    TextView Doctor_reasonTxt;
    String HospitalName = "";
    TextView HospitalnameTxt;
    String LocationId = "";
    String PatientAppointment_Id = "";
    String PatientUsername = "";
    String Qualification = "";
    TextView QualificationTxt;
    String Specialty = "";
    String Time = "";
    TextView TimeTxt;
    RelativeLayout backbtn;
    Button cancelAppointmentBtn;
    TextView contactTxt;
    TextView dateTxt;
    LinearLayout doctorReasonLinearLay;
    RelativeLayout settingsbtn;
    TextView specialtyTxt;
    String status = "";
    TextView titleTxt;

    class C14541 implements OnClickListener {
        C14541() {
        }

        public void onClick(View v) {
            PatientHistoryDetailsActivity.this.onBackPressed();
        }
    }

    class C14552 implements OnClickListener {
        C14552() {
        }

        public void onClick(View v) {
            PatientHistoryDetailsActivity.this.startActivity(new Intent(PatientHistoryDetailsActivity.this.getBaseContext(), CancelActivity.class).putExtra("PatientAppointment_Id", PatientHistoryDetailsActivity.this.PatientAppointment_Id).putExtra("LocationId", PatientHistoryDetailsActivity.this.LocationId).putExtra("PatientUsername", PatientHistoryDetailsActivity.this.PatientUsername));
        }
    }

    class C14563 implements DialogInterface.OnClickListener {
        C14563() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_patient_history_details);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        this.status = getIntent().getExtras().getString("Status");
        this.LocationId = getIntent().getExtras().getString("lochospid");
        this.DoctorName = getIntent().getExtras().getString("DoctorName");
        this.Date = getIntent().getExtras().getString("Date");
        this.Time = getIntent().getExtras().getString("Time");
        this.HospitalName = getIntent().getExtras().getString("HospitalName");
        this.ContactNo = getIntent().getExtras().getString("ContactDetails");
        this.Doctor_Reason = getIntent().getExtras().getString("Reason");
        this.PatientAppointment_Id = getIntent().getExtras().getString("PatientAppointmentId");
        this.PatientUsername = getIntent().getExtras().getString("patient_userName");
        this.AppointmentCancelledByWhom = getIntent().getExtras().getString("AppointmentCancelledbyWhom");
        this.AppointmentCancelReason = getIntent().getExtras().getString("AppointmentCancelReason");
        this.Qualification = getIntent().getExtras().getString("qualification");
        this.Specialty = getIntent().getExtras().getString("specialty");
        initialiseControls();
    }

    private void initialiseControls() {
        this.titleTxt = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        this.titleTxt.setText("My Appointments");
        this.QualificationTxt = (TextView) findViewById(C1469R.id.patient_doctorQualification);
        this.specialtyTxt = (TextView) findViewById(C1469R.id.patient_doctorSpecialty);
        this.backbtn = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.backbtn.setOnClickListener(new C14541());
        this.settingsbtn = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.settingsbtn.setVisibility(8);
        this.CancelReasonLinearLayout = (LinearLayout) findViewById(C1469R.id.linear_CancelReasonLay);
        this.CancelReasonLinearLayout.setVisibility(8);
        this.doctorReasonLinearLay = (LinearLayout) findViewById(C1469R.id.doctorReason_linearlayout);
        this.DoctorNameTxt = (TextView) findViewById(C1469R.id.patient_doctorName);
        this.TimeTxt = (TextView) findViewById(C1469R.id.patient_Time);
        this.dateTxt = (TextView) findViewById(C1469R.id.patient_Date);
        this.HospitalnameTxt = (TextView) findViewById(C1469R.id.patient_HospitalName);
        this.contactTxt = (TextView) findViewById(C1469R.id.patient_contactNo);
        this.Doctor_reasonTxt = (TextView) findViewById(C1469R.id.doctorReason);
        this.ApptCancel_WhomTxt = (TextView) findViewById(C1469R.id.cancelledByWhomTxt);
        this.ApptCancelReasonTxt = (TextView) findViewById(C1469R.id.cancelledByReasonTxt);
        this.DoctorNameTxt.setText(this.DoctorName);
        this.TimeTxt.setText(this.Time);
        this.dateTxt.setText(this.Date);
        this.HospitalnameTxt.setText(this.HospitalName);
        this.contactTxt.setText(this.ContactNo);
        this.Doctor_reasonTxt.setText(this.Doctor_Reason);
        this.QualificationTxt.setText(this.Qualification.trim());
        this.specialtyTxt.setText(this.Specialty);
        this.cancelAppointmentBtn = (Button) findViewById(C1469R.id.patientHisty_CancelAppment);
        this.cancelAppointmentBtn.setVisibility(8);
        if (this.status.equalsIgnoreCase("pending") || this.status.equalsIgnoreCase("confirmed")) {
            this.CancelReasonLinearLayout.setVisibility(8);
            this.cancelAppointmentBtn.setVisibility(0);
        } else {
            this.Doctor_reasonTxt.setText(this.Doctor_Reason);
            if (this.AppointmentCancelledByWhom.equalsIgnoreCase("") && this.AppointmentCancelReason.equalsIgnoreCase("")) {
                this.CancelReasonLinearLayout.setVisibility(8);
            } else if (this.AppointmentCancelledByWhom.equalsIgnoreCase("null") && this.AppointmentCancelReason.equalsIgnoreCase("NULL")) {
                this.CancelReasonLinearLayout.setVisibility(8);
            } else {
                this.CancelReasonLinearLayout.setVisibility(0);
                this.ApptCancel_WhomTxt.setText(this.AppointmentCancelledByWhom);
                this.ApptCancelReasonTxt.setText(this.AppointmentCancelReason);
            }
            this.doctorReasonLinearLay.setVisibility(0);
            this.cancelAppointmentBtn.setVisibility(8);
        }
        this.cancelAppointmentBtn.setOnClickListener(new C14552());
    }

    public void onBackPressed() {
        finish();
    }

    public void showAlertDialog(Context context, String message, int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new C14563());
        alertDialog.show();
    }
}
