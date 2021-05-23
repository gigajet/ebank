package nnhl.project.ebank.Firebase;

import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import nnhl.project.ebank.Client.IncomingCallActivity;
import nnhl.project.ebank.Const;
import nnhl.project.ebank.Counsellor.VideoCall.VideoCallActivity;
import nnhl.project.ebank.Global;

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
            if (msg_type.equals(Const.REMOTE_MSG_ACCEPT_CALL)) {
                //kill activity IncomingCallActivity, but don't know how
                Global.getInstance().getData().put(Const.TAG_WAITING_FOR_CALL, Const.TAG_NO);
                Intent intent=new Intent(getApplicationContext(), VideoCallActivity.class);
                intent.putExtra(Const.TAG_TOKEN_VIDEOCALL,
                        remoteMessage.getData().get(Const.TAG_COUNSELLOR_FCM_TOKEN));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            if (msg_type.equals(Const.REMOTE_MSG_DENY_CALL)) {
                Global.getInstance().getData().put(Const.TAG_WAITING_FOR_CALL, Const.TAG_NO);
            }
        }
    }
}
