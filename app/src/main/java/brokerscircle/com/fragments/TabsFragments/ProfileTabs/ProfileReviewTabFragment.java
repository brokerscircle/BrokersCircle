package brokerscircle.com.fragments.TabsFragments.ProfileTabs;

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

import brokerscircle.com.adapters.ReviewsRecyclerview_config;
import brokerscircle.com.api_helpers.BrokersDatabaseHelper;
import brokerscircle.com.api_helpers.ReviewsDatabaseHelper;
import brokerscircle.com.model.Brokers.BrokersData;
import brokerscircle.com.model.Reviews.ReviewData;
import brokerscircle.com.R;

public class ProfileReviewTabFragment extends Fragment {

    private ProgressBar mProgress_5, mProgress_4, mProgress_3, mProgress_2, mProgress_1;
    private TextView mAverageRatingCountTV, mTotalReviewCountTV;
    private RatingBar mRatingBar;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mRecyclerView;

    public ProfileReviewTabFragment() {
        // Required empty public constructor
    }

    public static ProfileReviewTabFragment newInstance(String param1, String param2) {
        ProfileReviewTabFragment fragment = new ProfileReviewTabFragment();
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
        View view = inflater.inflate(R.layout.profile_review_tab_fragment, container, false);
        //Getting id from Estate Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String brokerID = this.getArguments().getString("brokerid");

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

        new BrokersDatabaseHelper().readBrokersDetail(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {

                //Set Average rating to text view
                mAverageRatingCountTV.setText((brokersUtils.get(0).getAverageRating() == null)? "0" : String.valueOf(Float.parseFloat(brokersUtils.get(0).getAverageRating().toString())));

                //Set Rating to rating bar
                mRatingBar.setRating((brokersUtils.get(0).getAverageRating() == null)? (float) 0 : (float) brokersUtils.get(0).getAverageRating());
                LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingcolor), PorterDuff.Mode.SRC_ATOP);

                //set Total Reviews to
                mTotalReviewCountTV.setText((brokersUtils.get(0).getNoOfReviews() == null)? "0 Reviews" : brokersUtils.get(0).getNoOfReviews().toString()+" Reviews");
            }
        }, getContext(), brokerID);

        new ReviewsDatabaseHelper().readReviewList(new ReviewsDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ReviewData> reviewData) {
                new ReviewsRecyclerview_config().setConfig(mRecyclerView,getContext(), reviewData);
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, getContext(), brokerID, "broker");


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
