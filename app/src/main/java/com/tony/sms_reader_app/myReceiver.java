package com.tony.sms_reader_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class myReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();

        if (intent.getAction().equalsIgnoreCase("android.provider.Telephony.SMS_RECEIVED")){
            //SMS Received

            if (b != null){
                final Object[] pdusObj = (Object []) b.get("pdus");

                SmsMessage [] messages = new SmsMessage[pdusObj.length];

                for (int i = 0; i< messages.length; i++){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String format = b.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                    }
                    else
                        {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    }
                    String senderNum = messages[i].getOriginatingAddress();
                    String message = messages[i].getMessageBody();
                    String name = messages[i].getDisplayOriginatingAddress();
                    String mpesaText = "MPESA";

                    if (senderNum.equals("MPESA")) {
                        MpesaText mpesaTextClass = new MpesaText(message);
                        Toast.makeText(context, "Mpesa Text", Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(context, name + ":" + message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}
