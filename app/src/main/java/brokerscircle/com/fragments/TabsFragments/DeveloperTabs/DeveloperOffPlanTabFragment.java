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

public class DeveloperOffPlanTabFragment extends Fragment {

    private RecyclerView mRecyclerview;

    public DeveloperOffPlanTabFragment() {
        // Required empty public constructor
    }


    public static DeveloperOffPlanTabFragment newInstance(String param1, String param2) {
        DeveloperOffPlanTabFragment fragment = new DeveloperOffPlanTabFragment();
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
        View view = inflater.inflate(R.layout.project_off_plan_fragment, container, false);

        mRecyclerview = view.findViewById(R.id.recyclerview);

        //Implement recylerview
        new ProjectDatabaseHelper().readProjectByParentCategory(new ProjectDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProjectData> projectData) {
                new ProjectDetailTabsRecyclerview_Config().setConfig(mRecyclerview,getContext(),projectData);
            }
        }, getContext(), "2");

        return view;
    }
}
