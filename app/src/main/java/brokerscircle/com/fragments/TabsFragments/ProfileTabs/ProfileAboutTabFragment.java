package brokerscircle.com.fragments.TabsFragments.ProfileTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import brokerscircle.com.R;

public class ProfileAboutTabFragment extends Fragment {

    public ProfileAboutTabFragment() {
        // Required empty public constructor
    }


    public static ProfileAboutTabFragment newInstance(String param1, String param2) {
        ProfileAboutTabFragment fragment = new ProfileAboutTabFragment();
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
        return inflater.inflate(R.layout.profile_about_tab_fragment, container, false);
    }
}
