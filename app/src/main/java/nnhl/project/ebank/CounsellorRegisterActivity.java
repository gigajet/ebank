package nnhl.project.ebank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;

public class CounsellorRegisterActivity extends AppCompatActivity implements ICounsellorRegisterView {

    EditText edPhone, edName, edEmail, edSID;
    ICounsellorRegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(getApplicationContext());
        presenter = new CounsellorRegisterPresenter(this);
        setContentView(R.layout.activity_register);
        edPhone=findViewById(R.id.edPhone);
        edName=findViewById(R.id.edName);
        edSID=findViewById(R.id.edSID);
        edEmail=findViewById(R.id.edEmail);
    }

    public void onclick_register(View view) {
        String name=edName.getText().toString(),
                email=edEmail.getText().toString(),
                phone=edPhone.getText().toString(),
                SID=edSID.getText().toString();
        presenter.register(name,email,phone,SID);
    }

    @Override
    public void register_sucessful() {
        Toast.makeText(this, "Register sucessful", Toast.LENGTH_SHORT).show();
        finish(); //back to login
    }

    @Override
    public void register_failed(String error_msg) {
        Toast.makeText(this, "Error: "+error_msg, Toast.LENGTH_SHORT).show();
    }
}