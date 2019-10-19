package brokerscircle.com.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscircle.com.adapters.MapTypesRecyclerview_Config;
import brokerscircle.com.api_helpers.Map.MapTypesDatabaseHelper;
import brokerscircle.com.model.Map.MapTypes.MapTypesData;
import brokerscircle.com.R;
import brokerscircle.com.model.login_user.LoginUser;
import brokerscircle.com.util.Helper;

public class MapActivity extends AppCompatActivity implements PullRefreshLayout.OnRefreshListener, View.OnClickListener {

    private Helper helper;
    private LoginUser user = null;

    //Topbar
    private LinearLayout mBackButton;
    private RoundedImageView userProfileIMG;
    private TextView mTitleTextView;
    private ImageView mTitleImage;

    private ShimmerFrameLayout mShimmerViewContainer;
    private PullRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(this);
        user = helper.getLoggedInUser();
        if (user == null){
            startActivity(new Intent(MapActivity.this, LoginActivity.class));
            finish();
        }else {
            setContentView(R.layout.map_activity);

            initAppBar();

            initContent();
        }


    }

    private void initAppBar() {

        //Geting IDs
        mBackButton = findViewById(R.id.btn_back);
        mBackButton = findViewById(R.id.btn_back);
        userProfileIMG = findViewById(R.id.profile_image);
        mTitleImage = findViewById(R.id.titleimage);
        mTitleTextView = findViewById(R.id.tv_title);

        //User Profile Image
        if ((user.getResponse().getData().getUser().getImg() == null) || (user.getResponse().getData().getUser().getImg().equals(""))){
            userProfileIMG.setImageResource(R.drawable.ic_user_icon_white);
        }else {
            Picasso.get().load(user.getResponse().getData().getUser().getImg()).centerCrop().fit().placeholder(R.drawable.ic_user_icon_white).into(userProfileIMG);
        }

        //back listener
        mBackButton.setOnClickListener(this);

    }

    private void initContent() {

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerview = findViewById(R.id.recyclerview);

        new MapTypesDatabaseHelper().readMapsList(new MapTypesDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<MapTypesData> mapsData) {
                new MapTypesRecyclerview_Config().setConfig(mRecyclerview, MapActivity.this, mapsData);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, MapActivity.this);

        mRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        new MapTypesDatabaseHelper().readMapsList(new MapTypesDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<MapTypesData> mapsData) {
                new MapTypesRecyclerview_Config().setConfig(mRecyclerview, MapActivity.this, mapsData);
                //Stop refreshing
                mRefreshLayout.setRefreshing(false);
            }
        }, MapActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start Shimmer Effect's animation before data is loaded to Recyclerview
        mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        // Stopping Shimmer Effect's animation after data is loaded to ListView
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                onBackPressed();
                break;
        }
    }
}
