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

public class D1WantedTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public D1WantedTabFragment() {
        // Required empty public constructor
    }

    public static D1WantedTabFragment newInstance(String param1, String param2) {
        D1WantedTabFragment fragment = new D1WantedTabFragment();
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
        View view = inflater.inflate(R.layout.d1_wanted_tab_fragment, container, false);

        String wantedid = "";

        mRecyclerview = view.findViewById(R.id.recyclerview);
        new SystemSettingDatabaseHelper().readSettingList(new SystemSettingDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<SystemSettingData> systemSettingData) {

                new PropertyDatabaseHelper().readPropertyForWanted(new PropertyDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<PropertyData> propertyData) {
                        if (!propertyData.isEmpty()){
                            new PropertyListRecyclerview_config_One().setConfig(mRecyclerview,getContext(),propertyData);
                        }

                    }
                }, getContext(),systemSettingData.get(0).getAppDefaultWantedId());

            }
        }, getContext());

        return view;
    }
}
