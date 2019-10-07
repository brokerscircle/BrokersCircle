package brokerscirlce.com.fragments.TabsFragments.DeveloperTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import brokerscirlce.com.R;

public class DeveloperReviewTabFragment extends Fragment {

    private ProgressBar mProgress_5, mProgress_4, mProgress_3, mProgress_2, mProgress_1;
    private TextView mAverageRatingCountTV, mTotalReviewCountTV;
    private RatingBar mRatingBar;
    private RecyclerView mRecyclerView;

    public DeveloperReviewTabFragment() {
        // Required empty public constructor
    }


    public static DeveloperReviewTabFragment newInstance(String param1, String param2) {
        DeveloperReviewTabFragment fragment = new DeveloperReviewTabFragment();
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
        View view = inflater.inflate(R.layout.project_review_tab_fragment, container, false);

        //Getting id
        mProgress_5 = view.findViewById(R.id.progress_5);
        mProgress_4 = view.findViewById(R.id.progress_4);
        mProgress_3 = view.findViewById(R.id.progress_3);
        mProgress_2 = view.findViewById(R.id.progress_2);
        mProgress_1 = view.findViewById(R.id.progress_1);
        mAverageRatingCountTV = view.findViewById(R.id.average_rating_tv);
        mRatingBar = view.findViewById(R.id.rating_stars);
        mTotalReviewCountTV = view.findViewById(R.id.review_count_tv);
        mRecyclerView = view.findViewById(R.id.recyclerview);

//        new ProjectReviewCommentsDatabaseHelper().readReviewCommentsList(new ProjectReviewCommentsDatabaseHelper.DataStatus() {
//            @Override
//            public void DataIsLoaded(List<ReviewUtils> reviewCommentUtils, List<String> keys) {
//                new ReviewsRecyclerview_config().setConfig(mRecyclerView, getContext(), reviewCommentUtils,keys);
//            }
//        });

        return view;
    }

}
