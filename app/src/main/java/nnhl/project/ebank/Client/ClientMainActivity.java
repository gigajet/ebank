package nnhl.project.ebank.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import nnhl.project.ebank.R;

public class ClientMainActivity extends AppCompatActivity {
    private ImageButton btnRequest, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientmainview_1);
        initComponents();
    }

    private void initComponents() {
        btnRequest=findViewById(R.id.requestButton);
        btnBack=findViewById(R.id.backButton);
    }

    public void onclick_back(View view) {
        finish();
    }

    public void onclick_request(View view) {
        Intent intent=new Intent(this,ClientRequestActivity.class);
        startActivity(intent);
    }
}