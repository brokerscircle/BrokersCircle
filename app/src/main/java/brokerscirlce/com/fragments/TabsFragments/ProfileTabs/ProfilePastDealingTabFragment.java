package brokerscirlce.com.fragments.TabsFragments.ProfileTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscirlce.com.adapters.PastDealingRecyclerview_Config;
import brokerscirlce.com.api_helpers.PastDealingDatabaseHelper;
import brokerscirlce.com.model.PastDealing.PastDealingData;
import brokerscirlce.com.R;

public class ProfilePastDealingTabFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerview;
    TextView notFoundTV;

    public ProfilePastDealingTabFragment() {
        // Required empty public constructor
    }

    public static ProfilePastDealingTabFragment newInstance(String param1, String param2) {
        ProfilePastDealingTabFragment fragment = new ProfilePastDealingTabFragment();
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
        View view = inflater.inflate(R.layout.profile_past_dealing_tab_fragment, container, false);
        //Getting id from Profile Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String brokerID = this.getArguments().getString("brokerid");

        /*
         *  Shimmerview for Loading when data is fetchting from Server
         * */
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        //Getting id
        notFoundTV = view.findViewById(R.id.no_found_tv);
        mRecyclerview = view.findViewById(R.id.recyclerview);
        new PastDealingDatabaseHelper().readBrokerPastDealingList(new PastDealingDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PastDealingData> pastDealingData) {
                if (pastDealingData.isEmpty()){
                    notFoundTV.setVisibility(View.VISIBLE);
                }
                new PastDealingRecyclerview_Config().setConfig(mRecyclerview, getContext(), pastDealingData);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        },getContext(), brokerID);

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
