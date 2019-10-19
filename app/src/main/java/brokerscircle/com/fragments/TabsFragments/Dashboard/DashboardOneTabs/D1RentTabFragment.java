package brokerscircle.com.fragments.TabsFragments.Dashboard.DashboardOneTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscircle.com.adapters.PropertyListRecyclerview_config_One;
import brokerscircle.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscircle.com.api_helpers.SystemSettingDatabaseHelper;
import brokerscircle.com.model.Property.PropertyData;
import brokerscircle.com.model.System_Setting.SystemSettingData;
import brokerscircle.com.R;

public class D1RentTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public D1RentTabFragment() {
        // Required empty public constructor
    }


    public static D1RentTabFragment newInstance(String param1, String param2) {
        D1RentTabFragment fragment = new D1RentTabFragment();
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
        View view = inflater.inflate(R.layout.d1_rent_tab_fragment, container, false);

        String rentid = "";

        mRecyclerview = view.findViewById(R.id.recycle_lease_property);

        new SystemSettingDatabaseHelper().readSettingList(new SystemSettingDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<SystemSettingData> systemSettingData) {

                new PropertyDatabaseHelper().readPropertyForRent(new PropertyDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<PropertyData> propertyData) {
                        if (!propertyData.isEmpty()){
                            new PropertyListRecyclerview_config_One().setConfig(mRecyclerview,getContext(),propertyData);
                        }                    }
                }, getContext(),systemSettingData.get(0).getAppDefaultRentId());

            }
        }, getContext());


        return view;
    }
}
