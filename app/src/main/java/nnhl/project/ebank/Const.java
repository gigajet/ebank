package nnhl.project.ebank;

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
}