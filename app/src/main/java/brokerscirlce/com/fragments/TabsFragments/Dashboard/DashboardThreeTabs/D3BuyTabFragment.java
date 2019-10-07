package brokerscirlce.com.fragments.TabsFragments.Dashboard.DashboardThreeTabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscirlce.com.adapters.PropertyListRecyclerview_config_Three;
import brokerscirlce.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscirlce.com.api_helpers.SystemSettingDatabaseHelper;
import brokerscirlce.com.model.Property.PropertyData;
import brokerscirlce.com.model.System_Setting.SystemSettingData;
import brokerscirlce.com.R;

public class D3BuyTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public D3BuyTabFragment() {
        // Required empty public constructor
    }


    public static D3BuyTabFragment newInstance(String param1, String param2) {
        D3BuyTabFragment fragment = new D3BuyTabFragment();
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
        View view = inflater.inflate(R.layout.d3_buy_tab_fragment, container, false);

        mRecyclerview = view.findViewById(R.id.recyclerview);
        new SystemSettingDatabaseHelper().readSettingList(new SystemSettingDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<SystemSettingData> systemSettingData) {

                new PropertyDatabaseHelper().readPropertyForBuy(new PropertyDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<PropertyData> propertyData) {
                        if (!propertyData.isEmpty()){
                            new PropertyListRecyclerview_config_Three().setConfig(mRecyclerview,getContext(),propertyData);
                        }                    }
                }, getContext(),systemSettingData.get(0).getAppDefaultBuyId());
            }
        }, getContext());

        return view;
    }

}
