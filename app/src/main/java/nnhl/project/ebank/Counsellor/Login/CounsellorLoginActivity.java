package nnhl.project.ebank.Counsellor.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import nnhl.project.ebank.Counsellor.CounsellorMainActivity;
import nnhl.project.ebank.Counsellor.Register.CounsellorRegisterActivity;
import nnhl.project.ebank.R;
public class CounsellorLoginActivity extends AppCompatActivity implements CounsellorLoginPresenter.CounsellorLoginView{
    EditText accountEditText;
    EditText passwordEditText;
    ImageButton loginBtn, registernBtn, backBtn;
    CounsellorLoginPresenter counsellorLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counsellorloginview);
        counsellorLoginPresenter =  new CounsellorLoginPresenter(this);
        init();
    }

    void init()
    {
       accountEditText = findViewById(R.id.usernameLoginEdittext);
       passwordEditText = findViewById(R.id.passwordLoginEdittext);
       loginBtn = findViewById(R.id.loginButton);
       registernBtn = findViewById(R.id.registerInLoginButton);
       backBtn = findViewById(R.id.backButton);
       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String accountStr = String.valueOf(accountEditText.getText());
               String passwordStr = String.valueOf(passwordEditText.getText());
               //Toast.makeText(CounsellorLoginActivity.this,"Login clicked",Toast.LENGTH_SHORT).show();
               counsellorLoginPresenter.CounsellorLogin(accountStr,passwordStr);
           }
       });
       registernBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =  new Intent(CounsellorLoginActivity.this, CounsellorRegisterActivity.class);
               startActivity(intent);
           }
       });
       backBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

    @Override
    public void ActionLoginSuccess() {
        Toast.makeText(CounsellorLoginActivity.this,"Login successfully",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(CounsellorLoginActivity.this, CounsellorMainActivity.class);
        intent.putExtra("Account",accountEditText.getText().toString());
        startActivity(intent);
    }

    @Override
    public void ActionLoginFail() {
        Toast.makeText(CounsellorLoginActivity.this,"Login fail",Toast.LENGTH_LONG).show();
    }

    @Override
    public void ActionLoginWrongFormat(){
        Toast.makeText(CounsellorLoginActivity.this,"Wrong format account or password",Toast.LENGTH_LONG).show();
    }
}