package brokerscircle.com.fragments.TabsFragments.EstateTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscircle.com.adapters.PastDealingRecyclerview_Config;
import brokerscircle.com.api_helpers.PastDealingDatabaseHelper;
import brokerscircle.com.model.PastDealing.PastDealingData;
import brokerscircle.com.R;

public class EstatePastDealingTabFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerView;

    public EstatePastDealingTabFragment() {
        // Required empty public constructor
    }


    public static EstatePastDealingTabFragment newInstance() {
        EstatePastDealingTabFragment fragment = new EstatePastDealingTabFragment();
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
        View view = inflater.inflate(R.layout.estate_past_dealing_tab_fragment, container, false);
        //Getting id from Estate Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String estateidID = this.getArguments().getString("estateid");

        /*
         *  Shimmerview for Loading when data is fetchting from Server
         * */
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        mRecyclerView = view.findViewById(R.id.recyclerview);
        new PastDealingDatabaseHelper().readEstatePastDealingList(new PastDealingDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PastDealingData> pastDealingData) {
//                if (pastDealingData.isEmpty()){
//                    notFoundTV.setVisibility(View.VISIBLE);
//                }
                new PastDealingRecyclerview_Config().setConfig(mRecyclerView, getContext(), pastDealingData);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        },getContext(), estateidID);

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
