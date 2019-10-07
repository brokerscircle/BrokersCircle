package brokerscirlce.com.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscirlce.com.adapters.MapBankRecyclerview_Config;
import brokerscirlce.com.api_helpers.Map.MapBankDatabaseHelper;
import brokerscirlce.com.model.Map.MapBank.MapBankData;
import brokerscirlce.com.R;

public class MapDetailActivity extends AppCompatActivity implements PullRefreshLayout.OnRefreshListener {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerview;
    private PullRefreshLayout mRefreshLayout;
    private TextView mTitleTV;
    private LinearLayout mBackBtn;
    private String mapTypeID = "";
    private String mapTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_detail_activity);

        mapTypeID = getIntent().getStringExtra("maptypeid");
        mapTitle = getIntent().getStringExtra("maptitle");

        mBackBtn = findViewById(R.id.backbtn); // init a ImageView
        mTitleTV = findViewById(R.id.tv_title); // init a ImageView
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview = findViewById(R.id.recyclerview); // init a ImageView

        //Set Title
        mTitleTV.setText(mapTitle);

        new MapBankDatabaseHelper().readMapsListbyType(new MapBankDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<MapBankData> mapsData) {
                if (!mapsData.isEmpty()){
                    new MapBankRecyclerview_Config().setConfig(mRecyclerview, MapDetailActivity.this, mapsData);
                }
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, MapDetailActivity.this, mapTypeID);

        //On Refreshlistner
        mRefreshLayout.setOnRefreshListener(this);

        //On Back press
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onRefresh() {
        new MapBankDatabaseHelper().readMapsListbyType(new MapBankDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<MapBankData> mapsData) {
                if (!mapsData.isEmpty()){
                    new MapBankRecyclerview_Config().setConfig(mRecyclerview, MapDetailActivity.this, mapsData);
                }
                mRefreshLayout.setRefreshing(false);
            }
        }, MapDetailActivity.this, mapTypeID);
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
