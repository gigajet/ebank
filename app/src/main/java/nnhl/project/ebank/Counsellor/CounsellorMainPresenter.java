package nnhl.project.ebank.Counsellor;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.Constants;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nnhl.project.ebank.ApiClient;
import nnhl.project.ebank.ApiService;
import nnhl.project.ebank.Const;
import nnhl.project.ebank.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CounsellorMainPresenter {
    View view;
    boolean ready_to_call;
    String jitsi_room;
    String client_name;
    String req_content;

    public String get_client_fcm_token() {
        return client_fcm_token;
    }

    String client_fcm_token;
    FirebaseDatabase fb;
    DatabaseReference listen_ref;
    String TAG="COUNSELLOR-MAIN";
    public CounsellorMainPresenter(View view) {
        this.view = view;
        ready_to_call=false;
        fb=FirebaseDatabase.getInstance();
        listen_ref=null;
    }

    boolean is_ready_to_call() {return ready_to_call;}
    String get_videocall_token() {
        return jitsi_room;
    }

    void sendRemoteMessage (String remoteMessageBody, Callback<String> callback) {
        ApiClient.getInstance().create(ApiService.class)
                .sendRemoteMessage(Const.getRemoteHeaders(), remoteMessageBody)
                .enqueue(callback);
    }

    void call () {
        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    view.call_sucess();
                }
                else {
                    view.call_failure(response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.call_failure(t.getMessage());
            }
        };

        try {
            JSONArray tokens=new JSONArray();
            tokens.put(client_fcm_token);
            JSONObject body=new JSONObject();
            JSONObject data=new JSONObject();

            //Put something to data here
            data.put("motivation", "none");

            body.put(Const.REMOTE_MSG_DATA, data);
            body.put(Const.REMOTE_MSG_REGISTRATION_IDS, tokens);
            sendRemoteMessage(body.toString(), callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void start () {
        client_name=null; req_content=null;
        JSONObject data=new JSONObject();
        jitsi_room= Util.RandomJitsiRoomName();
        try {
            data.put("room_jitsi",jitsi_room);
            data.put("last_write", "counsellor");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DatabaseReference ref_root=fb.getReference("volatile");
        ref_root.runTransaction(new Transaction.Handler() {
            @NonNull
            @NotNull
            @Override
            public Transaction.Result doTransaction(@NonNull @NotNull MutableData currentData) {
                long nrootchild=currentData.getChildrenCount();
                if (nrootchild == 0) { //first time so null (we have data_leg so child never be 0)
                    //try to write dummy value, server will reject
                    //and run this again
                    return Transaction.success(currentData);
                }
                else {
                    for (MutableData waiting_counsellor_data : currentData.child(Const.TBL_WAITING_CLIENTS).getChildren()) {
                        String tmp=waiting_counsellor_data.getValue(String.class);
                        try {
                            JSONObject obj=new JSONObject(tmp);
                            if (obj.getString("last_write")=="counsellor") continue;
                            jitsi_room=obj.getString("room_jitsi");
                            client_name=obj.getString("client_name");
                            req_content=obj.getString("req_content");
                            client_fcm_token=obj.getString("fcm_token");

                            //the other counsellor data
                            JSONObject response=new JSONObject(data,
                                    new String[]{"last_write"});
                            waiting_counsellor_data.setValue(response.toString());
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            continue;
                        }
                        return Transaction.success(currentData);
                    }

                    Log.d(TAG,"No waiting counsellors");
                    long handle=currentData.child("next_handle").getValue(Long.class);
                    currentData.child("next_handle").setValue(handle+1);
                    currentData.child(Const.TBL_WAITING_COUNSELLOR)
                            .child(String.valueOf(handle))
                            .setValue(data.toString());
                    listen_ref=ref_root
                            .child(Const.TBL_WAITING_COUNSELLOR)
                            .child(String.valueOf(handle));
                    listen_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            String tmp=snapshot.getValue(String.class);
                            if (tmp==null) return;
                            try {
                                JSONObject tmp_json=new JSONObject(tmp);
                                if (tmp_json.getString("last_write").equals("counsellor")) return;
                                client_name=tmp_json.getString("client_name");
                                req_content=tmp_json.getString("req_content");
                            } catch (JSONException e) {
                                return;
                                //e.printStackTrace();
                            }
                            ready_to_call=true;
                            view.update_client_info(client_name, req_content);
                            Log.d(TAG+"X","Jitsi token: "+jitsi_room);
                            Log.d(TAG,"client fcm-token: "+client_fcm_token);

                            listen_ref.removeValue();
                            listen_ref.removeEventListener(this);
                            listen_ref=null;
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            Log.w(TAG, "loadPost:onCancelled", error.toException());
                        }
                    });
                    return Transaction.success(currentData);
                }
            }

            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, boolean committed, @Nullable @org.jetbrains.annotations.Nullable DataSnapshot currentData) {
                if (!committed)
                    Log.e(TAG, "onComplete: "+error);
                else {
                    //Complete callback
                    if (client_name != null) {
                        ready_to_call=true;
                        view.update_client_info(client_name, req_content);
                        Log.d(TAG,"Jitsi token: "+jitsi_room);
                        Log.d(TAG,"client fcm-token: "+client_fcm_token);
                    }
                }
            }
        }, false);
    }

    public interface View {
        void update_client_info(String client_name, String req_content);
        void call_failure (String error_msg);
        void call_sucess();
    }
}
