import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import suraj.zhcet.amu.vtas.MyVehicle;

/**
 * Created by Suraj on 4/8/2017.
 */

public class SmsBroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[0]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
            MyVehicle inst = MyVehicle.instance();
            inst.updateList(smsMessageStr);
        }
    }
}