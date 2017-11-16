package org.the3hackers.a3hacks;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutActivity extends Activity {
    ImageView MedlogoBtn;
    TextView TitleTxt;
    RelativeLayout aboutBackBtn;
    RelativeLayout aboutSettingsBtn;

    class C13701 implements OnClickListener {
        C13701() {
        }

        public void onClick(View v) {
            AboutActivity.this.onBackPressed();
        }
    }

    class C13712 implements OnClickListener {
        C13712() {
        }

        public void onClick(View v) {
            AboutActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Medindia&hl=en")));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_about);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        controls();
    }

    private void controls() {
        this.TitleTxt = (TextView) findViewById(C1469R.id.topbarTitleTxt);
        this.TitleTxt.setText("About");
        this.aboutBackBtn = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.aboutBackBtn.setOnClickListener(new C13701());
        this.aboutSettingsBtn = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.aboutSettingsBtn.setVisibility(8);
        this.MedlogoBtn = (ImageView) findViewById(C1469R.id.medindia_logo_imageview);
        this.MedlogoBtn.setOnClickListener(new C13712());
    }

    public void onBackPressed() {
        finish();
    }
}
