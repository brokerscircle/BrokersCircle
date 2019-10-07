package brokerscirlce.com.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import brokerscirlce.com.Activities.Brokers.ViewAllBrokersActivity;
import brokerscirlce.com.adapters.BrokersDashboardRecyclerview_Config;
import brokerscirlce.com.fragments.TabsFragments.Dashboard.DashboardThreeTabs.D3DashboardTabsAdapter;
import brokerscirlce.com.api_helpers.BrokersDatabaseHelper;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.R;

public class DashboardThreeFragment extends Fragment {

    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mBrokersRecyclerview;
    private D3DashboardTabsAdapter pageAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView mViewAllTV;

    ToggleButton showHideBroker;
    LinearLayout mBrokersLayout;

    public DashboardThreeFragment() {
        // Required empty public constructor
    }


    public static DashboardThreeFragment newInstance(String param1, String param2) {
        DashboardThreeFragment fragment = new DashboardThreeFragment();
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
        View view = inflater.inflate(R.layout.dashboard_three_fragment, container, false);

        //Geting IDs
        showHideBroker = view.findViewById(R.id.showhide);
        mBrokersLayout = view.findViewById(R.id.layoutbrokers);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        mViewAllTV = view.findViewById(R.id.tv_viewall);
        mBrokersRecyclerview = view.findViewById(R.id.recycle_broker);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_top_container);
        //generateDataList();

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

        //Tabs Adapter
        pageAdapter = new D3DashboardTabsAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        mViewAllTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllBrokersActivity.class);
                startActivity(intent);
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
