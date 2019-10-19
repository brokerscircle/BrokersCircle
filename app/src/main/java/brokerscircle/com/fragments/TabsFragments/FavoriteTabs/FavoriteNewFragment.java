package brokerscircle.com.fragments.TabsFragments.FavoriteTabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import brokerscircle.com.adapters.NewsRecyclerview_Config;
import brokerscircle.com.api_helpers.FavoriteDatabaseHelper;
import brokerscircle.com.api_helpers.NewsDatabaseHelper;
import brokerscircle.com.model.Favorite.FavoriteData;
import brokerscircle.com.model.Favorite.FavoritePostResponseUtils;
import brokerscircle.com.model.News.NewsData;
import brokerscircle.com.R;

public class FavoriteNewFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerview;

    private String currentUserID = "";
    private String type = "News";

    public FavoriteNewFragment() {
        // Required empty public constructor
    }


    public static FavoriteNewFragment newInstance(String param1, String param2) {
        FavoriteNewFragment fragment = new FavoriteNewFragment();
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
        View view = inflater.inflate(R.layout.favorite_new_fragment, container, false);

        mShimmerViewContainer = view.findViewById(R.id.shimmer_top_container);
        mRecyclerview = view.findViewById(R.id.recyclerview);

        //Implement recyclerview
        List<NewsData> favoriteNewsList = new ArrayList<>();
        if (!currentUserID.equals("")) {
            new FavoriteDatabaseHelper().readFavoriteWithReferenceID_AND_Type(new FavoriteDatabaseHelper.DataStatus() {
                @Override
                public void DataIsLoaded(List<FavoriteData> favoriteData) {

                    new NewsDatabaseHelper().readNewsList(new NewsDatabaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<NewsData> newsUtils) {
                            favoriteNewsList.clear();
                            for (int i = 0; i < favoriteData.size(); i++) {
                                for (NewsData data : newsUtils) {
                                    if (favoriteData.get(i).getReferenceId().equals(data.getId())) {
                                        favoriteNewsList.add(data);
                                    }
                                }
                            }
                            new NewsRecyclerview_Config().setConfig(mRecyclerview,getContext(),favoriteNewsList);
                            // Stopping Shimmer Effect's animation after data is loaded to ListView
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                        }
                    }, getContext());
                }

                @Override
                public void DataIsPosted(List<FavoritePostResponseUtils> responseUtils) {

                }

                @Override
                public void DataIsDeleted(List<FavoritePostResponseUtils> responseUtils) {

                }
            }, getContext(), currentUserID, type);
        }

        new NewsDatabaseHelper().readNewsList(new NewsDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<NewsData> newsUtils) {
                new NewsRecyclerview_Config().setConfig(mRecyclerview,getContext(),newsUtils);

                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        },getContext());

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
        super.onPause();
    }
}
