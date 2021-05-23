package nnhl.project.ebank;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class Global {
    private static Global instance=new Global();
    private Global() {
        init_fcm_token();
        this.data=new HashMap<>();
    }

    private void init_fcm_token() {
        fcm_token=null;
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        // Log and toast
                        Log.d("FCM", "FCM token: "+token);
                        getInstance().fcm_token = token;
                    }
                });
    }

    public String get_fcm_token() {
        return fcm_token;
    }
    String fcm_token=null;

    public HashMap<String, Object> getData() {
        return data;
    }
    HashMap<String, Object> data;


    public static Global getInstance() {
        return instance;
    }
}
