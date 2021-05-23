package nnhl.project.ebank.Firebase;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import nnhl.project.ebank.Client.IncomingCallActivity;
import nnhl.project.ebank.Const;

public class MessageService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);
        Log.d("FCM", "Token "+s);
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("FCM","onMessageReceived: "+remoteMessage.getData().toString());
        String msg_type=remoteMessage.getData().get(Const.REMOTE_MSG_TYPE);

        if (msg_type!=null) {
            if (msg_type.equals(Const.REMOTE_MSG_CALL)) {
                Intent intent=new Intent(getApplicationContext(), IncomingCallActivity.class);
                intent.putExtra(Const.REMOTE_MSG_CALL_TOKEN,
                        remoteMessage.getData().get(Const.REMOTE_MSG_CALL_TOKEN));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
