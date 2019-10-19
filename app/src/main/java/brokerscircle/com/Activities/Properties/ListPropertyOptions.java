package brokerscircle.com.Activities.Properties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.makeramen.roundedimageview.RoundedImageView;

import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.R;

public class ListPropertyOptions extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mBackButton, mVoiceRecordingbtn, mAdvanceFormbtn;
    private ImageView mChatBtn, mOnlineIcon;
    private RoundedImageView mProfileButton;

    private ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_property_options_activity);

        scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);
        scaleAnimation.setDuration(50);
        BounceInterpolator bounceInterpolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterpolator);

        mBackButton = findViewById(R.id.btn_back);
        mChatBtn = findViewById(R.id.btn_chat);
        mProfileButton = findViewById(R.id.btn_profile);
        mOnlineIcon = findViewById(R.id.user_online_icon);
        mVoiceRecordingbtn = findViewById(R.id.btn_voice_recording);
        mAdvanceFormbtn = findViewById(R.id.btn_advance_form);

        mBackButton.setOnClickListener(this);
        mChatBtn.setOnClickListener(this);
        mProfileButton.setOnClickListener(this);
        mVoiceRecordingbtn.setOnClickListener(this);
        mAdvanceFormbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_chat:
                intent = new Intent(ListPropertyOptions.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                //Todo Profile
                break;
            case R.id.btn_voice_recording:
                mVoiceRecordingbtn.startAnimation(scaleAnimation);
                intent = new Intent(ListPropertyOptions.this, ListVoiceProperty.class);
                startActivity(intent);
                break;
            case R.id.btn_advance_form:
                mAdvanceFormbtn.startAnimation(scaleAnimation);
                intent = new Intent(ListPropertyOptions.this, ListPropertyAdvance.class);
                startActivity(intent);
                break;
        }
    }
}
