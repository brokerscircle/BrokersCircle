package brokerscircle.com.fragments.TabsFragments.ProfileTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscircle.com.adapters.FilesRecyclerview_Config;
import brokerscircle.com.api_helpers.FilesDatabaseHelper;
import brokerscircle.com.model.Files.FilesData;
import brokerscircle.com.R;

public class ProfileFilesTabFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private String brokerID = "";
    private String type = "Broker";

    public ProfileFilesTabFragment() {
        // Required empty public constructor
    }

    public static ProfileFilesTabFragment newInstance(String param1, String param2) {
        ProfileFilesTabFragment fragment = new ProfileFilesTabFragment();
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
        View view = inflater.inflate(R.layout.profile_files_tab_fragment, container, false);
        //Getting id from Profile Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        brokerID = this.getArguments().getString("brokerid");
        //Getting xml ids
        mRecyclerview = view.findViewById(R.id.recyclerview);
        new FilesDatabaseHelper().readFilesWithReference_ID_AND_Type(new FilesDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<FilesData> filesData) {
                new FilesRecyclerview_Config().setConfig(mRecyclerview, getContext(), filesData);
            }
        }, getContext(), brokerID, type);

        return view;
    }
}
