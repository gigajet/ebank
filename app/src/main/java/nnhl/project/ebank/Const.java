package nnhl.project.ebank;

import java.util.HashMap;

public class Const {
    //FirebaseDatabase.getInstance(DB_URL); <- do not leave null here because not us-central1
    //
    public static final String DB_URL="https://snhl-bank-default-rtdb.asia-southeast1.firebasedatabase.app";
    public static final String  TBL_counsellors="Counsellors";
    public static final String  TBL_ADS="Ads_image";
    public static final String TBL_SID="SID";
    public static final String TBL_WAITING_COUNSELLOR="waiting-counsellors";
    public static final String TBL_WAITING_CLIENTS="waiting-clients";
    /*
    DB Scheme:
     --SID
        |- sid1
        |- sid2
     --Counsellors
        |-- username1
        |-- username2
        |       |- name
        |       |- password
        |       |- phone
        |       |- email
     */

    public static final String REMOTE_MSG_TYPE = "type";
    public static final String REMOTE_MSG_CALL = "call";
    public static final String REMOTE_MSG_CALL_TOKEN = "jitsi_room";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";
    public static final HashMap<String, String> getRemoteHeaders() {
        HashMap<String, String> header = new HashMap<>();
        header.put(Const.REMOTE_MSG_AUTHORIZATION,
                "key=AAAA2CG8k_8:APA91bFDBd9kInDYSMAFNBI944Q-mhnfCaYOyyuZpRkZuwRhy22v2XY-m7n__Kk5mkCcbuN0Jz1yhFbAVZS-aqgH93yAu7UMLq5WUlWQkN3jZun3keM6KPOlMJvAcjapdBic3jGikID0");
        header.put(Const.REMOTE_MSG_CONTENT_TYPE,
                "application/json");
        return header;
    }

    public static final String REMOTE_MSG_ACCEPT_CALL = "accept";
    public static final String REMOTE_MSG_DENY_CALL = "deny";
    public static final String TAG_TOKEN_VIDEOCALL = REMOTE_MSG_CALL_TOKEN;
    public static final String TAG_COUNSELLOR_FCM_TOKEN = "counsellor_fcm";
    public static final String TAG_WAITING_FOR_CALL = "waiting_for_call";
    public static final String TAG_YES = "yes";
    public static final String TAG_NO = "no";
}