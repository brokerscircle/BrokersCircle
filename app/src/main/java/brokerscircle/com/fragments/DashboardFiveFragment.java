package brokerscircle.com.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscircle.com.Activities.Brokers.ViewAllBrokersActivity;
import brokerscircle.com.Activities.Developers.ViewAllDevelopersActivity;
import brokerscircle.com.Activities.Estates.ViewAllEstateActivity;
import brokerscircle.com.Activities.Projects.ViewAllProjectsActivity;
import brokerscircle.com.Activities.Properties.ViewAllPropertiesActivity;
import brokerscircle.com.adapters.BrokersDashboardRecyclerview_Config;
import brokerscircle.com.api_helpers.BrokersDatabaseHelper;
import brokerscircle.com.model.Brokers.BrokersData;
import brokerscircle.com.R;

public class DashboardFiveFragment extends Fragment {

    //Brokers
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mBrokersRecyclerview;
    private TextView mViewAllTV;
    private ToggleButton showHideBroker;
    private LinearLayout mBrokersLayout;

    private LinearLayout mPropertyButton, mProjectButton, mBrokersButton, mDevelopersButton, mEstateButton, mJobButton;

    public DashboardFiveFragment() {
        // Required empty public constructor
    }

    public static DashboardFiveFragment newInstance(String param1, String param2) {
        DashboardFiveFragment fragment = new DashboardFiveFragment();
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
        View view = inflater.inflate(R.layout.dashboard_five_fragment, container, false);

        showHideBroker = view.findViewById(R.id.showhide);
        mBrokersLayout = view.findViewById(R.id.layoutbrokers);
        mViewAllTV = view.findViewById(R.id.tv_viewall);
        mBrokersRecyclerview = view.findViewById(R.id.recycle_broker);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_top_container);


        mPropertyButton = view.findViewById(R.id.search_property);
        mProjectButton = view.findViewById(R.id.search_projects);
        mBrokersButton = view.findViewById(R.id.search_brokers);
        mDevelopersButton = view.findViewById(R.id.search_developers);
        mEstateButton = view.findViewById(R.id.search_estates);
        mJobButton = view.findViewById(R.id.search_job_board);

        new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {
                new BrokersDashboardRecyclerview_Config().setConfig(mBrokersRecyclerview,getContext(),brokersUtils);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        },getContext());

        showHideBroker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    showHideBroker.setTextColor(getContext().getResources().getColor(R.color.colorRed));
                    mBrokersLayout.setVisibility(View.GONE);
                }else {
                    showHideBroker.setTextColor(getContext().getResources().getColor(R.color.colorDark));
                    mBrokersLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        mViewAllTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllBrokersActivity.class);
                startActivity(intent);
            }
        });

        //Property button
        mPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllPropertiesActivity.class);
                startActivity(intent);
            }
        });

        //Brokers button
        mBrokersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ViewAllBrokersActivity.class);
                startActivity(intent);
            }
        });

        //Developers button
        mDevelopersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllDevelopersActivity.class);
                startActivity(intent);
            }
        });

        //Estate button
        mEstateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllEstateActivity.class);
                startActivity(intent);
            }
        });

        //Project button
        mProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllProjectsActivity.class);
                startActivity(intent);            }
        });

        //Jobs button
        mJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Jobs Coming Soon", Toast.LENGTH_LONG).show();
            }
        });

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
