package brokerscirlce.com.fragments.TabsFragments.EstateTabs;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscirlce.com.adapters.ReviewsRecyclerview_config;
import brokerscirlce.com.api_helpers.RealEstateDatabaseHelper;
import brokerscirlce.com.api_helpers.ReviewsDatabaseHelper;
import brokerscirlce.com.model.RealEstates.RealEstateData;
import brokerscirlce.com.model.Reviews.ReviewData;
import brokerscirlce.com.R;

public class EstateReviewTabFragment extends Fragment {

    private ProgressBar mProgress_5, mProgress_4, mProgress_3, mProgress_2, mProgress_1;
    private TextView mAverageRatingCountTV, mTotalReviewCountTV;
    private RatingBar mRatingBar;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerView;

    public EstateReviewTabFragment() {
        // Required empty public constructor
    }

    public static EstateReviewTabFragment newInstance() {
        EstateReviewTabFragment fragment = new EstateReviewTabFragment();
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
        View view = inflater.inflate(R.layout.estate_review_tab_fragment, container, false);

        //Getting id from Estate Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String estateID = this.getArguments().getString("estateID");

        /*
         *  Shimmerview for Loading when data is fetchting from Server
         * */

        //Getting id
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        mProgress_5 = view.findViewById(R.id.progress_5);
        mProgress_4 = view.findViewById(R.id.progress_4);
        mProgress_3 = view.findViewById(R.id.progress_3);
        mProgress_2 = view.findViewById(R.id.progress_2);
        mProgress_1 = view.findViewById(R.id.progress_1);
        mAverageRatingCountTV = view.findViewById(R.id.average_rating_tv);
        mRatingBar = view.findViewById(R.id.rating_stars);
        mTotalReviewCountTV = view.findViewById(R.id.review_count_tv);
        mRecyclerView = view.findViewById(R.id.recyclerview);

        new RealEstateDatabaseHelper().readEstateDetail(new RealEstateDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<RealEstateData> realEstateData) {
                //Set Average rating to text view
                mAverageRatingCountTV.setText((realEstateData.get(0).getAverageRating() == null)? "0" : String.valueOf(Float.parseFloat(realEstateData.get(0).getAverageRating().toString())));

                //Set Rating to rating bar
                mRatingBar.setRating((realEstateData.get(0).getAverageRating() == null)? (float) 0 : (float) realEstateData.get(0).getAverageRating());
                LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);

                //set Total Reviews to
                mTotalReviewCountTV.setText((realEstateData.get(0).getNoOfReviews() == null)? "0 Reviews" : realEstateData.get(0).getNoOfReviews().toString()+" Reviews");

            }
        }, getContext(), estateID);

        new ReviewsDatabaseHelper().readReviewList(new ReviewsDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ReviewData> reviewData) {
                new ReviewsRecyclerview_config().setConfig(mRecyclerView,getContext(), reviewData);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, getContext(), estateID, "company");

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
