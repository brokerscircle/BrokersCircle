package brokerscirlce.com.fragments.TabsFragments.EstateTabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscirlce.com.adapters.FilesRecyclerview_Config;
import brokerscirlce.com.api_helpers.FilesDatabaseHelper;
import brokerscirlce.com.model.Files.FilesData;
import brokerscirlce.com.R;


public class EstateFilesTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public EstateFilesTabFragment() {
        // Required empty public constructor
    }


    public static EstateFilesTabFragment newInstance(String param1, String param2) {
        EstateFilesTabFragment fragment = new EstateFilesTabFragment();
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
        View view = inflater.inflate(R.layout.estate_files_tab_fragment, container, false);

        //Getting id from Estate Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String estateID = this.getArguments().getString("estateID");
        String type = "Company";
        //Getting ids
        mRecyclerview = view.findViewById(R.id.recyclerview);
        new FilesDatabaseHelper().readFilesWithReference_ID_AND_Type(new FilesDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FilesData> filesData) {
                new FilesRecyclerview_Config().setConfig(mRecyclerview, getContext(), filesData);
            }
        }, getContext(), estateID, type);

        return view;
    }

}
