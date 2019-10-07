package brokerscirlce.com.fragments.TabsFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscirlce.com.api_helpers.BrokersDatabaseHelper;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.R;

public class BrokersFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public BrokersFragment() {
        // Required empty public constructor
    }

    public static BrokersFragment newInstance(String param1, String param2) {
        BrokersFragment fragment = new BrokersFragment();
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
        View view = inflater.inflate(R.layout.brokers_fragment, container, false);

        mRecyclerview = view.findViewById(R.id.recyclerview);
        new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {
                //new BrokersRecyclerview_Config().setConfig(mRecyclerview, getContext(), brokersUtils);
            }
        },getContext());

        return view;
    }
}
