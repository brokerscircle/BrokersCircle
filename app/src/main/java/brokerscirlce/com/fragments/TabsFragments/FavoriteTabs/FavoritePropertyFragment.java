package brokerscirlce.com.fragments.TabsFragments.FavoriteTabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscirlce.com.adapters.PropertyListRecyclerview_config_One;
import brokerscirlce.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscirlce.com.model.Property.PropertyData;
import brokerscirlce.com.R;

public class FavoritePropertyFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerview;


    private String currentUserID = "";
    private String type = "Developer";

    public FavoritePropertyFragment() {
        // Required empty public constructor
    }


    public static FavoritePropertyFragment newInstance(String param1, String param2) {
        FavoritePropertyFragment fragment = new FavoritePropertyFragment();
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
        View view = inflater.inflate(R.layout.favorite_property_fragment, container, false);

        //Getting IDs
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mRecyclerview = view.findViewById(R.id.recyclerview);

        //Implement Recyclerview
//        List<ProjectData> favoritePropertyList = new ArrayList<>();
//        if (!currentUserID.equals("")) {
//            new FavoriteDatabaseHelper().readFavoriteWithReferenceID_AND_Type(new FavoriteDatabaseHelper.DataStatus() {
//                @Override
//                public void DataIsLoaded(List<FavoriteData> favoriteData) {
//
//                    new PropertyDatabaseHelper().readPropertyList(new PropertyDatabaseHelper.DataStatus() {
//                        @Override
//                        public void DataIsLoaded(List<ProjectData> propertyData) {
//                            favoritePropertyList.clear();
//                            for (int i = 0; i < favoriteData.size(); i++) {
//                                for (ProjectData data : propertyData) {
//                                    if (favoriteData.get(i).getReferenceId().equals(data.getId())) {
//                                        favoritePropertyList.add(data);
//                                    }
//                                }
//                            }
//                            new PropertyListRecyclerview_config_One().setConfig(mRecyclerview, getContext(), favoritePropertyList);
//                            // Stopping Shimmer Effect's animation after data is loaded to ListView
//                            mShimmerViewContainer.stopShimmer();
//                            mShimmerViewContainer.setVisibility(View.GONE);
//                        }
//                    }, getContext());
//
//                }
//            }, getContext(), currentUserID, type);
//        }


        new PropertyDatabaseHelper().readPropertyList(new PropertyDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyData> propertyData) {
                new PropertyListRecyclerview_config_One().setConfig(mRecyclerview, getContext(),propertyData);
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
