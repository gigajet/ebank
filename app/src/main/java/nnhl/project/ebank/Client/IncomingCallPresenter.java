package nnhl.project.ebank.Client;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Global;
import nnhl.project.ebank.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomingCallPresenter {
    View view;
    public IncomingCallPresenter(View view) {
        this.view = view;
    }

    void accept_call (String videocall_token) {

        /*
            //FCM qua cho counsellor để counsellor join room
            //Client này join luôn vào room
         */
        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //do nothing.
                Log.d("FCM", "I client accept the call");
                view.accept_call_sucess();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.accept_call_failure_callback(t.getMessage());
            }
        };
        try {
            JSONArray tokens=new JSONArray();
            tokens.put(Global.getInstance().getData().get(Const.TAG_COUNSELLOR_FCM_TOKEN));
            JSONObject data=new JSONObject();
            JSONObject body=new JSONObject();
            data.put(Const.REMOTE_MSG_TYPE, Const.REMOTE_MSG_ACCEPT_CALL);
            data.put(Const.TAG_TOKEN_VIDEOCALL, videocall_token);
            body.put(Const.REMOTE_MSG_DATA, data);
            body.put(Const.REMOTE_MSG_REGISTRATION_IDS, tokens);
            Util.sendRemoteMessage(body.toString(), callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void deny_call(String videocall_token) {
        /*
            //FCM qua cho counsellor để counsellor biết "Tôi gác máy"
         */
        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //do nothing.
                Log.d("FCM", "I client deny the call");
                view.deny_call_sucess();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.deny_call_failure_callback(t.getMessage());
            }
        };
        try {
            JSONArray tokens=new JSONArray();
            tokens.put(Global.getInstance().getData().get(Const.TAG_COUNSELLOR_FCM_TOKEN));
            JSONObject data=new JSONObject();
            JSONObject body=new JSONObject();
            data.put(Const.REMOTE_MSG_TYPE, Const.REMOTE_MSG_DENY_CALL);
            data.put(Const.TAG_TOKEN_VIDEOCALL, videocall_token);
            body.put(Const.REMOTE_MSG_DATA, data);
            body.put(Const.REMOTE_MSG_REGISTRATION_IDS, tokens);
            Util.sendRemoteMessage(body.toString(), callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public interface View {
        void accept_call_failure_callback(String error);
        public void accept_call_sucess();

        void deny_call_sucess();
        void deny_call_failure_callback(String error);
    }
}
