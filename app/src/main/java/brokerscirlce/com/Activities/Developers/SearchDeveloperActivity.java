package brokerscirlce.com.Activities.Developers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.makeramen.roundedimageview.RoundedImageView;

import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.R;

public class SearchDeveloperActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mBackButton;
    private ImageView mChatBtn, mOnlineIcon;
    private RoundedImageView mProfileButton;
    private EditText mTitle, mLocation;
    private RelativeLayout mSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_developer_activity);

        initAppBar();

        initContents();
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

    private void initContents() {

        mTitle = findViewById(R.id.et_title);
        mLocation = findViewById(R.id.et_location);
        mSearchBtn = findViewById(R.id.btn_search);
        mSearchBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_chat:
                intent = new Intent(SearchDeveloperActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                //Todo Profile
                break;
            case R.id.btn_search:
                intent = new Intent(SearchDeveloperActivity.this, ViewAllDevelopersActivity.class);
                startActivity(intent);
                break;
        }
    }
}
