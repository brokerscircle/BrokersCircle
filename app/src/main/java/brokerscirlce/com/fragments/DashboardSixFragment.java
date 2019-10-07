package brokerscirlce.com.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import brokerscirlce.com.Activities.CreateActivity;
import brokerscirlce.com.Activities.FollowersSuggestionActivity;
import brokerscirlce.com.Activities.MapActivity;
import brokerscirlce.com.Activities.Brokers.ViewAllBrokersActivity;
import brokerscirlce.com.Activities.Projects.ViewAllProjectsActivity;
import brokerscirlce.com.Activities.Properties.ViewAllPropertiesActivity;
import brokerscirlce.com.R;
import brokerscirlce.com.adapters.AddRecyclerview_horizontal_config;
import brokerscirlce.com.adapters.BrokersDashboardRecyclerview_Config_Two;
import brokerscirlce.com.adapters.FollowersSuggestion_Horizontal_Recyclerview_Config;
import brokerscirlce.com.adapters.JobsRecyclerview_Horizontal_Config;
import brokerscirlce.com.adapters.MapTypesRecyclerview_Config;
import brokerscirlce.com.adapters.NewsRecyclerview_Horizontal_Config;
import brokerscirlce.com.adapters.ProjectRecyclerview_Horizontal_Config;
import brokerscirlce.com.adapters.PropertyListRecyclerview_config_Four_small;
import brokerscirlce.com.api_helpers.BrokersDatabaseHelper;
import brokerscirlce.com.api_helpers.Jobs.JobsDatabaseHelper;
import brokerscirlce.com.api_helpers.Map.MapTypesDatabaseHelper;
import brokerscirlce.com.api_helpers.NewsDatabaseHelper;
import brokerscirlce.com.api_helpers.ProjectDatabaseHelper;
import brokerscirlce.com.api_helpers.Property.PropertyDatabaseHelper;
import brokerscirlce.com.api_helpers.UsersDatabaseHelper;
import brokerscirlce.com.model.AddUtil;
import brokerscirlce.com.model.Brokers.BrokersData;
import brokerscirlce.com.model.Map.MapTypes.MapTypesData;
import brokerscirlce.com.model.News.NewsData;
import brokerscirlce.com.model.Project.ProjectData;
import brokerscirlce.com.model.Property.PropertyData;
import brokerscirlce.com.model.Users.UsersData;
import brokerscirlce.com.model.jobs.JobData;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardSixFragment extends Fragment implements View.OnClickListener {

    //Dashboard Two contents
    private LinearLayout mMenuBtnLayoutD2;
    private ImageView mMenuIconD2;
    private ImageView mNotificationD2;
    private ImageView mChatButtonD2;
    private CircleImageView mProfileImageD2;
    private RecyclerView mBrokersRecyclerview, mBannerAddRecyclerview, mPropertiesRecyclerview, mLocationsRecyclerview, mSuggestionRecyclerview, mProjectsRecyclerview, mNewRecyclerview, mJobsRecyclerview;
    private TextView mBrokersViewallTV, mPropertiesViewallTV, mLocationsViewallTV, mSuggestionViewallTV, mProjectsViewallTV, mNewsViewallTV, mJobsViewallTV;
    private ToggleButton mBrokersShowHideTV;
    private RelativeLayout mBrokersLayout, mBannersLayout, mPropertiesLayout, mLocationLayout, mSuggestionLayout, mProjectLayout, mNewsLayout, mjobsLayout;
    private FloatingActionButton mCreateNewFabButton;

    public DashboardSixFragment() {
        // Required empty public constructor
    }


    public static DashboardSixFragment newInstance(String param1, String param2) {
        DashboardSixFragment fragment = new DashboardSixFragment();
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
        View view = inflater.inflate(R.layout.dashboard_six_fragment, container, false);

        //DashboardTwoContent ids
        mCreateNewFabButton = view.findViewById(R.id.createfab);
        mBrokersRecyclerview = view.findViewById(R.id.recyclerview_broker);
        mBannerAddRecyclerview = view.findViewById(R.id.recycleview_banners);
        mPropertiesRecyclerview = view.findViewById(R.id.recycleview_properties);
        mLocationsRecyclerview = view.findViewById(R.id.recycleview_locations);
        mSuggestionRecyclerview = view.findViewById(R.id.recycleview_suggestion);
        mProjectsRecyclerview = view.findViewById(R.id.recycleview_projects);
        mNewRecyclerview = view.findViewById(R.id.recycleview_news);
        mJobsRecyclerview = view.findViewById(R.id.recycleview_jobs);
        mBrokersShowHideTV = view.findViewById(R.id.showhide);
        mBrokersLayout = view.findViewById(R.id.layoutbrokers);
        mPropertiesLayout = view.findViewById(R.id.layoutpropeties);
        mSuggestionLayout = view.findViewById(R.id.layoutsuggestions);
        mLocationLayout = view.findViewById(R.id.layoutlocations);
        mProjectLayout = view.findViewById(R.id.layoutprojects);
        mNewsLayout = view.findViewById(R.id.layoutnews);
        mBrokersViewallTV = view.findViewById(R.id.tv_broker_viewall);
        mPropertiesViewallTV = view.findViewById(R.id.tv_viewall_properties);
        mSuggestionViewallTV = view.findViewById(R.id.tv_viewall_suggestion);
        mLocationsViewallTV = view.findViewById(R.id.tv_viewall_location);
        mProjectsViewallTV = view.findViewById(R.id.tv_viewall_projects);
        mNewsViewallTV = view.findViewById(R.id.tv_viewall_news);
        mJobsViewallTV = view.findViewById(R.id.tv_viewall_jobs);

        new BrokersDatabaseHelper().readBrokersList(new BrokersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<BrokersData> brokersUtils) {
                if ((brokersUtils.size())>0){
                    mBrokersShowHideTV.setVisibility(View.VISIBLE);
                    mBrokersLayout.setVisibility(View.VISIBLE);
                }
                new BrokersDashboardRecyclerview_Config_Two().setConfig(mBrokersRecyclerview, getContext(),brokersUtils);
            }
        },getContext());

        //ADDS Banner Recyclerview
        List<AddUtil> bannerList = new ArrayList<>();
        bannerList.add(new AddUtil(R.mipmap.b1, "Add One"));
        bannerList.add(new AddUtil(R.mipmap.b2, "Add Two"));
        bannerList.add(new AddUtil(R.mipmap.b3, "Add Three"));

        //Advertisement Banner
        new AddRecyclerview_horizontal_config().setConfig(mBannerAddRecyclerview,getContext(), bannerList);

        // Property recyclerview
        new PropertyDatabaseHelper().readPropertyList(new PropertyDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<PropertyData> propertyData) {
                if ((propertyData.size())>0){
                    mPropertiesLayout.setVisibility(View.VISIBLE);
                }
                new PropertyListRecyclerview_config_Four_small().setConfig(mPropertiesRecyclerview,getContext(),propertyData);
            }
        }, getContext());

        //Area Recyclerview
        new MapTypesDatabaseHelper().readMapsList(new MapTypesDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<MapTypesData> mapsData) {
                if ((mapsData.size())>0){
                    mLocationLayout.setVisibility(View.VISIBLE);
                }
                new MapTypesRecyclerview_Config().setConfig(mLocationsRecyclerview, getContext(), mapsData);
            }
        }, getContext());

        new UsersDatabaseHelper().readUserList(new UsersDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<UsersData> usersData) {
                if ((usersData.size())>0){
                    mSuggestionLayout.setVisibility(View.VISIBLE);
                }
                new FollowersSuggestion_Horizontal_Recyclerview_Config().setConfig(mSuggestionRecyclerview,getContext(), usersData);
            }
        }, getContext());


        new ProjectDatabaseHelper().readProjectList(new ProjectDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<ProjectData> projectData) {
                if ((projectData.size())>0){
                    mProjectLayout.setVisibility(View.VISIBLE);
                }
                new ProjectRecyclerview_Horizontal_Config().setConfig(mProjectsRecyclerview,getContext(),projectData);
            }
        }, getContext());

        new NewsDatabaseHelper().readNewsList(new NewsDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<NewsData> newsUtils) {
                if ((newsUtils.size())>0){
                    mNewsLayout.setVisibility(View.VISIBLE);
                }
                new NewsRecyclerview_Horizontal_Config().setConfig(mNewRecyclerview,getContext(),newsUtils);
            }
        },getContext());

        //Jobs
        new JobsDatabaseHelper().readJobList(new JobsDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<JobData> jobData) {
                new JobsRecyclerview_Horizontal_Config().setConfig(mJobsRecyclerview, getContext(),jobData);
            }
        },getContext());

        mBrokersShowHideTV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mBrokersShowHideTV.setTextColor(getResources().getColor(R.color.colorRed));
                    mBrokersLayout.setVisibility(View.GONE);
                }else {
                    mBrokersShowHideTV.setTextColor(getResources().getColor(R.color.colorDark));
                    mBrokersLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        mBrokersViewallTV.setOnClickListener(this);
        mPropertiesViewallTV.setOnClickListener(this);
        mSuggestionViewallTV.setOnClickListener(this);
        mProjectsViewallTV.setOnClickListener(this);
        //on menu click nav drawer open
//        mMenuBtnLayoutD2.setOnClickListener(this);
//        //Profile image click listener
//        mProfileImageD2.setOnClickListener(this);
//        //Chat Image Click listener
//        mChatButtonD2.setOnClickListener(this);
        //Create Fab click
        mCreateNewFabButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;

        switch (id){
            case R.id.createfab:
                startActivity(new Intent(getContext(), CreateActivity.class));
                break;
            case R.id.tv_broker_viewall :
                startActivity(new Intent(getContext(), ViewAllBrokersActivity.class));
                break;
            case R.id.tv_viewall_properties :
                startActivity(new Intent(getContext(), ViewAllPropertiesActivity.class));
                break;
            case R.id.tv_viewall_suggestion :
                startActivity(new Intent(getContext(), FollowersSuggestionActivity.class));
                break;
            case R.id.tv_viewall_location :
                startActivity(new Intent(getContext(), MapActivity.class));
                break;
            case R.id.tv_viewall_projects :
                startActivity(new Intent(getContext(), ViewAllProjectsActivity.class));
                break;
            case R.id.tv_viewall_news :
                break;
            case R.id.tv_viewall_jobs :
                break;
        }
    }
}
