package brokerscirlce.com.fragments.TabsFragments.FavoriteTabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscirlce.com.adapters.DevelopersRecyclerview_Config;
import brokerscirlce.com.api_helpers.DevelopersDatabaseHelper;
import brokerscirlce.com.model.Developers.DevelopersData;
import brokerscirlce.com.R;

public class FavoriteDeveloperFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerview;

    private String currentUserID = "";
    private String type = "Developer";

    public FavoriteDeveloperFragment() {
        // Required empty public constructor
    }


    public static FavoriteDeveloperFragment newInstance(String param1, String param2) {
        FavoriteDeveloperFragment fragment = new FavoriteDeveloperFragment();
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
        View view = inflater.inflate(R.layout.favorite_developer_fragment, container, false);

        //Getting Ids
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mRecyclerview = view.findViewById(R.id.recyclerview);

        //Implement Recyclerview

//        List<DevelopersData> favoriteDeveloperssList = new ArrayList<>();
//        if (!currentUserID.equals("")) {
//            new FavoriteDatabaseHelper().readFavoriteWithReferenceID_AND_Type(new FavoriteDatabaseHelper.DataStatus() {
//                @Override
//                public void DataIsLoaded(List<FavoriteData> favoriteData) {
//
//                    new DevelopersDatabaseHelper().readDeveloperssList(new DevelopersDatabaseHelper.DataStatus() {
//                        @Override
//                        public void DataIsLoaded(List<DevelopersData> developersData) {
//                            favoriteDeveloperssList.clear();
//                            for (int i = 0; i < favoriteData.size(); i++) {
//                                for (DevelopersData data : developersData) {
//                                    if (favoriteData.get(i).getReferenceId().equals(data.getId())) {
//                                        favoriteDeveloperssList.add(data);
//                                    }
//                                }
//                            }
//                            new DevelopersRecyclerview_Config().setConfig(mRecyclerview, getContext(), favoriteDeveloperssList, null);
//                            // Stopping Shimmer Effect's animation after data is loaded to ListView
//                            mShimmerViewContainer.stopShimmer();
//                            mShimmerViewContainer.setVisibility(View.GONE);
//                        }
//                    }, getContext());
//
//                }
//            }, getContext(), currentUserID, type);
//        }

        new DevelopersDatabaseHelper().readDeveloperssList(new DevelopersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<DevelopersData> developersData) {
                new DevelopersRecyclerview_Config().setConfig(mRecyclerview, getContext(), developersData, null);
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
