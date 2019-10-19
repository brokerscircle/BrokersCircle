package brokerscircle.com.fragments.TabsFragments.DeveloperTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscircle.com.adapters.ProjectDetailTabsRecyclerview_Config;
import brokerscircle.com.api_helpers.ProjectDatabaseHelper;
import brokerscircle.com.model.Project.ProjectData;
import brokerscircle.com.R;

public class DeveloperCompletedTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public DeveloperCompletedTabFragment() {
        // Required empty public constructor
    }


    public static DeveloperCompletedTabFragment newInstance(String param1, String param2) {
        DeveloperCompletedTabFragment fragment = new DeveloperCompletedTabFragment();
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
        View view = inflater.inflate(R.layout.project_completed_fragment, container, false);

        mRecyclerview = view.findViewById(R.id.recyclerview);

        //Implement recylerview
        new ProjectDatabaseHelper().readProjectByParentCategory(new ProjectDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProjectData> projectData) {
                new ProjectDetailTabsRecyclerview_Config().setConfig(mRecyclerview,getContext(),projectData);
            }
        }, getContext(), "1");

        return view;
    }
}
