package brokerscircle.com.Activities.Properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscircle.com.adapters.PropertyDashboardTwoListRecyclerview_config;
import brokerscircle.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscircle.com.model.Property.PropertyData;
import brokerscircle.com.R;

public class ViewAllPropertiesActivity extends AppCompatActivity implements PullRefreshLayout.OnRefreshListener {

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
        setContentView(R.layout.view_all_properties_activity);

        //Getting Ids
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mBackButton = findViewById(R.id.btn_back);
        mFilter = findViewById(R.id.img_filter);
        mResultTV = findViewById(R.id.tv_results);
        mFilterSpinner = findViewById(R.id.filter_spinner);
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerview = findViewById(R.id.recyclerview);

        //Recyclerview pull to refresh
        mRefreshLayout.setOnRefreshListener(this);

        //Putting filter adapter to spinner
        mFilterSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories));
        selectedFilter = mFilterSpinner.getSelectedItem().toString();

        //Implement Filter
        mFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedFilter = adapterView.getItemAtPosition(position).toString();
                //Implement Recyclerview
                new PropertyDatabaseHelper().readPropertyList(new PropertyDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<PropertyData> propertyData) {
                        mResultTV.setText(propertyData.size()+" Results");
                        if (!propertyData.isEmpty()){
                            new PropertyDashboardTwoListRecyclerview_config().setConfig(mRecyclerview,ViewAllPropertiesActivity.this,propertyData);
                        }
                        // Stopping Shimmer Effect's animation after data is loaded to ListView
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                    }
                }, ViewAllPropertiesActivity.this);
                //selectedFilter = mFilterSpinner.getSelectedItem().toString();
                Log.d("Properties", "onCreate: "+ selectedFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        //Implement Recyclerview
        new PropertyDatabaseHelper().readPropertyList(new PropertyDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyData> propertyData) {
                mResultTV.setText(propertyData.size()+" Results");
                if (!propertyData.isEmpty()){
                    new PropertyDashboardTwoListRecyclerview_config().setConfig(mRecyclerview,ViewAllPropertiesActivity.this,propertyData);
                }
                mRefreshLayout.setRefreshing(false);
            }
        }, ViewAllPropertiesActivity.this);
    }
}
