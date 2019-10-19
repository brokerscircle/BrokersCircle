package brokerscircle.com.Activities.ChangePassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.makeramen.roundedimageview.RoundedImageView;

import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.Activities.SettingActivity;
import brokerscircle.com.R;

public class ChangePasswordActivity extends AppCompatActivity  implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private LinearLayout mBackButton;
    private ImageView mChatBtn, mOnlineIcon;
    private RoundedImageView mProfileButton;
    private EditText et_old_password, et_new_password, et_retypepassword;
    private ToggleButton toggle_old_password, toggle_new_password, toggle_retypepassword;
    private RelativeLayout btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);

        initAppBar();

        et_old_password = findViewById(R.id.et_old_password);
        toggle_old_password = findViewById(R.id.toggle_old_password);
        et_new_password = findViewById(R.id.et_new_password);
        toggle_new_password = findViewById(R.id.toggle_new_password);
        et_retypepassword = findViewById(R.id.et_retypepassword);
        toggle_retypepassword = findViewById(R.id.toggle_retypepassword);
        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(this);
        toggle_old_password.setOnCheckedChangeListener(this);
        toggle_new_password.setOnCheckedChangeListener(this);
        toggle_retypepassword.setOnCheckedChangeListener(this);
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
                intent = new Intent(ChangePasswordActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_chat:
                intent = new Intent(ChangePasswordActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                //Todo Profile
                break;
            case R.id.btn_next:
                intent = new Intent(ChangePasswordActivity.this, ChangePasswordVerificationActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.toggle_old_password:
                if (b){
                    et_old_password.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    et_old_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.toggle_new_password:
                if (b){
                    et_new_password.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    et_new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.toggle_retypepassword:
                if (b){
                    et_retypepassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }else {
                    et_retypepassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
        }
    }
}
