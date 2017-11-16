package org.the3hackers.a3hacks;
import Adapter.ImageLoader;
import Opertaion.Constants;
import Opertaion.GlobalData;
import Opertaion.SplitJson;
import Services.Connection;
import Services.HttpCall;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsView extends Fragment {
    TextView Address;
    LinearLayout Address_LinearLay;
    TextView Area;
    Button BookAppointmentBTN;
    TextView City;
    LinearLayout Desig_LinearLay;
    TextView Designation;
    ImageView DocIMG;
    TextView DoctorName;
    TextView DoctorQualification;
    TextView DoctorSpecialty;
    TextView Email;
    LinearLayout Email_LinearLay;
    TextView FeesTxt;
    LinearLayout Fees_LinearLay;
    TextView HospitalName;
    LinearLayout Hospital_LinearLay;
    TextView Phone;
    LinearLayout Phone_LinearLay;
    TextView Pin;
    TextView State;
    LinearLayout Time_LinearLay;
    TextView Timing;
    TextView dummyTxt;
    public ImageLoader imageLoader;
    String login_Status;
    ImageView mobileImageView;
    View rootview;
    SharedPreferences sharedPreferences;

    class C13891 implements OnClickListener {
        C13891() {
        }

        public void onClick(View v) {
            if (GlobalData.AppointmentStatus.equalsIgnoreCase("No")) {
                Log.e("call for ", "Appointment");
                DetailsView.this.BookAppointmentBTN.setText("Call");
            } else if (GlobalData.AppointmentStatus.equalsIgnoreCase("Yes")) {
                DetailsView.this.sharedPreferences = DetailsView.this.getActivity().getSharedPreferences(Constants.PREFS_NAME, 0);
                DetailsView.this.login_Status = DetailsView.this.sharedPreferences.getString("login_Status", "");
                if (DetailsView.this.login_Status.equals("")) {
                    DetailsView.this.startActivity(new Intent(DetailsView.this.getActivity(), LoginActivity.class).putExtra("TAG", "doctorDetailsClass"));
                    return;
                }
                DetailsView.this.getTimeSlot();
            }
        }
    }

    class C13902 extends AsyncTask<Void, Void, Integer> {
        ProgressDialog pd;
        int resultConn = -1;

        C13902() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pd = new ProgressDialog(DetailsView.this.getActivity());
            this.pd.setMessage("Loading...");
            this.pd.setCanceledOnTouchOutside(false);
            this.pd.show();
        }

        protected Integer doInBackground(Void... params) {
            String locId = "215";
            this.resultConn = HttpCall.getTimeslotparameter(GlobalData.LocationID, GlobalData.DoctorUsername);
            if (this.resultConn == 1) {
                this.resultConn = SplitJson.splitAppointmentSlot(GlobalData.sDownloadedContent);
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
                DetailsView.this.startActivity(new Intent(DetailsView.this.getActivity(), BookAppointmentActivity.class).putExtra("doctorName", DetailsView.this.DoctorName.getText().toString().trim()).putExtra("qualification", DetailsView.this.DoctorQualification.getText().toString().trim()).putExtra("hospitalName", DetailsView.this.HospitalName.getText().toString().trim()).putExtra("hospitalLocation", GlobalData.City));
            } else if (this.resultConn == 4) {
                DetailsView.this.showAlertDialog(DetailsView.this.getActivity(), DetailsView.this.getString(C1469R.string.SERVER_PROBLEM_MSG), 1);
            } else if (this.resultConn == 66) {
                DetailsView.this.showAlertDialog(DetailsView.this.getActivity(), "Reached Limits ...Please Login..??", 1);
            } else if (this.resultConn == 11) {
                DetailsView.this.showAlertDialog(DetailsView.this.getActivity(), "NULL VALUE..??", 1);
            } else {
                DetailsView.this.showAlertDialog(DetailsView.this.getActivity(), DetailsView.this.getString(C1469R.string.Network_ErrorMsg), 1);
            }
        }
    }

    class C13913 implements DialogInterface.OnClickListener {
        C13913() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootview = inflater.inflate(C1469R.layout.activity_detail, container, false);
        this.imageLoader = new ImageLoader(getActivity());
        controls();
        return this.rootview;
    }

    private void controls() {
        this.DocIMG = (ImageView) this.rootview.findViewById(C1469R.id.doc_photo_detailsView);
        this.imageLoader.DisplayImage(GlobalData.DoctorPhoto, getActivity(), this.DocIMG);
        this.Hospital_LinearLay = (LinearLayout) this.rootview.findViewById(C1469R.id.Hospital_linearLay);
        this.mobileImageView = (ImageView) this.rootview.findViewById(C1469R.id.mobile_imgeView);
        this.Desig_LinearLay = (LinearLayout) this.rootview.findViewById(C1469R.id.desig_linearLay);
        this.Address_LinearLay = (LinearLayout) this.rootview.findViewById(C1469R.id.address_linearLay);
        this.Phone_LinearLay = (LinearLayout) this.rootview.findViewById(C1469R.id.Mobile_linearLay);
        this.Phone_LinearLay.setVisibility(8);
        this.Email_LinearLay = (LinearLayout) this.rootview.findViewById(C1469R.id.email_linearLay);
        this.Time_LinearLay = (LinearLayout) this.rootview.findViewById(C1469R.id.fax_linearLay);
        this.Fees_LinearLay = (LinearLayout) this.rootview.findViewById(C1469R.id.Fees_linearLay);
        this.DoctorName = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorNameTxt);
        this.DoctorName.setText(GlobalData.Doctorname);
        this.DoctorQualification = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorQualificationTxt);
        if (GlobalData.Qualification.equalsIgnoreCase("")) {
            this.DoctorQualification.setVisibility(8);
        } else {
            this.DoctorQualification.setText(GlobalData.Qualification);
        }
        this.DoctorSpecialty = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorSpecialtyTxt);
        if (GlobalData.Specialization.equalsIgnoreCase("")) {
            this.DoctorSpecialty.setVisibility(8);
        } else {
            this.DoctorSpecialty.setText(GlobalData.Specialization);
        }
        this.HospitalName = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorHospitalNameTxt);
        if (GlobalData.HospitalName.equalsIgnoreCase("") || GlobalData.HospitalName.equalsIgnoreCase("NULL")) {
            this.Hospital_LinearLay.setVisibility(8);
        } else {
            this.HospitalName.setText(GlobalData.HospitalName);
        }
        this.Designation = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorDesigTxt);
        if (GlobalData.Biography.equalsIgnoreCase("")) {
            Log.e("2. .Biography  ?????", "--------" + GlobalData.Biography);
            this.Desig_LinearLay.setVisibility(8);
        } else {
            Log.e("3.GlobalData.Biography  ?????", "--------" + GlobalData.Biography);
            this.Designation.setText(GlobalData.Biography);
        }
        this.Address = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorAddressTxt);
        if (GlobalData.Address.equalsIgnoreCase("")) {
            this.Address.setVisibility(8);
        } else {
            this.Address.setText(GlobalData.Address);
        }
        this.Area = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorAreaTxt);
        if (GlobalData.Area.equalsIgnoreCase("")) {
            this.Area.setVisibility(8);
        } else {
            this.Area.setText(GlobalData.Area);
        }
        this.City = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorCityTxt);
        if (GlobalData.City.equalsIgnoreCase("")) {
            this.City.setVisibility(8);
        } else {
            this.City.setText(GlobalData.City);
        }
        this.State = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorStateTxt);
        if (GlobalData.State.equalsIgnoreCase("")) {
            this.State.setVisibility(8);
        } else {
            this.State.setText(GlobalData.State);
        }
        this.Pin = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorPinTxt);
        this.dummyTxt = (TextView) this.rootview.findViewById(C1469R.id.detailsview_txt);
        if (GlobalData.Pincode.equalsIgnoreCase("")) {
            this.Pin.setVisibility(8);
            this.dummyTxt.setVisibility(8);
        } else {
            this.Pin.setText(GlobalData.Pincode);
        }
        this.Phone = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorPhoneTxt);
        Log.e("GlobalData.Phone  ", "" + GlobalData.Phone);
        if (GlobalData.Phone.trim().equalsIgnoreCase("")) {
            this.Phone_LinearLay.setVisibility(8);
            this.Phone.setVisibility(8);
            this.mobileImageView.setVisibility(8);
        } else {
            this.Phone_LinearLay.setVisibility(0);
            this.Phone.setVisibility(0);
            this.mobileImageView.setVisibility(0);
            this.Phone.setText(GlobalData.Phone);
        }
        this.Email = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorEmailTxt);
        if (GlobalData.Email.equalsIgnoreCase("")) {
            this.Email_LinearLay.setVisibility(8);
        } else {
            this.Email.setText(GlobalData.Email);
        }
        this.Timing = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorTimeTxt);
        if (GlobalData.AvialableTime.equalsIgnoreCase("")) {
            this.Time_LinearLay.setVisibility(8);
        } else {
            this.Timing.setText(GlobalData.AvialableTime);
        }
        this.FeesTxt = (TextView) this.rootview.findViewById(C1469R.id.detailsview_dotoctorFeesTxt);
        if (GlobalData.Fees.equalsIgnoreCase("")) {
            this.Fees_LinearLay.setVisibility(8);
        } else {
            this.FeesTxt.setText("INR " + GlobalData.Fees);
        }
        this.BookAppointmentBTN = (Button) this.rootview.findViewById(C1469R.id.detailedViewAppointmentBtn);
        if (GlobalData.AppointmentStatus.equalsIgnoreCase("No")) {
            Log.e("call for ", "Appointment");
            this.BookAppointmentBTN.setVisibility(8);
            this.BookAppointmentBTN.setText("Call");
            this.Phone_LinearLay.setVisibility(0);
        } else if (GlobalData.AppointmentStatus.equalsIgnoreCase("Yes")) {
            this.BookAppointmentBTN.setVisibility(0);
            this.Phone_LinearLay.setVisibility(8);
            this.BookAppointmentBTN.setText("Book an Appointment");
        }
        this.BookAppointmentBTN.setOnClickListener(new C13891());
    }

    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Tag", "FragmentA.onDestroyVivew() has been called.");
        try {
            if (this.rootview != null) {
                this.rootview = null;
            }
        } catch (Exception e) {
            Log.e("Error in onDestroyview in doctors map view ", " " + e);
        }
    }

    private void getTimeSlot() {
        new C13902().execute(new Void[]{null, null, null});
    }

    public void showAlertDialog(Context context, String message, int value) {
        AlertDialog alertDialog = new Builder(context).create();
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setButton("OK", new C13913());
        alertDialog.show();
    }
}
