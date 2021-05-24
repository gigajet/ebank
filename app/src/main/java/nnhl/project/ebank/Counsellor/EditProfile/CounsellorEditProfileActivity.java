package nnhl.project.ebank.Counsellor.EditProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import nnhl.project.ebank.R;
public class CounsellorEditProfileActivity extends AppCompatActivity implements CounsellorEditProfilePresenter.CounsellorEditProfileView {
    CounsellorEditProfilePresenter counsellorEditProfilePresenter;
    EditText  usernameProfile,phoneEditText, mailEditText, passEditText;
    ImageButton UpdateBtn,BackBtn;
    String accountName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counselloreditprofileview);
        init();
    }
    void init()
    {
        Intent intent = getIntent();
        accountName = intent.getStringExtra("AccountName");

        counsellorEditProfilePresenter =  new CounsellorEditProfilePresenter(CounsellorEditProfileActivity.this);
        phoneEditText = findViewById(R.id.telephoneEditProfile);
        mailEditText = findViewById(R.id.emailEditProfile);
        passEditText = findViewById(R.id.passwordEditProfile);
        usernameProfile = findViewById(R.id.usernameEditProfile);

        UpdateBtn = findViewById(R.id.requestEditProfile);
        BackBtn = findViewById(R.id.backEditProfileButton);

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counsellorEditProfilePresenter.EditProfile(accountName,usernameProfile.getText().toString(),passEditText.getText().toString(),phoneEditText.getText().toString(),mailEditText.getText().toString());
            }
        });
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void updateUserName() {

    }

    @Override
    public void updatePassWord() {

    }

    @Override
    public void updateTelephone() {

    }

    @Override
    public void updateEmail() {

    }

    @Override
    public void ActionNoDataFilled() {
        Toast.makeText(CounsellorEditProfileActivity.this,"No information filled",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ActionNoAccountName() {
        Toast.makeText(CounsellorEditProfileActivity.this,"No account name",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ActionUpdateSuccessful() {
        Toast.makeText(CounsellorEditProfileActivity.this,"The information is successfully updated",Toast.LENGTH_SHORT).show();
        phoneEditText.setText("");
        mailEditText.setText("");
        passEditText.setText("");
        usernameProfile.setText("");


    }

    @Override
    public void ActionUpdateFail() {
        Toast.makeText(CounsellorEditProfileActivity.this,"Update information fail",Toast.LENGTH_SHORT).show();
        phoneEditText.setText("");
        mailEditText.setText("");
        phoneEditText.setText("");
        usernameProfile.setText("");

    }
}