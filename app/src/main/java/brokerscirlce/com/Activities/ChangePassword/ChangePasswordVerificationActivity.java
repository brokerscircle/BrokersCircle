package brokerscirlce.com.Activities.ChangePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.R;

public class ChangePasswordVerificationActivity extends AppCompatActivity  implements View.OnClickListener  {

    private LinearLayout mBackButton;
    private ImageView mChatBtn, mOnlineIcon;
    private RoundedImageView mProfileButton;
    private TextView tv_resend_code;
    private RelativeLayout btn_verify;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_verification_activity);

        initAppBar();

        tv_resend_code = findViewById(R.id.tv_resend_code);
        btn_verify = findViewById(R.id.btn_verify);
    }

    private void initAppBar() {

        mBackButton = findViewById(R.id.btn_back);
        mChatBtn = findViewById(R.id.btn_chat);
        mProfileButton = findViewById(R.id.btn_profile);
        mOnlineIcon = findViewById(R.id.user_online_icon);

        mBackButton.setOnClickListener(this);
        mChatBtn.setOnClickListener(this);
        mProfileButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_back:
                intent = new Intent(ChangePasswordVerificationActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_chat:
                intent = new Intent(ChangePasswordVerificationActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                //Todo Profile
                break;
        }
    }
}
