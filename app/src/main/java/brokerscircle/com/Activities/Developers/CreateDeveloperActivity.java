package brokerscircle.com.Activities.Developers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import brokerscircle.com.Activities.Chat.ChatActivity;
import brokerscircle.com.R;

public class CreateDeveloperActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;

    private LinearLayout mBackButton;
    private ImageView mChatBtn, mOnlineIcon;
    private RoundedImageView mProfileButton;

    private LinearLayout mContentOne, mContentTwo;

    private RelativeLayout mNextButton;
    private RelativeLayout mPrevButtonTwo, mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_developer_activity);


        mBackButton = findViewById(R.id.btn_back);
        mChatBtn = findViewById(R.id.btn_chat);
        mProfileButton = findViewById(R.id.btn_profile);
        mOnlineIcon = findViewById(R.id.user_online_icon);

        mContentOne = findViewById(R.id.content_step_one);
        mContentTwo = findViewById(R.id.content_step_two);

        mNextButton = findViewById(R.id.btn_next);
        mPrevButtonTwo = findViewById(R.id.btn_prev_two);
        mSubmitButton = findViewById(R.id.btn_submit);

        mNextButton.setOnClickListener(this);
        mPrevButtonTwo.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mChatBtn.setOnClickListener(this);
        mProfileButton.setOnClickListener(this);

        initMapsControls();

    }

    private void initMapsControls() {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_chat:
                intent = new Intent(CreateDeveloperActivity.this, ChatActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_profile:
                //Todo Profile
                break;
            case R.id.btn_next:
                mContentOne.setVisibility(View.GONE);
                mContentTwo.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_prev_two:
                mContentOne.setVisibility(View.VISIBLE);
                mContentTwo.setVisibility(View.GONE);
                break;
            case R.id.btn_submit:
                Toast.makeText(this, "Data saved Successfully", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
