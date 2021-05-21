package nnhl.project.ebank.Client;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

import nnhl.project.ebank.Const;
import nnhl.project.ebank.Counsellor.CounsellorModel;
import nnhl.project.ebank.Counsellor.Login.CounsellorLoginPresenter;

public class ClientMainPresenter {
    private ClientMainView clientMainView;
    FirebaseDatabase firebase;
    DatabaseReference data;

    ClientMainPresenter(ClientMainView v)
    {
        clientMainView = v;
        firebase = FirebaseDatabase.getInstance(Const.DB_URL);
    }
    void  uploadImage()
    {

        Query query = firebase.getReference().child(Const.TBL_ADS);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue() != null) {
                    Random r = new Random();
                    int i1 = r.nextInt(6-0+1)+0;
                    int i2 = r.nextInt(6-0+1)+0;
                    if(i2==i1 && i2 != 6) ++i2;
                    else if(i2 ==i1 && i2 !=0) --i2;
                    int i = 0;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        if(i == i1) {
                            String link = child.getValue(String.class);
                            clientMainView.updateImageView1(link);
                        }
                        else if(i==i2) {
                            String link = child.getValue(String.class);
                            clientMainView.updateImageView2(link);
                        }
                        i++;

                    }
                }
                else{
                    clientMainView.notifyFailtFetchData();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public interface ClientMainView
    {
        public void updateImageView1(String ImgLink);
        public void updateImageView2(String ImgLink);
        public void notifyFailtFetchData();
    }
}
