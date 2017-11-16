package org.the3hackers.a3hacks;
import Adapter.SectionedGridViewAdapter;
import Adapter.SectionedGridViewAdapter.OnGridItemClickListener;
import Model.AppointmentSlots;
import Opertaion.Constants;
import Opertaion.Dataset;
import Opertaion.GlobalData;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.LinkedHashMap;

public class BookAppointmentActivity extends Activity implements OnGridItemClickListener {
    protected static final String TAG = "MainActivity";
    TextView DoctorNameTxt;
    TextView HospitalLocationTxt;
    private SectionedGridViewAdapter adapter = null;
    Animation backAnimation;
    int currentPage = 0;
    private LinkedHashMap<String, Cursor> cursorMap;
    private Dataset dataSet;
    TextView dateTxt;
    TextView dayTxt;
    TextView doctorClinicNameTxt;
    String doctorHospitalLocation = "";
    TextView doctorQualificationTxt;
    TextView doctorSpecialtyTxt;
    String doctor_hospitalName = "";
    String doctor_name = "";
    String doctor_qualification = "";
    private ListView listView;
    LinearLayout nextBtn;
    String patientUsername = "";
    Animation previousAnimation;
    LinearLayout previousBtn;
    SharedPreferences sharedPreferences;
    RelativeLayout topBackRelativeLay;
    RelativeLayout topSettingsRelativelay;
    TextView topTitle;

    class C13721 implements OnGlobalLayoutListener {
        C13721() {
        }

        public void onGlobalLayout() {
            BookAppointmentActivity.this.listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            int width = BookAppointmentActivity.this.listView.getWidth();
            BookAppointmentActivity.this.adapter = new SectionedGridViewAdapter(BookAppointmentActivity.this, BookAppointmentActivity.this.cursorMap, BookAppointmentActivity.this.listView.getWidth(), BookAppointmentActivity.this.listView.getHeight(), BookAppointmentActivity.this.getResources().getDimensionPixelSize(C1469R.dimen.grid_item_size));
            BookAppointmentActivity.this.adapter.setListener(BookAppointmentActivity.this);
            BookAppointmentActivity.this.adapter.notifyDataSetChanged();
            BookAppointmentActivity.this.listView.setAdapter(BookAppointmentActivity.this.adapter);
        }
    }

    class C13732 implements OnClickListener {
        C13732() {
        }

        public void onClick(View v) {
            BookAppointmentActivity.this.finish();
        }
    }

    class C13743 implements OnClickListener {
        C13743() {
        }

        public void onClick(View v) {
            BookAppointmentActivity.this.cursorMap.clear();
            BookAppointmentActivity.this.currentPage++;
            Log.e("Next currentPage" + BookAppointmentActivity.this.currentPage, "GlobalData.appointmentSlotMainAry.size() " + GlobalData.appointmentSlotMainAry.size());
            if (BookAppointmentActivity.this.currentPage < GlobalData.appointmentSlotMainAry.size()) {
                BookAppointmentActivity.this.nextTimeslot(BookAppointmentActivity.this.currentPage);
                if (BookAppointmentActivity.this.currentPage == GlobalData.appointmentSlotMainAry.size() - 1) {
                    BookAppointmentActivity.this.nextBtn.setEnabled(false);
                    BookAppointmentActivity.this.currentPage--;
                    return;
                }
                return;
            }
            BookAppointmentActivity.this.nextBtn.setEnabled(false);
            BookAppointmentActivity.this.currentPage--;
        }
    }

    class C13754 implements OnClickListener {
        C13754() {
        }

