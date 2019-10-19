package brokerscircle.com.Activities.Projects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscircle.com.adapters.ProjectDetailTabsRecyclerview_Config;
import brokerscircle.com.api_helpers.ProjectDatabaseHelper;
import brokerscircle.com.model.Project.ProjectData;
import brokerscircle.com.R;

public class ViewAllProjectsActivity extends AppCompatActivity implements PullRefreshLayout.OnRefreshListener {

    private ShimmerFrameLayout mShimmerViewContainer;
    private ImageView mFilter;
    private PullRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerview;
    private LinearLayout mBackButton;
    private Spinner mFilterSpinner;
    private TextView mResultTV;
    String selectedFilter = "";
    String[] categories={"All","Buy","Sell","Rent"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_projects_activity);

        //Getting Ids
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mBackButton = findViewById(R.id.btn_back);
        mFilter = findViewById(R.id.img_filter);
        mResultTV = findViewById(R.id.tv_results);
        mFilterSpinner = findViewById(R.id.filter_spinner);
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerview = findViewById(R.id.recyclerview);

        new ProjectDatabaseHelper().readProjectList(new ProjectDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProjectData> projectData) {
                new ProjectDetailTabsRecyclerview_Config().setConfig(mRecyclerview, ViewAllProjectsActivity.this,projectData);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, ViewAllProjectsActivity.this);

        mRefreshLayout.setOnRefreshListener(this);

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
    public void onRefresh() {
        new ProjectDatabaseHelper().readProjectList(new ProjectDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProjectData> projectData) {
                new ProjectDetailTabsRecyclerview_Config().setConfig(mRecyclerview, ViewAllProjectsActivity.this,projectData);
                mRefreshLayout.setRefreshing(false);
            }
        }, ViewAllProjectsActivity.this);
    }
}
