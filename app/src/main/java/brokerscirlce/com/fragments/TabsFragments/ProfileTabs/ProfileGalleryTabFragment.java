package brokerscirlce.com.fragments.TabsFragments.ProfileTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscirlce.com.adapters.GalleriesGridRecyclerview_Config;
import brokerscirlce.com.api_helpers.GalleriesDatabaseHelper;
import brokerscirlce.com.model.Galleries.GalleriesData;
import brokerscirlce.com.R;

public class ProfileGalleryTabFragment extends Fragment{

    private RecyclerView mRecyclerview;
    private String brokerID = "";
    private String type = "Broker";

    public ProfileGalleryTabFragment() {
        // Required empty public constructor
    }

    public static ProfileGalleryTabFragment newInstance(String param1, String param2) {
        ProfileGalleryTabFragment fragment = new ProfileGalleryTabFragment();
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
        View view = inflater.inflate(R.layout.profile_gallery_fragment, container, false);
        //Getting id from Profile Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        brokerID = this.getArguments().getString("brokerid");
        //Getting xml ids
        mRecyclerview = view.findViewById(R.id.recyclerview);

        new GalleriesDatabaseHelper().readGalleriesWithReference_ID_AND_Type(new GalleriesDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<GalleriesData> galleriesData) {
                Log.d("ProfileGallery", "notifySuccess: Galleries "+galleriesData);
                new GalleriesGridRecyclerview_Config().setConfig(mRecyclerview, getContext(), galleriesData);
            }
        }, getContext(), brokerID, type);

        return view;
    }
}