        public void onClick(View v) {
            BookAppointmentActivity.this.currentPage--;
            Log.e("previousBtn currentPage0", "previousBtn..?? " + BookAppointmentActivity.this.currentPage);
            if (BookAppointmentActivity.this.currentPage >= 0) {
                BookAppointmentActivity.this.previousTimeslot(BookAppointmentActivity.this.currentPage);
                return;
            }
            BookAppointmentActivity.this.previousBtn.setEnabled(false);
            BookAppointmentActivity.this.currentPage = 0;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.appointment_time_slot);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        this.previousAnimation = AnimationUtils.loadAnimation(this, C1469R.anim.flipinprevious);
        this.backAnimation = AnimationUtils.loadAnimation(this, C1469R.anim.flipinnext);
        this.sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        this.patientUsername = this.sharedPreferences.getString("username", "");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.doctor_name = extras.getString("doctorName");
            this.doctor_qualification = extras.getString("qualification");
            this.doctor_hospitalName = extras.getString("hospitalName");
            this.doctorHospitalLocation = extras.getString("hospitalLocation");
        }
        this.dataSet = new Dataset();
        this.dataSet.addSection("Session 1", 18);
        this.dataSet.addSection("Session 2", 18);
        this.cursorMap = this.dataSet.getSectionCursorMap(0);
        this.listView = (ListView) findViewById(C1469R.id.listview_grid);
        this.listView.getViewTreeObserver().addOnGlobalLayoutListener(new C13721());
        controls();
    }

    private void controls() {
        this.topTitle = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        if (GlobalData.patientFullName.equalsIgnoreCase("")) {
            this.topTitle.setText("Welcome Guest");
        } else {
            this.topTitle.setText("Welcome " + GlobalData.patientFullName);
        }
        this.doctorQualificationTxt = (TextView) findViewById(C1469R.id.Txt_timeSlot_doctorQualification);
        this.doctorSpecialtyTxt = (TextView) findViewById(C1469R.id.Txt_timeSlot_doctorSpecialty);
        this.doctorClinicNameTxt = (TextView) findViewById(C1469R.id.Txt_timeSlot_doctorHospitalName);
        this.topBackRelativeLay = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.topBackRelativeLay.setOnClickListener(new C13732());
        this.topSettingsRelativelay = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.topSettingsRelativelay.setVisibility(8);
        this.DoctorNameTxt = (TextView) findViewById(C1469R.id.Txt_timeSlot_doctorName);
        this.HospitalLocationTxt = (TextView) findViewById(C1469R.id.Txt_timeslot_location);
        this.dayTxt = (TextView) findViewById(C1469R.id.timeslot_day);
        this.dateTxt = (TextView) findViewById(C1469R.id.timeslot_date);
        if (GlobalData.appointmentSlotMainAry != null) {
            try {
                this.DoctorNameTxt.setText(this.doctor_name);
                this.HospitalLocationTxt.setText(this.doctorHospitalLocation);
                this.dateTxt.setText(((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(0)).timeSlot.getTs_Date());
                this.dayTxt.setText(((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(0)).timeSlot.getTs_day());
                this.doctorQualificationTxt.setText(((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(0)).getQualification());
                this.doctorSpecialtyTxt.setVisibility(8);
                this.doctorSpecialtyTxt.setText(((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(0)).getSpecialty());
                this.doctorClinicNameTxt.setText(((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(0)).getClinicname());
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } else {
            this.dayTxt.setText("");
            this.dateTxt.setText("");
            this.doctorQualificationTxt.setText("");
            this.doctorSpecialtyTxt.setText("");
            this.doctorClinicNameTxt.setText("");
        }
        this.nextBtn = (LinearLayout) findViewById(C1469R.id.nextTimeslot_linearLayout);
        this.previousBtn = (LinearLayout) findViewById(C1469R.id.previousTimeslot_linearLayout);
        this.nextBtn.setOnClickListener(new C13743());
        this.previousBtn.setOnClickListener(new C13754());
    }

    private void previousTimeslot(int currentPage) {
        try {
            this.dateTxt.setText(((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(currentPage)).timeSlot.getTs_Date());
            this.dayTxt.setText(((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(currentPage)).timeSlot.getTs_day());
            this.cursorMap = this.dataSet.getSectionCursorMap(currentPage);
            setGrid(this.cursorMap);
            this.nextBtn.setEnabled(true);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void nextTimeslot(int currentPage) {
        try {
            this.nextBtn.setEnabled(true);
            this.previousBtn.setEnabled(true);
            this.dateTxt.setText(((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(currentPage)).timeSlot.getTs_Date());
            this.dayTxt.setText(((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(currentPage)).timeSlot.getTs_day());
            this.cursorMap = this.dataSet.getSectionCursorMap(currentPage);
            setGrid(this.cursorMap);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        this.previousBtn.setEnabled(true);
    }

    private void setGrid(final LinkedHashMap<String, Cursor> cursorMap) {
        this.listView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                try {
                    BookAppointmentActivity.this.listView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int width = BookAppointmentActivity.this.listView.getWidth();
                    BookAppointmentActivity.this.adapter = new SectionedGridViewAdapter(BookAppointmentActivity.this, cursorMap, BookAppointmentActivity.this.listView.getWidth(), BookAppointmentActivity.this.listView.getHeight(), BookAppointmentActivity.this.getResources().getDimensionPixelSize(C1469R.dimen.grid_item_size));
                    BookAppointmentActivity.this.adapter.setListener(BookAppointmentActivity.this);
                    BookAppointmentActivity.this.adapter.notifyDataSetChanged();
                    BookAppointmentActivity.this.listView.setAdapter(BookAppointmentActivity.this.adapter);
                    BookAppointmentActivity.this.listView.startAnimation(BookAppointmentActivity.this.backAnimation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onGridItemClicked(String sectionName, int position, View v) {
        try {
            Cursor sectionCursor = (Cursor) this.cursorMap.get(sectionName);
            if (sectionCursor.moveToPosition(position)) {
                String data = sectionCursor.getString(0);
                if (!data.isEmpty()) {
                    Log.d(TAG, "Your Appointment Timing is:" + data);
                    startActivity(new Intent(getBaseContext(), SendAppointmentActivity.class).putExtra("AppointmentTime", data).putExtra("DoctorName", "" + this.DoctorNameTxt.getText().toString()).putExtra("AppointmentDate", "" + this.dateTxt.getText().toString()).putExtra("locationId", "" + ((AppointmentSlots) GlobalData.appointmentSlotMainAry.get(0)).getLocid()).putExtra("Patient_Username", this.patientUsername).putExtra("doctor_qualification", this.doctor_qualification).putExtra("doctor_hospitalName", this.doctor_hospitalName).putExtra("doctor_Appt_day", this.dayTxt.getText().toString()).putExtra("doctor_qualafication", this.doctorQualificationTxt.getText().toString().trim()).putExtra("doctorSpecialty", this.doctorSpecialtyTxt.getText().toString().trim()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
