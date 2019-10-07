package brokerscirlce.com.fragments.TabsFragments.EstateTabs;


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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstateGalleryTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstateGalleryTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public EstateGalleryTabFragment() {
        // Required empty public constructor
    }

    public static EstateGalleryTabFragment newInstance(String param1, String param2) {
        EstateGalleryTabFragment fragment = new EstateGalleryTabFragment();
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
        View view = inflater.inflate(R.layout.estate_gallery_tab_fragment, container, false);

        //Getting id from Estate Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        String estateID = this.getArguments().getString("estateID");
        String type = "Company";
        mRecyclerview = view.findViewById(R.id.recyclerview);
        new GalleriesDatabaseHelper().readGalleriesWithReference_ID_AND_Type(new GalleriesDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<GalleriesData> galleriesData) {
                Log.d("ProfileGallery", "notifySuccess: Galleries "+galleriesData);
                new GalleriesGridRecyclerview_Config().setConfig(mRecyclerview, getContext(), galleriesData);
            }
        }, getContext(), estateID, type);

        return view;
    }

}
