package nnhl.project.ebank.Counsellor.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nnhl.project.ebank.Counsellor.Register.CounsellorRegisterActivity;
import nnhl.project.ebank.R;
public class CounsellorLoginActivity extends AppCompatActivity implements CounsellorLoginPresenter.CounsellorLoginView{
    EditText accountEditText;
    EditText passwordEditText;
    Button loginBtn;
    Button registernBtn;
    CounsellorLoginPresenter counsellorLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_login);
        counsellorLoginPresenter =  new CounsellorLoginPresenter(this);
        init();
    }

    void init()
    {
       accountEditText = findViewById(R.id.Account);
       passwordEditText = findViewById(R.id.Password);
       loginBtn = findViewById(R.id.LoginBtn);
       registernBtn = findViewById(R.id.RegisterBtn);

       loginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String accountStr = String.valueOf(accountEditText.getText());
               String passwordStr = String.valueOf(passwordEditText.getText());
               Toast.makeText(CounsellorLoginActivity.this,"Login clicked",Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void ActionLoginSuccess() {
        //Intent to other activity
        Toast.makeText(CounsellorLoginActivity.this,"Login successfully",Toast.LENGTH_LONG).show();
    }

    @Override
    public void ActionLoginFail() {
        Toast.makeText(CounsellorLoginActivity.this,"Login fail",Toast.LENGTH_LONG).show();
    }
}