package brokerscircle.com.fragments.TabsFragments.ProfileTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brokerscircle.com.adapters.VideosRecyclerview_Config;
import brokerscircle.com.api_helpers.VideosDatabaseHelper;
import brokerscircle.com.model.Videos.VideosData;
import brokerscircle.com.R;

public class ProfileVideosTabFragment extends Fragment {

    private RecyclerView mRecyclerview;
    private String brokerID = "";
    private String type = "Broker";

    public ProfileVideosTabFragment() {
        // Required empty public constructor
    }

    public static ProfileVideosTabFragment newInstance(String param1, String param2) {
        ProfileVideosTabFragment fragment = new ProfileVideosTabFragment();
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
        View view = inflater.inflate(R.layout.profile_videos_fragment, container, false);
        //Getting id from Profile Detail Activity
        /*
         ***Id for getting brokers of this Estate***
         */
        brokerID = this.getArguments().getString("brokerid");
        //Getting xml ids
        mRecyclerview = view.findViewById(R.id.recyclerview);
        new VideosDatabaseHelper().readVideosWithReference_ID_AND_Type(new VideosDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<VideosData> videosData) {
                new VideosRecyclerview_Config().setConfig(mRecyclerview, getContext(), videosData);
            }
        }, getContext(), brokerID, type);

        return view;
    }
}
