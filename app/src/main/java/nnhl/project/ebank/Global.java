package nnhl.project.ebank;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

public class Global {
    static Global instance = new Global();
    public static Global getInstance() { return instance;}

    public void GetCurrentFCMToken () {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM", "Fetching FCM token failed: " + task.getException());
                            return;
                        }
                        String token = task.getResult();
                        Log.d("FCM", "Token: "+token);
                    }
                });
    }

    private Global() {
    }
}
