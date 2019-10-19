package brokerscircle.com.fragments;

import android.annotation.SuppressLint;
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

import brokerscircle.com.Activities.Brokers.ViewAllBrokersActivity;
import brokerscircle.com.adapters.BrokersDashboardRecyclerview_Config;
import brokerscircle.com.fragments.TabsFragments.Dashboard.DashboardOneTabs.D1DashboardTabsAdapter;
import brokerscircle.com.api_helpers.BrokersDatabaseHelper;
import brokerscircle.com.model.Brokers.BrokersData;
import brokerscircle.com.R;

public class DashboardFragment extends Fragment {


    private D1DashboardTabsAdapter pageAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //Brokers
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView mBrokersRecyclerview;
    private TextView mViewAllTV;
    private ToggleButton showHideBroker;
    private LinearLayout mBrokersLayout;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        //Geting IDs
        showHideBroker = view.findViewById(R.id.showhide);
        mBrokersLayout = view.findViewById(R.id.layoutbrokers);
        mViewAllTV = view.findViewById(R.id.tv_viewall);
        mBrokersRecyclerview = view.findViewById(R.id.recycle_broker);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_top_container);

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);
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
        pageAdapter = new D1DashboardTabsAdapter(getChildFragmentManager(), tabLayout.getTabCount());
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
