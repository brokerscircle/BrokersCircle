package brokerscircle.com.fragments.TabsFragments.EstateTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import brokerscircle.com.adapters.BrokersRecyclerview_Config;
import brokerscircle.com.api_helpers.BrokersDatabaseHelper;
import brokerscircle.com.model.Brokers.BrokersData;
import brokerscircle.com.R;
import pl.droidsonroids.gif.GifImageView;

public class EstateBrokersTabFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerview;

    GifImageView notFoundGIF;

    public EstateBrokersTabFragment() {
        // Required empty public constructor
    }


    public static EstateBrokersTabFragment newInstance(String param1, String param2) {
        EstateBrokersTabFragment fragment = new EstateBrokersTabFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.estate_brokers_tab_fragment, container, false);

        //Getting id from Estate Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String estateID = this.getArguments().getString("estateID");

        /*
        *  Shimmerview for Loading when data is fetchting from Server
        * */
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        notFoundGIF = view.findViewById(R.id.no_found_gif);
        mRecyclerview = view.findViewById(R.id.recyclerview);

        new BrokersDatabaseHelper().readBrokersByEstate(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {
                if (brokersUtils.isEmpty()){
                    notFoundGIF.setVisibility(View.VISIBLE);
                }
                new BrokersRecyclerview_Config().setConfig(mRecyclerview, getContext(), brokersUtils, null);
                Log.d("EstateBroker", "DataIsLoaded: "+brokersUtils);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, getContext(), estateID);

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
