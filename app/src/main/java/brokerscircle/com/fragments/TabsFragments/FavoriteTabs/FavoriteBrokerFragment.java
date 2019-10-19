package brokerscircle.com.fragments.TabsFragments.FavoriteTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscircle.com.adapters.BrokersRecyclerview_Config;
import brokerscircle.com.api_helpers.BrokersDatabaseHelper;
import brokerscircle.com.model.Brokers.BrokersData;
import brokerscircle.com.R;

public class FavoriteBrokerFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerview;

    private String currentUserID = "";
    private String type = "Broker";

    public FavoriteBrokerFragment() {
        // Required empty public constructor
    }

    public static FavoriteBrokerFragment newInstance(String param1, String param2) {
        FavoriteBrokerFragment fragment = new FavoriteBrokerFragment();
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
        View view = inflater.inflate(R.layout.favorite_broker_fragment, container, false);

        //Getting IDs
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mRecyclerview = view.findViewById(R.id.recyclerview);

        //Implement Recyclerview
//        List<BrokersData> favoriteBrokersList = new ArrayList<>();
//        if (!currentUserID.equals("")){
//            new FavoriteDatabaseHelper().readFavoriteWithReferenceID_AND_Type(new FavoriteDatabaseHelper.DataStatus() {
//                @Override
//                public void DataIsLoaded(List<FavoriteData> favoriteData) {
//
//                    new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
//                        @Override
//                        public void DataIsLoaded(List<BrokersData> brokersUtils) {
//                            favoriteBrokersList.clear();
//                            for (int i=0; i<favoriteData.size(); i++){
//                                for ( BrokersData data : brokersUtils ) {
//                                    if (favoriteData.get(i).getReferenceId().equals(data.getId())){
//                                        favoriteBrokersList.add(data);
//                                    }
//                                }
//                            }
//                            new BrokersRecyclerview_Config().setConfig(mRecyclerview, getContext(),favoriteBrokersList, null );
//                            // Stopping Shimmer Effect's animation after data is loaded to ListView
//                            mShimmerViewContainer.stopShimmer();
//                            mShimmerViewContainer.setVisibility(View.GONE);
//                        }
//                    }, getContext());
//                }
//            }, getContext(), currentUserID, type);
//        }

        new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {

                new BrokersRecyclerview_Config().setConfig(mRecyclerview, getContext(),brokersUtils, null );
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, getContext());

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
