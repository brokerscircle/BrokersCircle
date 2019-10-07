package brokerscirlce.com.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.baoyz.widget.PullRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscirlce.com.adapters.UsersRecyclerview_Config;
import brokerscirlce.com.api_helpers.UsersDatabaseHelper;
import brokerscirlce.com.model.Users.UsersData;
import brokerscirlce.com.R;

public class UsersActivity extends AppCompatActivity implements PullRefreshLayout.OnRefreshListener {

    //For loading view
    private ShimmerFrameLayout mShimmerViewContainer;
    private EditText mEditTextSearch;
    private PullRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerview;
    private ImageView mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_activity);

        //Getting IDs
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mBackButton = findViewById(R.id.btn_back);
        mEditTextSearch = findViewById(R.id.et_search);
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerview = findViewById(R.id.recyclerview);

        //Implement Recyclerview items
        new UsersDatabaseHelper().readUserList(new UsersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UsersData> usersData) {
                new UsersRecyclerview_Config().setConfig(mRecyclerview, UsersActivity.this, usersData);

                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, UsersActivity.this);

        mRefreshLayout.setOnRefreshListener(UsersActivity.this);

        //back listener
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start Shimmer Effect's animation before data is loaded to ListView
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
    public void onRefresh() {
        new UsersDatabaseHelper().readUserList(new UsersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UsersData> usersData) {
                new UsersRecyclerview_Config().setConfig(mRecyclerview, UsersActivity.this, usersData);

                mRefreshLayout.setRefreshing(false);
            }
        }, UsersActivity.this);
    }
}
