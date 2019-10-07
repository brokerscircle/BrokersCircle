package brokerscirlce.com.fragments.TabsFragments.DeveloperTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscirlce.com.adapters.ProjectDetailTabsRecyclerview_Config;
import brokerscirlce.com.api_helpers.ProjectDatabaseHelper;
import brokerscirlce.com.model.Project.ProjectData;
import brokerscirlce.com.R;

public class DeveloperInProgressTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public DeveloperInProgressTabFragment() {
        // Required empty public constructor
    }


    public static DeveloperInProgressTabFragment newInstance(String param1, String param2) {
        DeveloperInProgressTabFragment fragment = new DeveloperInProgressTabFragment();
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
        View view = inflater.inflate(R.layout.project_in_progress_fragment, container, false);

        mRecyclerview = view.findViewById(R.id.recyclerview);

        //Implement recylerview
        new ProjectDatabaseHelper().readProjectByParentCategory(new ProjectDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProjectData> projectData) {
                new ProjectDetailTabsRecyclerview_Config().setConfig(mRecyclerview,getContext(),projectData);
            }
        }, getContext(), "3");

        return view;
    }
}
