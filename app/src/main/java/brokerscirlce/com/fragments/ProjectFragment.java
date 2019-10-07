package brokerscirlce.com.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import brokerscirlce.com.Activities.Brokers.BrokerProfileActivity;
import brokerscirlce.com.Activities.Chat.ChatActivity;
import brokerscirlce.com.adapters.ProjectRecyclerview_Config;
import brokerscirlce.com.api_helpers.ProjectDatabaseHelper;
import brokerscirlce.com.model.Project.ProjectData;
import brokerscirlce.com.R;
import brokerscirlce.com.model.login_user.LoginUser;
import brokerscirlce.com.util.Helper;

public class ProjectFragment extends Fragment implements PullRefreshLayout.OnRefreshListener, View.OnClickListener {

    View mView;
    private Helper helper;
    private LoginUser user = null;

    //Toolbar
    private ImageView mMenuIconD1;
    private ImageView mChatButtonD1;
    private LinearLayout mMenuBtnLayoutD1;
    private RoundedImageView mProfileImageD1;

    private DrawerLayout drawer;

    private PullRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerview;


    public ProjectFragment() {
        // Required empty public constructor
    }

    public static ProjectFragment newInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
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
        helper = new Helper(getContext());
        user = helper.getLoggedInUser();
        mView = inflater.inflate(R.layout.project_fragment, container, false);

        initAppBar();

        mRecyclerview = mView.findViewById(R.id.recyclerview);
        mRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);

        mRefreshLayout.setOnRefreshListener(this);
        //Implement recylerview
        new ProjectDatabaseHelper().readProjectList(new ProjectDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProjectData> projectData) {
                if (!projectData.isEmpty()){
                    new ProjectRecyclerview_Config().setConfig(mRecyclerview,getContext(),projectData);
                }
            }
        }, getContext());

        return mView;
    }

    private void initAppBar() {
        mMenuBtnLayoutD1 = mView.findViewById(R.id.menubtn_layout);
        mMenuIconD1 = mView.findViewById(R.id.menuIcon);
        mChatButtonD1 = mView.findViewById(R.id.img_chat);
        mProfileImageD1 = mView.findViewById(R.id.profile_image);

        drawer = getActivity().findViewById(R.id.drawer_layout);

        //Toolbar profile image Profile Image
        if ((user.getResponse().getData().getUser().getImg() == null) || (user.getResponse().getData().getUser().getImg().equals(""))){
            mProfileImageD1.setImageResource(R.drawable.ic_user_icon_colored);
        }else {
            Picasso.get().load(user.getResponse().getData().getUser().getImg()).centerCrop().fit().placeholder(R.drawable.ic_user_icon_colored).into(mProfileImageD1);
        }

        //on menu click nav drawer open
        mMenuBtnLayoutD1.setOnClickListener(this);
        //Profile image click listener
        mProfileImageD1.setOnClickListener(this);
        //Chat Image Click listener
        mChatButtonD1.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        new ProjectDatabaseHelper().readProjectList(new ProjectDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProjectData> projectData) {
                if (!projectData.isEmpty()){
                    new ProjectRecyclerview_Config().setConfig(mRecyclerview,getContext(),projectData);
                }
                mRefreshLayout.setRefreshing(false);
            }
        }, getContext());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;

        switch (id) {
            case R.id.menubtn_layout:
                // If the navigation drawer is not open then open it, if its already open then close it.
                if (!drawer.isDrawerOpen(Gravity.LEFT)) drawer.openDrawer(Gravity.LEFT);
                else drawer.closeDrawer(Gravity.RIGHT);
                break;
            case R.id.profile_image:
                intent = new Intent(getContext(), BrokerProfileActivity.class);
                intent.putExtra("brokerid", user.getResponse().getData().getUser().getUserId());
                startActivity(intent);
                break;
            case R.id.img_chat:
                startActivity(new Intent(getContext(), ChatActivity.class));
                break;
            case R.id.menubtn_layout_two:
                // If the navigation drawer is not open then open it, if its already open then close it.
                if (!drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.openDrawer(Gravity.LEFT);
                } else {
                    drawer.closeDrawer(Gravity.RIGHT);
                }
                break;
        }
    }
}
