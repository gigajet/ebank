package nnhl.project.ebank.Counsellor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import nnhl.project.ebank.Counsellor.CreateNote.CounsellorCreateNoteActivity;
import nnhl.project.ebank.Counsellor.EditProfile.CounsellorEditProfileActivity;
import nnhl.project.ebank.Counsellor.Login.CounsellorLoginActivity;
import nnhl.project.ebank.Const;
import nnhl.project.ebank.Counsellor.VideoCall.OutgoingActivity;

import nnhl.project.ebank.Counsellor.VideoCall.VideoCallActivity;
import nnhl.project.ebank.Counsellor.ViewNote.CounsellorViewNoteActivity;
import nnhl.project.ebank.Global;
import nnhl.project.ebank.R;

public class CounsellorMainActivity extends AppCompatActivity implements CounsellorMainPresenter.View {

    private ImageButton btnLogout;
    CounsellorMainPresenter presenter;
    TextView tvRequestContent, tvClientName;
    String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counsellormainview);
        presenter=new CounsellorMainPresenter(this);
        Intent temp = getIntent();
        account = temp.getStringExtra("Account");
        initComponents();
    }

    private ImageButton btnCall, btnStart,btnEdit , btnTakeNote,btnViewNote;
    private void initComponents() {
        btnLogout=findViewById(R.id.logoutButton);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CounsellorMainActivity.this.finish();
            }
        });
        btnCall=findViewById(R.id.callButton);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!presenter.is_ready_to_call()) {
                    Toast.makeText(CounsellorMainActivity.this, "Client not available to call",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    //Call code
                    /*
                    String jitsi_room=presenter.get_videocall_token();
                    Intent intent=new Intent(CounsellorMainActivity.this, VideoCallActivity.class);
                    intent.putExtra("jitsi_room", presenter.get_videocall_token());
                    startActivity(intent);
                     */
                    presenter.call();
                }
            }
        });
        btnEdit = findViewById(R.id.editButton);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(CounsellorMainActivity.this, CounsellorEditProfileActivity.class);
                intent.putExtra("AccountName",account);
                startActivity(intent);

            }
        });
        tvRequestContent=findViewById(R.id.requestContentTextview);
        tvClientName=findViewById(R.id.clientNameTextview);
        btnStart=findViewById(R.id.startButton);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvClientName.setText("Getting info...");
                tvRequestContent.setText("Getting info...");
                btnStart.setEnabled(false);
                presenter.start();
            }
        });

        btnTakeNote = findViewById(R.id.takeNoteButton);
        btnTakeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounsellorMainActivity.this,CounsellorCreateNoteActivity.class);
                startActivity(intent);
            }
        });
        btnViewNote = findViewById(R.id.viewNoteButton);
        btnViewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CounsellorMainActivity.this, CounsellorViewNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666) { //comes back from call
            update_client_info("", "");
            presenter.reset_after_call();
            Intent intent=new Intent(this, CounsellorCreateNoteActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void update_client_info(String client_name, String req_content) {
        tvClientName.setText(client_name);
        tvRequestContent.setText(req_content);
        btnStart.setEnabled(true);
    }

    @Override
    public void call_failure(String error_msg) {
        Toast.makeText(this, error_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void call_sucess() {
        Toast.makeText(this, "FCM sent to client", Toast.LENGTH_SHORT).show();
        Global.getInstance().getData().put(Const.TAG_WAITING_FOR_CALL, Const.TAG_YES);
        Intent intent=new Intent(this, OutgoingActivity.class);
        intent.putExtra("jitsi_room", presenter.get_videocall_token());
        intent.putExtra("client_fcm", presenter.get_client_fcm_token());
        startActivityForResult(intent, 666);
    }
}