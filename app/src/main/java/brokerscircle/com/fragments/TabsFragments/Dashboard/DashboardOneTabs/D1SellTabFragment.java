package brokerscircle.com.fragments.TabsFragments.Dashboard.DashboardOneTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brokerscircle.com.R;

public class D1SellTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public D1SellTabFragment() {
        // Required empty public constructor
    }


    public static D1SellTabFragment newInstance(String param1, String param2) {
        D1SellTabFragment fragment = new D1SellTabFragment();
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
        View view = inflater.inflate(R.layout.d1_sell_tab_fragment, container, false);

        mRecyclerview = view.findViewById(R.id.recycle_sale_property);
//        new PropertyDatabaseHelper().readPropertyForSale(new PropertyDatabaseHelper.DataStatus() {
//            @Override
//            public void DataIsLoaded(List<ProjectData> propertyData) {
//                new PropertyListRecyclerview_config_One().setConfig(mRecyclerview,getContext(),propertyData);
//            }
//        }, getContext());

        return view;
    }
}
