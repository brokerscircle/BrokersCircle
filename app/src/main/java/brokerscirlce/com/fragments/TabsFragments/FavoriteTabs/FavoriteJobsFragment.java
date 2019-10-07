package brokerscirlce.com.fragments.TabsFragments.FavoriteTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brokerscirlce.com.R;

public class FavoriteJobsFragment extends Fragment {

    private String currentUserID = "";
    private String type = "Jobs";

    public FavoriteJobsFragment() {
        // Required empty public constructor
    }


    public static FavoriteJobsFragment newInstance(String param1, String param2) {
        FavoriteJobsFragment fragment = new FavoriteJobsFragment();
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
        View view = inflater.inflate(R.layout.favorite_jobs_fragment, container, false);



        return view;
    }


}
