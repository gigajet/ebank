package nnhl.project.ebank;

public class Const {
    //FirebaseDatabase.getInstance(DB_URL); <- do not leave null here because not us-central1
    public static final String DB_URL="https://snhl-bank-default-rtdb.asia-southeast1.firebasedatabase.app";
    public static final String TBL_counsellors="Counsellors";
    /*
    DB Scheme:
        Counsellors
        |
        |-- SID 1 (not registered)
        |
        |
        |-- SID 2
             |--- name
             |--- phone
             |--- email

     */
}
