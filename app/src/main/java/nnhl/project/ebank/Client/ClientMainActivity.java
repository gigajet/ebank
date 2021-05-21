package nnhl.project.ebank.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import nnhl.project.ebank.R;

public class ClientMainActivity extends AppCompatActivity implements ClientMainPresenter.ClientMainView{
    private ImageButton btnRequest, btnBack;
    private ImageView imgView1;
    private ImageView imgView2;
    private ClientMainPresenter clientMainPresenter;
    private TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientmainview_1);
        initComponents();

        textView1.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        clientMainPresenter.uploadImage();
        //imgView.setImageResource(R.drawable.picture1);

    }

    private void initComponents() {
        btnRequest=findViewById(R.id.requestButton);
        btnBack=findViewById(R.id.backButton);
        imgView1 = findViewById(R.id.imageview_main_0);
        imgView2 = findViewById(R.id.imageview_main_1);
        textView1 = findViewById(R.id.TextOfImgView1);
        textView2 = findViewById(R.id.TextOfImgView2);
        textView1.setVisibility(View.VISIBLE);
        textView2.setVisibility(View.VISIBLE);
        clientMainPresenter =  new ClientMainPresenter(this);
    }

    public void onclick_back(View view) {
        finish();
    }

    public void onclick_request(View view) {
        Intent intent=new Intent(this,ClientRequestActivity.class);
        startActivity(intent);

    }

    @Override
    public void updateImageView1(String link) {

        Picasso.get().load(link).into(imgView1);
        textView1.setVisibility(View.INVISIBLE);

    }
    @Override
    public void updateImageView2(String link) {


        Picasso.get().load(link).into(imgView2);

        textView2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void notifyFailtFetchData() {
        Toast.makeText(ClientMainActivity.this," fetch images from database failed",Toast.LENGTH_LONG).show();

    }
}