package brokerscirlce.com.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.adapters.BrokersRecyclerview_Config;
import brokerscirlce.com.api_helpers.BrokersDatabaseHelper;
import brokerscirlce.com.api_helpers.FollowersDatabaseHelper;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.model.Followers.FollowPostResponseUtils;
import brokerscirlce.com.model.Followers.FollowersData;
import brokerscirlce.com.R;

public class ProfileFollowersActivity extends AppCompatActivity implements PullRefreshLayout.OnRefreshListener {

    private ShimmerFrameLayout mShimmerViewContainer;
    private PullRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerview;
    private LinearLayout mBackButton;

    String userId = "";
    String followType = "UserToBroker";
    List<BrokersData> brokersDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_followers_activity);

        Intent intent = getIntent();
        if (intent != null){
            userId = intent.getStringExtra("brokerid");
        }

        //Getting IDs
        mBackButton = findViewById(R.id.btn_back);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerview = findViewById(R.id.recyclerview);
        mRefreshLayout.setOnRefreshListener(this);

        if (!userId.equals("")){
            new FollowersDatabaseHelper().readFollowedToWithFollowedID_AND_Type(new FollowersDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<FollowersData> usersData) {
                    Log.d("ProfileFollow", "FollowersData: "+usersData.size());
                    //getAll Brokers List
                    new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<BrokersData> brokersUtils) {
                            brokersDataList.clear();
                            for (int i=0; i<usersData.size(); i++){
                                Log.d("ProfileFollow", "to id: "+usersData.get(i).getFollowedToId());
                                Log.d("ProfileFollow", "From id: "+usersData.get(i).getFollowedFromId());
                                //Collecting all followers of this user
                                for (BrokersData data : brokersUtils) {
                                    //Adding item to list
                                    if (usersData.get(i).getFollowedFromId().equals(data.getId())){
                                        Log.d("ProfileFollow", "brokersUtils: "+data.getTitle());
                                        brokersDataList.add(data);
                                    }
                                }
                            }

                            if (brokersDataList.size() == usersData.size()){
                                new BrokersRecyclerview_Config().setConfig(mRecyclerview, ProfileFollowersActivity.this, brokersDataList, null);
                            }
                            // Stopping Shimmer Effect's animation after data is loaded to ListView
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                    }, ProfileFollowersActivity.this);
                }

                @Override
                public void DataIsPosted(List<FollowPostResponseUtils> responseUtils) {

                }

                @Override
                public void DataIsDeleted(List<FollowPostResponseUtils> usersData) {

                }
            }, ProfileFollowersActivity.this, userId, followType);
        }


        //back listener
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onRefresh() {
        if (!userId.equals("")){
            new FollowersDatabaseHelper().readFollowedToWithFollowedID_AND_Type(new FollowersDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<FollowersData> usersData) {
                    //getAll Brokers List
                    new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<BrokersData> brokersUtils) {
                            brokersDataList.clear();
                            for (int i=0; i<usersData.size(); i++){
                                //Collecting all followers of this user
                                for (BrokersData data : brokersUtils) {
                                    //Adding item to list
                                    if (usersData.get(i).getFollowedFromId().equals(data.getId())){
                                        brokersDataList.add(data);
                                    }
                                }
                            }

                            if (brokersDataList.size() == usersData.size()){
                                new BrokersRecyclerview_Config().setConfig(mRecyclerview, ProfileFollowersActivity.this, brokersDataList, null);
                                // Stopping Refresh
                                mRefreshLayout.setRefreshing(false);
                            }
                        }
                    }, ProfileFollowersActivity.this);
                }

                @Override
                public void DataIsPosted(List<FollowPostResponseUtils> responseUtils) {

                }

                @Override
                public void DataIsDeleted(List<FollowPostResponseUtils> usersData) {

                }
            }, ProfileFollowersActivity.this, userId, followType);
        }
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
}
