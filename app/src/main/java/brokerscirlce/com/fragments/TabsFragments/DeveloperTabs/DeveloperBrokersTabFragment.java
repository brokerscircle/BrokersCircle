package brokerscirlce.com.fragments.TabsFragments.DeveloperTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.adapters.BrokersRecyclerview_Config;
import brokerscirlce.com.api_helpers.BrokersDatabaseHelper;
import brokerscirlce.com.api_helpers.DeveloperBrokerDatabaseHepler;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.model.Developers.DeveloperBrokers.DeveloperBrokersData;
import brokerscirlce.com.R;

public class DeveloperBrokersTabFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerview;

    public DeveloperBrokersTabFragment() {
        // Required empty public constructor
    }

    public static DeveloperBrokersTabFragment newInstance(String param1, String param2) {
        DeveloperBrokersTabFragment fragment = new DeveloperBrokersTabFragment();
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
        View view = inflater.inflate(R.layout.project_brokers_tab_fragment, container, false);

        //Getting id from Estate Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String developerID = this.getArguments().getString("developerID");

        /*
         *  Shimmerview for Loading when data is fetchting from Server
         * */
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mRecyclerview = view.findViewById(R.id.recyclerview);
        List<BrokersData> brokersDataList = new ArrayList<>();
        new DeveloperBrokerDatabaseHepler().readBrokerList(new DeveloperBrokerDatabaseHepler.DataStatus() {
            @Override
            public void DataIsLoaded(List<DeveloperBrokersData> developerBrokersData) {
                if (developerBrokersData.size() > 0){
                    brokersDataList.clear();

                    new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<BrokersData> brokersUtils) {
                            //fetching developer broker data from list
                            for ( DeveloperBrokersData data : developerBrokersData){
                                //fetching broker all list
                                for ( BrokersData broker : brokersUtils){
                                    //checking if developer id and broker id is equal then add item to list
                                    if (data.getBrokerId().equals(broker.getId())){
                                        brokersDataList.add(broker);
                                        //chekc if developer data size == broker list the show recyclerview
                                        if (developerBrokersData.size() == brokersDataList.size()){
                                            new BrokersRecyclerview_Config().setConfig(mRecyclerview,getContext(),brokersDataList, null);
                                            // Stopping Shimmer Effect's animation after data is loaded to ListView
                                            mShimmerViewContainer.stopShimmer();
                                            mShimmerViewContainer.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        }
                    }, getContext());
                }else {
                    //Do something here
                    // Stopping Shimmer Effect's animation after data is loaded to ListView
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "No Broker Found", Toast.LENGTH_SHORT).show();
                }
            }
        },getContext(), developerID);

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
