package nnhl.project.ebank.Counsellor.Login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Counsellor.CounsellorModel;

public class CounsellorLoginPresenter {

    private CounsellorLoginView counsellorLoginView;
    FirebaseDatabase firebase;
    DatabaseReference data;

    CounsellorLoginPresenter(CounsellorLoginView v)
    {
        counsellorLoginView = v;
        firebase = FirebaseDatabase.getInstance(Const.DB_URL);
    }
    void CounsellorLogin(String account, String password)
    {

        Query query = firebase.getReference().child(Const.TBL_counsellors).child(account);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int x = 5;
                if(snapshot.getValue() != null) {
                    CounsellorModel p = snapshot.getValue(CounsellorModel.class);
                    assert p != null;
                    if (p.getPassword().equals(password)) {
                        Toast.makeText((Context) counsellorLoginView,password,Toast.LENGTH_SHORT).show();
                        counsellorLoginView.ActionLoginSuccess();

//                            Intent intent = new Intent(activity, HomeActivity.class);
//                            activity.startActivity(intent);

                    }
                }
                else{
                    counsellorLoginView.ActionLoginFail();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public interface CounsellorLoginView
    {
        public void ActionLoginSuccess();
        public void ActionLoginFail();
    }
}
