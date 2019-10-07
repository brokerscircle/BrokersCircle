package brokerscirlce.com.fragments.TabsFragments.FavoriteTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brokerscirlce.com.R;


public class FavoriteProjectFragment extends Fragment {


    private String currentUserID = "";
    private String type = "Project";

    public FavoriteProjectFragment() {
        // Required empty public constructor
    }


    public static FavoriteProjectFragment newInstance(String param1, String param2) {
        FavoriteProjectFragment fragment = new FavoriteProjectFragment();
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
        View view = inflater.inflate(R.layout.favorite_project_fragment, container, false);


        return view;
    }

}
