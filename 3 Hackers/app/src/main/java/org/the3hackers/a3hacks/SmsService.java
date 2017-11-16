package org.the3hackers.a3hacks;
import Opertaion.Constants;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsService extends Service {
    private BroadcastReceiver broadcastReceiver = new C14831();

    class C14831 extends BroadcastReceiver {
        C14831() {
        }

        public void onReceive(Context context, Intent intent) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            SmsMessage shortMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);
            StringBuilder text = new StringBuilder();
            for (Object obj : pdus) {
                shortMessage = SmsMessage.createFromPdu((byte[]) obj);
                text.append(shortMessage.getMessageBody());
            }
            Log.e("d", "" + text);
            Toast.makeText(context, text + "," + shortMessage.getOriginatingAddress(), 0).show();
            if (shortMessage.getOriginatingAddress().equalsIgnoreCase("IX-MEDSMS")) {
                try {
                    String[] parts = text.toString().split("Your Medindia Verification Code is");
                    String part1 = parts[0];
                    Log.e("Your Medindia Verification Code is ===== +++ ", parts[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        registerReceiver(this.broadcastReceiver, new IntentFilter(Constants.MY_BROADCAST_ACTION));
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.broadcastReceiver);
    }
}
