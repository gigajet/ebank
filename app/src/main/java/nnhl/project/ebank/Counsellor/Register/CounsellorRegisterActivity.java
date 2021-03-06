package nnhl.project.ebank.Counsellor.Register;
import nnhl.project.ebank.Counsellor.CounsellorModel;
import nnhl.project.ebank.Counsellor.Login.CounsellorLoginActivity;
import nnhl.project.ebank.Counsellor.Register.CounsellorRegisterPresenter;
import nnhl.project.ebank.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.FirebaseApp;

public class CounsellorRegisterActivity extends AppCompatActivity implements ICounsellorRegisterView {

    EditText edPhone, edEmail, edSID, edUserName, edPassword, edConfirmPassword;
    ImageButton btnRegister;
    ImageButton btnBack;
    ICounsellorRegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(getApplicationContext());
        presenter = new CounsellorRegisterPresenter(this);
        setContentView(R.layout.counsellorregisterview);

        initComponents();
    }

    private void initComponents() {
        edPhone=findViewById(R.id.telephoneEdittext);
        edUserName=findViewById(R.id.usernameRegisterEdittext);
        edPassword=findViewById(R.id.passwordRegisterEdittext);
        edConfirmPassword=findViewById(R.id.confirmPasswordRegisterEdittext);
        edSID=findViewById(R.id.securityIDEdittext);
        edEmail=findViewById(R.id.emailEdittext);
        btnRegister=findViewById(R.id.registerButton);
        btnBack  = findViewById(R.id.backRegisterButton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounsellorRegisterActivity.this, CounsellorLoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(v -> {
            String email=edEmail.getText().toString(),
                    phone=edPhone.getText().toString(),
                    SID=edSID.getText().toString(),
                    user_name=edUserName.getText().toString(),
                    password=edPassword.getText().toString(),
                    confirm_password=edConfirmPassword.getText().toString();
            if (password.equals(confirm_password)) {
                CounsellorModel counsellorModel = new CounsellorModel(email, user_name, password, phone);
                presenter.register(counsellorModel, SID);
            }
            else {
                Toast.makeText(this, "Confirm password not match", Toast.LENGTH_SHORT).show();
            }
        });
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