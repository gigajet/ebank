package nnhl.project.ebank;

import android.util.Log;

import retrofit2.Callback;

public class Util {
    public static String RandomAlphanumeric (int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public static String RandomJitsiRoomName() {
        return "nnhl-ebank-"+RandomAlphanumeric(15);
    }

    public static void sendRemoteMessage (String remoteMessageBody, Callback<String> callback) {
        Log.d("FCM", "sendRemoteMessage: "+remoteMessageBody);
        ApiClient.getInstance().create(ApiService.class)
                .sendRemoteMessage(Const.getRemoteHeaders(), remoteMessageBody)
                .enqueue(callback);
    }
}
