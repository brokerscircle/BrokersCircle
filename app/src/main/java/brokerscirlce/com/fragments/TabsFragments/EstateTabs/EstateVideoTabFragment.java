package brokerscirlce.com.fragments.TabsFragments.EstateTabs;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscirlce.com.adapters.VideosRecyclerview_Config;
import brokerscirlce.com.api_helpers.VideosDatabaseHelper;
import brokerscirlce.com.model.Videos.VideosData;
import brokerscirlce.com.R;

public class EstateVideoTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public EstateVideoTabFragment() {
        // Required empty public constructor
    }


    public static EstateVideoTabFragment newInstance(String param1, String param2) {
        EstateVideoTabFragment fragment = new EstateVideoTabFragment();
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
        View view = inflater.inflate(R.layout.estate_video_tab_fragment, container, false);

        //Getting id from Estate Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String estateID = this.getArguments().getString("estateID");
        String type = "Company";
        //Getting ids
        mRecyclerview = view.findViewById(R.id.recyclerview);
        new VideosDatabaseHelper().readVideosWithReference_ID_AND_Type(new VideosDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<VideosData> videosData) {
                new VideosRecyclerview_Config().setConfig(mRecyclerview, getContext(), videosData);
            }
        }, getContext(), estateID, type);

        return view;
    }

}
