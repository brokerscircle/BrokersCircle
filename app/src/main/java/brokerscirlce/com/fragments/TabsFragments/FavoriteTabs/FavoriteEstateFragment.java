package brokerscirlce.com.fragments.TabsFragments.FavoriteTabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.adapters.RealEstateRecyclerview_Config;
import brokerscirlce.com.api_helpers.RealEstateDatabaseHelper;
import brokerscirlce.com.model.RealEstates.RealEstateData;
import brokerscirlce.com.R;

public class FavoriteEstateFragment extends Fragment {

    //For loading view
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerview;

    private String currentUserID = "";
    private String type = "Estate";

    public FavoriteEstateFragment() {
        // Required empty public constructor
    }


    public static FavoriteEstateFragment newInstance(String param1, String param2) {
        FavoriteEstateFragment fragment = new FavoriteEstateFragment();
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
        View view = inflater.inflate(R.layout.favorite_estate_fragment, container, false);

        //Getting Ids
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mRecyclerview = view.findViewById(R.id.recyclerview);

        //Implement Recyclerview
        List<RealEstateData> favoriteEstateList = new ArrayList<>();
//        if (!currentUserID.equals("")) {
//            new FavoriteDatabaseHelper().readFavoriteWithReferenceID_AND_Type(new FavoriteDatabaseHelper.DataStatus() {
//                @Override
//                public void DataIsLoaded(List<FavoriteData> favoriteData) {
//
//                    new RealEstateDatabaseHelper().readEstateList(new RealEstateDatabaseHelper.DataStatus() {
//                        @Override
//                        public void DataIsLoaded(List<RealEstateData> realEstateData) {
//                            favoriteEstateList.clear();
//                            for (int i = 0; i < favoriteData.size(); i++) {
//                                for (RealEstateData data : realEstateData) {
//                                    if (favoriteData.get(i).getReferenceId().equals(data.getId())) {
//                                        favoriteEstateList.add(data);
//                                    }
//                                }
//                            }
//                            new RealEstateRecyclerview_Config().setConfig(mRecyclerview, getContext(),favoriteEstateList, null);
//                            // Stopping Shimmer Effect's animation after data is loaded to ListView
//                            mShimmerViewContainer.stopShimmer();
//                            mShimmerViewContainer.setVisibility(View.GONE);
//                        }
//                    },getContext());
//                }
//            }, getContext(), currentUserID, type);
//        }


        new RealEstateDatabaseHelper().readEstateList(new RealEstateDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<RealEstateData> realEstateData) {
                new RealEstateRecyclerview_Config().setConfig(mRecyclerview, getContext(),realEstateData, null);

                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        },getContext());

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Start Shimmer Effect's animation before data is loaded to ListView
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
