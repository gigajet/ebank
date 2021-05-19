package nnhl.project.ebank.hikikomori;

import android.app.Service;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

public class firebase_msg_service extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        Log.d("FBMSG","New token: "+s);
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("FBMSG", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("FBMSG", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("FBMSG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
