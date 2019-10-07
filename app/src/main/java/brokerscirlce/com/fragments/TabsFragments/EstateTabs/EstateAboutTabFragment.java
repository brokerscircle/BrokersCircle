package brokerscirlce.com.fragments.TabsFragments.EstateTabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brokerscirlce.com.R;

public class EstateAboutTabFragment extends Fragment {

    public EstateAboutTabFragment() {
        // Required empty public constructor
    }


    public static EstateAboutTabFragment newInstance(String param1, String param2) {
        EstateAboutTabFragment fragment = new EstateAboutTabFragment();
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
        View view = inflater.inflate(R.layout.estate_about_tab_fragment, container, false);



        return view;
    }

}
