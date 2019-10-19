package brokerscircle.com.fragments.TabsFragments.Dashboard.DashboardFourTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscircle.com.adapters.PropertyListRecyclerview_config_Four;
import brokerscircle.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscircle.com.api_helpers.SystemSettingDatabaseHelper;
import brokerscircle.com.model.Property.PropertyData;
import brokerscircle.com.model.System_Setting.SystemSettingData;
import brokerscircle.com.R;

public class D4WantedTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public D4WantedTabFragment() {
        // Required empty public constructor
    }


    public static D4WantedTabFragment newInstance(String param1, String param2) {
        D4WantedTabFragment fragment = new D4WantedTabFragment();
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
        View view = inflater.inflate(R.layout.fragment_d4_wanted_tab, container, false);

        mRecyclerview = view.findViewById(R.id.recyclerview);
        new SystemSettingDatabaseHelper().readSettingList(new SystemSettingDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<SystemSettingData> systemSettingData) {

                new PropertyDatabaseHelper().readPropertyForBuy(new PropertyDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<PropertyData> propertyData) {
                        if (!propertyData.isEmpty()){
                            new PropertyListRecyclerview_config_Four().setConfig(mRecyclerview,getContext(),propertyData);
                        }                    }
                }, getContext(),systemSettingData.get(0).getAppDefaultWantedId());
            }
        }, getContext());

        return view;
    }
}
