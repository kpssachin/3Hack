package org.the3hackers.a3hacks;
import Opertaion.GlobalData;
import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class HelpActivity extends Activity {
    ImageView aboutBtn;
    ImageView feedbackBtn;
    RelativeLayout helpBackBtn;
    RelativeLayout helpSettingsBtn;
    ImageView sharebtn;

    class C14161 implements OnClickListener {
        C14161() {
        }

        public void onClick(View v) {
            HelpActivity.this.onBackPressed();
        }
    }

    class C14172 implements OnClickListener {
        C14172() {
        }

        public void onClick(View v) {
            GlobalData.generalShare(HelpActivity.this.getApplicationContext());
        }
    }

    class C14183 implements OnClickListener {
        C14183() {
        }

        public void onClick(View v) {
            HelpActivity.this.startActivity(new Intent(HelpActivity.this.getBaseContext(), FeedBackActivity.class));
        }
    }

    class C14194 implements OnClickListener {
        C14194() {
        }

        public void onClick(View v) {
            HelpActivity.this.startActivity(new Intent(HelpActivity.this.getBaseContext(), AboutActivity.class));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C1469R.layout.activity_help);
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(C1469R.color.status_barColor));
        }
        controls();
    }

    private void controls() {
        this.helpBackBtn = (RelativeLayout) findViewById(C1469R.id.topbar_back_relativeLay);
        this.helpBackBtn.setOnClickListener(new C14161());
        this.helpSettingsBtn = (RelativeLayout) findViewById(C1469R.id.top_bar_setting_icon);
        this.helpSettingsBtn.setVisibility(8);
        this.sharebtn = (ImageView) findViewById(C1469R.id.share_imageview);
        this.sharebtn.setOnClickListener(new C14172());
        this.feedbackBtn = (ImageView) findViewById(C1469R.id.feedback_imageview);
        this.feedbackBtn.setOnClickListener(new C14183());
        this.aboutBtn = (ImageView) findViewById(C1469R.id.about_imageview);
        this.aboutBtn.setOnClickListener(new C14194());
    }

    public void onBackPressed() {
        finish();
    }
}
