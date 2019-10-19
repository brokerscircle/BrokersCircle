package brokerscircle.com.fragments.TabsFragments.ProfileTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscircle.com.adapters.PropertyListRecyclerview_config_One;
import brokerscircle.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscircle.com.model.Property.PropertyData;
import brokerscircle.com.R;

public class ProfileListingTab extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerView;

    public ProfileListingTab() {
        // Required empty public constructor
    }

    public static ProfileListingTab newInstance(String param1, String param2) {
        ProfileListingTab fragment = new ProfileListingTab();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_listing_tab_fragment, container, false);
        //Getting id from Profile Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String brokerID = this.getArguments().getString("brokerid");
        //getting id
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mRecyclerView = view.findViewById(R.id.recyclerview);

        new PropertyDatabaseHelper().readPropertyByUser(new PropertyDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyData> propertyData) {
                new PropertyListRecyclerview_config_One().setConfig(mRecyclerView, getContext(), propertyData);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, getContext(), brokerID);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Start Shimmer Effect's animation before data is loaded to Recyclerview
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        // Stopping Shimmer Effect's animation after data is loaded to ListView
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        super.onPause();
    }
}
