package brokerscirlce.com.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import brokerscirlce.com.Activities.Brokers.ViewAllBrokersActivity;
import brokerscirlce.com.adapters.BrokersDashboardRecyclerview_Config_Two;
import brokerscirlce.com.adapters.MapTypesRecyclerview_Config;
import brokerscirlce.com.adapters.PropertyListRecyclerview_config_Four_small;
import brokerscirlce.com.api_helpers.BrokersDatabaseHelper;
import brokerscirlce.com.api_helpers.Map.MapTypesDatabaseHelper;
import brokerscirlce.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.model.Map.MapTypes.MapTypesData;
import brokerscirlce.com.model.Property.PropertyData;
import brokerscirlce.com.R;


public class DashboardTwoFragmentV2 extends Fragment {

    private TextView mViewAllBroker;
    ShimmerFrameLayout mBrokerShimmer;
    RecyclerView mBrokerRecyclerview, mPropertyRecyclerview, mAreaRecyclerview;

    public DashboardTwoFragmentV2() {
        // Required empty public constructor
    }


    public static DashboardTwoFragmentV2 newInstance(String param1, String param2) {
        DashboardTwoFragmentV2 fragment = new DashboardTwoFragmentV2();
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
        View view = inflater.inflate(R.layout.dashboard_two_fragment, container, false);

        mViewAllBroker = view.findViewById(R.id.tv_viewall);
        mBrokerShimmer = view.findViewById(R.id.shimmer_top_container);
        mBrokerRecyclerview = view.findViewById(R.id.recycle_broker);
        mPropertyRecyclerview = view.findViewById(R.id.recycle_buy);
        mAreaRecyclerview = view.findViewById(R.id.recycleview_area);

        new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {
                new BrokersDashboardRecyclerview_Config_Two().setConfig(mBrokerRecyclerview,getContext(),brokersUtils);
                mBrokerShimmer.stopShimmer();
                mBrokerShimmer.setVisibility(View.GONE);
            }
        },getContext());

        mViewAllBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewAllBrokersActivity.class);
                startActivity(intent);
            }
        });

        // Property recyclerview
        new PropertyDatabaseHelper().readPropertyList(new PropertyDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyData> propertyData) {
                if (!propertyData.isEmpty()){
                    new PropertyListRecyclerview_config_Four_small().setConfig(mPropertyRecyclerview,getContext(),propertyData);
                }                    }
        }, getContext());

        //Area Recyclerview
        new MapTypesDatabaseHelper().readMapsList(new MapTypesDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<MapTypesData> mapsData) {
                new MapTypesRecyclerview_Config().setConfig(mAreaRecyclerview, getContext(), mapsData);
            }
        }, getContext());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBrokerShimmer.startShimmer();
    }

    @Override
    public void onPause() {
        mBrokerShimmer.stopShimmer();
        mBrokerShimmer.setVisibility(View.GONE);
        super.onPause();
    }
}
