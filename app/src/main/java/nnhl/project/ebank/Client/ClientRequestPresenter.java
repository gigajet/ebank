package nnhl.project.ebank.Client;

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

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Util;

public class ClientRequestPresenter {
    View view;
    FirebaseDatabase fb;
    String TAG="CLIENT-REQUEST";
    String jitsi_room;
    DatabaseReference listen_ref;
    boolean really_complete;
    public ClientRequestPresenter(View view) {
        this.view = view;
        fb=FirebaseDatabase.getInstance(Const.DB_URL);
        jitsi_room=null;
        listen_ref=null;
    }

    void request (String client_name, String req_content) {
        really_complete=false;
        JSONObject data=new JSONObject();
        jitsi_room= Util.RandomJitsiRoomName();
        try {
            data.put("room_jitsi",jitsi_room);
            data.put("last_write", "client");
            data.put("client_name", client_name);
            data.put("req_content",req_content);
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
                    for (MutableData waiting_counsellor_data : currentData.child(Const.TBL_WAITING_COUNSELLOR).getChildren()) {
                        String tmp=waiting_counsellor_data.getValue(String.class);
                        try {
                            JSONObject obj=new JSONObject(tmp);
                            if (obj.getString("last_write")=="client") continue;
                            jitsi_room=obj.getString("room_jitsi");

                            //the other counsellor data
                            JSONObject response=new JSONObject(data,
                                    new String[]{"last_write", "client_name", "req_content"});
                            waiting_counsellor_data.setValue(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            continue;
                        }
                        really_complete=true;
                        return Transaction.success(currentData);
                    }

                    Log.d(TAG,"No waiting counsellors");
                    long handle=currentData.child("next_handle").getValue(Long.class);
                    currentData.child("next_handle").setValue(handle+1);
                    currentData.child(Const.TBL_WAITING_CLIENTS)
                            .child(String.valueOf(handle))
                            .setValue(data.toString());
                    listen_ref=ref_root
                            .child(Const.TBL_WAITING_CLIENTS)
                            .child(String.valueOf(handle));
                    listen_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            String tmp=snapshot.getValue(String.class);
                            if (tmp==null) return;
                            try {
                                JSONObject tmp_json=new JSONObject(tmp);
                                if (tmp_json.getString("last_write").equals("client")) return;
                                //Add code here if you wanna read things sent from counsellor
                            } catch (JSONException e) {
                                return;
                                //e.printStackTrace();
                            }
                            //Complete callback
                            view.fetch_callback(jitsi_room);

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
                    if (really_complete) {
                        //Complete callback
                        view.fetch_callback(jitsi_room);
                    }
                }
            }
        }, false);
    }

    public interface View {
        void fetch_callback (String videocall_token);
    };
}
